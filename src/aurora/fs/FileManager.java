package aurora.fs;

import aurora.parser.PathContainer;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/*
 * @project aurora
 * @author Gabriel Honda on 20/02/2020
 */
public class FileManager {

    private PathContainer manager;

    public FileManager(PathContainer manager) {
        this.manager = manager;
    }

    public List<String> readLinesAuroraFile() {
        List<String> lines = new ArrayList<>();
        try(var buffer = Files.newBufferedReader(manager.getAurora())) {
            lines = buffer.lines().collect(Collectors.toList());
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    // TODO: Implementar escrita do arquivo .asm
    public void writeDataOnAsmFile() {}
}
