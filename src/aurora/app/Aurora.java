package aurora.app;

import aurora.core.analyzer.factory.AnalyzerFactory;
import aurora.core.synthesis.IntermediateCode;
import aurora.util.fs.AuroraFileManager;
import aurora.util.fs.factory.AsmFileFactory;
import aurora.util.fs.factory.PathFactory;
import aurora.util.parser.ArgumentService;
import aurora.util.parser.Flag;
import aurora.util.parser.FlagManager;

import java.util.Arrays;

/*
 * @project aurora
 * @author Gabriel Honda on 20/02/2020
 */
public class Aurora {

    public static void main(String... args) {
        // TODO: 14/05/2020 Implementar fuzz teste no nucleo do compilador passando uma flag
        var argumentService = new ArgumentService(new FlagManager(),
                                                  new PathFactory(new AsmFileFactory()),
                                                  new AuroraFileManager()
        );
        var code = argumentService.analyze(args);
        var data = new AnalyzerFactory(code)
                .initializeLexicalAnalysis()
                .initializeSyntacticAnalysis()
                .initializeSemanticAnalysis()
                .getGeneratedData();
        var finalCode = new IntermediateCode(data).build();
        if(Flag.FINAL_CODE.isActive()) {
            finalCode.forEach(f -> System.out.println("\t" + f));
        }
    }
}
