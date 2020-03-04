package aurora.analyzer.lexical.utils;

import aurora.analyzer.lexical.exception.LexicalException;

/*
 * @project aurora
 * @author Gabriel Honda on 23/02/2020
 */
public class LogLexical {

    public static void message(TokenContainer tk) {
        System.out.println(tk.getToken() + " at [" + tk.getLine() + ", " + tk.getColumn() + "]" +
                                   ": token='" + tk.getLexeme() + '\'');
    }

    public static void error(String err, int line, int column) {
        throw new LexicalException("Error at [" + line + ", " + column + "]: " + err);
    }
}
