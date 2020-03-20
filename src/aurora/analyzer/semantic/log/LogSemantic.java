package aurora.analyzer.semantic.log;


import aurora.analyzer.semantic.exception.SemanticException;

/*
 * @project aurora
 * @author Gabriel Honda on 18/03/2020
 */
public class LogSemantic {

    public static void log(String msg) {
        System.out.println(msg);
    }
    public static void error(String err, int line, int column) {
        throw new SemanticException(" at ["+line+", " + column +"]\n\t"+ err);
    }
}
