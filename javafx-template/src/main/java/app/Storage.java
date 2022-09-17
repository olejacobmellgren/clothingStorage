package app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Storage {

    private HashMap<Item, List<Object>>  storageList;

    /* 
    Forutsetninger:
    - Tar utgangspunkt i at når man legger til et item i lageret definerer man pris også. På denne måten slipper brukeren
      å gå på oversiktssiden for å bestemme pris etter å ha lagt til et nytt item i lageret. Pris kan deretter oppdateres i 
      oversiktssiden

    - Antar at tekstfeltet for å legge til eller fjerne antall items fra lager gjør nettopp dette, og at man ikke
      bestemmer et oppdatert antall i tekstfeltet.

    Ting kan lett endres på om forutsetningene mine viser seg til å være ugunstige
    */ 

    public Storage() {
        this.storageList = new HashMap<>();
    }

    public void addNewItem(Item item, int quantity, float price) {
        if (!isItemInStorage(item) && isValidQuantity(quantity) && isValidPrice(price)) {
            List<Object> list = new ArrayList<>(Arrays.asList(quantity, price));
            this.storageList.put(item, list);
        } else {
            throw new IllegalStateException("This item is already in storage");
        }
    }

    public void removeItem(Item item) {
        if (!isItemInStorage(item)) {
            throw new IllegalStateException("This item is not in storage");
        }
        this.storageList.remove(item);
    }

    public void IncreaseInventoryByOne(Item item) {
        this.AddQuantity(item, 1);
    } 

    public void DecreaseInventoryByOne(Item item) {
        if (getQuantity(item) == 0) {
            throw new IllegalStateException("You can not have negative quantity of item");
        }
        this.removeQuantity(item, 1);
    }

    public void AddQuantity(Item item, int quantity) {
        if (isItemInStorage(item) && isValidQuantity(quantity)) {
            List<Object> list = this.getKey(item);
            list.set(0, (Integer) list.get(0) + quantity);
        }
    }

    public void removeQuantity(Item item, int quantity) {
        if (isItemInStorage(item) && isValidQuantity(quantity)) {
            List<Object> list = this.getKey(item);
            list.set(0, (Integer) list.get(0) - quantity);
        }
    }

    /*
    Bruk denne metoden for å få alle items på lager på en fin måte, sammen med antall varer. Brukes i Controller i ListView.
    Er på formatet:

    Jacket: 5
    Hat: 3
    Socks: 8

    */

    public String homepageDisplay() {
        String output = "";
        ArrayList<Item> keyList = new ArrayList<Item>(storageList.keySet());

        for (Item item : keyList) {
            if (item.equals(keyList.get(keyList.size()-1))) {
                output += item.getTitle() + ": " + this.getQuantity(item);
            } else {
                output += item.getTitle() + ": " + this.getQuantity(item) + "\n";
            }
        }
        return output;
    }

    /*
    Bruk denne metoden for å få alle items på lager på en fin måte, sammen med pris. Brukes i Controller i ListView.
    Er på formatet:

    Socks
        Sale: No
        Price: 5kr
    Jeans
        Sale: Yes
        Price before: 10kr
        Price now: 6kr
        Total sale: 40%
    
    */

    public String MarketDisplay() { // ikke ferdig metode, Item-klassen må bli ferdig først
        String output = "";
        ArrayList<Item> keyList = new ArrayList<Item>(storageList.keySet());

        for (Item item : keyList) {
            if (item.equals(keyList.get(keyList.size()-1))) {
                output += item.getTitle() + ": " + this.getQuantity(item);
            } else {
                output += item.getTitle() + ": " + this.getQuantity(item) + "\n";
            }
        }
        return output;
        
    }

    private boolean isValidQuantity(int quantity) {
        if (quantity>=1) {
            return true;
        }
        return false;
    }

    private boolean isValidPrice(float price) {
        if (price >=0) {
            return true;
        }
        return false;
    }

    private boolean isItemInStorage(Item item) {
        if (this.storageList.containsKey(item)) {
            return true;
        }
        return false;
    }

    public List<Object> getKey(Item item) {
        return this.storageList.get(item);
    }

    public int getQuantity(Item item) {
        return (Integer) this.getKey(item).get(0);
    }

    public int getPrice(Item item) {
        return (Integer) this.getKey(item).get(1);
    }

    @Override
    public String toString() {
        String output = "";
        ArrayList<Item> keyList = new ArrayList<Item>(storageList.keySet());

        for (Item item : keyList) {
            if (item.equals(keyList.get(keyList.size()-1))) {
                output += item.getTitle() + ", Antall: " + String.valueOf(storageList.get(item).get(0)) + ", Pris: " + String.valueOf(storageList.get(item).get(1)) + " kr";
            } else {
                output += item.getTitle() + ", Antall: " + String.valueOf(storageList.get(item).get(0)) + ", Pris: " + String.valueOf(storageList.get(item).get(1)) + " kr" + "\n";
            }
        }
        return output;
    }

    /*
    Små tester for oppførsel i main-metode
    Alt funker som det skal
    */

    public static void main(String[] args) {
        Storage storage = new Storage();
        Item item = new Book("Nike sko", "Andreas");
        Item item2 = new Book("Bukse", "Andreas");
        storage.addNewItem(item, 2, 35);
        storage.addNewItem(item2, 3, 60);
        System.out.println(storage);
        System.out.println("---------");
        storage.AddQuantity(item, 1);
        System.out.println(storage);
        System.out.println("---------");
        storage.removeQuantity(item, 2);
        System.out.println(storage);
        System.out.println("---------");
        storage.IncreaseInventoryByOne(item2);
        System.out.println(storage);
        System.out.println("---------");
        storage.DecreaseInventoryByOne(item2);
        System.out.println(storage);
        System.out.println("---------");
        storage.removeItem(item);
        System.out.println(storage);
        System.out.println("---------");
    }
}
