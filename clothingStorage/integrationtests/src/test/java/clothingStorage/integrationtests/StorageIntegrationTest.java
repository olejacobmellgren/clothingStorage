package clothingStorage.integrationtests;

import static org.junit.jupiter.api.Assertions.*;

import java.net.URISyntaxException;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import com.fasterxml.jackson.core.JsonProcessingException;

import clothingStorage.client.StorageClient;
import clothingStorage.core.Clothing;
import clothingStorage.core.Storage;
import clothingStorage.restserver.ClothingStorageApplication;
import clothingStorage.restserver.ClothingStorageController;
import clothingStorage.restserver.ClothingStorageService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(classes = {ClothingStorageController.class, ClothingStorageApplication.class, ClothingStorageService.class})
public class StorageIntegrationTest {

    @Autowired
    public ClothingStorageController controller;

    private StorageClient storageClient;


    @BeforeEach
    public void startClient() throws URISyntaxException, JsonProcessingException {
        this.storageClient = new StorageClient();
        
        Clothing nikePants = new Clothing("Pants", "Nike", 'M', 43);
        Clothing adidasPants = new Clothing("Pants", "Adidas", 'S', 99);
        Clothing lacosteJacket = new Clothing("Jacket", "Lacoste", 'L', 13);

        storageClient.putClothing(nikePants);
        storageClient.putClothing(adidasPants);
        storageClient.putClothing(lacosteJacket);
        storageClient.putQuantity(nikePants.getName(), 4);
        storageClient.putQuantity(adidasPants.getName(), 9);
        storageClient.putQuantity(lacosteJacket.getName(), 6);
    }
    
    @Test
    public void testGetStorage() {
        Storage storage = storageClient.getStorage();
        Clothing nikePants = storage.getClothing("PantsNikeM");
        Clothing adidasPants = storage.getClothing("PantsAdidasS");
        Clothing lacosteJacket = storage.getClothing("JacketLacosteL");

        assertEquals(4, storage.getQuantity(nikePants));
        assertEquals(9, storage.getQuantity(adidasPants));
        assertEquals(6, storage.getQuantity(lacosteJacket));
        assertEquals(43, storage.getClothing("PantsNikeM").getPrice());
        assertEquals(99, storage.getClothing("PantsAdidasS").getPrice());
        assertEquals(13, storage.getClothing("JacketLacosteL").getPrice());
    }

    @Test
    public void testGetClothing() {
        Clothing nikePants = storageClient.getClothing("PantsNikeM");
        assertEquals("Pants", nikePants.getType());
        assertEquals("Nike", nikePants.getBrand());
        assertEquals('M', nikePants.getSize());
        assertEquals(43, nikePants.getPrice());
        assertEquals(0.0, nikePants.getDiscount());

        Clothing adidasPants = storageClient.getClothing("PantsAdidasS");
        assertEquals("Pants", adidasPants.getType());
        assertEquals("Adidas", adidasPants.getBrand());
        assertEquals('S', adidasPants.getSize());
        assertEquals(99, adidasPants.getPrice());
        assertEquals(0.0, adidasPants.getDiscount());

        Clothing lacosteJacket = storageClient.getClothing("JacketLacosteL");
        assertEquals("Jacket", lacosteJacket.getType());
        assertEquals("Lacoste", lacosteJacket.getBrand());
        assertEquals('L', lacosteJacket.getSize());
        assertEquals(13, lacosteJacket.getPrice());
        assertEquals(0.0, lacosteJacket.getDiscount());
    }

    @Test
    public void testPutClothing() {
        Clothing supremeShorts = new Clothing("Shorts", "Supreme", 'M', 150);
        try {
            boolean bool = storageClient.putClothing(supremeShorts);
            assertTrue(bool);
        } catch (JsonProcessingException e) {
            fail();
        }
        Clothing supremeShorts2 = storageClient.getClothing("ShortsSupremeM");
        assertEquals(supremeShorts.toString(), supremeShorts2.toString());
        storageClient.removeClothing("ShortsSupremeM");
    }

