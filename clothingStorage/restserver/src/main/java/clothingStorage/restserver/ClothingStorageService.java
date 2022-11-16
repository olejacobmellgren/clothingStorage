package clothingStorage.restserver;

import clothingStorage.core.Storage;
import clothingStorage.json.ClothingStoragePersistence;
import java.io.IOException;
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
    private Storage storage;
    /**
     * Persistens for json.
     */
    private final ClothingStoragePersistence storagePersistence; 

    /**
     * Create a new Storage object on initialization
     * and loads data using localpercistence.
     */
    public ClothingStorageService() {
        this.storage = new Storage();
        this.storagePersistence = new ClothingStoragePersistence();
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
    
    /**
     * Gets the storage from restserver.
     *
     * @return storage used in restserver
     */
    public Storage getStorage() {
        return this.storage;
    }

    /**
     * Sets the storage to the given storage.
     *
     * @param storage to be set
     */
    public void setStorage(Storage storage) {
        this.storage = storage;
    }

}