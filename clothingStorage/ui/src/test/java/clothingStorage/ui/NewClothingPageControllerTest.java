package clothingStorage.ui;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.InputStreamReader;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import clothingStorage.core.Clothing;
import clothingStorage.core.Storage;
import clothingStorage.json.ClothingStoragePersistence;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class NewClothingPageControllerTest extends ApplicationTest {
    
    private NewClothingPageController controller;
    private Parent root;
    private Stage stage;
    private Storage storage;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        final FXMLLoader loader = new FXMLLoader(getClass().getResource("NewClothingPage.fxml"));
        root = loader.load();
        this.controller = loader.getController();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @BeforeEach
    public void setupClothingItems() {
        try {
            ClothingStoragePersistence storagePersistence = new ClothingStoragePersistence();
            storage = storagePersistence.readClothingStorage(new InputStreamReader(getClass().getResourceAsStream("storage-test.json")));
        } catch (IOException e) {
            fail("Couldn't load storage-test.json");
        }
        controller.setStorage(storage);
    }

    @Test
    public void testCancel() {
        clickOn("#cancel");
        assertEquals("Clothing Storage", this.stage.getTitle());
        clickOn("#newClothingItem");
        assertEquals("New Clothing", this.stage.getTitle());
    }

    @Test
    public void testNewClothingItem() {
        clickOn("#type").clickOn("Pants");
        clickOn("#brand").clickOn("Adidas");
        clickOn("#size").clickOn("S");
        clickOn("#price").write("150");
        clickOn("#quantity").write("5");
        clickOn("#ok");

        Clothing clothing = new Clothing("Pants", "Adidas", 'S', 150);
        assertEquals("You successfully added the following: " + clothing.toString(), controller.getConfirmMessage());
    }

    @Test
    public void testErrorClothingItemAlreadyInStorageWithDifferentPrice() {
        clickOn("#type").clickOn("Pants");
        clickOn("#brand").clickOn("Nike");
        clickOn("#size").clickOn("S");
        clickOn("#price").write("150");
        clickOn("#quantity").write("5");
        clickOn("#ok");
        assertEquals("Clothing already exists in different size, but with different price. Price should be: 10.0" , controller.getErrorMessage());
    }

    @Test
    public void testErrorNewClothingNoFields() {
        clickOn("#ok");
        assertEquals("Fill in all fields", controller.getErrorMessage());
    }

    @Test
    public void testErrorNewClothingWrongNumber() {
        clickOn("#type").clickOn("Jacket");
        clickOn("#brand").clickOn("Adidas");
        clickOn("#size").clickOn("L");
        clickOn("#price").write("hei");
        clickOn("#quantity").write("8");
        clickOn("#ok");
        assertEquals("Price must be a positive decimal number" +  "\n" 
            + "Quantity must a positive integer", controller.getErrorMessage());
    }
}