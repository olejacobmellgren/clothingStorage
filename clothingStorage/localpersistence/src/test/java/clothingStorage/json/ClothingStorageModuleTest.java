package clothingStorage.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import clothingStorage.core.Clothing;
import clothingStorage.core.Storage;


public class ClothingStorageModuleTest {

    private static ObjectMapper mapper;
    final static String storageWithTwoClothes = """
    {
        "clothes": [
            {
                "type": "Pants",
                "brand": "Nike",
                "size": "M",
                "price": 199.0,
                "discount": 0.5
            },
            {
                "quantity": 1
            },
            {
                "type": "Shirt",
                "brand": "Adidas",
                "size": "S",
                "price": 599.9,
                "discount": 0.9
            },
            {
                "quantity": 5
            },
            {
                "isSorted":false
            }
        ]
    }
    """;

    @BeforeAll
    public static void setUp() {
    mapper = ClothingStoragePersistence.createObjectMapper();
    }
    

    @Test
    public void testSerializers() {
        Storage storage = createStorageWithTwoClothes();
        try {
            assertEquals(storageWithTwoClothes.replaceAll("\\s+", ""), mapper.writeValueAsString(storage));
        } catch (JsonProcessingException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testDeserializers() {
        try {
            Storage storage = mapper.readValue(storageWithTwoClothes, Storage.class);
            //Så inser dette mest sannsynelig ikke funker ettersom vi ikker har iteratorer 
            assertFalse(storage.getAllClothes().isEmpty());
            LinkedHashMap<Clothing, Integer> clothes = storage.getAllClothes();
            ArrayList<Clothing> keys = new ArrayList<>(clothes.keySet());
            checkClothing(keys.get(0), "Pants", "Nike", 'M', 199.0, 0.5);
            checkClothing(keys.get(1), "Shirt", "Adidas", 'S', 599.9, 0.9);
            assertFalse(clothes.size() > 2);
        } catch (JsonProcessingException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testSerializersDeserializers() {
        Storage storage = new Storage();
        Clothing clothing1 = new Clothing("Pants", "Nike", 'M', 199.0);
        Clothing clothing2 = new Clothing("Shirt", "Adidas", 'S', 599.9);
        Clothing clothing3 = new Clothing("Shorts", "Nike", 'L', 20.0);
        storage.addNewClothing(clothing1, 1);
        storage.addNewClothing(clothing2, 1);
        storage.addNewClothing(clothing3, 2);
        try {
            String json = mapper.writeValueAsString(storage);
            Storage storage2 = mapper.readValue(json, Storage.class);
            Clothing c1 = storage2.getClothing(0);
            Clothing c2 = storage2.getClothing(1);
            assertEquals("Pants", c1.getType());
            assertEquals("Shirt", c2.getType());
            checkClothing(clothing1, c1);
            checkClothing(clothing2, c2);
            assertFalse(clothing1.getType() == clothing3.getType());
        } catch (JsonProcessingException e) {
        fail(e.getMessage());
        }
    }

    //HELPFUNCTIONS

    static Storage createStorageWithTwoClothes() {
        Storage storage = new Storage();
        Clothing clothing1 = new Clothing("Pants", "Nike", 'M', 199.0);
        Clothing clothing2 = new Clothing("Shirt", "Adidas", 'S', 599.9);
        clothing1.setDiscount(0.5);
        clothing2.setDiscount(0.9);
        storage.addNewClothing(clothing1, 1);
        storage.addNewClothing(clothing2, 5);
        return storage;
    }

    static void checkClothing(Clothing clothing, String type, String brand, char size, Double price, Double discount) {
        assertEquals(type, clothing.getType());
        assertEquals(brand, clothing.getBrand());
        assertEquals(size, clothing.getSize());
        assertTrue(price == clothing.getPrice());
        assertTrue(discount == clothing.getDiscount());
    }

    static void checkClothing(Clothing clothing1, Clothing clothing2) {
        checkClothing(clothing1, clothing2.getType(), clothing2.getBrand(), clothing2.getSize(), clothing2.getPrice(), clothing2.getDiscount());
    }



}
