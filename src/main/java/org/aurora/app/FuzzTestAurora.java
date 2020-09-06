package org.aurora.app;

import org.aurora.core.analyzer.factory.AnalyzerFactory;
import org.aurora.core.analyzer.lexical.Lexical;
import org.aurora.core.analyzer.lexical.utils.Tokens;
import org.aurora.core.analyzer.semantic.Semantic;
import org.aurora.core.analyzer.syntactic.Syntactic;
import org.aurora.core.synthesis.SynthesisFactory;
import org.aurora.util.fs.AuroraFileManager;
import org.aurora.util.fs.factory.AsmFileFactory;
import org.aurora.util.fs.factory.PathFactory;
import org.aurora.util.parser.ArgumentService;
import org.aurora.util.parser.FlagManager;
import org.aurora.util.parser.IPathFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
 * @project org.aurora
 * @author Gabriel Honda on 14/05/2020
 */
public class FuzzTestAurora {
    // fase de testes, não utilizar
    public static void main(String[] args) {
        try(Stream<Path> walk = Files.walk(Paths.get("/home/daohn/Documentos/code/aurora" +
                                                             "/resources/src"))) {
            List<String> result =
                    walk.map(Path::toString).filter(obj -> obj.contains(".au")).collect(Collectors.toList());
            result.forEach(auFile -> {
                var argumentService = new ArgumentService(new FlagManager(),
                                                          new PathFactory(new AsmFileFactory()),
                                                          new AuroraFileManager()
                );

                var code = argumentService.analyze(new String[]{auFile});
                System.out.println("-----------------------");
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
                System.out.println("-----------------------");
                Tokens.reset();
            });
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }
}
