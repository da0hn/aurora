package aurora.analyzer.lexical.utils;

import aurora.analyzer.lexical.exception.LexicalException;
import aurora.log.Log;

/*
 * @project aurora
 * @author Gabriel Honda on 23/02/2020
 */
public class LogLexical implements Log {
    @Override
    public void message(String msg, int line, int column) {
        System.out.println("["+line + ", "+ column +"]: "+ msg );
    }

    @Override
    public void error(String err, int line, int column) {
        System.err.println("["+line+", "+column+"]: " + err);
        //        throw new LexicalException();
    }
}
