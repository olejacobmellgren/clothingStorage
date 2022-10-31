package clothingStorage.core;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class StorageTest {
    
    private Storage storage;
    private Clothing leviPants;
    private Clothing supremeShorts;
    private Clothing louisVuittonJacket;
    private Clothing adidasSocks;
    private Clothing lacosteShirt;
    private Clothing lacosteShorts;

    @BeforeEach
    public void setUp() {
        storage = new Storage();
        leviPants = new Clothing("Pants", "Levi's", 'M', 199);
        supremeShorts = new Clothing("Shorts", "Supreme", 'S', 159);
        louisVuittonJacket = new Clothing("Jacket", "Louis Vuitton", 'L', 1599);
        adidasSocks = new Clothing("Socks", "Adidas", 'M', 99);
        lacosteShirt = new Clothing("Shirt", "Lacoste", 'L', 699);
        lacosteShorts = new Clothing("Shorts", "Lacoste", 'L', 699);

        storage.addNewClothing(lacosteShirt, 11);
        storage.addNewClothing(supremeShorts, 15);
        storage.addNewClothing(louisVuittonJacket, 14);
        storage.addNewClothing(adidasSocks, 56);
        storage.addNewClothing(lacosteShorts, 2);
        storage.decreaseQuantity(lacosteShorts, 2);
    }

    @Test
    public void testAddNewClothing() {
        storage.addNewClothing(leviPants, 3);
        assertEquals(3, storage.getQuantity(leviPants));
        assertThrows(IllegalStateException.class, () -> {
            storage.addNewClothing(supremeShorts, 3);
        }, "This item is already in storage");
    }

    @Test 
    public void testRemoveClothing() {
        assertEquals(5, storage.getAllClothes().size());
        storage.removeClothing(adidasSocks);
        assertEquals(4, storage.getAllClothes().size());
        assertThrows(IllegalStateException.class, () -> {
            storage.removeClothing(leviPants);
        }, "This item is not in storage");
    }

    @Test
    public void testIncreaseQuantity() {
        storage.increaseQuantity(lacosteShirt, 10);
        assertEquals(21, storage.getQuantity(lacosteShirt));
        assertThrows(IllegalArgumentException.class, () -> {
            storage.increaseQuantity(lacosteShirt, -5);
        }, "Input can not be negative");
    }

    @Test
    public void testDecreaseQuantity() {
        storage.decreaseQuantity(lacosteShirt, 10);
        assertEquals(1, storage.getQuantity(lacosteShirt));
        assertThrows(IllegalStateException.class, () -> {
            storage.decreaseQuantity(lacosteShirt, 15);
        }, "Can not have negative quantity of an item");
        assertThrows(IllegalArgumentException.class, () -> {
            storage.decreaseQuantity(lacosteShirt, -5);
        }, "Input can not be negative");
    }

    @Test
    public void testStorageDisplay() {
        storage = new Storage();
        storage.addNewClothing(leviPants, 5);
        assertEquals("Pants; Levi's; M; 5", storage.storageDisplay().get(0));
    }

    
    @Test
    public void testPriceDisplay() {
        storage = new Storage();
        storage.addNewClothing(leviPants, 4);
        assertEquals("Pants; Levi's; 199.0,-", storage.priceDisplay().get(0));
    }

    @Test
    public void testToString() {
        storage = new Storage();
        storage.addNewClothing(leviPants, 5);
        storage.addNewClothing(adidasSocks, 4);
        assertEquals(leviPants.toString() + "\n" + "   - Quantity: 5" + "\n" + adidasSocks.toString() + "\n" + "   - Quantity: 4", storage.toString());
    }

    @Test
    public void testGetClothing() {
        assertEquals(supremeShorts, storage.getClothing(1));
        assertThrows(IllegalStateException.class, () -> {
            storage.getClothing(8);
        }, "Index is bigger than storage size");
    }
} 
