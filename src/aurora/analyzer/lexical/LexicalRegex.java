package aurora.analyzer.lexical;

import aurora.analyzer.lexical.interfaces.AnalyzerService;
import aurora.analyzer.lexical.utils.LogLexical;

import java.util.Collections;
import java.util.List;

import static aurora.analyzer.lexical.interfaces.AnalyzerService.*;

/*
 * @project aurora
 * @author Gabriel Honda on 22/02/2020
 */
public class LexicalRegex {

    LogLexical log;

    public LexicalRegex(LogLexical log) {
        this.log = log;
    }

    public void analyze(List<String> lines){
        for(String line : lines) {
            List<String> analyzedLines = splitBy("\\)")
                    .andThen(splitBy("\\("))
                    .andThen(splitBy("\\s"))
                    .andThen(splitBy(";"))
                    .andThen(splitBy("\""))
                    .andThen(splitBy("\\\\"))
                    .andThen(splitBy("\\+"))
                    .andThen(splitBy("\\-"))
                    .andThen(splitBy("\\*"))
                    .andThen(splitBy("/"))
                    .apply(Collections.singletonList(line));
            System.out.println(analyzedLines);
        }
    }


}
