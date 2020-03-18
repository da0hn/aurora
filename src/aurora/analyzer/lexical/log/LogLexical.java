package aurora.analyzer.lexical.log;

import aurora.analyzer.lexical.exception.LexicalException;
import aurora.analyzer.lexical.interfaces.LexicalObject;
import aurora.analyzer.lexical.utils.ErrorMessage;
import aurora.analyzer.lexical.utils.TokenContainer;
import aurora.analyzer.lexical.utils.TokenContainer1;
import aurora.parser.Flag;

import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Collectors;

/*
 * @project aurora
 * @author Gabriel Honda on 23/02/2020
 */
public class LogLexical {
    private static Queue<LexicalObject> queueLog;

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

        if(Flag.TOKENS.getValue()) {
            System.out.println("--------------------------------------");
            executeLog();
            System.out.println("--------------------------------------");
        }
    }

    private static void executeLog() {
        try {
            for(LexicalObject obj : queueLog) {
                System.out.println(obj.print());
                if(Flag.READABLE.getValue()) {
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
}
