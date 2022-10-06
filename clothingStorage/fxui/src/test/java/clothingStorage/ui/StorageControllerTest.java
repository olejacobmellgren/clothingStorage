package clothingStorage.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.control.LabeledMatchers;

import clothingStorage.core.Clothing;
import clothingStorage.core.Storage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;


public class StorageControllerTest extends ApplicationTest {

    private StorageController controller;
    private Parent root;
    private Storage storage;

    @Override
    public void start(final Stage stage) throws Exception {
        final FXMLLoader loader = new FXMLLoader(getClass().getResource("Storage_test.fxml"));
        root = loader.load();
        this.controller = loader.getController();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @BeforeEach
    public void setupClothingItems() {
        Storage storage = new Storage();
        Clothing clothing1 = new Clothing("Jeans", "Nike", 'S', 10);
        Clothing clothing2 = new Clothing("Shorts", "Louis Vuitton", 'M', 20);
        Clothing clothing3 = new Clothing("Socks", "Adidas", 'L', 30);
        storage.addNewClothing(clothing1, 5);
        storage.addNewClothing(clothing2, 8);
        storage.addNewClothing(clothing3, 4);
        controller.setStorage(storage);
        controller.updateStorageList();
    }

    @Test
    public void testNewClothingItem() {
        clickOn("#newClothingItem");
        clickOn("#typeOfClothing").write("Jeans");
        clickOn("#brand").clickOn("Adidas");
        clickOn("#size").clickOn("S");
        clickOn("#price").write("150");
        clickOn("#quantity").write("5");
        clickOn("#ok");
        clickOn(LabeledMatchers.hasText("OK"));

        
        Clothing clothing = new Clothing("Jeans", "Adidas", 'S', 150);
        //assertEquals(clothing.toString(), storage.getClothing(storage.getAllClothes().size()-1).toString());
        ListView<String> storageView = lookup("#storageList").query();
        List<String> storageList = storageView.getItems();
        Clothing newClothing = makeClothingFromListView(storageList.get(storageList.size()-1));
        assertTrue(clothing.equals(newClothing));
    }

    

    @Test
    public void testIncreaseClothingItemByOne() {
        clickOn("#storageList");
        clickOn(LabeledMatchers.hasText("Jeans; Nike; S; 5"));
        clickOn("#increaseByOne");
        ListView<String> storageView = lookup("#storageList").query();
        List<String> storageList = storageView.getItems();
        String[] nikeJeans = storageList.get(0).split(";");
        int quantity = Integer.parseInt(nikeJeans[3].strip());
        assertEquals(6, quantity);
    }

    

    @Test
    public void testDecreaseClothingItemByOne() {
        clickOn("#storageList");
        clickOn(LabeledMatchers.hasText("Jeans; Nike; S; 5"));
        clickOn("#decreaseByOne");
        ListView<String> storageView = lookup("#storageList").query();
        List<String> storageList = storageView.getItems();
        String[] nikeJeans = storageList.get(0).split(";");
        int quantity = Integer.parseInt(nikeJeans[3].strip());
        assertEquals(4, quantity);
    }
    

    @Test
    public void testAddQuantity() {
        clickOn("#storageList");
        clickOn(LabeledMatchers.hasText("Jeans; Nike; S; 5"));
        clickOn("#newQuantity").write("3");
        clickOn("#addQuantity");
        ListView<String> storageView = lookup("#storageList").query();
        List<String> storageList = storageView.getItems();
        String[] nikeJeans = storageList.get(0).split(";");
        int quantity = Integer.parseInt(nikeJeans[3].strip());
        assertEquals(8, quantity);
    }

    
    @Test
    public void testRemoveQuantity() {
        clickOn("#storageList");
        clickOn(LabeledMatchers.hasText("Jeans; Nike; S; 5"));
        clickOn("#newQuantity").write("4");
        clickOn("#removeQuantity");
        ListView<String> storageView = lookup("#storageList").query();
        List<String> storageList = storageView.getItems();
        String[] nikeJeans = storageList.get(0).split(";");
        int quantity = Integer.parseInt(nikeJeans[3].strip());
        assertEquals(1, quantity);
    }
    



    

    private Clothing makeClothingFromListView(String clothing) {
        String[] clothingProperties = clothing.split(";");
        String type = clothingProperties[0].strip();
        String brand = clothingProperties[1].strip();
        char size = clothingProperties[2].strip().charAt(0);
        return new Clothing(type, brand, size, 150);
    }

    /*
    private void checkSelectedClothingItem(int index) {
        final ListView<String> storageView = lookup("#storage").query();
        assertEquals(index, storageView.getSelectionModel().getSelectedIndex());
    }
    */

    


    






    /*
     * Tester vi trenger:
     * 
     * Legge til Clothing
     * Sjekke at errormessage dukker opp på feil input i jeans
     * Sjekke at errormessage dukker opp på feil input i quantity
     * Fjerne Clothing
     * Øke beholdning med 1
     * Øke beholdning med x
     * Minke beholdning med 1
     * Minke beholdning med x
     * Endre pris
     * Legge til rabatt
     * 
     */




}

