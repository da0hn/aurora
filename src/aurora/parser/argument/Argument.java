package aurora.parser.argument;

import aurora.parser.ParsedPath;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

/*
 * @project aurora
 * @author Gabriel Honda on 19/02/2020
 */
public class Argument {

    /*
     * criar .asm com o mesmo nome do arquivo .au
     * */
    public static ParsedPath parseArgs(String[] args) {
        final String aurora = ".au";
        final String assembly = ".asm";
        // converte os argumentos para ArrayList para utilizar Stream
        List<String> listArgs = Arrays.asList(args);

        if(listArgs.contains("--all")) {
            // ativa todas as flags
            Stream.of(Flag.values())
                    .forEach(f -> f.setValue(true));
        } else {
            listArgs.stream()
                    // filtra as Strings dentro de listArgs checando se estao contidas dentro
                    // da lista E se comecam com '--' que sinaliza o inicio de uma flag
                    .filter(arg -> listArgs.contains(arg) && arg.startsWith("--"))
                    .forEach(arg -> Flag.getFlag(arg).setValue(true));
        }
        // assume-se que um arquivo aurora deve-se ter a extensao .au,
        // procura dentro da lista uma string que termina com .au
        Path pathAU = Path.of(findFile(listArgs,
                                       arg -> arg.endsWith(aurora),
                                       aurora
        ));
        Path pathASM = Path.of(findFile(listArgs,                       // lista que talvez contenha o caminho do arquivo
                                        arg -> arg.endsWith(assembly),  // teste para encontrar o arquivo
                                        assembly
        ));                     // extensão para a mensagem de erro
        // retorna uma instancia de ParsedPath que possui a informacao do caminho dos arquivos de input e output
        // que serao usados por outras classes
        return new ParsedPath(pathAU, pathASM);
    }

    private static String findFile(List<String> list, Predicate<String> constraint, String ext) {
        return list.stream()
                .filter(constraint)     // executa o metodo de teste
                .findFirst()            // recupera o primeiro resultado que é um Optional
                // se o Optional for nulo lança a exceção
                .orElseThrow(() -> new IllegalArgumentException("Não foi encontrado o arquivo do tipo" + ext));
    }

}
