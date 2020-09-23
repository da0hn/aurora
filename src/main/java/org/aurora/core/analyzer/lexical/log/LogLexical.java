package org.aurora.core.analyzer.lexical.log;

import org.aurora.core.analyzer.lexical.exception.LexicalException;
import org.aurora.core.analyzer.lexical.interfaces.LexicalObject;
import org.aurora.core.analyzer.lexical.utils.ErrorMessage;
import org.aurora.core.analyzer.lexical.utils.TokenContainer;

import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Collectors;

import static org.aurora.util.parser.Flag.READABLE;
import static org.aurora.util.parser.Flag.TOKENS;

/**
 * @author Gabriel Honda on 23/02/2020
 * @project org.aurora
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
        if(queueLog.stream().anyMatch(ErrorMessage.class::isInstance)) {
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
            System.out.printf("%6s  %3s  %12s%9s\t%10s%n", "i:j", separator,
                              "TOKENS", separator, "LEXEME"
            );
            System.out.println("=".repeat(125));
            for(LexicalObject obj : queueLog) {
                System.out.println(print((TokenContainer) obj));
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
                .filter(ErrorMessage.class::isInstance)
                //                .filter(obj -> obj instanceof ErrorMessage)
                .collect(Collectors.toList());
        extractedErrors.forEach(obj -> {
            err.append("\t").append(obj.print()).append("\n");
        });

        throw new LexicalException(err.toString());
    }

    private static String print(TokenContainer obj) {
        var separator = "|";
        return String.format(" %3d:%3d %2s  %-17s%4s\t%-20s\t", obj.getLine(),
                             obj.getColumn(), separator, obj.getToken(), separator, obj.getLexeme()
        );
    }
}
