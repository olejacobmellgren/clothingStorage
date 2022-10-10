package clothingStorage.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class StorageTest {
    
    private Storage storage;
    private Clothing leviJeans;
    private Clothing supremeShorts;
    private Clothing louisVuittonJacket;
    private Clothing adidasSocks;
    private Clothing lacosteShirt;
    private Clothing lacosteShorts;

    @BeforeEach
    public void testItems(){
        Storage storage = new Storage();
        Clothing leviJeans = new Clothing("jeans", "Levi's", 'M', 199);
        Clothing supremeShorts = new Clothing("shorts", "Supreme", 'S', 159);
        Clothing louisVuittonJacket = new Clothing("jacket", "Louis Vuitton", 'L', 1599);
        Clothing adidasSocks = new Clothing("socks", "adidas", 'M', 99);
        Clothing lacosteShirt = new Clothing("shirt", "Lacoste", 'L', 699);
        Clothing lacosteShorts = new Clothing("shorts", "Lacoste", 'L', 699);

        storage.addNewClothing(lacosteShirt, 11);
        storage.addNewClothing(supremeShorts, 15);
        storage.addNewClothing(louisVuittonJacket, 14);
        storage.addNewClothing(adidasSocks, 56);
        storage.addNewClothing(lacosteShorts, 1);
    }

    @Test
    public void CheckAddNewClothing(){
        storage.addNewClothing(leviJeans, 3);
        Assertions.assertEquals(3, storage.getQuantity(leviJeans));
    }

    @Test
    public void CheckAddNewClothingErrorHandler(){
        IllegalArgumentException thrown = assertThrows(
           IllegalArgumentException.class,
           () -> storage.addNewClothing(supremeShorts, 3),
           "This item is already in storage"
    );

    assertTrue(thrown.getMessage().contains("This item is already in storage"));
    }


    @Test 
    public void CheckRemoveClothing(){
        storage.removeClothing(adidasSocks);
        Assertions.assertEquals(4, storage.getAllClothes().size());
    }

    @Test
    public void CheckRemoveClothingErrorHandler(){
        IllegalArgumentException thrown = assertThrows(
           IllegalArgumentException.class,
           () -> storage.removeClothing(leviJeans),
           "This item is not in storage");

    assertTrue(thrown.getMessage().contains("This item is not in storage"));
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
        IllegalArgumentException thrown = assertThrows(
           IllegalArgumentException.class,
           () -> storage.decreaseQuantityByOne(lacosteShorts),
           "You can not have negative quantity of item");

    assertTrue(thrown.getMessage().contains("You can not have negative quantity of item"));
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
        IllegalArgumentException thrown = assertThrows(
           IllegalArgumentException.class,
           () -> storage.decreaseQuantity(lacosteShirt, 15),
           "Can not have negative quantity of an item");

    assertTrue(thrown.getMessage().contains("Can not have negative quantity of an item"));
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