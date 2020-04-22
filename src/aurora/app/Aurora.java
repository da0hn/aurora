package aurora.app;

import aurora.analyzer.lexical.Lexical;
import aurora.analyzer.semantic.Semantic;
import aurora.analyzer.syntactic.Syntactic;
import aurora.fs.FileManager;
import aurora.parser.Argument;

/*
 * @project aurora
 * @author Gabriel Honda on 20/02/2020
 */
public class Aurora {

    public static void main(String[] args) {
        new Lexical().analyze(new FileManager(Argument.parseArgs(args))
                                      .readLinesAuroraFile());
        new Syntactic().analyze();
        new Semantic().analyze();
    }

}
