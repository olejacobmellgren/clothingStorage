package app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Storage {

    private HashMap<Clothing, Integer>  storageList;

    /* 
    Forutsetninger:
    - Tar utgangspunkt i at når man legger til et item i lageret definerer man pris også. På denne måten slipper brukeren
      å gå på oversiktssiden for å bestemme pris etter å ha lagt til et nytt item i lageret. Pris kan deretter oppdateres i 
      oversiktssiden med tekstfelt  + knapp

    - Antar at tekstfeltet for å legge til eller fjerne antall items fra lager gjør nettopp dette, og at man ikke
      bestemmer et oppdatert antall i tekstfeltet.
    */ 

    public Storage() {
        this.storageList = new HashMap<>();
    }

    public void addNewClothing(Clothing clothing, int quantity) {
        if (!isItemInStorage(clothing) && isValidQuantity(quantity)) {
            this.storageList.put(clothing, quantity);
        } else {
            throw new IllegalStateException("This item is already in storage");
        }
    }

    public void removeItem(Clothing clothing) {
        if (!isItemInStorage(clothing)) {
            throw new IllegalStateException("This item is not in storage");
        }
        this.storageList.remove(clothing);
    }

    public void IncreaseInventoryByOne(Clothing clothing) {
        this.AddQuantity(clothing, 1);
    } 

    public void DecreaseInventoryByOne(Clothing clothing) {
        if (getQuantity(clothing) == 0) {
            throw new IllegalStateException("You can not have negative quantity of item");
        }
        this.removeQuantity(clothing, 1);
    }

    public void AddQuantity(Clothing clothing, int quantity) {
        if (isItemInStorage(clothing) && isValidQuantity(quantity)) {
            this.storageList.put(clothing, this.getQuantity(clothing) + quantity);
        }
    }

    public void removeQuantity(Clothing clothing, int quantity) {
        if (isItemInStorage(clothing) && isValidQuantity(quantity)) {
            this.storageList.put(clothing, this.getQuantity(clothing) - quantity);
        }
    }

    /*
    Bruk denne metoden for å få alle items på lager på en fin måte, sammen med antall varer. Brukes i Controller i ListView.
    Er på formatet:

    Jacket, S: 5
    Jeans, L: 3
    Socks, M: 8

    */

    public List<String> homepageDisplay() {
        List<String> list = new ArrayList<>();
        ArrayList<Clothing> keyList = new ArrayList<Clothing>(storageList.keySet());

        for (Clothing clothing : keyList) {
            list.add(clothing.getName() + ", " + clothing.getSize() + ": " + this.getQuantity(clothing));
        }
        return list;
    }

    /*
    Bruk denne metoden for å få alle items på lager på en fin måte, sammen med pris. Brukes i Controller i ListView.
    Usikker på hvilket format det skal være på... 
    Mitt forslag:

    Socks
        - Brand: Nike
        - Size: M
        - Sale: No
        - Price: 5kr
    Jeans
        - Brand: Levi's
        - Size: L
        - Sale: Yes
        - Price before: 10kr
        - Price now: 6kr
        - Total sale: 40%
    
    Blir dette for mye informasjon til et item?
    */

    public String MarketDisplay() { // ikke ferdig metode, er usikker på format
        String output = "";
        ArrayList<Clothing> keyList = new ArrayList<Clothing>(storageList.keySet());

        for (Clothing clothing : keyList) {
            if (clothing.equals(keyList.get(keyList.size()-1))) {
                output += clothing.getName() + ": " + this.getQuantity(clothing);
            } else {
                output += clothing.getName() + ": " + this.getQuantity(clothing) + "\n";
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

    private boolean isItemInStorage(Clothing clothing) {
        if (this.storageList.containsKey(clothing)) {
            return true;
        }
        return false;
    }

    public int getQuantity(Clothing clothing) {
        return this.storageList.get(clothing);
    }

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

    /*
    Små tester for oppførsel i main-metode
    Alt funker som det skal utifra forutsetninger
    */

    public static void main(String[] args) {
        Storage storage = new Storage();
        Clothing item = new Clothing("Bukse", "Levi's", 'M', 25);
        Clothing item2 = new Clothing("Jakke", "Nike", 'S', 54.5);
        storage.addNewClothing(item, 2);
        storage.addNewClothing(item2, 3);
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
        System.out.println(storage.homepageDisplay());
    }
}


