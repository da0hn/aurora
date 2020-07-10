package aurora.core.analyzer.semantic.log;


import aurora.core.analyzer.semantic.exception.SemanticException;

import static aurora.util.parser.Flag.SEMANTIC;

/*
 * @project aurora
 * @author Gabriel Honda on 18/03/2020
 */
public class LogSemantic {

    public static void log(String msg) {
        if(SEMANTIC.isActive()) System.out.println(msg);
    }

    public static void error(String err, int line, int column) {
        throw new SemanticException(" at [" + line + ", " + column + "]\n\t" + err);
    }
}
