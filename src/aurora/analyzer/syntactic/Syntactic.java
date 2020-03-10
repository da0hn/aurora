package aurora.analyzer.syntactic;

import aurora.analyzer.lexical.utils.TokenContainer;
import aurora.analyzer.lexical.utils.Tokens;
import aurora.analyzer.syntactic.utils.PopulateService;
import aurora.analyzer.syntactic.utils.SyntacticService;
import aurora.lang.Language;
import aurora.lang.NonTerminal;
import aurora.lang.Terminal;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import static aurora.analyzer.syntactic.log.LogSyntactic.error;
import static aurora.analyzer.syntactic.log.LogSyntactic.log;

/*
 * @project aurora
 * @author Gabriel Honda on 22/02/2020
 */
public class Syntactic {
    private List<List<Language>> stackTable;
    private List<List<Integer>> parseTable;
    private Stack<Language> stack;
    private LinkedList<TokenContainer> tokens;

    public Syntactic() {
        this.stackTable = PopulateService.stackTable();
        this.parseTable = PopulateService.parseTable();
        this.tokens = new LinkedList<>(Tokens.get());
        this.stack = new Stack<>();
    }

    public boolean analyze() {
        System.out.println("--------------------------------------");
        PopulateService.stack(stack);
        while(!stack.isEmpty()) {
            var token = tokens.getFirst().getToken();
            var line = tokens.getFirst().getLine();
            var column = tokens.getFirst().getColumn();

            log("token " + token.getName() + " was selected from tokens.");

            if(stack.peek() instanceof Terminal) {
                if(stack.peek().getIndex() == token.getIndex()) {
                    log("token " + token.getName() + " was erased from tokens.");
                    tokens.removeFirst();
                    log("token " + token.getName() + " was poped of the stack.");
                    stack.pop();
                }
                else {
                    error(stack.peek(), token, line, column);
                }
            }
            else {
                var nonTerminal = stack.peek();
                var index = parseTable.get(nonTerminal.getIndex())
                        .get(token.getIndex());

                if(index < 0) error(nonTerminal, token, line, column);

                var grammar = stackTable.get(index);
                log((stack.peek() instanceof NonTerminal ? "non terminal " : "token ")
                            + stack.peek() + " was poped of the stack.");
                stack.pop();
                for(Language lang : SyntacticService.reverseGrammar(grammar)) {
                    log((stack.peek() instanceof NonTerminal ? "non terminal " : "token ") +
                                stack.peek() + " was pushed to the stack.");
                    stack.push(lang);
                }
            }
        }
        System.out.println("--------------------------------------");
        return stack.isEmpty() && tokens.isEmpty();
    }
}
