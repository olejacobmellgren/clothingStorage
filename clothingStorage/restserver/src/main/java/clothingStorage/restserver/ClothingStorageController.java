package clothingStorage.restserver;

import clothingStorage.core.Clothing;
import clothingStorage.core.Storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping(ClothingStorageController.STORAGE_SERVICE_PATH)
public class ClothingStorageController {

    public static final String STORAGE_SERVICE_PATH = "storage";

    @Autowired
    private ClothingStorageService clothingStorageService;

    @GetMapping
    public Storage getStorage() {
        return clothingStorageService.getStorage();
    }

    private void autoSaveStorage() {
        clothingStorageService.autoSaveTodoModel();
    }

    private void checkClothing(Clothing clothing, String name) {
        if (clothing == null) {
            throw new IllegalArgumentException("No Clothing named \"" + name + "\"");
        }
    }

    /**
     * Gets the corresponding Clothing.
     *
     * @param name the name of the Clothing
     * @return the corresponding Clothing
     */
    @GetMapping(path = "/clothes/{name}")
    public Clothing getClothing(@PathVariable("name") String name) {
        Clothing clothing = getStorage().getClothing(name);
        checkClothing(clothing, name);
        return clothing;
    }

    // NOTE: kan godt hende vi her målegge til en ekstra ettersom det er forskjell
    // på fjering og tillegg av klær


    @PutMapping(path = "/clothes/{name}")
    public boolean addClothing(@PathVariable("name") String name, @RequestBody Clothing clothing,
            @RequestBody int quantity) {
        boolean added = true;
        try {
            getStorage().addNewClothing(clothing, quantity);
        } catch (Exception e) {
            added = false;
        }
        autoSaveStorage();
        return added;
    }

    @PutMapping(path = "/clothes/{name}")
    public boolean putClothing(@PathVariable("name") String name, @RequestBody Clothing clothing, @RequestBody int quantity) {
        boolean added = true;
        try {
            getStorage().putClothing(clothing, quantity);
        } catch (Exception e) {
            added = false;
        }
        autoSaveStorage();
        return added;
    }

    @PutMapping(path = "/clothes/{name}")
    public boolean updateClothingPrice(@PathVariable("name") String name, @RequestBody Clothing clothing) {
        boolean added = true;
        try {
            getStorage().getClothing(name).setPrice(clothing.getPrice());
            getStorage().getClothing(name).setDiscount(clothing.getDiscount());
        } catch (Exception e) {
            added = false;
        }
        autoSaveStorage();
        return added;
    }

    /**
     * Removes the clothing.
     *
     * @param name the name of the clothing
     */
    @DeleteMapping(path = "/clothes/{name}")
    public boolean removeClothing(@PathVariable("name") String name) {
        Clothing clothing = getStorage().getClothing(name);
        checkClothing(clothing, name);
        getStorage().removeClothing(clothing);
        autoSaveStorage();
        return true;
    }

}
