package clothingStorage.ui;

import static org.junit.jupiter.api.Assertions.*;

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
import javafx.scene.control.ListView;
import javafx.stage.Stage;


public class StoragePageControllerTest extends ApplicationTest {

    private StoragePageController controller;
    private Parent root;
    private Stage stage;
    private Storage storage;
    
    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        final FXMLLoader loader = new FXMLLoader(getClass().getResource("StoragePage.fxml"));
        root = loader.load();
        this.controller = loader.getController();
        stage.setScene(new Scene(root));
        stage.show();
    }
    
    @BeforeEach
    public void setupClothingItems() {
        storage = new Storage();
        Clothing clothing1 = new Clothing("Jeans", "Nike", 'S', 10);
        Clothing clothing2 = new Clothing("Shorts", "Louis Vuitton", 'M', 20);
        Clothing clothing3 = new Clothing("Socks", "Adidas", 'L', 30);
        storage.addNewClothing(clothing1, 5);
        storage.addNewClothing(clothing2, 8);
        storage.addNewClothing(clothing3, 4);
        controller.setStorage(storage);
    }

    @Test
    public void testPricePageButton() {
        clickOn("#pricePageButton");
        assertEquals("Clothing Prices", this.stage.getTitle());
        clickOn("#storagePageButton");
        assertEquals("Clothing Storage", this.stage.getTitle());
    }

    @Test
    public void testNewClothingItem() {
        clickOn("#newClothingItem");
        assertEquals("New Clothing", this.stage.getTitle());
        clickOn("#cancel");
        assertEquals("Clothing Storage", this.stage.getTitle());
    }

    @Test
    public void testRemoveClothing() {
        clickOn("#storageList");
        clickOn(LabeledMatchers.hasText("Jeans; Nike; S; 5"));
        clickOn("#removeClothingItem");
        ListView<String> storageView = lookup("#storageList").query();
        int storageListSize = storageView.getItems().size();
        assertEquals(2, storageListSize);
    }

    @Test
    public void testRemoveClothingError() {
        clickOn("#removeClothingItem");
        assertEquals("You need to select an item from the list", controller.getErrorMessage());
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
        clickOn("#quantity").write("3");
        clickOn("#addQuantity");
        ListView<String> storageView = lookup("#storageList").query();
        List<String> storageList = storageView.getItems();
        String[] nikeJeans = storageList.get(0).split(";");
        int quantity = Integer.parseInt(nikeJeans[3].strip());
        assertEquals(8, quantity);
    }
    
    @Test
    public void testRemoveQuantity() {
        clickOn("#storageList").clickOn(LabeledMatchers.hasText("Jeans; Nike; S; 5"));
        clickOn("#quantity").write("4");
        clickOn("#removeQuantity");
        ListView<String> storageView = lookup("#storageList").query();
        List<String> storageList = storageView.getItems();
        String[] nikeJeans = storageList.get(0).split(";");
        int quantity = Integer.parseInt(nikeJeans[3].strip());
        assertEquals(1, quantity);
    }
    
    @Test
    public void testErrorOnChangingQuantity() {
        controller.setStorage(new Storage());
        clickOn("#increaseByOne");
        assertEquals("Add a new clothing to storage first", controller.getErrorMessage());
        clickOn(LabeledMatchers.hasText("OK"));
        clickOn("#decreaseByOne");
        assertEquals("Add a new clothing to storage first", controller.getErrorMessage());
        clickOn(LabeledMatchers.hasText("OK"));
        clickOn("#addQuantity");
        assertEquals("Add a new clothing to storage first", controller.getErrorMessage());
        clickOn(LabeledMatchers.hasText("OK"));
        clickOn("#removeQuantity");
        assertEquals("Add a new clothing to storage first", controller.getErrorMessage());
        clickOn(LabeledMatchers.hasText("OK"));
    }

    @Test
    public void testErrorNoItemSelected() {
        clickOn("#increaseByOne");
        assertEquals("Select a clothing before increasing quantity", controller.getErrorMessage());
        clickOn(LabeledMatchers.hasText("OK"));
        clickOn("#decreaseByOne");
        assertEquals("Select a clothing before decreasing quantity", controller.getErrorMessage());
        clickOn(LabeledMatchers.hasText("OK"));
        clickOn("#addQuantity");
        assertEquals("Select a clothing before increasing quantity", controller.getErrorMessage());
        clickOn(LabeledMatchers.hasText("OK"));
        clickOn("#removeQuantity");
        assertEquals("Select a clothing before decreasing quantity", controller.getErrorMessage());
        clickOn(LabeledMatchers.hasText("OK"));
    }

    @Test
    public void testErrorNotSpecifyQuantity() {
        clickOn("#storageList").clickOn(LabeledMatchers.hasText("Socks; Adidas; L; 4"));
        clickOn("#addQuantity");
        assertEquals("Specify quantity first in textfield", controller.getErrorMessage());
        clickOn(LabeledMatchers.hasText("OK"));
        clickOn("#removeQuantity");
        assertEquals("Specify quantity first in textfield", controller.getErrorMessage());
        clickOn(LabeledMatchers.hasText("OK"));
    }

    @Test
    public void testErrorInputNotNumber() {
        clickOn("#quantity").write("hei");
        clickOn("#storageList").clickOn(LabeledMatchers.hasText("Socks; Adidas; L; 4"));
        clickOn("#addQuantity");
        assertEquals("Input must be a number", controller.getErrorMessage());
        clickOn(LabeledMatchers.hasText("OK"));
        clickOn("#removeQuantity");
        assertEquals("Input must be a number", controller.getErrorMessage());
        clickOn(LabeledMatchers.hasText("OK"));
    }
    
}

