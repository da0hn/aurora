package aurora.util.fs.factory;

import aurora.core.log.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Predicate;

/*
 * @project aurora
 * @author Gabriel Honda on 21/02/2020
 */
public class AsmFileFactory implements IAsmFileFactory {

    /*
     * caso o arquivo .asm não exista cria-se um novo
     * na mesma pasta que o .au e com o mesmo nome
     * */
    @Override public Path create(Path auroraPath) {
        try {
            var asm = new File(getDir(auroraPath)
                                       + "/"
                                       + getAsmName(extractAuroraName(auroraPath))
                                       + ".asm");
            boolean asmExists = asmExists().and(deleteAsmFile()).test(asm);
            if(asmExists) {
                Logger.log("O arquivo " + asm.getAbsoluteFile().getName() + " ja existe, " +
                                           "um novo arquivo .asm sera criado");
            } else {
                Logger.log("Criado arquivo " + asm.getAbsoluteFile().getName());
            }
            isAsmFileCreated(asm);
            return asm.toPath();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void isAsmFileCreated(File asm) throws IOException {
        boolean fileCreated = asm.createNewFile();

        if(fileCreated) {
            Logger.log("Arquivo " + asm.getAbsoluteFile().getName() + " criado com sucesso");
        }
        else {
            throw new IOException("O arquivo .asm nao foi criado.");
        }
    }

    private Predicate<File> asmExists() {
        return File::exists;
    }

    private Predicate<File> deleteAsmFile() {
        return File::delete;
    }

    private String getAsmName(String inputName) {
        // remove a extensao do arquivo para ser utilizado na criacao do .asm
        return inputName.substring(0,                           // percorre do inicio da string
                                   inputName.indexOf(".")
        );     // ate a localizacao do ponto ( nao incluido )

    }

    private String getDir(Path path) {
        // obtem o caminho absoluto do arquivo ate o diretorio ignorando o arquivo
        return path.toFile()
                .getAbsoluteFile()
                .getParent();
    }

    private String extractAuroraName(Path path) {
        // obtem o caminho absoluto, porem so devolve o nome do arquivo com a extensao
        return path.toFile()
                .getAbsoluteFile()
                .getName();
    }

}
