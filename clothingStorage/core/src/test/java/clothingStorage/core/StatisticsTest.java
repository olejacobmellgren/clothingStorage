package clothingStorage.core;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class StatisticsTest {

    Storage storage;

    @BeforeEach
    public void setUpStorage() {
        storage = new Storage();
        Clothing clothing1 = new Clothing("Pants", "Nike", 'M', 88);
        Clothing clothing2 = new Clothing("Socks", "H&M", 'S', 75);
        Clothing clothing3 = new Clothing("Jacket", "Lacoste", 'L', 150);
        Clothing clothing4 = new Clothing("Socks", "Nike", 'M', 35);
        storage.addNewClothing(clothing1, 7);
        storage.addNewClothing(clothing2, 2);
        storage.addNewClothing(clothing3, 5);
        storage.addNewClothing(clothing4, 9);
    }

    @Test
    public void testGetTotalQuantity() {
        assertEquals(23, StorageStatistics.getTotalQuantity(storage));
        Clothing clothing = new Clothing("Shirt", "Adidas", 'L', 43);
        storage.addNewClothing(clothing, 6);
        assertEquals(29, StorageStatistics.getTotalQuantity(storage));
    }

    @Test
    public void testGetTotalValue() {
        assertEquals(1831, StorageStatistics.getTotalValue(storage));
        Clothing clothing = new Clothing("Shirt", "H&M", 'S', 99);
        storage.addNewClothing(clothing, 3);
        assertEquals(2128, StorageStatistics.getTotalValue(storage));
    }

    @Test
    public void testGetQuantityForType() {
        assertEquals(5, StorageStatistics.getQuantityForType(storage, "Jacket"));
        Clothing clothing = new Clothing("Jacket", "Nike", 'L', 46);
        storage.addNewClothing(clothing, 4);
        assertEquals(9, StorageStatistics.getQuantityForType(storage, "Jacket"));

        assertEquals(7, StorageStatistics.getQuantityForType(storage, "Pants"));
        Clothing clothing2 = new Clothing("Pants", "Lacoste", 'M', 74);
        storage.addNewClothing(clothing2, 8);
        assertEquals(15, StorageStatistics.getQuantityForType(storage, "Pants"));
    }

    @Test
    public void testGetQuantityForTypeAndSize() {
        assertEquals(2, StorageStatistics.getQuantityForTypeAndSize(storage, "Socks", 'S'));
        assertEquals(9, StorageStatistics.getQuantityForTypeAndSize(storage, "Socks", 'M'));

        Clothing clothing = new Clothing("Jacket", "Supreme", 'S', 149);
        storage.addNewClothing(clothing, 8);
        assertEquals(5, StorageStatistics.getQuantityForTypeAndSize(storage, "Jacket", 'L'));
        assertEquals(8, StorageStatistics.getQuantityForTypeAndSize(storage, "Jacket", 'S'));
    }
    
}
