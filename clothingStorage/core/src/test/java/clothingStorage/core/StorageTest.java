package app;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StorageTest {
    
    Storage storage = new Storage();
    Clothing newJeans = new Clothing("jeans", "Nike", 'M', 199);

    @Test
    public void CheckAddNewClothing(){
        storage.addNewClothing(newJeans, 3);
        Assertions.assertEquals(3, storage.getQuantity(newJeans));
    }
}


/*
 * lage test for å legge til clothing objekt
 * sjekke at key i hashmap for storage har 3 på socks
 */