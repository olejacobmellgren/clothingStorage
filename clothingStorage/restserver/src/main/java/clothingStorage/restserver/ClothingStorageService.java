package clothingStorage.restserver;

import clothingStorage.core.Storage;
import clothingStorage.json.ClothingStoragePersistence;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import org.springframework.stereotype.Service;


/**
 * Gives the server access
 * to methods from core and local-persistence.
 */
@Service 
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
    
    /**
     * dunno.
     */
    public ClothingStorageService() {
        this.storagePersistence = new ClothingStoragePersistence();
        URL url = ClothingStorageService.class.getResource("default-storage.json");
        if (url != null) {
            try (Reader reader = new InputStreamReader(url.openStream(), StandardCharsets.UTF_8)) {
                this.storage = storagePersistence.readClothingStorage(reader);
            } catch (IOException e) {
                System.out.println("Couldn't read default-storage.json, "
                    + "so rigging TodoModel manually ("
                    + e + ")");
            }
        } else {
            this.storage = new Storage();
        }
        this.storagePersistence.setSaveFile("server-storage.json");
    }

    /**
     * Saves the Storage to memory/disk.
     * Should be used after evry change in the storage.
     */
    public void autoSaveStorage() {
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