package clothingStorage.core;

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
        if (!isClothingInStorage(clothing) && isValidQuantity(quantity)) {
            this.storageList.put(clothing, quantity);
        } else {
            throw new IllegalStateException("This item is already in storage");
        }
    }

    public void removeClothing(Clothing clothing) {
        if (!isClothingInStorage(clothing)) {
            throw new IllegalStateException("This item is not in storage");
        }
        this.storageList.remove(clothing);
    }

    public void increaseQuantityByOne(Clothing clothing) {
        this.increaseQuantity(clothing, 1);
    } 

    public void decreaseQuantityByOne(Clothing clothing) {
        if (getQuantity(clothing) == 0) {
            throw new IllegalStateException("You can not have negative quantity of item");
        }
        this.decreaseQuantity(clothing, 1);
    }

    public void increaseQuantity(Clothing clothing, int quantity) {
        if (isClothingInStorage(clothing) && isValidQuantity(quantity)) {
            this.storageList.put(clothing, this.getQuantity(clothing) + quantity);
        }
    }

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

    public List<String> homepageDisplay() {
        List<String> list = new ArrayList<>();
        ArrayList<Clothing> keyList = new ArrayList<Clothing>(storageList.keySet());

        for (Clothing clothing : keyList) {
            list.add(clothing.getName() + ", " + clothing.getBrand() + ", " + clothing.getSize() + ": " + this.getQuantity(clothing));
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

    public List<String> marketDisplay() { 
        List<String> list = new ArrayList<>();
        ArrayList<Clothing> keyList = new ArrayList<Clothing>(storageList.keySet());

        for (Clothing clothing : keyList) {
            if (clothing.equals(keyList.get(keyList.size()-1))) {
                list.add(clothing.getName() + ", " + clothing.getBrand() + ": " + clothing.getPrice() + ",-");
            } else {
                list.add(clothing.getName() + ", " + clothing.getBrand() + ": " + clothing.getPrice() + ",-" + "\n");
            }
        }
        return list;
    }

    private boolean isValidQuantity(int quantity) {
        if (quantity>=1) {
            return true;
        }
        return false;
    }

    private boolean isClothingInStorage(Clothing clothing) {
        if (this.storageList.containsKey(clothing)) {
            return true;
        }
        return false;
    }

    public int getQuantity(Clothing clothing) {
        return this.storageList.get(clothing);
    }

    public HashMap<Clothing, Integer> getAllClothes() {
        return new HashMap<Clothing, Integer>(this.storageList);
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
        System.out.println(storage.homepageDisplay());
        System.out.println(storage.marketDisplay());
    }
}
