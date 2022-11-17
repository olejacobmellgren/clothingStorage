package clothingStorage.core;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

public class StorageTest {
    
    private Storage storage;
    private Clothing leviPantsM;
    private Clothing leviPantsL;
    private Clothing supremeShorts;
    private Clothing louisVuittonJacket;
    private Clothing adidasSocks;
    private Clothing lacosteShirt;
    private Clothing lacosteShorts;

    @BeforeEach
    public void setUp() {
        storage = new Storage();
        leviPantsM = new Clothing("Pants", "Levi's", 'M', 199);
        leviPantsL = new Clothing("Pants", "Levi's", 'L', 199);
        supremeShorts = new Clothing("Shorts", "Supreme", 'S', 159);
        louisVuittonJacket = new Clothing("Jacket", "LouisVuitton", 'L', 1599);
        adidasSocks = new Clothing("Socks", "Adidas", 'M', 99);
        lacosteShirt = new Clothing("Shirt", "Lacoste", 'L', 699);
        lacosteShorts = new Clothing("Shorts", "Lacoste", 'L', 799);

        storage.addNewClothing(supremeShorts, 15);
        storage.addNewClothing(louisVuittonJacket, 14);
        storage.addNewClothing(adidasSocks, 56);
        storage.addNewClothing(lacosteShirt, 11);
        storage.addNewClothing(lacosteShorts, 2);
        storage.decreaseQuantity(lacosteShorts, 2);
    }

    @Test
    public void testConstructor() {
        Storage storage2 = new Storage(storage);
        assertEquals(5, storage2.getAllClothes().size());
    }

    @Test
    public void testAddNewClothing() {
        storage.addNewClothing(leviPantsM, 3);
        assertEquals(3, storage.getQuantity(leviPantsM));
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
            storage.removeClothing(leviPantsM);
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
        storage.addNewClothing(leviPantsM, 5);
        assertEquals("Pants; Levi's; M; 5", storage.storageDisplay().get(0));
    }

    
    @Test
    public void testPriceDisplay() {
        storage = new Storage();
        storage.addNewClothing(leviPantsM, 4);
        storage.addNewClothing(leviPantsL, 6);
        storage.addNewClothing(supremeShorts, 10);
        assertEquals(2, storage.priceDisplay().size());
        assertEquals("Pants; Levi's; 199.0kr", storage.priceDisplay().get(0));
        assertEquals("Shorts; Supreme; 159.0kr", storage.priceDisplay().get(1));
    }

    @Test
    public void testToString() {
        storage = new Storage();
        storage.addNewClothing(leviPantsM, 5);
        storage.addNewClothing(adidasSocks, 4);
        assertEquals(leviPantsM.toString() + "\n" + "   - Quantity: 5" + "\n" + adidasSocks.toString() + "\n" + "   - Quantity: 4false", storage.toString());
    }

    @Test
    public void testGetClothingOnIndex() {
        assertEquals(supremeShorts, storage.getClothing(0));
        assertThrows(IllegalStateException.class, () -> {
            storage.getClothing(8);
        }, "Index is bigger than storage size");
    }

    @Test
    public void testGetClothingOnName() {
        assertEquals(supremeShorts, storage.getClothing("ShortsSupremeS"));
        assertThrows(IllegalArgumentException.class, () -> {
            storage.getClothing("PantsSupremeL");
        }, "Clothing does not exist");
    }

    @Test
    public void testGetIsSortedClothes() {
        assertFalse(storage.getIsSortedClothes());
        storage.sortOnLowestPrice();
        assertTrue(storage.getIsSortedClothes());
    }

    @Test
    public void testSortOnLowestPrice() {
        storage.sortOnLowestPrice();
        assertEquals(Arrays.asList(adidasSocks, supremeShorts, lacosteShirt, lacosteShorts, louisVuittonJacket),
                    storage.getSortedClothings());
    }

    @Test
    public void testSortOnHighestPrice() {
        storage.sortOnHighestPrice();
        assertEquals(Arrays.asList(louisVuittonJacket, lacosteShorts, lacosteShirt, supremeShorts, adidasSocks),
                    storage.getSortedClothings());
    }

    @Test
    public void testFilterOnBrand() {
        storage.filterOnBrand("Lacoste");
        assertEquals(Arrays.asList(lacosteShirt, lacosteShorts),
                    storage.getSortedClothings());
    }

    @Test
    public void testFilterOnType() {
        storage.filterOnType("Shorts");
        assertEquals(Arrays.asList(supremeShorts, lacosteShorts),
                    storage.getSortedClothings());
    }

    @Test
    public void testFilterOnDiscount() {
        supremeShorts.setDiscount(0.5);
        louisVuittonJacket.setDiscount(0.1);
        lacosteShorts.setDiscount(0.3);
        storage.filterOnDiscount();
        assertEquals(Arrays.asList(supremeShorts, louisVuittonJacket, lacosteShorts),
                    storage.getSortedClothings());
    }

    @Test
    public void testGetClothingFromSortedClothes() {
        storage.sortOnHighestPrice();
        assertEquals(louisVuittonJacket, storage.getClothingFromSortedClothes(0));
        assertEquals(lacosteShorts, storage.getClothingFromSortedClothes(1));
        assertThrows(IllegalStateException.class, () -> {
            storage.getClothingFromSortedClothes(8);
        }, "Index is bigger than storage size");
    }

    @Test
    public void testUpdateQuantity() {
        assertEquals(11, storage.getQuantity(lacosteShirt));
        storage.updateQuantity(lacosteShirt, 4);
        assertEquals(4, storage.getQuantity(lacosteShirt));
        assertThrows(IllegalArgumentException.class, () -> {
            storage.updateQuantity(lacosteShirt, -5);;
        }, "Quantity must be greater or equal to 0");
    }

    @Test
    public void testAddSortedClothing() {
        storage.addSortedClothing(lacosteShirt);
        assertEquals(1, storage.getSortedClothings().size());
        storage.addSortedClothing(supremeShorts);
        assertEquals(2, storage.getSortedClothings().size());
    }
} 