package aurora.analyzer.lexical.log;

import aurora.analyzer.lexical.exception.LexicalException;
import aurora.analyzer.lexical.utils.TokenContainer;
import aurora.parser.Flag;

/*
 * @project aurora
 * @author Gabriel Honda on 23/02/2020
 */
public class LogLexical {

    public static void message(String msg) {
        System.out.println(msg);
    }

    public static void message(TokenContainer tk) {
        if(Flag.TOKENS.getValue()) {
            System.out.println(tk.getToken() + " at [" + tk.getLine()
                                       + ", " + tk.getColumn() + "]" +
                                       ": token='" + tk.getLexeme() + '\'');
        }
    }

    public static void error(String err, int line, int column) {
        throw new LexicalException("Error at [" + line
                                           + ", " + column + "]: " + err);
    }
}
