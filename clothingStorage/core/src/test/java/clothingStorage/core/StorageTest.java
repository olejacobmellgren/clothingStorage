package clothingStorage.core;

import java.beans.Transient;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StorageTest {
    
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
        Assertions.assertEquals("This item is already in storage", storage.addNewClothing(supremeShorts));
    }

    @Test 
    public void CheckRemoveClothing(){
        Assertions.assertEquals(null, storage.removeClothing(adidasSocks));
    }

    @Test
    public void CheckRemoveClothingErrorHandler(){
        Assertions.assertEquals("This item is not in storage", storage.removeClothing(leviJeans));
    }

    @Test
    public void CheckIncreaseQuantityByOne(){
        Assertions.assertEquals(12, storage.increaseQuantityByOne(lacosteShirt));
    }

    @Test
    public void CheckDecreaseQuantityByOne(){
        Assertions.assertEquals(10, storage.decreaseQuantityByOne(lacosteShirt));
    }

    @Test
    public void CheckDecreaseQuantityByOneErrorHandler(){
        Assertions.assertEquals("You can not have negative quantity of item", storage.decreaseQuantityByOne(lacosteShorts));
    }

    @Test
    public void CheckIncreaseQuantity(){
        Assertions.assertEquals(21, storage.increaseQuantity(lacosteShirt, 10));
    }

    @Test
    public void CheckDecreaseQuantity(){
        Assertions.assertEquals(1, storage.decreaseQuantity(lacosteShirt, 10));
    }

    @Test
    public void CheckDecreaseQuantityErrorHandler(){
        Assertions.assertEquals("Can not have negative quantity of an item", storage.decreaseQuantity(lacosteShirt, 15));
    }

    @Test
    public void CheckisValidQuantity(){
        Assertions.assertEquals(true, storage.isValidQuantity(4));
        Assertions.assertEquals(false, storage.isValidQuantity(0));
    }

    @Test
    public void CheckisClothingInStorage(){
        Assertions.assertEquals(true, storage.isClothingInStorage(lacosteShirt));
        Assertions.assertEquals(false, storage.isClothingInStorage(leviJeans));
    }

    @Test
    public void CheckGetQuantity(){
        Assertions.assertEquals(56, storage.getQuantity(adidasSocks));
    }

    @Test
    public void CheckHomageDisplay(){
        storage.homepageDisplay();

        Assertions.assertEquals("jeans", storage.homepageDisplay().getName("jeans"));
        Assertions.assertEquals("jeans", storage.homepageDisplay().getName("jeans"));
    }

    
    @Test
    public void CheckMarketDisplay(){
        storage.marketDisplay();

        Assertions.assertEquals("jeans", storage.marketDisplay().getName("jeans"));
    }
}


/*
 * lage test for å legge til clothing objekt
 * sjekke at key i hashmap for storage har 3 på socks
 */