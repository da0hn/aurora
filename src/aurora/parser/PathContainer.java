package aurora.parser;

import java.nio.file.Path;

/*
 * @project aurora
 * @author Gabriel Honda on 18/03/2020
 */
public record PathContainer(
        Path aurora,
        Path assembly
) {
}
