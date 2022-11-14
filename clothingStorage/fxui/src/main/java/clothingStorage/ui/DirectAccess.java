package clothingStorage.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import clothingStorage.core.Clothing;
import clothingStorage.core.Storage;
import clothingStorage.core.StorageStatistics;
import clothingStorage.json.ClothingStoragePersistence;

public class DirectAccess implements Access {

    private ClothingStoragePersistence storagePersistence;
    private Storage storage;

    
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

    public DirectAccess(Storage storage) {
        this.setStorage(storage);
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    @Override
    public Storage getStorage() {
        return this.storage;
    }

    @Override
    public Clothing getClothing(String name) {
        return this.storage.getClothing(name);
    }

    @Override
    public boolean addClothing(Clothing clothing, int quantity) {
        this.storage.addNewClothing(clothing, quantity);
        fireAutoSaveStorage();
        return true;
        
    }

    @Override
    public boolean removeClothing(String name) {
        this.storage.removeClothing(this.storage.getClothing(name));
        fireAutoSaveStorage();
        return true;
    }

    @Override
    public boolean increaseQuantityByOne(String name) {
        this.storage.increaseQuantity(this.storage.getClothing(name), 1);
        fireAutoSaveStorage();
        return true;
    }

    @Override
    public boolean decreaseQuantityByOne(String name) {
        this.storage.decreaseQuantity(this.storage.getClothing(name), 1);
        fireAutoSaveStorage();
        return true;
    }

    @Override
    public boolean increaseQuantity(String name, int quantity) {
        this.storage.increaseQuantity(this.storage.getClothing(name), quantity);
        fireAutoSaveStorage();
        return true;
    }

    @Override
    public boolean decreaseQuantity(String name, int quantity) {
        this.storage.decreaseQuantity(this.storage.getClothing(name), quantity);
        fireAutoSaveStorage();
        return true;
    }

    @Override
    public List<String> getStorageDisplay() {
        return this.storage.storageDisplay();
    }

    @Override
    public List<String> getPriceDisplay() {
        this.storage.setIsSortedPricePage(false);
        return this.storage.priceDisplay();
    }

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

    @Override
    public List<String> getSortedTypePriceDisplay(String type) {
        this.storage.filterOnType(type);
        fireAutoSaveStorage();
        return this.storage.priceDisplay();
    }

    @Override
    public List<String> getSortedBrandPriceDisplay(String brand) {
        this.storage.filterOnBrand(brand);
        fireAutoSaveStorage();
        return this.storage.priceDisplay();
    }

    @Override
    public List<String> getSortedNames() {
        ArrayList<String> names = new ArrayList<>();
        for (Clothing clothing : this.storage.getSortedClothings()) {
            names.add(clothing.getName());
        }
        return names;
    }

    @Override
    public boolean updatePrice(Clothing clothing, double price) {
        this.storage.getClothing(clothing.getName()).setPrice(price, true);
        fireAutoSaveStorage();
        return true;
    }

    @Override
    public boolean updateDiscount(Clothing clothing, double discount) {
        this.storage.getClothing(clothing.getName()).setPriceAfterAddedDiscount(discount);
        fireAutoSaveStorage();
        return true;
    }

    @Override
    public boolean removeDiscount(Clothing clothing) {
        this.storage.getClothing(clothing.getName()).removeDiscount();
        fireAutoSaveStorage();
        return true;
    }

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

    @Override
    public List<String> getNames() {
        ArrayList<String> names = new ArrayList<>();
        for (Clothing clothing : this.storage.getAllClothes().keySet()) {
            names.add(clothing.getName());
        }
        return names;
    }

    @Override
    public int getQuantity(String name) {
        return this.storage.getQuantity(this.storage.getClothing(name));
    }

    @Override
    public double getTotalValue() {
        return StorageStatistics.getTotalValue(this.storage);
    }

    @Override
    public int getTotalQuantity() {
        return StorageStatistics.getTotalQuantity(this.storage);
    }
}