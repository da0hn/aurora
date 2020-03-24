package aurora.analyzer.syntactic;

import aurora.analyzer.lexical.utils.TokenContainer;
import aurora.analyzer.lexical.utils.Tokens;
import aurora.lang.Language;
import aurora.lang.NonTerminal;
import aurora.lang.Terminal;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import static aurora.analyzer.syntactic.log.LogSyntactic.error;
import static aurora.analyzer.syntactic.log.LogSyntactic.log;
import static aurora.analyzer.syntactic.utils.PopulateService.*;

/*
 * @project aurora
 * @author Gabriel Honda on 22/02/2020
 */
public class Syntactic {
    private Stack<Language> stack;
    private LinkedList<TokenContainer> tokens;

    public Syntactic() {
        this.tokens = new LinkedList<>(Tokens.get());
        this.stack = initializeStack();
    }

    public void analyze() {
        while(!stack.isEmpty()) {
            var token = tokens.getFirst().getToken();
            var line = tokens.getFirst().getLine();
            var column = tokens.getFirst().getColumn();

            log("token " + "'" + token.getName() + "'" + " was selected from tokens.");

            if(stack.peek() instanceof Terminal) {
                terminalOnPeek(token, line, column);
            }
            else {
                nonTerminalOnPeek(token, line, column);
            }
        }
        log("Syntactic OK!");
        System.out.println("--------------------------------------");
    }

    private void nonTerminalOnPeek(Terminal token, Integer line, Integer column) {
        var nonTerminal = stack.peek();
        var index = parseTable().get(nonTerminal.getIndex())
                .get(token.getIndex());

        if(index < 0) error(nonTerminal, token, line, column);

        var grammar = commandSequenceTable().get(index);
        log((stack.peek() instanceof NonTerminal ? "non terminal " : "token ")
                    + "'" + stack.peek() + "'" + " was poped of the stack.");
        stack.pop();
        reverseGrammar(grammar).forEach(lang -> {
            log((stack.peek() instanceof NonTerminal ? "non terminal " : "token ") +
                        "'" + stack.peek() + "'" + " was pushed to the stack.");
            stack.push(lang);
        });
    }

    private void terminalOnPeek(Terminal token, Integer line, Integer column) {
        if(stack.peek().getIndex() == token.getIndex()) {
            log("token " + "'" + token.getName() + "'" + " was erased from tokens.");
            tokens.removeFirst();
            log("token " + "'" + token.getName() + "'" + " was poped of the stack.");
            stack.pop();
        }
        else {
            error(stack.peek(), token, line, column);
        }
    }

    private List<Language> reverseGrammar(List<Language> grammar) {
        var reverse = new LinkedList<Language>();
        new LinkedList<>(grammar)
                .descendingIterator()
                .forEachRemaining(reverse::add);
        return reverse;
    }
}
