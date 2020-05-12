package aurora.app;

import aurora.analyzer.lexical.Lexical;
import aurora.analyzer.semantic.Semantic;
import aurora.analyzer.syntactic.Syntactic;
import aurora.fs.AsmFileFactory;
import aurora.fs.AuroraFileManager;
import aurora.parser.ArgumentService;
import aurora.parser.FlagManager;
import aurora.parser.PathFactory;

import java.util.List;

/*
 * @project aurora
 * @author Gabriel Honda on 20/02/2020
 */
public class Aurora {

    public static void main(String... args) {
        ArgumentService argumentService = new ArgumentService(new FlagManager(),
                                                              new PathFactory(new AsmFileFactory()),
                                                              new AuroraFileManager());
        List<String> code =  argumentService.analyze(args);

        new Lexical().analyze(code);
        new Syntactic().analyze();
        new Semantic().analyze();
    }
}
