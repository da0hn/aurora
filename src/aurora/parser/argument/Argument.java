package aurora.parser.argument;

import aurora.parser.ParsedData;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/*
 * @project aurora
 * @author Gabriel Honda on 19/02/2020
 */
public class Argument {

    /*
    * criar .asm com o mesmo nome do arquivo .au
    * */

    private static final String aurora = ".au";
    private static final String assembly  = ".asm";

    public static ParsedData parseArgs(String[] args) {
        List<String> listArgs = Arrays.asList(args);

        System.out.println(listArgs.toString());

        if(listArgs.contains("--all")){
            for(Flag f : Flag.values()){
                f.setValue(true);
            }
            listArgs.removeIf(s -> s.equals("--all"));
        }
        else {
            for(String arg : args) {
                if(listArgs.contains(arg) && arg.contains("--")) {
                    Flag.getFlag(arg).setValue(true);
                }
            }
        }
        Path input = parseInput(listArgs.stream()
                                .filter(arg -> arg.endsWith(aurora))
                                .findFirst()
                                .orElseThrow(() -> new IllegalArgumentException("Não foi encontrado o arquivo " + aurora))
        );
        Path output = parseOutput(listArgs.stream()
                                .filter(arg -> arg.endsWith(assembly))
                                .findFirst()
                                .orElseThrow(() -> new IllegalStateException("Não foi encontrado o arquivo " + assembly)));
        return new ParsedData(input, output);
    }

    private static Path parseOutput(String output) {
        return Path.of(output);
    }

    private static Path parseInput(String input){
        return Path.of(input);
    }
}
