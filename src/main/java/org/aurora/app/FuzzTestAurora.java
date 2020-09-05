package org.aurora.app;

/*
 * @project org.aurora
 * @author Gabriel Honda on 14/05/2020
 */
public class FuzzTestAurora {
    // fase de testes, não utilizar
    public static void main(String[] args) {
//        try(Stream<Path> walk = Files.walk(Paths.get("C:\\_CODE\\org.aurora\\resources\\src"))) {
//            List<String> result =
//                    walk.map(Path::toString).filter(obj -> obj.contains(".au")).collect(Collectors.toList());
//            result.forEach(auFile -> {
//                ArgumentService argumentService = new ArgumentService(new FlagManager(),
//                                                                      new PathFactory(new AsmFileFactory()),
//                                                                      new AuroraFileManager());
//                List<String> code =  argumentService.analyze(new String[]{auFile});
//
//                System.out.println("-----------------------");
//                new Lexical(code).analyze();
//                new Syntactic().analyze();
//                new Semantic().analyze();
//                System.out.println("-----------------------");
//                Tokens.reset();
//            });
//        }
//        catch(IOException e) {
//            e.printStackTrace();
//        }
    }
}
