package clothingStorage.restserver;

import clothingStorage.core.Clothing;
import clothingStorage.core.Storage;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * The service implementation.
 */
@RestController
@RequestMapping("/clothingStorage")
public class ClothingStorageController {

    public static final String STORAGE_SERVICE_PATH = "clothingStorage";

    @Autowired
    private ClothingStorageService clothingStorageService;

    @GetMapping
    public Storage getStorage() {
        return clothingStorageService.getStorage();
    }

    @GetMapping(path="/names")
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        for (Clothing clothing : getStorage().getAllClothes().keySet()) {
            names.add(clothing.getName());
        }
        return names;
    }

    @GetMapping(path="/sortedNames")
    public List<String> getSortedNames() {
        List<String> names = new ArrayList<>();
        for (Clothing clothing : getStorage().getSortedClothings()) {
            names.add(clothing.getName());
        }
        return names;
    }

    @GetMapping(path="/sorted/{id}")
    public List<String> getSortedClothes(@PathVariable("id") String id) {
        List<String> sortedClothes = new ArrayList<>();
        switch (id) {
            case "0":
                getStorage().sortOnLowestPrice();
                sortedClothes = getStorage().priceDisplay();
                break;
            case "1":
                getStorage().sortOnHighestPrice();
                sortedClothes = getStorage().priceDisplay();
                break;
            case "2":
                getStorage().filterOnDiscount();
                sortedClothes = getStorage().priceDisplay();
                break;
        }
        return sortedClothes;
    }

    @GetMapping(path="/sortedType/{type}")
    public List<String> getSortedClothesType(@PathVariable("type") String type) {
        List<String> sortedClothes;
        getStorage().filterOnType(type);
        sortedClothes = getStorage().priceDisplay();
        return sortedClothes;
    }

    @GetMapping(path="/sortedBrand/{brand}")
    public List<String> getSortedClothesBrand(@PathVariable("brand") String brand) {
        List<String> sortedClothes;
        getStorage().filterOnBrand(brand);
        sortedClothes = getStorage().priceDisplay();
        return sortedClothes;
    }

    @GetMapping(path="/storageDisplay")
    public List<String> getStorageDisplay() {
        return getStorage().storageDisplay();
    }

    @GetMapping(path="/priceDisplay")
    public List<String> getPriceDisplay() {
        return getStorage().priceDisplay();
    }

    private void autoSaveStorage() {
        clothingStorageService.autoSaveTodoModel();
    }

    /**
     * Gets the corresponding Clothing.
     *
     * @param name of the Clothing
     * @return the corresponding Clothing
     */
    @GetMapping(path = "/clothes/{name}")
    public Clothing getClothing(@PathVariable("name") String name) {
        Clothing clothing = getStorage().getClothing(name);
        return clothing;
    }

    // NOTE: kan godt hende vi her målegge til en ekstra ettersom det er forskjell
    // på fjering og tillegg av klær

    /**
     * Replaces or adds a clothing.
     *
     * @param name of the clothing
     * @param clothing corresponding clothing
     * @return true if success, false if not
     */
    @PutMapping(path = "/clothes/{name}")
    public boolean putClothing(@PathVariable("name") String name, @RequestBody Clothing clothing) {
        boolean exists = false;
        boolean added = true;
        for (Clothing clothing2 : getStorage().getAllClothes().keySet()) {
            if (clothing.getName().equals(clothing2.getName())) {
                exists = true;
            }
        }
        try {
            if (exists) {
                getStorage().getClothing(name).setPrice(clothing.getPrice(), false);
                getStorage().getClothing(name).setDiscount(clothing.getDiscount());
            } else {
                getStorage().addNewClothing(clothing, 4);
            }
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
     * @return true if success, false if not
     */
    @DeleteMapping(path = "/clothes/{name}")
    public boolean removeClothing(@PathVariable("name") String name) {
        Clothing clothing = getStorage().getClothing(name);
        getStorage().removeClothing(clothing);
        autoSaveStorage();
        return true;
    }

    /**
     * Gets quantity of clothing.
     *
     * @param name of clothing
     * @return quantity of clothing
     */
    @GetMapping(path = "/quantity/{name}")
    public int getQuantity(@PathVariable("name") String name) {
        int quantity = getStorage().getQuantity(getStorage().getClothing(name));
        return quantity;
    }

    /**
     * Replaces or adds quantity.
     *
     * @param name of clothing
     * @param quantity to be replaced or added
     * @return true if success, false if not
     */
    @PutMapping(path = "/quantity/{name}")
    public boolean putQuantity(@PathVariable("name") String name,
        @RequestParam(value="quantity", required = false) String quantity) {
        boolean added = true;
        try {
            getStorage().updateQuantity(getStorage().getClothing(name), Integer.parseInt(quantity));
        } catch (Exception e) {
            added = false;
        }
        autoSaveStorage();
        return added;
    }

    /**
     * Removes quantity.
     *
     * @param name of clothing
     * @return true if success, false if not
     */
    @DeleteMapping(path = "/quantity/{name}")
    public boolean removeQuantity(@PathVariable("name") String name) {
        boolean deleted = true;
        try {
            getStorage().updateQuantity(getStorage().getClothing(name), 0);
        } catch (Exception e) {
            deleted = false;
        }
        autoSaveStorage();
        return deleted;
    }
}
