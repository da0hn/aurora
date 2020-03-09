package aurora.analyzer.syntactic;

import aurora.analyzer.lexical.utils.TokenContainer;
import aurora.analyzer.lexical.utils.Tokens;
import aurora.analyzer.syntactic.utils.PopulateService;
import aurora.lang.Language;
import aurora.lang.NonTerminal;
import aurora.lang.Terminal;
import aurora.lang.Token;

import java.util.*;

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
        PopulateService.stack(stack);
    }

    public void analyze() {
        while(!stack.isEmpty()) {
            var token = tokens.get(0).getToken();
            var line = tokens.get(0).getLine();
            var column = tokens.get(0).getColumn();

            // TODO: log

            if(stack.peek() instanceof Terminal) {
                if(stack.peek().getIndex() == token.getIndex()) {
                    // TODO: log
                    tokens.removeFirst();
                    // TODO: log
                    stack.pop();
                }
                else {
                    throw new IllegalStateException("xesquedele");
                }
            }
            else {
                var nonTerminal = stack.peek();

                System.out.println(nonTerminal.getIndex());
                System.out.println(token.getIndex());

                var index = parseTable.get(nonTerminal.getIndex())
                                        .get(token.getIndex());
                if( index < 0) {
                    throw new IllegalStateException("xesquedele");
                }
                var grammar = stackTable.get(index);
                Collections.reverse(grammar);
                // log
                // log

                stack.pop();
                for(Language lang : grammar) {
//                    if(!lang.getName().equals("î")){
                        stack.push(lang);
//                    }
                }
            }
        }
    }
}
