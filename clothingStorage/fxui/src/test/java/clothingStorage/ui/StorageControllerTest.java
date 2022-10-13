package clothingStorage.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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


public class StorageControllerTest extends ApplicationTest {

    private StorageController controller;
    private Parent root;
    private Storage storage;

    /**
     * Starting the app
     *
     * @result ClothingStorage will be openend and used for the tests
     */
    @Override
    public void start(final Stage stage) throws Exception {
        final FXMLLoader loader = new FXMLLoader(getClass().getResource("Storage_test.fxml"));
        root = loader.load();
        this.controller = loader.getController();
        stage.setScene(new Scene(root));
        stage.show();
    }
    
    /**
     * Setting up clothing items
     *
     * @result initialize controller with Storage containing three Clothing-items
     */
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
        controller.updateStorageList();
        controller.updatePriceList();
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
        assertTrue(clothing.equalsButDifferentPrice(newClothing));
    }

    /**
     *
     */
    @Test
    public void testErrorNewClothingNoFields() {
        clickOn("#newClothingItem");
        clickOn("#ok");
        assertEquals("Fill in all fields", controller.getErrorMessage());

    }

    @Test
    public void testErrorNewClothingWrongName() {
        clickOn("#newClothingItem");
        clickOn("#typeOfClothing").write("jacket");
        clickOn("#brand").clickOn("Adidas");
        clickOn("#size").clickOn("L");
        clickOn("#price").write("159");
        clickOn("#quantity").write("8");
        clickOn("#ok");
        assertEquals("Name of clothing must start with uppercase letter", controller.getErrorMessage());

    }

    @Test
    public void testErrorNewClothingWrongNumber() {
        clickOn("#newClothingItem");
        clickOn("#typeOfClothing").write("Jacket");
        clickOn("#brand").clickOn("Adidas");
        clickOn("#size").clickOn("L");
        clickOn("#price").write("hei");
        clickOn("#quantity").write("8");
        clickOn("#ok");
        assertEquals("Price must be a positive decimal number" +  "\n" 
            + "Quantity must a positive integer", controller.getErrorMessage());
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

    private Clothing makeClothingFromListView(String clothing) {
        String[] clothingProperties = clothing.split(";");
        String type = clothingProperties[0].strip();
        String brand = clothingProperties[1].strip();
        char size = clothingProperties[2].strip().charAt(0);
        return new Clothing(type, brand, size, 150);
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
        clickOn("#storageList").clickOn(LabeledMatchers.hasText("Jeans; Nike; S; 5"));
        clickOn("#newQuantity").write("4");
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
        clickOn("#newQuantity").write("hei");
        clickOn("#storageList").clickOn(LabeledMatchers.hasText("Socks; Adidas; L; 4"));
        clickOn("#addQuantity");
        assertEquals("Input must be a number", controller.getErrorMessage());
        clickOn(LabeledMatchers.hasText("OK"));
        clickOn("#removeQuantity");
        assertEquals("Input must be a number", controller.getErrorMessage());
        clickOn(LabeledMatchers.hasText("OK"));
    }
    
    @Test
    public void testNewPrice() {
        clickOn("#pricePageButton");
        clickOn("#priceList");
        clickOn(LabeledMatchers.hasText("Shorts; Louis Vuitton; 20.0,-"));
        clickOn("#newPrice").write("30");
        clickOn("#confirmNewPrice");
        ListView<String> priceView = lookup("#priceList").query();
        List<String> priceList = priceView.getItems();
        String[] nikeJeans = priceList.get(1).split(";");
        double price = Double.parseDouble(nikeJeans[2].split(",")[0].strip());
        assertEquals(30, price);
    }

    @Test
    public void testDiscount() {
        clickOn("#pricePageButton");
        clickOn("#priceList");
        clickOn(LabeledMatchers.hasText("Jeans; Nike; 10.0,-"));
        clickOn("#discount").write("50");
        clickOn("#confirmDiscount");
        ListView<String> priceView = lookup("#priceList").query();
        List<String> priceList = priceView.getItems();
        String[] nikeJeans = priceList.get(0).split(";");
        double price = Double.parseDouble(nikeJeans[2].split(",")[0].strip());
        assertEquals(5, price);
    }

    @Test
    public void testRemoveDiscount() {
        clickOn("#pricePageButton");
        clickOn("#priceList");
        clickOn(LabeledMatchers.hasText("Jeans; Nike; 10.0,-"));
        clickOn("#discount").write("50");
        clickOn("#confirmDiscount");
        clickOn("#removeDiscount");
        ListView<String> priceView = lookup("#priceList").query();
        List<String> priceList = priceView.getItems();
        String[] nikeJeans = priceList.get(0).split(";");
        double price = Double.parseDouble(nikeJeans[2].split(",")[0].strip());
        assertEquals(5, price);
    }

    @Test
    public void testErrorNoItemSelectedForDiscount() {
        clickOn("#pricePageButton");
        clickOn("#discount").write("50");
        clickOn("#confirmDiscount");
        assertEquals("You need to select an item from the list", controller.getErrorMessage());
        clickOn("#removeDiscount");
        assertEquals("You need to select an item from the list", controller.getErrorMessage());
        clickOn(LabeledMatchers.hasText("OK"));
    }

    @Test
    public void testErrorNoItemSelectedForPriceChange() {
        clickOn("#pricePageButton");
        clickOn("#newPrice").write("30");
        clickOn("#confirmNewPrice");
        assertEquals("You need to select an item from the list", controller.getErrorMessage());        
    }

    @Test
    public void testErrorNotSpecifyPrice() {
        clickOn("#pricePageButton");
        clickOn("#priceList").clickOn(LabeledMatchers.hasText("Jeans; Nike; 10.0,-"));
        clickOn("#confirmNewPrice");
        assertEquals("Specify price first in textfield", controller.getErrorMessage());
        clickOn(LabeledMatchers.hasText("OK"));
        clickOn("#confirmDiscount");
        assertEquals("Specify price first in textfield", controller.getErrorMessage());
        clickOn(LabeledMatchers.hasText("OK"));
    }

    @Test
    public void testErrorInputNotNumberForPrice() {
        clickOn("#pricePageButton");
        clickOn("#newPrice").write("hei");
        clickOn("#priceList").clickOn(LabeledMatchers.hasText("Jeans; Nike; 10.0,-"));
        clickOn("#confirmNewPrice");
        assertEquals("Input must be a number", controller.getErrorMessage());
        clickOn(LabeledMatchers.hasText("OK"));
        clickOn("#confirmDiscount");
        assertEquals("Input must be a number", controller.getErrorMessage());
        clickOn(LabeledMatchers.hasText("OK"));
        clickOn("#storagePageButton");
    }
}

