package aurora.files;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

/*
 * @project aurora
 * @author Gabriel Honda on 21/02/2020
 */
public class AsmFile {

    /*
     * caso o arquivo .asm não exista cria-se um novo
     * na mesma pasta que o .au e com o mesmo nome
     * */
    public static Path create(Path path){
        try {
            var asm = new File(getDir(path)
                                       + "/"
                                       + getAsmName(extractAuroraName(path))
                                       + ".asm");
            boolean fileCreated = asm.createNewFile();
            if( fileCreated ) {
                System.out.println("Arquivo " + asm.getAbsoluteFile().getName() + " criado com sucesso");
            } else {
                throw new IOException("O arquivo .asm nao foi criado.");
            }
            return asm.toPath();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getAsmName(String inputName) {
        // remove a extensao do arquivo para ser utilizado na criacao do .asm
        return inputName.substring(0,                       // percorre do inicio da string
                                   inputName.indexOf(".")); // ate a localizacao do ponto ( nao incluido )
    }

    private static String getDir(Path path) {
        // obtem o caminho absoluto do arquivo ate o diretorio ignorando o arquivo
        return path.toFile()
                        .getAbsoluteFile()
                        .getParent();
    }

    private static String extractAuroraName(Path path) {
        // obtem o caminho absoluto, porem so devolve o nome do arquivo com a extensao
        return path.toFile()
                        .getAbsoluteFile()
                        .getName();
    }

}
