package aurora.analyzer.lexical.utils;

import aurora.analyzer.lexical.enums.IToken;
import aurora.analyzer.lexical.exception.LexicalException;
import aurora.log.Log;

/*
 * @project aurora
 * @author Gabriel Honda on 23/02/2020
 */
public class LogLexical {

    public void message(TokenContainer tk) {
        System.out.println( tk.getToken() + " at [" + tk.getLine() + ", " + tk.getColumn() + "]" +
                                   ": lexeme='" + tk.getLexeme() + '\'');
    }

    public void error(String err, int line, int column) {
        System.err.println("Error at ["+line+", "+column+"]: " + err);
        //        throw new LexicalException();
    }
}
