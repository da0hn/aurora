package aurora.parser;

import java.io.File;
import java.nio.file.Path;

/*
 * @project aurora
 * @author Gabriel Honda on 20/02/2020
 */
public class ParsedPathData {

    private final Path aurora;
    private final Path assembly;

    public ParsedPathData(Path aurora, Path assembly) {
        this.aurora = aurora;
        this.assembly = assembly;
    }

    public Path getAurora() {
        return aurora;
    }

    public Path getAssembly() {
        return assembly;
    }
}
