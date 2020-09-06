package org.aurora.app;

import org.aurora.core.analyzer.factory.AnalyzerFactory;
import org.aurora.core.synthesis.SynthesisFactory;
import org.aurora.util.fs.AuroraFileManager;
import org.aurora.util.fs.factory.AsmFileFactory;
import org.aurora.util.fs.factory.PathFactory;
import org.aurora.util.parser.ArgumentService;
import org.aurora.util.parser.FlagManager;

/*
 * @project org.aurora
 * @author Gabriel Honda on 20/02/2020
 */
public class Aurora {

    public static void main(String... args) {
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

        var synthesisFactory = new SynthesisFactory(new AuroraFileManager(),
                                                    argumentService.getPathContainer(),
                                                    data
        );
        synthesisFactory.initateSynthesis();
    }
}
