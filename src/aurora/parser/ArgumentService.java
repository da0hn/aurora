package aurora.parser;

import aurora.fs.IAsmFileFactory;
import aurora.fs.IFileManager;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/*
 * @project aurora
 * @author Gabriel Honda on 19/02/2020
 */
public class ArgumentService {

    private final IFlagManager flagManager;
    private final IPathFactory pathFactory;
    private final IFileManager fileManager;
    private IPathContainer container;

    public ArgumentService(IFlagManager flagManager, IPathFactory pathFactory, IFileManager fileManager) {
        this.flagManager = flagManager;
        this.pathFactory = pathFactory;
        this.fileManager = fileManager;
    }

    public List<String> analyze(String[] args) {
        List<String> argumentList = Arrays.asList(args);
        flagManager.activateFlags(argumentList);

        this.container = pathFactory.create(argumentList);

        return fileManager.readAuroraFile(getPathContainer());
    }

    public IPathContainer getPathContainer() {
        if( this.container == null ) {
            throw new IllegalStateException("O container de arquivos não foi criado");
        }
        return this.container;
    }
}
