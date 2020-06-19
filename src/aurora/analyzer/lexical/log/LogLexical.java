package aurora.analyzer.lexical.log;

import aurora.analyzer.lexical.exception.LexicalException;
import aurora.analyzer.lexical.interfaces.LexicalObject;
import aurora.analyzer.lexical.utils.ErrorMessage;
import aurora.analyzer.lexical.utils.TokenContainer;

import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Collectors;

import static aurora.parser.Flag.READABLE;
import static aurora.parser.Flag.TOKENS;

/**
 * @project aurora
 * @author Gabriel Honda on 23/02/2020
 */
public class LogLexical {
    private final static Queue<LexicalObject> queueLog;

    static {
        queueLog = new LinkedList<>();
    }

    public static void add(TokenContainer tk) {
        queueLog.add(tk);
    }

    public static void log() {
        if(queueLog.stream().anyMatch(t -> t instanceof ErrorMessage)) {
            foundError();
        }

        if(TOKENS.isActive()) {
            System.out.println("=".repeat(125));
            executeLog();
            System.out.println("=".repeat(125));
        }
    }

    private static void executeLog() {
        try {
            var separator = "|";
            System.out.println(String.format("%6s  %3s  %12s%9s\t%10s", "i:j", separator,
                    "TOKENS", separator, "LEXEME"));
            System.out.println("=".repeat(125));
            for(LexicalObject obj : queueLog) {
                System.out.println(print((TokenContainer)obj));
                if(READABLE.isActive()) {
                    Thread.sleep(400);
                }
            }
        }
        catch(InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void error(String err, int line, int column) {
        queueLog.add(new ErrorMessage(err, line, column));
    }

    private static void foundError() {
        var err = new StringBuilder();
        err.append('\n');
        var extractedErrors = queueLog.stream()
                .filter(obj -> obj instanceof ErrorMessage)
                .collect(Collectors.toList());
        extractedErrors.forEach(obj -> {
            err.append("\t").append(obj.print()).append("\n");
        });

        throw new LexicalException(err.toString());
    }

    private static String print(TokenContainer obj) {
        var separator = "|";
        return String.format(" %3d:%3d %2s  %-17s%4s\t%-20s\t", obj.getLine(),
                obj.getColumn(), separator, obj.getToken(), separator,obj.getLexeme()
        );
    }
}
