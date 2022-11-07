package clothingStorage.restserver;

import clothingStorage.json.ClothingStoragePersistence;
import clothingStorage.core.Storage;
import clothingStorage.core.Clothing;
import org.springframework.stereotype.Service;  //NOTE: Får ikke til å importere den

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * Gives the server access
 * to methods from core and local-persistence.
 */
@Service //NOTE:Antar jeg trenger denne senere
public class ClothingStorageService {
    /**
     * The storage.
     */
    private Storage storage; //NOTE: Kan hende den bør være final med idk hvordan
    /**
     * Persistens for json.
     */
    private final ClothingStoragePersistence storagePersistence; 

    /**
     * Create a new Storage object on initialization
     * and loads data using localpercistence.
     */
    public ClothingStorageService(Storage storage) {
        this.storage = storage;
        this.storagePersistence = new ClothingStoragePersistence();
        this.storagePersistence.setSaveFile("server-storage.json");
    }

    public ClothingStorageService() {
        this.storagePersistence = new ClothingStoragePersistence();
        URL url = ClothingStorageService.class.getResource("default-storage.json");
        if (url != null) {
          try (Reader reader = new InputStreamReader(url.openStream(), StandardCharsets.UTF_8)) {
            this.storage = storagePersistence.readClothingStorage(reader);
          } catch (IOException e) {
            System.out.println("Couldn't read default-storage.json, so rigging TodoModel manually ("
                + e + ")");
          }
        } else {
            this.storage = new Storage();
        }
        this.storagePersistence.setSaveFile("server-storage.json");
    }


    /**
     * Initializion function for the ClothingStoragePersistence.
     * Makes a new ClothingStoragePersistion and sets the file to
     * save and load from.
     */
/* 
    private void initializeStoragePersistence() {
        this.storagePersistence = new ClothingStoragePersistence();
        this.storagePersistence.setSaveFile("storage.json");
    }
*/


    /**
     * Use ClothingStoragePersistence from localpersistence to load
     * the content of the save file to the Storage.
     */
    public void load() {
        try {
            this.storage = storagePersistence.loadClothingStorage();
        } catch (IllegalArgumentException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Use ClothingStoragePersistence from localpersistence to save
     * the state of the Storage to file.
     */
    public void save() {
        try {
            storagePersistence.saveClothingStorage(storage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves the Storage to memory/disk.
     * Should be used after evry change in the storage.
     */
    public void autoSaveTodoModel() {
        if (storagePersistence != null) {
          try {
            storagePersistence.saveClothingStorage(this.storage);
          } catch (IllegalStateException | IOException e) {
            System.err.println("Couldn't auto-save storage: " + e);
          }
        }
      }
    
    
    
    //NOTE: Kan hende de to nederste funksjonene kan slettes...
    public Storage getStorage() {
        return this.storage;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

}
