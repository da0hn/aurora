package aurora.core.analyzer.syntactic;

import aurora.core.analyzer.lexical.utils.TokenContainer;
import aurora.core.lang.Language;
import aurora.core.lang.NonTerminal;
import aurora.core.lang.Terminal;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.function.UnaryOperator;

import static aurora.core.analyzer.syntactic.log.LogSyntactic.error;
import static aurora.core.analyzer.syntactic.log.LogSyntactic.log;
import static aurora.core.analyzer.syntactic.utils.PopulateService.commandSequenceTable;
import static aurora.core.analyzer.syntactic.utils.PopulateService.initializeStack;
import static aurora.core.analyzer.syntactic.utils.PopulateService.parseTable;

/*
 * @project aurora
 * @author Gabriel Honda on 22/02/2020
 */
public class Syntactic {

    /* A lista é composta por objetos de TokenConteiner que possuem internamente um Terminal
     * na hierarquia um Terminal pode ser um Symbol ou um Token
     * além disso, os objetos Terminal e NonTerminal implementam a interface Language
     */
    private Stack<Language> stack;

    public Syntactic() {

        // Inicializa a pilha com um $ (Terminal) e AURORA (NonTerminal) nessa ordem
        this.stack = initializeStack();
    }

    public void analyze(List<TokenContainer> generatedTokens) {
        // Cria uma cópia da lista de tokens vinda do Léxico para alteração
        // A instancia de LinkedList é utilizada para que a lista se comporte como uma fila
        LinkedList<TokenContainer> tokens = new LinkedList<>(generatedTokens);
        /* Percorre a pilha e a fila comparando os objetos no topo/primeiro da fila de ambos
         * depois analisa a instancia e executa o método correspondente
         */
        while(!stack.isEmpty()) {
            // recupera os dados do primeiro elemento da fila de tokens
            var token = tokens.getFirst().getToken();   // Uma instancia de Terminal
            var line = tokens.getFirst().getLine();
            var column = tokens.getFirst().getColumn();

            log("token " + "'" + token.getName() + "'" + " was selected from tokens.");

            if(stack.peek() instanceof Terminal) {
                terminalOnPeek(tokens, token, line, column);
            }
            else {
                nonTerminalOnPeek(token, line, column, grammar -> {
                    // cria uma cópia da lista recebida como argumento invertendo
                    // a ordem do seu conteudo
                    var reverse = new LinkedList<Language>();
                    new LinkedList<>(grammar)
                            .descendingIterator()
                            .forEachRemaining(reverse::add);
                    return reverse;
                });
            }
        }
//        log("Syntactic OK!");
        System.out.println("Syntactic OK");
//        System.out.println("--------------------------------------");
    }

    private void nonTerminalOnPeek(Terminal token, Integer line,
            Integer column,
            UnaryOperator<List<Language>> reverse) {
        // Recupera um NonTerminal da pilha
        var nonTerminal = stack.peek();
        /* Usa o indice do nonTerminal combinado com o do token (Terminal) na matriz de indices
         * Essa matriz possui o indice da sequencia de comandos que deve ser inserida na pilha de
         *  NonTerminal
         */
        var index = parseTable().get(nonTerminal.getIndex())
                .get(token.getIndex());
        // Se o indice for negativo, não é uma combinação de Terminal e NonTerminal válida
        if(index < 0) error(nonTerminal, token, line, column);

        // Uma lista com a sequencia de comandos
        var grammar = commandSequenceTable().get(index);
        log((stack.peek() instanceof NonTerminal ? "non terminal " : "token ")
                + "'" + stack.peek() + "'" + " was poped of the stack.");
        // Retira o NonTerminal anterior que gerou a nova sequencia de comandos
        stack.pop();
        // Aplica o UnaryOperator que inverte a lista
        reverse.apply(grammar).forEach(lang -> {
            log((stack.peek() instanceof NonTerminal ? "non terminal " : "token ") +
                    "'" + stack.peek() + "'" + " was pushed to the stack.");
            stack.push(lang);
        });
    }

    private void terminalOnPeek(LinkedList<TokenContainer> tokens, Terminal token, Integer line,
            Integer column) {
        /* A igualdade entre dois objetos do tipo Terminal é definida por seu index
         * Se o indice for igual são removidos ambos objetos do topo da pila / primeiro da fila
         */
        if(stack.peek().getIndex() == token.getIndex()) {
            log("token " + "'" + token.getName() + "'" + " was erased from tokens.");
            tokens.removeFirst();
            log("token " + "'" + token.getName() + "'" + " was poped of the stack.");
            stack.pop();
        }
        else {
            /* Se a igualdade não for verdadeira termina o programa,
             * por que o código fonte analisado não está correto
             */
            error(stack.peek(), token, line, column);
        }
    }
}
