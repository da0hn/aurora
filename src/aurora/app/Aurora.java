package aurora.app;

import aurora.analyzer.lexical.Lexical;
import aurora.analyzer.syntactic.Syntactic;
import aurora.files.FileManager;
import aurora.parser.Argument;
import aurora.parser.PathContainer;

import java.util.Arrays;
import java.util.List;

/*
 * @project aurora
 * @author Gabriel Honda on 20/02/2020
 */
public class Aurora {

    public static void main(String[] args) {
        PathContainer data = Argument.parseArgs(args);

        FileManager manager = new FileManager(data);
        List<String> code = manager.readLinesAuroraFile();
        var lex = new Lexical();
        lex.analyze(code);

        var syn = new Syntactic();
        syn.analyze();
    }

}