    @Test
    public void testRemoveClothing() throws JsonProcessingException {
        assertEquals(3, storageClient.getStorage().getAllClothes().size());
        boolean bool = storageClient.removeClothing("JacketLacosteL");
        assertTrue(bool);
        assertEquals(2, storageClient.getStorage().getAllClothes().size());
        
        storageClient.putClothing(new Clothing("Jacket", "Lacoste", 'L', 13));
    }

    @Test
    public void testGetQuantity() {
        int amount = storageClient.getQuantity("JacketLacosteL");
        assertEquals(6, amount);
    }

    @Test
    public void testPutQuantity() {
        boolean bool = storageClient.putQuantity("PantsNikeM", 20);
        assertTrue(bool);
        assertEquals(20, storageClient.getQuantity("PantsNikeM"));
    }

    @Test
    public void testGetNames() {
        List<String> names = storageClient.getNames();
        assertEquals(3, names.size());
        assertEquals("PantsNikeM", names.get(0));
        assertEquals("PantsAdidasS", names.get(1));
        assertEquals("JacketLacosteL", names.get(2));
    }

    @Test
    public void testGetSortedNames() {
        storageClient.getSortedType("Pants");
        List<String> sortedNames = storageClient.getSortedNames();
        assertEquals(2, sortedNames.size());
        assertEquals("PantsNikeM", sortedNames.get(0));
        assertEquals("PantsAdidasS", sortedNames.get(1));
    }

    @Test
    public void testGetSorted() {
        List<String> sortedClothes = storageClient.getSorted(0);
        assertEquals(3, sortedClothes.size());
        assertEquals("Jacket; Lacoste; 13.0kr", sortedClothes.get(0));
        assertEquals("Pants; Nike; 43.0kr", sortedClothes.get(1));
        assertEquals("Pants; Adidas; 99.0kr", sortedClothes.get(2));
    }

    @Test
    public void testGetSortedType() {
        List<String> sortedPants = storageClient.getSortedType("Pants");
        assertEquals(2, sortedPants.size());
        assertEquals("Pants; Nike; 43.0kr", sortedPants.get(0));
        assertEquals("Pants; Adidas; 99.0kr", sortedPants.get(1));
    }

    @Test
    public void testGetSortedBrand() {
        List<String> sortedNike = storageClient.getSortedBrand("Nike");
        assertEquals(1, sortedNike.size());
        assertEquals("Pants; Nike; 43.0kr", sortedNike.get(0));
    }

    @Test
    public void testGetStorageDisplay() {
        List<String> storageDisplay = storageClient.getStorageDisplay();
        assertEquals(3, storageDisplay.size());
        assertEquals("Pants; Nike; M; 4", storageDisplay.get(0));
        assertEquals("Pants; Adidas; S; 9", storageDisplay.get(1));
        assertEquals("Jacket; Lacoste; L; 6", storageDisplay.get(2));
    }

    @Test
    public void testGetPriceDisplay() {
        List<String> priceDisplay = storageClient.getPriceDisplay();
        assertEquals(3, priceDisplay.size());
        assertEquals("Pants; Nike; 43.0kr", priceDisplay.get(0));
        assertEquals("Pants; Adidas; 99.0kr", priceDisplay.get(1));
        assertEquals("Jacket; Lacoste; 13.0kr", priceDisplay.get(2));
    }

    @Test
    public void testGetQuantitiesForTypeAndSizes() {
        List<Integer> sizes = storageClient.getQuantitiesForTypeAndSizes("Pants");
        assertEquals(3, sizes.size());
        assertEquals(9, sizes.get(0));
        assertEquals(4, sizes.get(1));
        assertEquals(0, sizes.get(2));
    }

    @Test
    public void testGetTotalValue() {
        double totalValue = storageClient.getTotalValue();
        assertEquals(1141.0, totalValue);
    }

    @Test
    public void testGetTotalQuantity() {
        int totalQuantity = storageClient.getTotalQuantity();
        assertEquals(19, totalQuantity);
    }
}