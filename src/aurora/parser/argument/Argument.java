package aurora.parser.args;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/*
 * @project aurora
 * @author Gabriel Honda on 19/02/2020
 */
public class Args {

    /*
    * criar .asm com o mesmo nome do arquivo .au
    * */
    public static File parseArgs(String[] args) {
        List<String> listArgs = Arrays.asList(args);
        if(listArgs.contains("--all")){
            for(Flag f : Flag.values()){
                f.setValue(true);
            }
            listArgs.removeIf(s -> s.equals("--all"));

            File file = parsePath(listArgs.stream()
                                          .filter(arg -> arg.contains(".au"))
                                          .findFirst());
            return file;
        }

    }

    private static File parsePath(Optional<String> path){

    }
}
