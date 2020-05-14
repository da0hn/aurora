package aurora.app;

import aurora.analyzer.lexical.Lexical;
import aurora.analyzer.lexical.utils.Tokens;
import aurora.analyzer.semantic.Semantic;
import aurora.analyzer.syntactic.Syntactic;
import aurora.fs.AsmFileFactory;
import aurora.fs.AuroraFileManager;
import aurora.parser.ArgumentService;
import aurora.parser.FlagManager;
import aurora.parser.PathFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.asList;

/*
 * @project aurora
 * @author Gabriel Honda on 14/05/2020
 */
public class FuzzTestAurora {
    public static void main(String[] args) {
        try(Stream<Path> walk = Files.walk(Paths.get("C:\\_CODE\\aurora\\resources\\src"))) {
            List<String> result =
                    walk.map(Path::toString).filter(obj -> obj.contains(".au")).collect(Collectors.toList());
            result.forEach(auFile -> {
                ArgumentService argumentService = new ArgumentService(new FlagManager(),
                                                                      new PathFactory(new AsmFileFactory()),
                                                                      new AuroraFileManager());
                List<String> code =  argumentService.analyze(new String[]{auFile});

                new Lexical(code).analyze();
                new Syntactic().analyze();
                new Semantic().analyze();
                Tokens.reset();
            });
        }
        catch(IOException e) {
            e.printStackTrace();
        }

    }
}
