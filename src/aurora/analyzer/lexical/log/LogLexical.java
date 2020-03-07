package aurora.analyzer.lexical.log;

import aurora.analyzer.lexical.exception.LexicalException;
import aurora.analyzer.lexical.utils.TokenContainer;
import aurora.parser.Flag;

import java.util.LinkedList;
import java.util.Queue;

/*
 * @project aurora
 * @author Gabriel Honda on 23/02/2020
 */
public class LogLexical {
    private static Queue<TokenContainer> queueLog;

    static {
        queueLog = new LinkedList<>();
    }

    public static void add(TokenContainer tk) {
        queueLog.add(tk);
    }

    public static void log() {
        try {
            if(Flag.TOKENS.getValue()) {
                System.out.println("--------------------------------------");
                for(TokenContainer tk : queueLog) {
                    System.out.println(tk.getToken() + " at [" + tk.getLine()
                                               + ", " + tk.getColumn() + "]" +
                                               ": '" + tk.getLexeme() + '\'');
                    if(Flag.READABLE.getValue()) {
                        Thread.sleep(400);
                    }
                }
                System.out.println("--------------------------------------");
            }
        }
        catch(InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void error(String err, int line, int column) {
        throw new LexicalException("Error at [" + line
                                           + ", " + column + "]: " + err);
    }
}
