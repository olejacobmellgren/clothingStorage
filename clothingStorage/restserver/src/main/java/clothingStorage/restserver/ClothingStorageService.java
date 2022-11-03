package clothingStorage.restserver;

import clothingStorage.json.ClothingStoragePersistence;
import clothingStorage.core.Storage;
import org.springframework.stereotype.Service;  //NOTE: Får ikke til å importere den

import java.io.IOException;

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
    public ClothingStorageService() {
        this.storage = new Storage();
        this.storagePersistence = new ClothingStoragePersistence();
        this.storagePersistence.setSaveFile("storage.json");
        // initializeStoragePersistence()
        load();
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

}
