package clothingStorage.core;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Represents a storage in a clothing shop.
 */
public class Storage {

    private LinkedHashMap<Clothing, Integer>  storageList;
    private ArrayList<Clothing> sortedClothes;
    private boolean isSortedPricePage = false;

    /**
     * Initializes Storage object.
     */
    public Storage() {
        this.storageList = new LinkedHashMap<>();
        this.sortedClothes = new ArrayList<>();
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
     * Updates quantity of Clothing directly.
     *
     * @param clothing item to be updated
     * @param quantity to be set
     */
    public void updateQuantity(Clothing clothing, int quantity) {
        if (isClothingInStorage(clothing) && isValidQuantity(quantity)) {
            this.storageList.put(clothing, quantity);
        } else if (!isValidQuantity(quantity)) {
            throw new IllegalArgumentException("Quantity must be greater or equal to 1");
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
            list.add(clothing.getType() + "; " + clothing.getBrand() + "; "
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
        List<String> list2 = new ArrayList<>();
        List<Clothing> keyList = new ArrayList<>(this.getAllClothes().keySet());
        if (this.isSortedPricePage == true) {
            keyList = sortedClothes;
        }
        for (Clothing clothing : keyList) {
            if (list.isEmpty()) {
                list.add(clothing.getType() + "; " + clothing.getBrand()
                    + "; " + clothing.getPrice() + "kr");
                list2.add(clothing.getType() + "; " + clothing.getBrand());
                continue;
            } else if (list2.contains(clothing.getType() + "; " + clothing.getBrand())) {
                continue;
            } else {
                list.add(clothing.getType() + "; " + clothing.getBrand()
                    + "; " + clothing.getPrice() + "kr");
                list2.add(clothing.getType() + "; " + clothing.getBrand());
            }
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
            if (clothing.equalsButDifferentPrice(clothing2)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Sets if Price-Page is sorted or not.
     *
     * @param bool boolean to set
     */
    public void setIsSortedPricePage(boolean bool) {
        this.isSortedPricePage = bool;
        if (this.isSortedPricePage == false) {
            this.sortedClothes = new ArrayList<>();
        }
    }

    /**
     * Retrieves boolean of wether Price-Page is sorted or not.
     *
     * @return true if sorted, false if not
     */
    public boolean getIsSortedClothes() {
        return this.isSortedPricePage;
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
     * Retrieves sorted clothes in Price-Page. 
     *
     * @return sorted list
     */
    public ArrayList<Clothing> getSortedClothings() {
        return new ArrayList<Clothing>(this.sortedClothes);
    }

    /**
     * Adds a clothing to the sorted Clothing-list.
     *
     * @param clothing to be added
     */
    public void addSortedClothing(Clothing clothing) {
        if (this.getSortedClothings() == null || this.getSortedClothings().size() == 0) {
            sortedClothes = new ArrayList<>();
            sortedClothes.add(clothing);
        } else {
            sortedClothes.add(clothing);
        }
    }

    /**
     * Sorts the storage-list based on lowest price.
     */
    public void sortOnLowestPrice() {
        List<Clothing> keyList = new ArrayList<Clothing>(this.getAllClothes().keySet());
        List<Clothing> sortedList = keyList.stream()
                                            .sorted(Comparator.comparing(Clothing::getPrice))
                                            .toList();
        sortedClothes = new ArrayList<>(sortedList);
        this.setIsSortedPricePage(true);
    }

    /**
     * Sorts the storage-list based on highest price.
     */
    public void sortOnHighestPrice() {
        List<Clothing> keyList = new ArrayList<Clothing>(this.getAllClothes().keySet());
        List<Clothing> sortedList = keyList.stream()
                                        .sorted(Comparator.comparing(Clothing::getPrice).reversed())
                                        .toList();
        sortedClothes = new ArrayList<>(sortedList);
        this.setIsSortedPricePage(true);
    }

    /**
     * Filters the storage-list based on brand.
     *
     * @param brand to be filtered on
     */
    public void filterOnBrand(String brand) {
        List<Clothing> keyList = new ArrayList<Clothing>(this.getAllClothes().keySet());
        List<Clothing> filteredList = keyList.stream()
                                    .filter(c -> c.getBrand().equals(brand))
                                    .toList();
        sortedClothes = new ArrayList<>(filteredList);
        this.setIsSortedPricePage(true);
    }

    /**
     * Filters the storage-list based on type.
     *
     * @param type to be filtered on
     */
    public void filterOnType(String type) {
        List<Clothing> keyList = new ArrayList<Clothing>(this.getAllClothes().keySet());
        List<Clothing> filteredList = keyList.stream()
                                    .filter(c -> c.getType().equals(type))
                                    .toList();                      
        sortedClothes = new ArrayList<>(filteredList);
        this.setIsSortedPricePage(true);
    }

    /**
     * Filters the storage-list based on discount.
     */
    public void filterOnDiscount() {
        List<Clothing> keyList = new ArrayList<Clothing>(this.getAllClothes().keySet());
        List<Clothing> filteredList = keyList.stream()
                                        .filter(c -> c.isOnDiscount() == true).toList();
        sortedClothes = new ArrayList<>(filteredList);
        this.setIsSortedPricePage(true);
    }

    /**
     * Retrieves clothing item in hashmap on index.
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
     * Retrieves clothing item in hashmap on name.
     *
     * @param name of the clothing item to retrieve
     * @return clothing item 
     */
    public Clothing getClothing(String name) {
        ArrayList<Clothing> keyList = new ArrayList<Clothing>(storageList.keySet());
        for (Clothing clothing : keyList) {
            if (clothing.getName().equals(name)) {
                return clothing;
            }
        }
        throw new IllegalArgumentException("Clothing does not exist");
    }

    /**
     * Retrieves clothing item in sorted list.
     *
     * @param index of the clothing item to retrieve
     * @return clothing item
     */
    public Clothing getClothingFromSortedClothes(int index) {
        if (index >= sortedClothes.size()) {
            throw new IllegalStateException("Invalid index, not in storage");
        }
        return this.sortedClothes.get(index);
    }

    /**
     * Custom string formatting, overrides default toString() function.
     */
    @Override
    public String toString() {
        StringBuffer output = new StringBuffer();
        ArrayList<Clothing> keyList = new ArrayList<Clothing>(storageList.keySet());

        for (Clothing clothing : keyList) {
            if (clothing.equals(keyList.get(keyList.size() - 1))) {
                output.append(clothing + "\n" + "   - Quantity: "
                    + String.valueOf(this.getQuantity(clothing)));
            } else {
                output.append(clothing + "\n" + "   - Quantity: "
                    + String.valueOf(this.getQuantity(clothing)) + "\n");
            }
        }
        output.append(this.getIsSortedClothes());
        return output.toString();
    }
}