package aurora.util.parser;

import java.nio.file.Path;

/*
 * @project aurora
 * @author Gabriel Honda on 20/02/2020
 */
public class PathContainer implements IPathContainer {

    private Path aurora;
    private Path assembly;

    public PathContainer(Path aurora, Path assembly) {
        this.aurora = aurora;
        this.assembly = assembly;
    }

    @Override
    public Path getAurora() {
        return aurora;
    }

    @Override
    public Path getAssembly() {
        return assembly;
    }
}
