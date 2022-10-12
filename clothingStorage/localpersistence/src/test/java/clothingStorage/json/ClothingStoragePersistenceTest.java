package clothingStorage.json;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Iterator;
import org.junit.jupiter.api.Test;
import clothingStorage.core.Clothing;
import clothingStorage.core.Storage;

public class ClothingStoragePersistenceTest {

  private ClothingStoragePersistence clothingStoragePersistence = new ClothingStoragePersistence();

  private Storage createSampleStorage() {
    Clothing clothing1 = new Clothing("Pans", "Nike", 'M', 199.0);
    Clothing clothing2 = new Clothing("Top", "Adidas", 'S', 599.9);
    Clothing clothing3 = new Clothing("Leggings", "Nike", 'L', 20.0);
    clothing3.setDiscount(0.8);
    Storage storage = new Storage();
    storage.addNewClothing(clothing1, 4);
    storage.addNewClothing(clothing3, 2);
    storage.addNewClothing(clothing2, 1);
    return storage;
  }

  private void checkSampleStorage(Storage storage, Storage storage2) {
    assertTrue(storage.getAllClothes().size() == storage2.getAllClothes().size());
    for (int i = 0; i < storage.getAllClothes().size(); i++) {
      assertEquals(storage.getClothing(i), storage2.getClothing(i));
    }
  }

  @Test
  public void testSerializersDeserializers_usingStringWriter() {
    Storage storage = createSampleStorage();
    try {
      StringWriter writer = new StringWriter();
      clothingStoragePersistence.writeClothingStorage(storage, writer);
      String json = writer.toString();
      Storage storage2 = clothingStoragePersistence.readClothingStorage(new StringReader(json));
      checkSampleStorage(storage, storage2);
    } catch (IOException e) {
      fail(e.getMessage());
    }
  }

  @Test
  public void testSerializersDeserializers_usingSaveFile() {
    Storage storage = createSampleStorage();
    clothingStoragePersistence.setSaveFile(); 
    Path saveFilePath = clothingStoragePersistence.getSaveFilePath();
    try {
      clothingStoragePersistence.saveClothingStorage(storage);
      assertTrue(Files.exists(saveFilePath));
      Storage storage2 = clothingStoragePersistence.loadClothingStorage();
      checkSampleStorage(storage, storage2);
    } catch (IOException e) {
      fail(e.getMessage());
    } finally {
      try {
        Files.deleteIfExists(saveFilePath);
      } catch (IOException e) {
      }
    }
  }
}

