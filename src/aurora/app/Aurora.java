package aurora.app;

import aurora.analyzer.lexical.Lexical;
import aurora.files.FileManager;
import aurora.parser.PathContainer;
import aurora.parser.Argument;
import aurora.parser.Flag;

import java.util.List;

/*
 * @project aurora
 * @author Gabriel Honda on 20/02/2020
 */
public class Aurora {

    public static void main(String[] args) {
        PathContainer data = Argument.parseArgs(args);

       for(Flag f : Flag.values()) {
           System.out.println(f);
       }

        FileManager manager = new FileManager(data);
        List<String> code = manager.readLinesAuroraFile();

        for(String line : code) {
            System.out.println(line);
        }

        Lexical lex = new Lexical();
        lex.analyze(code);
        System.out.println(lex.getTokens());
    }

}
