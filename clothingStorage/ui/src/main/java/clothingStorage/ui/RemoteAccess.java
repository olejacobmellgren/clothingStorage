package clothingStorage.ui;

import clothingStorage.client.StorageClient;
import clothingStorage.core.Clothing;
import clothingStorage.core.Storage;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Class that is used for remote access. Updates a storage on the restserver-side.
 */
public class RemoteAccess implements Access {

    StorageClient storageClient;

    /**
     * Initializes a client used for communicating with restserver.
     *
     * @throws URISyntaxException if there is an error with the URI.
     */
    public RemoteAccess() throws URISyntaxException {
        storageClient = new StorageClient();
    }

    /**
     * Gets the storage from the restserver.
     *
     * @return storage from restserver
     */
    @Override
    public Storage getStorage() {
        Storage storage = storageClient.getStorage();
        return storage;
    }

    /**
     * Gets Clothing from restserver.
     *
     * @param name of clothing to be retrieved
     * @return Clothing item
     */
    @Override
    public Clothing getClothing(String name) {
        Clothing clothing = storageClient.getClothing(name);
        return clothing;
    }

    /**
     * Adds Clothing to restserver.
     *
     * @param clothing to be added
     * @param quantity to be set
     * @return boolean true if added, false if not
     */
    @Override
    public boolean addClothing(Clothing clothing, int quantity) {
        boolean added = false;
        try {
            added = storageClient.putClothing(clothing);
        } catch (JsonProcessingException e) {
            System.err.println("Could not add clothing");
        }
        boolean added2 = storageClient.putQuantity(clothing.getName(), quantity);
        return added && added2;
    }

    /**
     * Removes Clothing from restserver.
     *
     * @param name of clothing to be removed
     * @return boolean true if success, false if not
     */
    @Override
    public boolean removeClothing(String name) {
        return storageClient.removeClothing(name);
    }

    /**
     * Increases number of clothing items by one.
     *
     * @param name of clothing to be increased
     * @return true if success, false if not
     */
    @Override
    public boolean increaseQuantityByOne(String name) {
        int oldQuantity = this.getQuantity(name);
        int newQuantity = oldQuantity + 1;
        boolean increased = storageClient.putQuantity(name, newQuantity);
        return increased;
    }

    /**
     * Decreases number of clothing items by one.
     *
     * @param name of clothing item to be decreased
     * @throws IllegalStateException if number of clothing items is less than 0
     */
    @Override
    public boolean decreaseQuantityByOne(String name) {
        int oldQuantity = this.getQuantity(name);
        int newQuantity = oldQuantity - 1;
        boolean decreased;
        if (newQuantity >= 0) {
            decreased = storageClient.putQuantity(name, newQuantity);
        } else {
            throw new IllegalStateException("Can not have negative quantity");
        }
        return decreased;
    }

    /**
     * Increases number of clothing items.
     *
     * @param name of clothing item to be increased
     * @param quantity number of clothing items to add
     * @return true if success, false if not
     */
    @Override
    public boolean increaseQuantity(String name, int quantity) {
        int oldQuantity = this.getQuantity(name);
        int newQuantity = oldQuantity + quantity;
        boolean increased = storageClient.putQuantity(name, newQuantity);
        return increased;
    }

    /**
     * Decreases number of clothing items.
     *
     * @param name of clothing item to be decreased
     * @param quantity number of clothing items to remove
     * @throws IllegalStateException if number of clothing items is less than 0
     */
    @Override
    public boolean decreaseQuantity(String name, int quantity) {
        int oldQuantity = this.getQuantity(name);
        int newQuantity = oldQuantity - quantity;
        boolean added;
        if (newQuantity >= 0) {
            added = storageClient.putQuantity(name, newQuantity);
        } else {
            throw new IllegalStateException("Can not have negative quantity"); 
        }
        return added;
    }

