package clothingStorage.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Contains tests of all functions in Storage.java
 */
public class StorageTest {
    
    /**
     * Initializes storage
     */
    private Storage storage;

    /**
     * Initializes example clothing items
     */
    private Clothing leviJeans;
    private Clothing supremeShorts;
    private Clothing louisVuittonJacket;
    private Clothing adidasSocks;
    private Clothing lacosteShirt;
    private Clothing lacosteShorts;

    /**
     * Creates test objects before each test and adds some to storage
     */
    @BeforeEach
    public void testItems(){
        storage = new Storage();
        leviJeans = new Clothing("Jeans", "Levi's", 'M', 199);
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

    /**
     * Checks if clothing can be added to storage
     * @result 3 Levi jeans will be added to storage
     */
    @Test
    public void CheckAddNewClothing(){
        storage.addNewClothing(leviJeans, 3);
        Assertions.assertEquals(3, storage.getQuantity(leviJeans));
    }

    /**
     * Checks if exception to AddNewClothing function is thrown when existing clothing items is added to storage
     * @result Adds three clothing items to storage that already exist and exception is thrown
     */
    @Test
    public void CheckAddNewClothingErrorHandler(){
        assertThrows(IllegalStateException.class, () -> 
        {storage.addNewClothing(supremeShorts, 3);}, 
        "This item is already in storage");
    }


    /**
     * Checks if clothing items can be removed from storage
     * @result One key is removed from hashmap
     */
    @Test 
    public void CheckRemoveClothing(){
        Assertions.assertEquals(5, storage.getAllClothes().size());
        storage.removeClothing(adidasSocks);
        Assertions.assertEquals(4, storage.getAllClothes().size());
    }

    @Test
    public void CheckRemoveClothingErrorHandler(){
        assertThrows(IllegalStateException.class,
           () -> {storage.removeClothing(leviJeans);},
           "This item is not in storage");
    }

    @Test
    public void CheckIncreaseQuantityByOne(){
        storage.increaseQuantityByOne(lacosteShirt);
        Assertions.assertEquals(12, storage.getQuantity(lacosteShirt));
    }

    @Test
    public void CheckDecreaseQuantityByOne(){
        storage.decreaseQuantityByOne(lacosteShirt);
        Assertions.assertEquals(10, storage.getQuantity(lacosteShirt));
    }

    @Test
    public void CheckDecreaseQuantityByOneErrorHandler(){
        assertThrows(IllegalStateException.class,
           () -> {storage.decreaseQuantityByOne(lacosteShorts);},
           "You can not have negative quantity of item");
    }

    @Test
    public void CheckIncreaseQuantity(){
        storage.increaseQuantity(lacosteShirt, 10);
        Assertions.assertEquals(21, storage.getQuantity(lacosteShirt));
    }

    @Test
    public void CheckDecreaseQuantity(){
        storage.decreaseQuantity(lacosteShirt, 10);
        Assertions.assertEquals(1, storage.getQuantity(lacosteShirt));
    }

    @Test
    public void CheckDecreaseQuantityErrorHandler(){
        // bruk dette formatet for å sjekke exceptions
        assertThrows(IllegalStateException.class, () -> {
        storage.decreaseQuantity(lacosteShirt, 15);
        }, "Can not have negative quantity of an item");
    }

    @Test
    public void CheckGetQuantity(){
        Assertions.assertEquals(56, storage.getQuantity(adidasSocks));
    }

    /* @Test
    public void CheckHomageDisplay(){
        storage.homepageDisplay();

        Assertions.assertEquals("shorts", storage.homepageDisplay().get(1));
        Assertions.assertEquals("jeans", storage.homepageDisplay().get(3));
    }

    
    @Test
    public void CheckMarketDisplay(){
        storage.marketDisplay();

        Assertions.assertEquals("jeans", storage.marketDisplay().getName(leviJeans));
    }*/
} 


/*
 * lage test for å legge til clothing objekt
 * sjekke at key i hashmap for storage har 3 på socks
 */
