package aurora.parser;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/*
 * @project aurora
 * @author Gabriel Honda on 22/04/2020
 */
public interface IFlagManager {
    default void activateFlags(List<String> argumentList) {
        // converte os argumentos para ArrayList para utilizar Stream
        // analisa se os argumentos possuem alguma flag e as ativam para serem utilizadas
        // em outras classes
        if(argumentList.contains("--all")) {
            // ativa todas as flags
            System.out.println("--all");
            System.out.println("Ativando todas as flags");
            Stream.of(Flag.values())
                    .forEach(f -> f.setValue(true));
        }
        else {
            argumentList.stream()
                    // filtra as Strings dentro de listArgs checando se estao contidas dentro
                    // da lista E se comecam com '--' que sinaliza o inicio de uma flag
                    .filter(arg -> argumentList.contains(arg) && arg.startsWith("--"))
                    .forEach(arg -> {
                        System.out.println("Flag ativa: " + arg);
                        Flag.getFlag(arg).setValue(true);
                    });
        }
    }
}
