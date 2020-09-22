package org.aurora.core.analyzer.semantic;

import org.aurora.core.analyzer.IAnalyzer;
import org.aurora.core.analyzer.lexical.utils.TokenContainer;
import org.aurora.core.analyzer.semantic.utils.NameMangling;
import org.aurora.core.analyzer.semantic.utils.Scope;
import org.aurora.core.analyzer.semantic.utils.SemanticData;
import org.aurora.core.analyzer.utils.PredicateService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;

import static org.aurora.util.Color.*;
import static org.aurora.core.analyzer.semantic.log.LogSemantic.error;
import static org.aurora.core.analyzer.semantic.log.LogSemantic.log;
import static org.aurora.core.analyzer.semantic.utils.NameMangling.Status.NON_ZERO;
import static org.aurora.core.analyzer.semantic.utils.NameMangling.Status.ZERO;
import static org.aurora.core.lang.Symbol.EQUALS;
import static org.aurora.core.lang.Symbol.SEMICOLON;
import static org.aurora.core.lang.Token.*;

/*
 * @project org.aurora
 * @author Gabriel Honda on 21/02/2020
 */
public class Semantic implements IAnalyzer {

    // lista que possui os escopos do programa
    private final Stack<Scope> scopeStack;
    private SemanticData semanticData;

    public Semantic() {
        this.scopeStack = new Stack<>();
    }

    public SemanticData getSemanticData() {
        return semanticData;
    }

    @Override
    public void analyze(List<TokenContainer> generatedTokens) {
        // cria uma copia da lista de tokens que ira ser alterada durante a analise
        List<TokenContainer> tokens = new ArrayList<>(generatedTokens);
        // NameMangling possui o enumerado Status que define se o valor da variavel é ou não zero.
        List<NameMangling> table = new ArrayList<>();

        this.semanticData = new SemanticData(tokens, table);

        // a utilizacao do AtomicInteger e necessaria para a passagem de referencia do valor inteiro
        // alem disso, possui metodos de auxiliares para incremento
        var index = new AtomicInteger(0);

        // escopo inicial (org.aurora::init/org.aurora::close)
        scopeStack.push(new Scope("_0"));
        log(GREEN + "begin scope " + scopeStack.peek().getLabel() + " " + tokens.get(0).print() +
                    "." + RESET);

        // percorre a lista de tokens utilizando o index como indice
        while(index.get() < tokens.size()) {

            // armazena o token atual na variavel container
            // a lista de TokenContainer possui os
            // seguintes atributos nessa ordem
            // line, column, token, lexeme
            var container = tokens.get(index.get());

            if(VAR.equals(container.getToken())) {
                // se um token VAR (au::var) for encontrado
                // o proximo token e uma variavel
                declarationProcedure(tokens, table, index);
            }
            else if(IF.equals(container.getToken()) || LOOP.equals(container.getToken())) {
                // o tratamento de IF e LOOP são parecidos
                ifOrLoopProcedure(tokens, table, index, container);
            }
            // else, endloop e close possuem semelhanças, porém executam procedimentos secundarios
            // que é passado na Runnable como parametro
            else if(ELSE.equals(container.getToken())) {
                // fecha o escopo anterior e inicia o escopo do else
                closeScope(container, () -> scopeStack.peek().increaseLevel());
                initScope(container);
            }
            else if(END_LOOP.equals(container.getToken()) || ENDIF.equals(container.getToken())) {
                // fecha o escopo do loop
                closeScope(container, () -> this.scopeStack.peek().increaseLevel());
            }
            else if(CLOSE.equals(container.getToken())) {
                // fecha o escopo principal
                closeScope(container, () -> {});
            }
            else if(READ.equals(container.getToken())) {
                var readValue = tokens.get(index.addAndGet(2));
                if(ID.equals(readValue.getToken())) {
                    var id = scopeStack.peek().getLabel();
                    Optional<NameMangling> label = findPreviousScope(table, readValue.getLexeme() + id);
                    label.ifPresent(obj -> {
                        obj.setStatus(NON_ZERO);
                        tokens.get(index.get()).setLexeme(obj.getDecoration());
                    });
                }
            }
            else if(ID.equals(container.getToken())) {
                identifierProcedure(tokens, table, index, container);
            }
            index.getAndIncrement();
        }
        table.forEach(System.out::println);
    }

    private void identifierProcedure(List<TokenContainer> tokens, List<NameMangling> table,
            AtomicInteger index,
            TokenContainer container) {
        // checa se a variavel foi declarada
        varDeclaredProcedure(tokens, table, index, container);
        index.getAndIncrement();
        // checa se é uma expressao (possui ' = ')
        if(EQUALS.equals(tokens.get(index.get()).getToken())) {
            // armazena o escopo atual com a label '_escopo'
            var id = scopeStack.peek().getLabel();

            Optional<NameMangling> label = findPreviousScope(table, container.getLexeme() + id);
            index.getAndIncrement();
            var basicExpression = new StringBuffer();

            expressionProcedure(tokens, table, index, container, basicExpression);

            label.ifPresentOrElse(obj -> {
                if("0".equals(basicExpression.toString())) {
                    obj.setStatus(ZERO);
                }
                else if(basicExpression.toString().contains("/0")) {
                    error("division by zero", container.getLine(), container.getColumn());
                }
                else {
                    obj.setStatus(NON_ZERO);
                }
                log(PURPLE + "|\tassign: " + obj.getDecoration() + ", value: " + basicExpression + RESET);
            }, () -> {
                var err = "identifier '" + tokens.get(index.get()) + "' was not declared.";
                error(err, container.getLine(), container.getColumn());
            });
        }
    }

