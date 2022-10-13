package clothingStorage.json;

import clothingStorage.core.Storage;
import clothingStorage.json.internal.ClothingStorageModule;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Wrapper class for JSON serialization,
 * to avoid direct compile dependencies on Jackson for other modules.
 */

public class ClothingStoragePersistence {

    private ObjectMapper mapper;

    public ClothingStoragePersistence() {
        mapper = createObjectMapper();
    }

    public static SimpleModule createJacksonModule() {
        return new ClothingStorageModule();
    }

    public static ObjectMapper createObjectMapper() {
        return new ObjectMapper().registerModule(createJacksonModule());
    }

    public Storage readClothingStorage(Reader reader) throws IOException {
        return mapper.readValue(reader, Storage.class);
    }

    public void writeClothingStorage(Storage storage, Writer writer) throws IOException {
        mapper.writerWithDefaultPrettyPrinter().writeValue(writer, storage);
    }

    private Path saveFilePath = null;

    public void setSaveFile() {
        this.saveFilePath = Paths.get(System.getProperty("user.home"), "storage.json");
    }

    public Path getSaveFilePath() {
        return this.saveFilePath;
    }

    /**
     * Loads a ClothingStorage (Storage) from the saved file (saveFilePath).
     *
     * @return the loaded ClothingStorage (Storage)
     */
    public Storage loadClothingStorage() throws IOException, IllegalStateException {
        if (saveFilePath == null) {
            throw new IllegalStateException("Save file path is not set, yet");
        }
        try (Reader reader = new FileReader(saveFilePath.toFile(), StandardCharsets.UTF_8)) {
            return readClothingStorage(reader);
        }
    }

    /**
     * Saves a ClothingStorage (Storage) to the saveFilePath.
     *
     * @param storage the ClothingStorage (Storage) to save
     */
    public void saveClothingStorage(Storage storage) throws IOException, IllegalStateException {
        if (saveFilePath == null) {
            throw new IllegalStateException("Save file path is not set, yet");
        }
        try (Writer writer = new FileWriter(saveFilePath.toFile(), StandardCharsets.UTF_8)) {
            writeClothingStorage(storage, writer);
        }
    }
}

