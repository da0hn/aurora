package aurora.core.analyzer.syntactic.log;

import aurora.core.analyzer.syntactic.exception.SyntacticException;
import aurora.core.lang.Language;
import aurora.core.lang.Terminal;

import static aurora.util.parser.Flag.SYNTACTIC;

/*
 * @project aurora
 * @author Gabriel Honda on 09/03/2020
 */
public class LogSyntactic {

    public static void log(String msg) {
        if(SYNTACTIC.isActive()) {
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
