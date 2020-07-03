package aurora.app;

import aurora.analyzer.lexical.Lexical;
import aurora.analyzer.semantic.Semantic;
import aurora.analyzer.syntactic.Syntactic;
import aurora.fs.AsmFileFactory;
import aurora.fs.AuroraFileManager;
import aurora.parser.ArgumentService;
import aurora.parser.FlagManager;
import aurora.parser.PathFactory;
import aurora.synthesis.IntermediateCode;

import java.util.List;

/*
 * @project aurora
 * @author Gabriel Honda on 20/02/2020
 */
public class Aurora {

    public static void main(String... args) {
        // TODO: 14/05/2020 Implementar fuzz teste no nucleo do compilador passando uma flag
        ArgumentService argumentService = new ArgumentService(new FlagManager(),
                                                              new PathFactory(new AsmFileFactory()),
                                                              new AuroraFileManager());
        List<String> code =  argumentService.analyze(args);

        new Lexical(code).analyze();
        new Syntactic().analyze();
        var semantic = new Semantic();
        semantic.analyze();
        new IntermediateCode(semantic.getTable(), semantic.getTokens()).build();
    }
}