    /**
     * Gets the display for the storage on storage-page.
     *
     * @return the clothings to be displayed on storage-page
     */
    @Override
    public List<String> getStorageDisplay() {
        return storageClient.getStorageDisplay();
    }

    /**
     * Gets the display for the storage on price-page.
     *
     * @return the clothings to be displayed on price-page
     */
    @Override
    public List<String> getPriceDisplay() {
        return storageClient.getPriceDisplay();
    }

    /**
     * Gets the sorted pricedisplay based on id.
     *
     * @param id 0 for lowest price, 1 for highest price and 2 for on discount
     * @return the sorted pricedisplay containing list of strings for every clothing in storage
     */
    @Override
    public List<String> getSortedPriceDisplay(int id) {
        return storageClient.getSorted(id);
    }

    /**
     * Gets the sorted pricedisplay based on type.
     *
     * @param type to be sorted on
     * @return the sorted pricedisplay containing list of strings for every clothing in storage
     */
    @Override
    public List<String> getSortedTypePriceDisplay(String type) {
        return storageClient.getSortedType(type);
    }

    /**
     * Gets the sorted pricedisplay based on brand.
     *
     * @param brand to be sorted on
     * @return the sorted pricedisplay containing list of strings for every clothing in storage
     */
    @Override
    public List<String> getSortedBrandPriceDisplay(String brand) {
        return storageClient.getSortedBrand(brand);
    }

    /**
     * Gets the names for clothings in the storage when sorted.
     *
     * @return the names for clothings in storage when sorted
     */
    @Override
    public List<String> getSortedNames() {
        return storageClient.getSortedNames();
    }

    /**
     * Updates price for a clothing.
     *
     * @param clothing the clothing to update price for
     * @return true if updated, false if not
     */
    @Override
    public boolean updatePrice(Clothing clothing, double price) {
        boolean updated = false;
        try {
            clothing.setPrice(price, true);
            updated = storageClient.putClothing(clothing);
        } catch (JsonProcessingException e) {
            System.err.println(e);
        }
        return updated;
    }

    /**
     * Updates discount for a clothing.
     *
     * @param clothing the clothing to update discount for
     * @return true if updated, false if not
     */
    @Override
    public boolean updateDiscount(Clothing clothing, double discount) {
        boolean updated = false;
        try {
            clothing.setPriceAfterAddedDiscount(discount);
            updated = storageClient.putClothing(clothing);
        } catch (JsonProcessingException e) {
            System.err.println(e);
        }
        return updated;
    }

    /**
     * Removes discount for a clothing.
     *
     * @param clothing the clothing to remove discount for
     * @return true if updated, false if not
     */
    @Override
    public boolean removeDiscount(Clothing clothing) {
        boolean updated = false;
        try {
            clothing.removeDiscount();
            updated = storageClient.putClothing(clothing);
        } catch (JsonProcessingException e) {
            System.err.println(e);
        }
        return updated;
    }

    /**
     * Gets the quantities of sizes for a type of clothing.
     *
     * @param type the type of clothing to get quantities sizes for
     * @return amount of clothing for each size of that type
     */
    @Override
    public List<Integer> getQuantitiesForTypeAndSizes(String type) {
        return storageClient.getQuantitiesForTypeAndSizes(type);
    }

    /**
     * Gets the names for clothings from restserver.
     *
     * @return the names for clothings from restserver
     */
    @Override
    public List<String> getNames() {
        return storageClient.getNames();
    }

    /**
     * Gets quantity from restserver.
     *
     * @param name to retrieve quantity for
     * @return quantity from restserver
     */
    public int getQuantity(String name) {
        return storageClient.getQuantity(name);
    }

    /**
     * Gets total value from restserver.
     *
     * @return total value of storage from restserver
     */
    @Override
    public double getTotalValue() {
        return storageClient.getTotalValue();
    }

    /**
     * Gets total quantity from restserver.
     *
     * @return total quantity from restserver
     */
    @Override
    public int getTotalQuantity() {
        return storageClient.getTotalQuantity();
    }
}
