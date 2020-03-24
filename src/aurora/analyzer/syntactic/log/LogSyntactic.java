package aurora.analyzer.syntactic.log;

import aurora.analyzer.syntactic.exception.SyntacticException;
import aurora.lang.Language;
import aurora.lang.Terminal;
import aurora.parser.Flag;

/*
 * @project aurora
 * @author Gabriel Honda on 09/03/2020
 */
public class LogSyntactic {

    public static void log(String msg) {
        if(Flag.SYNTACTIC.getValue()) {
            System.out.println(msg);
        }
    }

    public static void error(Language obj, Terminal token, int line, int column) {
        var builder = new StringBuilder();
        builder.append("at [").append(line).append(", ").append(column).append("]").append("\n");
        builder.append("\t").append("'").append(obj.getName()).append("'").append(" was expected, but ")
            .append("'").append(token.getName()).append("'").append(" was found.").append("\n");
        builder.append("\t").append("compilation terminated\n");
        throw new SyntacticException(builder.toString());
    }

}
