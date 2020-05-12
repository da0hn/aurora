package aurora.parser;

import aurora.fs.IAsmFileFactory;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

/*
 * @project aurora
 * @author Gabriel Honda on 22/04/2020
 */
public interface IPathFactory {
    // metodos default dependem da implementacao
    IAsmFileFactory getAsmFactory();
    String getAsmExtension();
    String getAuroraExtension();

    default IPathContainer create(List<String> argumentList) {
        Path asmPath, auroraPath;

        // assume-se que um arquivo aurora deve-se ter a extensao .au,
        // procura dentro da lista uma string que termina com .au
        Optional<String> _au = findFileOnArgumentList(argumentList, arg -> arg.endsWith(getAuroraExtension()));

        // caso nao seja encontrado o arquivo aurora o lança excecao e encerra a aplicacao
        auroraPath = Path.of(_au.orElseThrow(() -> new IllegalArgumentException("Não foi encontrado o arquivo " +
                                                                                        "do tipo " + getAuroraExtension())));

        // procura o arquivo passado por argumento,
        // caso o arquivo nao seja encontrado gera um .asm com o mesmo nome
        // do arquivo aurora
        asmPath = processAsmPath(auroraPath, argumentList);

        // retorna uma instancia de ParsedPath que possui a informacao do caminho
        // dos arquivos de input e output
        // que serao usados por outras classes
        return new PathContainer(auroraPath, asmPath);
    }

    default Path processAsmPath(Path auroraPath, List<String> argumentList) {
        // busca o argumento que possui a extensao .asm
        Optional<String> filePath = findFileOnArgumentList(argumentList, arg -> arg.endsWith(getAsmExtension()));
        // se a extensao nao existir cria-se um arquivo com o nome do
        // arquivo aurora e a extensao .asm e retorna como Path
        // caso contrario, retorna o caminho do arquivo .asm como objeto Path
        return filePath.isEmpty() ? getAsmFactory().create(auroraPath) : Path.of(filePath.get());
    }

    default Optional<String> findFileOnArgumentList(List<String> argumentList, Predicate<String> extensionPredicate) {
        return argumentList.stream()
                .filter(extensionPredicate)     // executa o metodo que testa a extensao
                .findFirst();                   // recupera o primeiro resultado que é um Optional
    }
}
