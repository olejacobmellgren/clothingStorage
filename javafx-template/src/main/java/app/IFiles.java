package app;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public interface IFiles {
    public void writeToFile(String filename, Storage storage) throws FileNotFoundException;
    public Storage readFromFile(String filename) throws FileNotFoundException;
    public File getFile(String filename);
    public String readAsString(String filename) throws FileNotFoundException;
}

