package aurora.util.fs.factory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

/*
 * @project aurora
 * @author Gabriel Honda on 22/04/2020
 */
public interface IAsmFileFactory {
    Path create(Path auroraPath);
    void isAsmFileCreated(File asm) throws IOException;
}
