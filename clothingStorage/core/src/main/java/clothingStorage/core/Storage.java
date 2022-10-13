package clothingStorage.core;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Represents a storage in a clothing shop.
 */
public class Storage {

    private LinkedHashMap<Clothing, Integer>  storageList;

    /**
     * Initializes Storage object.
     */
    public Storage() {
        this.storageList = new LinkedHashMap<>();
    }

    /**
     * Adds a certain number of clothing items to storage.
     *
     * @param clothing item to add to storage
     * @param quantity number of clothing items to add
     * @throws IllegalStateException if item is already in storage
     */
    public void addNewClothing(Clothing clothing, int quantity) {
        if (!isClothingInStorage(clothing) && isValidQuantity(quantity)) {
            this.storageList.put(clothing, quantity);
        } else {
            throw new IllegalStateException("This item is already in storage");
        }
    }

    /**
     * Removes clothing item from storage.
     *
     * @param clothing item to be removed
     * @throws IllegalStateException if item is not in storage
     */
    public void removeClothing(Clothing clothing) {
        if (!isClothingInStorage(clothing)) {
            throw new IllegalStateException("This item is not in storage");
        }
        this.storageList.remove(clothing);
    }

    /**
     * Increases number of clothing items by one.
     *
     * @param clothing to increase by one
     */
    public void increaseQuantityByOne(Clothing clothing) {
        this.increaseQuantity(clothing, 1);
    } 

    /**
     * Decreases number of clothing items by one.
     *
     * @param clothing to decrease by one
     * @throws IllegalStateException if the quantity is less than 0
     */
    public void decreaseQuantityByOne(Clothing clothing) {
        if (getQuantity(clothing) == 0) {
            throw new IllegalStateException("You can not have negative quantity of item");
        }
        this.decreaseQuantity(clothing, 1);
    }

    /**
     * Increases number of clothing items.
     *
     * @param clothing item to be increased
     * @param quantity number of clothing items to add
     * @throws IllegalArgumentException if quantity is less than 1
     */
    public void increaseQuantity(Clothing clothing, int quantity) {
        if (isClothingInStorage(clothing) && isValidQuantity(quantity)) {
            this.storageList.put(clothing, this.getQuantity(clothing) + quantity);
        } else {
            throw new IllegalArgumentException("Quantity must be greater or equal to 1");
        }
    }

    /**
     * Decreases number of clothing items.
     *
     * @param clothing item to be decreased
     * @param quantity number of clothing items to remove
     * @throws IllegalArgumentException if quantity is less than 1
     * @throws IllegalStateException if number of clothing items is less than 0
     */
    public void decreaseQuantity(Clothing clothing, int quantity) {
        if (isClothingInStorage(clothing) && isValidQuantity(quantity) 
            && getQuantity(clothing) - quantity >= 0) {
            this.storageList.put(clothing, this.getQuantity(clothing) - quantity);
        } else if (!isValidQuantity(quantity)) {
            throw new IllegalArgumentException("Quantity must be greater or equal to 1");
        } else {
            throw new IllegalStateException("Can not have negative quantity of an item");
        }
    }

    /**
     * Iterates through all clothing items in storage and formats them to a string.
     * Used to display clothes in storage-page.
     *
     * @return list of strings for every clothing in storage
     */
    public List<String> storageDisplay() {
        List<String> list = new ArrayList<>();
        List<Clothing> keyList = new ArrayList<>(this.getAllClothes().keySet());
        for (Clothing clothing : keyList) {
            list.add(clothing.getName() + "; " + clothing.getBrand() + "; "
                + clothing.getSize() + "; " + this.getQuantity(clothing));
        }
        return list;
    }

    /**
     * Iterates through all clothing items and formats them to a string.
     * Used to display clothes in price-page.
     *
     * @return list of strings for every clothing in storage
     */
    public List<String> priceDisplay() { 
        List<String> list = new ArrayList<>();
        List<Clothing> keyList = new ArrayList<>(this.getAllClothes().keySet());
        for (Clothing clothing : keyList) {
            list.add(clothing.getName() + "; " + clothing.getBrand()
                + "; " + clothing.getPrice() + ",-");
        }
        return list;
    }

    /**
     * Checks if the input quantity is valid.
     *
     * @param quantity to check
     * @return true if valid, false if invalid
     */
    private boolean isValidQuantity(int quantity) {
        if (quantity >= 1) {
            return true;
        }
        return false;
    }

    /**
     * Checks if the clothing item is in storage.
     *
     * @param clothing item to check
     * @return true if clothing item is in storage, false if not in storage
     */
    public boolean isClothingInStorage(Clothing clothing) {
        List<Clothing> keyList = new ArrayList<>(this.getAllClothes().keySet());
        for (Clothing clothing2 : keyList) {
            if (clothing.equals(clothing2)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retrieves quantity of clothing item.
     *
     * @param clothing to retrieve quantity of
     * @return number of clothing items
     */
    public int getQuantity(Clothing clothing) {
        return this.storageList.get(clothing);
    }

    /**
     * Retrieves all clothes in storage with a hashmap.
     *
     * @return hashmap with all clothing items in storage
     */
    public LinkedHashMap<Clothing, Integer> getAllClothes() {
        return new LinkedHashMap<Clothing, Integer>(this.storageList);
    }

    /**
     * Retrieves clothing item in hashmap.
     *
     * @param index of the clothing item to retrieve
     * @return clothing item 
     */
    public Clothing getClothing(int index) {
        ArrayList<Clothing> keyList = new ArrayList<Clothing>(storageList.keySet());
        if (index >= keyList.size()) {
            throw new IllegalStateException("Invalid index, not in storage");
        }
        return keyList.get(index);
    }

    /**
     * Custom string formatting, overrides default toString() function.
     */
    @Override
    public String toString() {
        String output = "";
        ArrayList<Clothing> keyList = new ArrayList<Clothing>(storageList.keySet());

        for (Clothing clothing : keyList) {
            if (clothing.equals(keyList.get(keyList.size() - 1))) {
                output += clothing + "\n" + "   - Quantity: "
                    + String.valueOf(this.getQuantity(clothing));
            } else {
                output += clothing + "\n" + "   - Quantity: "
                    + String.valueOf(this.getQuantity(clothing)) + "\n";
            }
        }
        return output;
    }
}
