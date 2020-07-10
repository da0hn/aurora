package aurora.util.fs.factory;

import aurora.util.parser.IPathFactory;

/*
 * @project aurora
 * @author Gabriel Honda on 22/04/2020
 */
public class PathFactory implements IPathFactory {

    private final IAsmFileFactory asmFactory;

    public PathFactory(IAsmFileFactory asmFactory) {this.asmFactory = asmFactory;}

    @Override
    public String getAsmExtension() {
        return ".asm";
    }

    @Override
    public String getAuroraExtension() {
        return ".au";
    }

    @Override public IAsmFileFactory getAsmFactory() {
        return this.asmFactory;
    }
}
