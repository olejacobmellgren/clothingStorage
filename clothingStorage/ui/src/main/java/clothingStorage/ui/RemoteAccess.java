package clothingStorage.ui;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;

import clothingStorage.client.StorageClient;
import clothingStorage.core.Clothing;
import clothingStorage.core.Storage;

public class RemoteAccess implements Access {

    StorageClient storageClient;

    public RemoteAccess() throws URISyntaxException {
        storageClient = new StorageClient();
    }

    @Override
    public Storage getStorage() {
        Storage storage = storageClient.getStorage();
        return storage;
    }

    @Override
    public Clothing getClothing(String name) {
        Clothing clothing = storageClient.getClothing(name);
        return clothing;
    }

    @Override
    public boolean addClothing(Clothing clothing, int quantity) {
        // legge til både Clothing og quantity til serveren
        boolean added = false;
        try {
            added = storageClient.putClothing(clothing);
        } catch (JsonProcessingException e) {
            System.err.println("Could not add clothing");
        }
        boolean added2 = storageClient.putQuantity(clothing.getName(), quantity);
        return added && added2;
    }

    @Override
    public boolean removeClothing(String name) {
        return storageClient.removeClothing(name);
    }

    @Override
    public boolean increaseQuantityByOne(String name) {
        int oldQuantity = this.getQuantity(name);
        int newQuantity = oldQuantity + 1;
        boolean increased = storageClient.putQuantity(name, newQuantity);
        return increased;
    }

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

    @Override
    public boolean increaseQuantity(String name, int quantity) {
        int oldQuantity = this.getQuantity(name);
        int newQuantity = oldQuantity + quantity;
        boolean increased = storageClient.putQuantity(name, newQuantity);
        return increased;
    }

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

    @Override
    public List<String> getStorageDisplay() {
        return storageClient.getStorageDisplay();
    }

    @Override
    public List<String> getPriceDisplay() {
        return storageClient.getPriceDisplay();
    }

    @Override
    public List<String> getSortedPriceDisplay(int id) {
        return storageClient.getSorted(id);
    }

    @Override
    public List<String> getSortedTypePriceDisplay(String type) {
        return storageClient.getSortedType(type);
    }

    @Override
    public List<String> getSortedBrandPriceDisplay(String brand) {
        return storageClient.getSortedBrand(brand);
    }

    @Override
    public List<String> getSortedNames() {
        return storageClient.getSortedNames();
    }

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

    @Override
    public List<Integer> getQuantitiesForTypeAndSizes(String type) {
        return storageClient.getQuantitiesForTypeAndSizes(type);
    }

    @Override
    public List<String> getNames() {
        return storageClient.getNames();
    }

    @Override
    public int getQuantity(String name) {
        return storageClient.getQuantity(name);
    }

    @Override
    public double getTotalValue() {
        return storageClient.getTotalValue();
    }

    @Override
    public int getTotalQuantity() {
        return storageClient.getTotalQuantity();
    }
}
