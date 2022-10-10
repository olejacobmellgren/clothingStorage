package clothingStorage.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        storage = new Storage();
        leviJeans = new Clothing("Jeans", "Levi's", 'M', 199);
        supremeShorts = new Clothing("Shorts", "Supreme", 'S', 159);
        louisVuittonJacket = new Clothing("Jacket", "Louis Vuitton", 'L', 1599);
        adidasSocks = new Clothing("Socks", "Adidas", 'M', 99);
        lacosteShirt = new Clothing("Shirt", "Lacoste", 'L', 699);
        lacosteShorts = new Clothing("Shorts", "Lacoste", 'L', 699);

        storage.addNewClothing(leviJeans, 3);
        storage.addNewClothing(lacosteShirt, 11);
        storage.addNewClothing(supremeShorts, 15);
        storage.addNewClothing(louisVuittonJacket, 14);
        storage.addNewClothing(adidasSocks, 56);
        storage.addNewClothing(lacosteShorts, 1);
    }

    @Test
    public void CheckAddNewClothing(){
        Clothing clothing = new Clothing("Hat", "Nike", 'M', 39);
        storage.addNewClothing(clothing, 3);
        Assertions.assertEquals(3, storage.getQuantity(clothing));
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