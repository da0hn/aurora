package aurora.parser;

import aurora.log.Logger;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
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
            Logger.log("Flag '--all' localizada");
            Logger.log("Ativando todas as flags");
            Stream.of(Flag.values())
                    .forEach(Flag::activate);
        }
        else {
            argumentList.stream()
                    // filtra as Strings dentro de listArgs checando se estao contidas dentro
                    // da lista E se comecam com '--' que sinaliza o inicio de uma flag
                    .filter(arg -> argumentList.contains(arg) && arg.startsWith("--"))
                    .forEach(arg -> Flag.getFlag(arg).activate());
            final var builder = new StringBuilder();
            Arrays.stream(Flag.values())
                    .filter(Flag::isActive)
                    .collect(Collectors.toList())
                    .forEach(f -> builder.append("\n|\t")
                            .append(f.getName()));
            Logger.log("Flag ativa: " + builder.toString());
        }
    }
}
