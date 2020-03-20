package aurora.analyzer.semantic;

import aurora.analyzer.lexical.utils.TokenContainer;
import aurora.analyzer.lexical.utils.Tokens;
import aurora.analyzer.semantic.utils.NameMangling;
import aurora.analyzer.semantic.utils.Scope;

import java.util.*;
import java.util.function.Consumer;

import static aurora.analyzer.semantic.log.LogSemantic.error;
import static aurora.analyzer.semantic.log.LogSemantic.log;
import static aurora.analyzer.semantic.utils.NameMangling.Status.ZERO;
import static aurora.lang.Token.*;

/*
 * @project aurora
 * @author Gabriel Honda on 21/02/2020
 */
public class Semantic {

    private List<TokenContainer> tokens;
    private List<NameMangling> table;
    private Stack<Scope> scopeStack;

    public Semantic() {
        this.tokens = new ArrayList<>(Tokens.get());
        this.table = new ArrayList<>();
        this.scopeStack = new Stack<>();
    }


    public void analyze() {
        var currentScope = 0;

        scopeStack.push(new Scope("_0"));

        log("begin scope " + scopeStack.peek().getLabel() + ".");

        for(var i = 0; i < tokens.size(); i++) {

            var container = tokens.get(i);

            if(VAR.equals(container.getToken())) {
                final var variable = tokens.get(i + 1);
                final var declared = variable.getLexeme();
                final var decoration = declared + scopeStack.peek().getLabel();

                var line = variable.getLine();
                var column = variable.getColumn();

                var notDeclared = table.stream()
                        .map(NameMangling::getDecoration)
                        .noneMatch(decoration::equals);
                if(notDeclared) {
                    table.add(new NameMangling(declared, decoration, line, column, ZERO));
                }
                else {
                    var err = "identifier '" + variable.getLexeme() + "' was already declared.";
                    error(err, line, column);
                }
            }
            else if(IF.equals(container.getToken()) || LOOP.equals(container.getToken())) {
                while(!tokens.get(++i).getLexeme().equals(")")) {
                    if(tokens.get(i).getToken().equals(ID)) {
                        final var id = scopeStack.peek().getLabel();
                        final var decoration = tokens.get(i).getLexeme() + id;
                        var temp = new ArrayList<>(table);
                        Collections.reverse(temp);
                        Optional<String> first = temp.stream().map(NameMangling::getDecoration)
                                .filter(decoration::startsWith)
                                .findFirst();
                        var index = i;
                        first.ifPresentOrElse(label -> tokens.get(index).setLexeme(label), () -> {
                            var err = "identifier '" + tokens.get(index) + "' was not declared.";
                            error(err, container.getLine(), container.getColumn());
                        });
                    }
                }
                beginScope(container);
            }
            else if(ELSE.equals(container.getToken())) {
                endScope(container, stack -> stack.peek().increaseLevel());
                beginScope(container);
            }
            else if(END_LOOP.equals(container.getToken()) || ENDIF.equals(container.getToken())) {
                endScope(container, stack -> stack.peek().increaseLevel());
            }
            else if(CLOSE.equals(container.getToken())) {
                endScope(container, stack -> {return;});
            }
        }
        table.forEach(System.out::println);
    }

    private void beginScope(TokenContainer container) {
        var label = scopeStack.peek().getLabel() + "_" + scopeStack.peek().getLevel();
        scopeStack.push(new Scope(label));
        log("begin scope " + scopeStack.peek().getLabel() + " " + container.print() + ".");
    }

    private void endScope(TokenContainer container, Consumer<Stack<Scope>> action) {
        log("end scope " + scopeStack.peek().getLabel() + " " + container.print() + ".");
        scopeStack.pop();
        action.accept(scopeStack);
    }
}
