package clothingStorage.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

    //@BeforeEach
    //public void setUp() {
    //
    //}



    @Test
    public void testNewClothingItem() {
        clickOn("#newClothingItem");
        clickOn("#typeOfClothing").write("Jeans");
        clickOn("#brand").clickOn("Adidas");
        clickOn("#size").clickOn("S");
        clickOn("#price").write("150");
        clickOn("#quantity").write("5");
        clickOn("#ok");
        clickOn(1100, 460);
        
        clickOn("#newClothingItem");
        clickOn("#typeOfClothing").write("Jeans");
        clickOn("#brand").clickOn("Adidas");
        clickOn("#size").clickOn("S");
        clickOn("#price").write("150");
        clickOn("#quantity").write("5");
        clickOn(700, 400);

        storage = controller.getController();
        Clothing clothing = new Clothing("Jeans", "Adidas", 'S', 150);
        ListView<String> storageView = lookup("#storageList").query();
        List<String> storageList = storageView.getItems();
        System.out.println(storageList);
        assertEquals(storageList.get(0), storage.homepageDisplay().get(0));
        
        int index = storageView.getSelectionModel().getSelectedIndex();

        System.out.println(clothing.equals(storage.getClothing(0)));
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

