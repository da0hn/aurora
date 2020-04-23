package aurora.fs;

import aurora.parser.IPathContainer;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

/*
 * @project aurora
 * @author Gabriel Honda on 20/02/2020
 */
public class AuroraFileManager implements IFileManager {

    @Override
    public List<String> readAuroraFile(IPathContainer container) {
        List<String> lines = null;
        try(var buffer = Files.newBufferedReader(container.getAurora())) {
            lines = getLines(buffer);
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    // TODO: Implementar escrita do arquivo .asm
    @Override
    public void writeDataOnAsmFile() {}
}
