package aurora.util.fs;

import aurora.util.parser.IPathContainer;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/*
 * @project aurora
 * @author Gabriel Honda on 22/04/2020
 */
public interface IFileManager {

    List<String> readAuroraFile(IPathContainer container);
    void writeDataOnAsmFile();

    default List<String> read(String path) {
        List<String> lines = null;
        try(var buffer = Files.newBufferedReader(Paths.get(path))) {
            lines = getLines(buffer);
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    default List<String> getLines(BufferedReader buffer) {
        return buffer.lines().collect(Collectors.toList());
    }
}
