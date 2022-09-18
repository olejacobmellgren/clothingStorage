package app;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public interface IFiles {
    public void writeToFile(String filename, Marked marked) throws FileNotFoundException;
    public List readFromFile(String filename) throws FileNotFoundException;
    public File getFile(String filename);
    public String readAsString(String filename) throws FileNotFoundException;
}

