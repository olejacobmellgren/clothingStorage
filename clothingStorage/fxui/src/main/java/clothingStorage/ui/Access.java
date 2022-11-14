package clothingStorage.ui;

import java.util.List;

import clothingStorage.core.Clothing;
import clothingStorage.core.Storage;

public interface Access {

    public Storage getStorage();

    public Clothing getClothing(String name);

    public boolean addClothing(Clothing clothing, int quantity);

    public boolean removeClothing(String name);
    
    public boolean increaseQuantityByOne(String name);

    public boolean decreaseQuantityByOne(String name);

    public boolean increaseQuantity(String name, int quantity);

    public boolean decreaseQuantity(String name, int quantity);

    public List<String> getStorageDisplay();

    public List<String> getPriceDisplay();

    public List<String> getSortedPriceDisplay(int id);

    public List<String> getSortedTypePriceDisplay(String type);

    public List<String> getSortedBrandPriceDisplay(String brand);

    public List<String> getSortedNames();

    public List<String> getNames();

    public void updatePrice(Clothing clothing, int price);

    public void updateDiscount(Clothing clothing, double discount);

    public void removeDiscount(Clothing clothing);

    public List<Integer> getQuantityForType(String type);

    public int getQuantity(String name);

    public double getTotalValue();

    public int getTotalQuantity();

}
