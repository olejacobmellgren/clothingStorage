package clothingStorage.ui;

import clothingStorage.core.Clothing;
import clothingStorage.core.Storage;
import clothingStorage.core.StorageStatistics;
import clothingStorage.json.ClothingStoragePersistence;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that is used for direct access. Saves and loads data to file.
 */
public class DirectAccess implements Access {

    private ClothingStoragePersistence storagePersistence;

    private Storage storage;

    /**
     * Constructor that initializes the storage from a file.
     */
    public DirectAccess() {
        this.storage = new Storage();
        this.storagePersistence = new ClothingStoragePersistence();
        this.storagePersistence.setSaveFile("storageDirect.json");
        try {
            this.storage = storagePersistence.loadClothingStorage();
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    /**
     * Initializes storage based on argument. Only used in controller-tests.
     *
     * @param storage to be set
     */
    public DirectAccess(Storage storage) {
        this.setStorage(storage);
    }

    /**
     * Sets the storage.
     *
     * @param storage to be set
     */
    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    /**
     * Gets the storage.
     *
     * @return storage used
     */
    @Override
    public Storage getStorage() {
        return new Storage(this.storage);
    }

    /**
     * Gets clothing from the storage based on name.
     *
     * @param name of clothing to retrieve
     * @returns clothing
     */
    @Override
    public Clothing getClothing(String name) {
        return this.storage.getClothing(name);
    }

    /**
     * Adds clothing to the storage.
     *
     * @param clothing to be added
     * @param quantity to be set
     * @return true if success
     */
    @Override
    public boolean addClothing(Clothing clothing, int quantity) {
        this.storage.addNewClothing(clothing, quantity);
        fireAutoSaveStorage();
        return true;
        
    }

    /**
     * Removes clothing from the storage based on name.
     *
     * @param name of clothing to be retrieved
     * @return true if success
     */
    @Override
    public boolean removeClothing(String name) {
        this.storage.removeClothing(this.storage.getClothing(name));
        fireAutoSaveStorage();
        return true;
    }

    /**
     * Increase quantity by one based on name of clothing.
     *
     * @param name of clothing to increase quantity of
     * @return true if success
     */
    @Override
    public boolean increaseQuantityByOne(String name) {
        this.storage.increaseQuantity(this.storage.getClothing(name), 1);
        fireAutoSaveStorage();
        return true;
    }

    /**
     * Decrease quantity by one based on name of clothing.
     *
     * @param name of clothing to decrease quantity of
     * @return true if success
     */
    @Override
    public boolean decreaseQuantityByOne(String name) {
        this.storage.decreaseQuantity(this.storage.getClothing(name), 1);
        fireAutoSaveStorage();
        return true;
    }

    /**
     * Increase quantity based on name of clothing.
     *
     * @param name of clothing to increase quantity of
     * @param quantity to be added
     * @return true if success
     */
    @Override
    public boolean increaseQuantity(String name, int quantity) {
        this.storage.increaseQuantity(this.storage.getClothing(name), quantity);
        fireAutoSaveStorage();
        return true;
    }

    /**
     * Decrease quantity based on name of clothing.
     *
     * @param name of clothing to decrease quantity of
     * @param quantity to be decreased
     * @return true if success
     */
    @Override
    public boolean decreaseQuantity(String name, int quantity) {
        this.storage.decreaseQuantity(this.storage.getClothing(name), quantity);
        fireAutoSaveStorage();
        return true;
    }

    /**
     * Gets the storagedisplay to the storage.
     *
     * @return the storagedisplay containing list of strings for every clothing in storage
     */
    @Override
    public List<String> getStorageDisplay() {
        return this.storage.storageDisplay();
    }

    /**
     * Gets the pricedisplay to the storage.
     *
     * @return the pricedisplay containing list of strings for every clothing in storage
     */
    @Override
    public List<String> getPriceDisplay() {
        this.storage.setIsSortedPricePage(false);
        return this.storage.priceDisplay();
    }

    /**
     * Gets the sorted pricedisplay to the storage.
     *
     * @param id 0 for sorting on lowest price, 1 for highest price and 2 for on discount
     * @return the sorted pricedisplay containing list of strings for every clothing in storage
     */
    @Override
    public List<String> getSortedPriceDisplay(int id) {
        switch (id) {
            case 0:
                this.storage.sortOnLowestPrice();
                break;
            case 1:
                this.storage.sortOnHighestPrice();
                break;
            case 2:
                this.storage.filterOnDiscount();
                break;
            default:
                break;
        }
        fireAutoSaveStorage();
        return this.storage.priceDisplay();
    }

    /**
     * Gets the sorted pricedisplay to the storage.
     *
     * @param type to be sorted on
     * @return the sorted pricedisplay containing list of strings for every clothing in storage
     */
    @Override
    public List<String> getSortedTypePriceDisplay(String type) {
        this.storage.filterOnType(type);
        fireAutoSaveStorage();
        return this.storage.priceDisplay();
    }

    /**
     * Gets the sorted pricedisplay to the storage.
     *
     * @param brand to be sorted on
     * @return the sorted pricedisplay containing list of strings for every clothing in storage
     */
    @Override
    public List<String> getSortedBrandPriceDisplay(String brand) {
        this.storage.filterOnBrand(brand);
        fireAutoSaveStorage();
        return this.storage.priceDisplay();
    }

    /**
     * Gets the names for clothings in the storage when sorted.
     *
     * @return the names for clothings in storage when sorted
     */
    @Override
    public List<String> getSortedNames() {
        ArrayList<String> names = new ArrayList<>();
        for (Clothing clothing : this.storage.getSortedClothings()) {
            names.add(clothing.getName());
        }
        return names;
    }

    /**
     * Updates price of clothing in storage.
     *
     * @param clothing to be updated
     * @param price to be set
     * @return true if success
     */
    @Override
    public boolean updatePrice(Clothing clothing, double price) {
        this.storage.getClothing(clothing.getName()).setPrice(price, true);
        fireAutoSaveStorage();
        return true;
    }

    /**
     * Adds discount to clothing in storage.
     *
     * @param clothing to be updated
     * @param discount to be added
     * @return true if success
     */
    @Override
    public boolean updateDiscount(Clothing clothing, double discount) {
        this.storage.getClothing(clothing.getName()).setPriceAfterAddedDiscount(discount);
        fireAutoSaveStorage();
        return true;
    }

    /**
     * Removes discount of clothing in storage.
     *
     * @param clothing to be updated
     * @return true if success
     */
    @Override
    public boolean removeDiscount(Clothing clothing) {
        this.storage.getClothing(clothing.getName()).removeDiscount();
        fireAutoSaveStorage();
        return true;
    }

    /**
     * Gets the quantities of sizes for a type of clothing.
     *
     * @param type the type of clothing to get quantities sizes for
     * @return amount of clothing for each size of that type
     */
    @Override
    public List<Integer> getQuantitiesForTypeAndSizes(String type) {
        List<Integer> quantities = new ArrayList<>();
        quantities.add(StorageStatistics.getQuantityForTypeAndSize(this.storage, type, 'S'));
        quantities.add(StorageStatistics.getQuantityForTypeAndSize(this.storage, type, 'M'));
        quantities.add(StorageStatistics.getQuantityForTypeAndSize(this.storage, type, 'L'));
        return quantities;
    }
    
    /**
     * Autosaves storage to json-file.
     */
    private void fireAutoSaveStorage() {    
        if (storagePersistence != null) {
            try {
                storagePersistence.saveClothingStorage(storage);
            } catch (Exception e) {
                System.err.println("Fikk ikke lagret storage: " + e.getMessage());
            }
        }
    }

    /**
     * Gets the names for clothings in the storage.
     *
     * @return the names for clothings in storage
     */
    @Override
    public List<String> getNames() {
        ArrayList<String> names = new ArrayList<>();
        for (Clothing clothing : this.storage.getAllClothes().keySet()) {
            names.add(clothing.getName());
        }
        return names;
    }

    /**
     * Gets quantity for given name.
     *
     * @param name to retrieve quantity for
     * @return quantity for clothing with the name
     */
    @Override
    public int getQuantity(String name) {
        return this.storage.getQuantity(this.storage.getClothing(name));
    }

    /**
     * Gets total value of storage.
     *
     * @return total value of storage
     */
    @Override
    public double getTotalValue() {
        return StorageStatistics.getTotalValue(this.storage);
    }

    /**
     * Gets total quantity for storage.
     *
     * @return total quantity in storage
     */
    @Override
    public int getTotalQuantity() {
        return StorageStatistics.getTotalQuantity(this.storage);
    }
}