package clothingStorage.core;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Represents a storage in a clothing shop
 */
public class Storage {

    private LinkedHashMap<Clothing, Integer>  storageList;

    /**
     * Prerequisites:
     *  - The price is set when an item is added to storage to prevent navigating to the overview page to set the price after the item has been added.
     *    Subsequently, the price can be edited on the overview page with the text area and button.
     * 
     *  - Assume text area is used to add or remove a certain number of items from storage. The updated number of items is not determined in the text field.
     */
    public Storage() {
        this.storageList = new LinkedHashMap<>();
    }

    /**
     * Adds a certain number of clothing items to storage
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
     * Removes clothing item from storage
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
     * Increases number of clothing items by one
     * @param clothing to increase by one
     */
    public void increaseQuantityByOne(Clothing clothing) {
        this.increaseQuantity(clothing, 1);
    } 

    /**
     * Decreases number of clothing items by one
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
     * Increases number of clothing items
     * @param clothing item to be increased
     * @param quantity number of clothing items to add
     */
    public void increaseQuantity(Clothing clothing, int quantity) {
        if (isClothingInStorage(clothing) && isValidQuantity(quantity)) {
            this.storageList.put(clothing, this.getQuantity(clothing) + quantity);
        }
    }

    /**
     * Decreases number of clothing items
     * @param clothing item to be decreased
     * @param quantity number of clothing items to remove
     * @throws IllegalStateException if number of clothing items if less than 0
     */
    public void decreaseQuantity(Clothing clothing, int quantity) {
        if (isClothingInStorage(clothing) && isValidQuantity(quantity) && getQuantity(clothing) - quantity >= 0 ) {
            this.storageList.put(clothing, this.getQuantity(clothing) - quantity);
        } else {
            throw new IllegalStateException("Can not have negative quantity of an item");
        }
    }

    /*
    Bruk denne metoden for å få alle items på lager på en fin måte til hjemsiden, sammen med antall varer. Brukes i Controller i ListView.
    Er på formatet:

    Jacket, S: 5
    Jeans, L: 3
    Socks, M: 8

    */

    public List<String> storageDisplay() {
        List<String> list = new ArrayList<>();
        List<Clothing> keyList = new ArrayList<>(this.getAllClothes().keySet());
        for (Clothing clothing : keyList) {
            list.add(clothing.getName() + "; " + clothing.getBrand() + "; " + clothing.getSize() + "; " + this.getQuantity(clothing));
        }
        return list;
    }

    /*
    Bruk denne metoden for å få alle items på lager på en fin måte til oversiktssiden, sammen med pris. Brukes i Controller i ListView.
    Er på formatet:

    Jacket, Nike: 54,-
    Socks, Adidas: 36,-
    Jeans, Levi's: 60,-
    */

    public List<String> priceDisplay() { 
        List<String> list = new ArrayList<>();
        List<Clothing> keyList = new ArrayList<>(this.getAllClothes().keySet());
        for (Clothing clothing : keyList) {
            list.add(clothing.getName() + "; " + clothing.getBrand() + "; " + clothing.getPrice() + ",-");
        }
        return list;
    }

    /**
     * Checks if the input quantity is valid
     * @param quantity to check
     * @return true if valid, false if invalid
     */
    private boolean isValidQuantity(int quantity) {
        if (quantity>=1) {
            return true;
        }
        return false;
    }

    /**
     * Checks if the clothing item is in storage
     * @param clothing item to check
     * @return true if clothing item is in storage, false if not in storage
     */
    private boolean isClothingInStorage(Clothing clothing) {
        if (this.storageList.containsKey(clothing)) {
            return true;
        }
        return false;
    }

    /**
     * Retrieves quantity of clothing item
     * @param clothing to retrieve quantity of
     * @return number of clothing items
     */
    public int getQuantity(Clothing clothing) {
        return this.storageList.get(clothing);
    }

    /**
     * Retrieves all clothes in storage with a hashmap
     * @return hashmap with all clothing items in storage
     */
    public LinkedHashMap<Clothing, Integer> getAllClothes() {
        return new LinkedHashMap<Clothing, Integer>(this.storageList);
    }

    /**
     * Retrieves clothing item in hashmap
     * @param index of the clothing item to retrieve
     * @return clothing item 
     */
    public Clothing getClothing(int index) {
        ArrayList<Clothing> keyList = new ArrayList<Clothing>(storageList.keySet());
        if (index>=keyList.size()) {
            throw new IllegalStateException("Invalid index, not in storage");
        }
        return keyList.get(index);
    }

    /**
     * Custom string formatting, overrides default toString() function
     */
    @Override
    public String toString() {
        String output = "";
        ArrayList<Clothing> keyList = new ArrayList<Clothing>(storageList.keySet());

        for (Clothing clothing : keyList) {
            if (clothing.equals(keyList.get(keyList.size()-1))) {
                output += clothing + "\n" + "   - Quantity: " + String.valueOf(this.getQuantity(clothing));
            } else {
                output += clothing + "\n" + "   - Quantity: " + String.valueOf(this.getQuantity(clothing)) + "\n";
            }
        }
        return output;
    }

    /* Original tests to see if the functions work */
    /* 
    public static void main(String[] args) {
        Storage storage = new Storage();
        Clothing item = new Clothing("Bukse", "Levi's", 'M', 25);
        Clothing item2 = new Clothing("Jakke", "Adidas", 'S', 54.5);
        Clothing item3 = new Clothing("Jakke", "Nike", 'S', 100);
        Clothing item4 = new Clothing("Sokker", "H&M", 'L', 122);

        

        
        storage.addNewClothing(item, 2);
        storage.addNewClothing(item2, 3);
        storage.addNewClothing(item3, 6);
        storage.addNewClothing(item4, 8);

        System.out.println(storage.storageDisplay());

        System.out.println(storage.getClothing(2));
        System.out.println(item2.equals(item3));
        System.out.println(item2.equals(storage.getClothing(0)));
        
        System.out.println(storage);
        System.out.println("---------");
        storage.increaseQuantity(item, 1);
        System.out.println(storage);
        System.out.println("---------");
        storage.decreaseQuantity(item, 1);
        System.out.println(storage);
        System.out.println("---------");
        storage.increaseQuantityByOne(item2);
        System.out.println(storage);
        System.out.println("---------");
        storage.decreaseQuantityByOne(item2);
        System.out.println(storage);
        System.out.println("---------");
        storage.removeClothing(item);
        System.out.println(storage);
        System.out.println("---------");
        System.out.println(storage.storageDisplay());
        System.out.println(storage.priceDisplay());
    }
    */
}