    private void expressionProcedure(List<TokenContainer> tokens,
            List<NameMangling> table, AtomicInteger index,
            TokenContainer container,
            StringBuffer basicExpression) {
        // percorre enquanto não encontrar ';'
        // builda a expressão com o valor de cada variavel
        // que estiver contida nela
        // EX: var = a + b - 5/2;
        // verifica se a variavel já foi declarada no escopo anterior
        // assina-la o valor de 'a' e 'b'
        while(!SEMICOLON.equals(tokens.get(index.get()).getToken())) {
            var value = tokens.get(index.get()).getLexeme();

            if(PredicateService.isIdentifier(value)) {
                Optional<NameMangling> obj =
                        findPreviousScope(table, value + scopeStack.peek().getLabel());
                obj.ifPresentOrElse(var -> {
                    // adiciona o valor da variavel no builder
                    // no lugar do nome dela
                    basicExpression.append(ZERO.equals(var.getStatus()) ? "0" :
                            var.getDeclared());
                    tokens.get(index.get()).setLexeme(var.getDecoration());
                }, () -> {
                    var err = "identifier '" + tokens.get(index.get()) + "' was not declared.";
                    error(err, container.getLine(), container.getColumn());
                });
            }
            else {
                // se não for uma variavel, é um numero adiciona na expressao do mesmo jeito
                basicExpression.append(value);
            }
            index.getAndIncrement();
        }
    }

    private void declarationProcedure(List<TokenContainer> tokens, List<NameMangling> table,
            AtomicInteger index) {
        // acessa a variavel e armazena
        var variable = tokens.get(index.get() + 1);

        // gera a decoracao que sera utilizada para diferenciar o escopo
        var declared = variable.getLexeme();
        var decoration = declared + scopeStack.peek().getLabel();

        var line = variable.getLine();
        var column = variable.getColumn();

        // checa atraves da decoracao se aquela variavel foi declarada no escopo
        // se encontrar uma variavel com o mesmo nome e mesmo escopo (decoration)
        // 'notDeclared' assume falso caso contrário verdadeiro
        boolean notDeclared = table.stream()
                .map(NameMangling::getDecoration)
                .noneMatch(decoration::equals);
        if(notDeclared) {
            // como a variavel não foi declarada ela é inserida na table
            table.add(new NameMangling(declared, decoration, line, column, ZERO));
            log(CYAN + "The identifier " + decoration + " has been declared" + RESET);
        }
        else {
            var err = "identifier '" + variable.getLexeme() + "' was already declared.";
            // lança exceção
            error(err, line, column);
        }
    }

    private void ifOrLoopProcedure(List<TokenContainer> tokens, List<NameMangling> table,
            AtomicInteger index,
            TokenContainer container) {
        // percorre a lista de tokens até encontrar o fim do if/for
        while(!tokens.get(index.incrementAndGet()).getLexeme().equals(")")) {
            // checa se a variavel foi declarada
            varDeclaredProcedure(tokens, table, index, container);
        }
        // inicia o escopo do if/for
        initScope(container);
    }

    private void varDeclaredProcedure(List<TokenContainer> tokens, List<NameMangling> table,
            AtomicInteger index,
            TokenContainer container) {
        // se for uma variavel
        if(tokens.get(index.get()).getToken().equals(ID)) {
            // gera uma decoracao para a variavel encontrada
            final var level = scopeStack.peek().getLabel();
            final var decoration = tokens.get(index.get()).getLexeme() + level;

            Optional<NameMangling> obj = findPreviousScope(table, decoration);
            obj.ifPresentOrElse(_obj -> {
                // altera a lista de tokens setando o lexema com a decoração
                tokens.get(index.get()).setLexeme(_obj.getDecoration());
            }, () -> {
                // Runnable, lança uma SemanticException
                var err = "identifier '" + tokens.get(index.get()) + "' was not declared.";
                error(err, container.getLine(), container.getColumn());
            });
        }
    }

    private Optional<NameMangling> findPreviousScope(List<NameMangling> table, String decoration) {
        // cria uma copia inversa temporaria
        var arr = new ArrayList<>(table);
        Collections.reverse(arr);
        // decoration = variavelDeclarada + escopo
        // compara com a variavel declarada contida no NameMangling
        // se ela for encontrada retorna um Optional
        return arr.stream()
                // nameMangling.getDecoration()
                .filter(nameMangling -> decoration.startsWith(nameMangling.getDeclared()))
                .findFirst();
    }

    private void initScope(TokenContainer container) {
        // cria o label que junto com a variavel formara seu escopo,
        // nomeDaVariavel_level_level_level...
        var label = scopeStack.peek().getLabel() + "_" + scopeStack.peek().getLevel();
        // insere o novo escopo na pilha
        scopeStack.push(new Scope(label));
        log(GREEN + "begin scope " + scopeStack.peek().getLabel() + " " + container.print() + "." + RESET);
    }

    private void closeScope(TokenContainer container, Runnable action) {
        log(YELLOW + "end scope " + scopeStack.peek().getLabel() + " " + container.print() + "." + RESET);
        // retira o escopo da pilha
        scopeStack.pop();
        // executa o procedimento
        action.run();
    }
}
