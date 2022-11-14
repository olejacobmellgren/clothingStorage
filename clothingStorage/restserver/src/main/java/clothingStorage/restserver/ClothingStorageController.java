package clothingStorage.restserver;

import clothingStorage.core.Clothing;
import clothingStorage.core.Storage;
import clothingStorage.core.StorageStatistics;
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

    @Autowired
    private ClothingStorageService clothingStorageService;

    /**
     * Gets the Storage.
     *
     * @return the storage in restserver
     */
    @GetMapping
    public Storage getStorage() {
        return clothingStorageService.getStorage();
    }

    /**
     * Gets list of clothings-names in storage.
     *
     * @return list of clothing-names
     */
    @GetMapping(path = "/names")
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        for (Clothing clothing : getStorage().getAllClothes().keySet()) {
            names.add(clothing.getName());
        }
        return names;
    }

    /**
     * Gets the sorted clothing-names in storage.
     *
     * @return the list of clothing-names
     */
    @GetMapping(path = "/sortedNames")
    public ArrayList<String> getSortedNames() {
        ArrayList<String> names = new ArrayList<>();
        for (Clothing clothing : getStorage().getSortedClothings()) {
            names.add(clothing.getName());
        }
        return names;
    }

    /**
     * Gets pricedisplay of sorted clothes.
     *
     * @param id which sorting-method to use. Either 0, 1 or 2
     * @return pricedisplay of sorted clothes
     */
    @GetMapping(path = "/sorted/{id}")
    public List<String> getSortedClothes(@PathVariable("id") String id) {
        List<String> sortedClothes = new ArrayList<>();
        switch (id) {
            case "0":
                getStorage().sortOnLowestPrice();
                autoSaveStorage();
                sortedClothes = getStorage().priceDisplay();
                break;
            case "1":
                getStorage().sortOnHighestPrice();
                autoSaveStorage();
                sortedClothes = getStorage().priceDisplay();
                break;
            case "2":
                getStorage().filterOnDiscount();
                autoSaveStorage();
                sortedClothes = getStorage().priceDisplay();
                break;
            default:
        }
        return sortedClothes;
    }

    /**
     * Gets the storage when filtered on a type.
     *
     * @param type for the storage to be filtered on
     * @return the filtered list based on type
     */
    @GetMapping(path = "/sortedType/{type}")
    public List<String> getSortedClothesType(@PathVariable("type") String type) {
        List<String> sortedClothes;
        getStorage().filterOnType(type);
        autoSaveStorage();
        sortedClothes = getStorage().priceDisplay();
        return sortedClothes;
    }

    /**
     * Gets the storage when filtered on a brand.
     *
     * @param brand for the storage to be filtered on
     * @return the filtered list based on brand
     */
    @GetMapping(path = "/sortedBrand/{brand}")
    public List<String> getSortedClothesBrand(@PathVariable("brand") String brand) {
        List<String> sortedClothes;
        getStorage().filterOnBrand(brand);
        autoSaveStorage();
        sortedClothes = getStorage().priceDisplay();
        return sortedClothes;
    }

    /**
     * Gets the storage-display for the storage.
     *
     * @return the storage-display
     */
    @GetMapping(path = "/storageDisplay")
    public List<String> getStorageDisplay() {
        return getStorage().storageDisplay();
    }

    /**
     * Gets the price-display for the storage.
     *
     * @return the price-display
     */
    @GetMapping(path = "/priceDisplay")
    public List<String> getPriceDisplay() {
        getStorage().setIsSortedPricePage(false);
        autoSaveStorage();
        return getStorage().priceDisplay();
    }

    private void autoSaveStorage() {
        clothingStorageService.autoSaveStorage();
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
                autoSaveStorage();
                getStorage().getClothing(name).setDiscount(clothing.getDiscount());
                autoSaveStorage();
            } else {
                getStorage().addNewClothing(clothing, 4);
                autoSaveStorage();
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
        @RequestParam(value = "quantity", required = false) String quantity) {
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

    /**
     * Gets quantities for each size for a type of clothing.
     *
     * @return quantities for sizes for a type of clothing
     */
    @GetMapping(path = "stats/chartData/{type}")
    public List<Integer> getQuantitiesForTypeAndSizes(@PathVariable("type") String type) {
        List<Integer> quantities = new ArrayList<>();
        quantities.add(StorageStatistics.getQuantityForTypeAndSize(getStorage(), type, 'S'));
        quantities.add(StorageStatistics.getQuantityForTypeAndSize(getStorage(), type, 'M'));
        quantities.add(StorageStatistics.getQuantityForTypeAndSize(getStorage(), type, 'L'));
        return quantities;
    }

    /**
     * Gets total quantity in storage.
     *
     * @return total quantity of storage
     */
    @GetMapping(path = "/stats/totalQuantity")
    public int getTotalQuantity() {
        int totalQuantity = StorageStatistics.getTotalQuantity(getStorage());
        return totalQuantity;
    }

    /**
     * Gets total value of storage.
     *
     * @return total value of clothing
     */
    @GetMapping(path = "/stats/totalValue")
    public double getTotalValue() {
        double totalQuantity = StorageStatistics.getTotalValue(getStorage());
        return totalQuantity;
    }
}
