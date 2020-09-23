package org.aurora.util.fs;

import org.aurora.util.parser.IPathContainer;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

/*
 * @project org.aurora
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

    @Override
    public void writeDataOnAsmFile(String asmCode, IPathContainer container) {
        try(var buffer = Files.newBufferedWriter(container.getAssembly())) {
            buffer.write(asmCode);
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }
}
