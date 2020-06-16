package aurora.parser;

import aurora.fs.IFileManager;
import aurora.log.Logger;

import java.util.Arrays;
import java.util.List;

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
        // ativa as flags passadas na compilacao da aurora
        flagManager.activateFlags(argumentList);

        // gera um container que guarda o caminho do arquivo .au e do arquivo .asm
        this.container = pathFactory.create(argumentList);

        var fileName = getPathContainer().getAurora().getFileName();
        Logger.log(String.format("Iniciando análise do arquivo %s", fileName));

        // retorna uma lista de string com o conteudo do arquivo .au
        return fileManager.readAuroraFile(getPathContainer());
    }

    // expoe o container gerado para os outros pacotes
    public IPathContainer getPathContainer() {
        if( this.container == null ) {
            throw new IllegalStateException("O container de arquivos não foi criado");
        }
        return this.container;
    }
}
