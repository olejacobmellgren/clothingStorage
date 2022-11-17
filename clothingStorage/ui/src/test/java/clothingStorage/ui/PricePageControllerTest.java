package clothingStorage.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.control.LabeledMatchers;

import clothingStorage.core.Storage;
import clothingStorage.json.ClothingStoragePersistence;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class PricePageControllerTest extends ApplicationTest {
    
    private PricePageController controller;
    private Parent root;
    private Stage stage;
    private Storage storage;


    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        final FXMLLoader loader = new FXMLLoader(getClass().getResource("PricePage.fxml"));
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
    public void testStoragePageButton() {
        clickOn("#storagePageButton");
        assertEquals("Clothing Storage", this.stage.getTitle());
        clickOn("#pricePageButton");
        assertEquals("Clothing Prices", this.stage.getTitle());
    }

    @Test
    public void testNewPrice() {
        clickOn("#priceList");
        clickOn(LabeledMatchers.hasText("Shorts; Louis Vuitton; 20.0kr"));
        clickOn("#newPrice").write("30");
        clickOn("#confirmNewPrice");
        ListView<String> priceView = lookup("#priceList").query();
        List<String> priceList = priceView.getItems();
        String[] nikePants = priceList.get(1).split(";");
        double price = Double.parseDouble(nikePants[2].split("k")[0].strip());
        assertEquals(30, price);
    }

    @Test
    public void testDiscount() {
        clickOn("#priceList");
        clickOn(LabeledMatchers.hasText("Pants; Nike; 10.0kr"));
        clickOn("#discount").write("50");
        clickOn("#confirmDiscount");
        ListView<String> priceView = lookup("#priceList").query();
        List<String> priceList = priceView.getItems();
        String[] nikePants = priceList.get(0).split(";");
        double price = Double.parseDouble(nikePants[2].split("k")[0].strip());
        assertEquals(5, price);
    }

    @Test
    public void testRemoveDiscount() {
        clickOn("#priceList");
        clickOn(LabeledMatchers.hasText("Pants; Nike; 10.0kr"));
        clickOn("#discount").write("50");
        clickOn("#confirmDiscount");
        clickOn("#priceList");
        clickOn(LabeledMatchers.hasText("Pants; Nike; 5.0kr"));
        clickOn("#removeDiscount");
        ListView<String> priceView = lookup("#priceList").query();
        List<String> priceList = priceView.getItems();
        String[] nikePants = priceList.get(0).split(";");
        double price = Double.parseDouble(nikePants[2].split("k")[0].strip());
        assertEquals(10, price);
    }

    @Test
    public void testErrorClothingNotOnDiscount() {
        clickOn("#priceList");
        clickOn(LabeledMatchers.hasText("Pants; Nike; 10.0kr"));
        clickOn("#removeDiscount");
        assertEquals("Clothing is not on discount", controller.getErrorMessage());
    }

    @Test
    public void testErrorDiscountNotValid() {
        clickOn("#priceList");
        clickOn(LabeledMatchers.hasText("Pants; Nike; 10.0kr"));
        clickOn("#discount").write("120");
        clickOn("#confirmDiscount");
        assertEquals("Given discount is not valid", controller.getErrorMessage());
        clickOn(LabeledMatchers.hasText("OK"));
    }

    @Test
    public void testErrorClothingAlreadyOnDiscount() {
        clickOn("#priceList");
        clickOn(LabeledMatchers.hasText("Pants; Nike; 10.0kr"));
        clickOn("#discount").write("50");
        clickOn("#confirmDiscount");
        clickOn("#priceList");
        clickOn(LabeledMatchers.hasText("Pants; Nike; 5.0kr"));
        clickOn("#discount").write("50");
        clickOn("#confirmDiscount");
        assertEquals("Clothing is already on discount", controller.getErrorMessage());
        ListView<String> priceView = lookup("#priceList").query();
        List<String> priceList = priceView.getItems();
        String[] nikePants = priceList.get(0).split(";");
        double price = Double.parseDouble(nikePants[2].split("k")[0].strip());
        assertEquals(5, price);
    }

    @Test
    public void testErrorNoItemSelectedForDiscount() {
        clickOn("#discount").write("50");
        clickOn("#confirmDiscount");
        assertEquals("Select a clothing before adding discount", controller.getErrorMessage());
        clickOn(LabeledMatchers.hasText("OK"));
        clickOn("#removeDiscount");
        assertEquals("Select a clothing before removing discount", controller.getErrorMessage());
    }

    @Test
    public void testErrorNoItemSelectedForPriceChange() {
        clickOn("#newPrice").write("30");
        clickOn("#confirmNewPrice");
        assertEquals("Select a clothing before changing price", controller.getErrorMessage());        
    }

    @Test
    public void testErrorNotSpecifyPriceAndDiscount() {
        clickOn("#priceList").clickOn(LabeledMatchers.hasText("Pants; Nike; 10.0kr"));
        clickOn("#confirmNewPrice");
        assertEquals("Specify price first in textfield", controller.getErrorMessage());
        clickOn(LabeledMatchers.hasText("OK"));
        clickOn("#confirmDiscount");
        assertEquals("Specify discount first in textfield", controller.getErrorMessage());
        clickOn(LabeledMatchers.hasText("OK"));
    }

    @Test
    public void testErrorInputNotNumberForPrice() {
        clickOn("#newPrice").write("hei");
        clickOn("#priceList").clickOn(LabeledMatchers.hasText("Pants; Nike; 10.0kr"));
        clickOn("#confirmNewPrice");
        assertEquals("Input must be a number", controller.getErrorMessage());
        clickOn(LabeledMatchers.hasText("OK"));
        clickOn("#confirmDiscount");
        assertEquals("Specify discount first in textfield", controller.getErrorMessage());
        clickOn(LabeledMatchers.hasText("OK"));
    }
    
    @Test
    public void testFilterBrand() {
        clickOn("#filters").clickOn("Brand");
        clickOn("#brands").clickOn("Nike");
        clickOn("#confirmFilter");
        ListView<String> priceView = lookup("#priceList").query();
        List<String> priceList = priceView.getItems();
        String nikePants = priceList.get(0);
        String expected = "Pants; Nike; 10.0kr";
        assertEquals(expected, nikePants);
    }

    @Test
    public void testFilterLowPrice() {
        clickOn("#filters").clickOn("Lowest Price");
        clickOn("#confirmFilter");
        ListView<String> priceView = lookup("#priceList").query();
        List<String> priceList = priceView.getItems();
        List<String> expectedList = new ArrayList<String>(List.of(
            "Pants; Nike; 10.0kr",
            "Shorts; Louis Vuitton; 20.0kr",
            "Socks; Adidas; 30.0kr"
        ));
        assertEquals(expectedList, priceList);
    }

    @Test
    public void testFilterHighPrice() {
        clickOn("#filters").clickOn("Highest Price");
        clickOn("#confirmFilter");
        ListView<String> priceView = lookup("#priceList").query();
        List<String> priceList = priceView.getItems();
        List<String> expectedList = new ArrayList<String>(List.of(
            "Socks; Adidas; 30.0kr",
            "Shorts; Louis Vuitton; 20.0kr",
            "Pants; Nike; 10.0kr"
        ));
        assertEquals(expectedList, priceList);
    }

    @Test
    public void testFilterOnSale() {
        clickOn("#priceList");
        clickOn(LabeledMatchers.hasText("Pants; Nike; 10.0kr"));
        clickOn("#discount").write("40");
        clickOn("#confirmDiscount");

        clickOn("#filters").clickOn("On Sale");
        clickOn("#confirmFilter");
        ListView<String> priceView = lookup("#priceList").query();
        List<String> priceList = priceView.getItems();
        List<String> expectedList = new ArrayList<String>(List.of(
            "Pants; Nike; 6.0kr"
        ));
        assertEquals(expectedList, priceList);
    }

    @Test
    public void testResetFilter() {
        clickOn("#filters").clickOn("Lowest Price");
        ChoiceBox<String> box = lookup("#filters").query();
        assertEquals("Lowest Price", box.getValue());
        clickOn("#resetFilter");
        ChoiceBox<String> box2 = lookup("#filters").query();
        assertEquals(null, box2.getValue());

        clickOn("#filters").clickOn("Highest Price");
        clickOn("#confirmFilter");
        clickOn("#resetFilter");
        ListView<String> priceView = lookup("#priceList").query();
        List<String> priceList = priceView.getItems();
        List<String> expectedList = new ArrayList<String>(List.of(
            "Pants; Nike; 10.0kr",
            "Shorts; Louis Vuitton; 20.0kr",
            "Socks; Adidas; 30.0kr"
        ));
        assertEquals(expectedList, priceList);
    }

    @Test
    public void testErrorNoFilterSelected() {
        clickOn("#confirmFilter");
        assertEquals("You must choose a filter in the choice box first", controller.getErrorMessage());
    }
    

    @Test
    public void testErrorNoSecondFilterBrand() {
        clickOn("#filters").clickOn("Brand");
        clickOn("#confirmFilter");
        assertEquals("You must choose a brand in the choice box first",
            controller.getErrorMessage());
    }
    
    @Test
    public void testErrorResetFilter() {
        clickOn("#resetFilter");
        assertEquals("Filter is not applied", controller.getErrorMessage());
    }

    @Test
    public void testNewPriceWhenSorted() throws InterruptedException {
        clickOn("#filters");
        clickOn("Lowest Price");
        clickOn("#confirmFilter");
        clickOn("#priceList");
        clickOn(LabeledMatchers.hasText("Shorts; Louis Vuitton; 20.0kr"));
        clickOn("#newPrice").write("30");
        clickOn("#confirmNewPrice");
        ListView<String> priceView = lookup("#priceList").query();
        List<String> priceList = priceView.getItems();
        String[] shorts = priceList.get(1).split(";");
        double price = Double.parseDouble(shorts[2].split("k")[0].strip());
        assertEquals(30, price);
    }

    @Test
    public void testDiscountWhenSorted() {
        clickOn("#filters").clickOn("Brand");
        clickOn("#brands").clickOn("Nike");
        clickOn("#confirmFilter");
        clickOn("#priceList");
        clickOn(LabeledMatchers.hasText("Pants; Nike; 10.0kr"));
        clickOn("#discount").write("50");
        clickOn("#confirmDiscount");
        ListView<String> priceView = lookup("#priceList").query();
        List<String> priceList = priceView.getItems();
        String[] nikePants = priceList.get(0).split(";");
        double price = Double.parseDouble(nikePants[2].split("k")[0].strip());
        assertEquals(5, price);
    }

    @Test
    public void testRemoveDiscountWhenSorted() {
        clickOn("#filters").clickOn("Brand");
        clickOn("#brands").clickOn("Nike");
        clickOn("#confirmFilter");
        clickOn("#priceList");
        clickOn(LabeledMatchers.hasText("Pants; Nike; 10.0kr"));
        clickOn("#discount").write("50");
        clickOn("#confirmDiscount");
        clickOn("#priceList");
        clickOn(LabeledMatchers.hasText("Pants; Nike; 5.0kr"));
        clickOn("#removeDiscount");
        ListView<String> priceView = lookup("#priceList").query();
        List<String> priceList = priceView.getItems();
        String[] nikePants = priceList.get(0).split(";");
        double price = Double.parseDouble(nikePants[2].split("k")[0].strip());
        assertEquals(10, price);
    } 
}