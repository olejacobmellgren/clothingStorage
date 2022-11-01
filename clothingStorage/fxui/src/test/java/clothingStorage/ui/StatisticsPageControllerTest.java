package clothingStorage.ui;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.control.LabeledMatchers;

import clothingStorage.core.Clothing;
import clothingStorage.core.Storage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.stage.Stage;

public class StatisticsPageControllerTest extends ApplicationTest {

    private StatisticsPageController controller;
    private Parent root;
    private Stage stage;
    private Storage storage;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        final FXMLLoader loader = new FXMLLoader(getClass().getResource("StatisticsPage.fxml"));
        root = loader.load();
        this.controller = loader.getController();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @BeforeEach
    public void setUpStorage() {
        storage = new Storage();
        Clothing clothing1 = new Clothing("Pants", "Nike", 'M', 88);
        Clothing clothing2 = new Clothing("Socks", "H&M", 'S', 75);
        Clothing clothing3 = new Clothing("Jacket", "Lacoste", 'L', 150);
        Clothing clothing4 = new Clothing("Shorts", "Nike", 'M', 35);
        storage.addNewClothing(clothing1, 7);
        storage.addNewClothing(clothing2, 2);
        storage.addNewClothing(clothing3, 5);
        storage.addNewClothing(clothing4, 9);
        controller.setStorage(storage);
    }

    @Test
    public void testAllClothesChart() {
        clickOn("#typeForDiagram").clickOn(LabeledMatchers.hasText("Pants"));
        clickOn("#typeForDiagram").clickOn("All Clothes");

        BarChart<String, Integer> actualBarchart = lookup("#quantityChart").query();

        assertEquals(7, actualBarchart.getData().get(0).getData().get(0).getYValue());
        assertEquals(2, actualBarchart.getData().get(0).getData().get(3).getYValue());
        assertEquals(5, actualBarchart.getData().get(0).getData().get(5).getYValue());
        assertEquals(9, actualBarchart.getData().get(0).getData().get(6).getYValue());
    }

    @Test
    public void testTypeChart() {
        Clothing clothing = new Clothing("Pants", "Adidas", 'S', 89);
        storage.addNewClothing(clothing, 4);
        controller.setStorage(storage);
        clickOn("#typeForDiagram").clickOn(LabeledMatchers.hasText("Pants"));

        BarChart<String, Integer> actualBarchart = lookup("#quantityChart").query();
        
        assertEquals(4, actualBarchart.getData().get(0).getData().get(0).getYValue());
        assertEquals(7, actualBarchart.getData().get(0).getData().get(1).getYValue());
        assertEquals(0, actualBarchart.getData().get(0).getData().get(2).getYValue());
    }

    @Test
    public void testPricePageButton() {
        clickOn("#pricePageButton");
        assertEquals("Clothing Prices", this.stage.getTitle());
        clickOn("#statisticsPageButton");
        assertEquals("Statistics", this.stage.getTitle());
    }

    @Test
    public void testStoragePageButton() {
        clickOn("#storagePageButton");
        assertEquals("Clothing Storage", this.stage.getTitle());
        clickOn("#statisticsPageButton");
        assertEquals("Statistics", this.stage.getTitle());
    } 
}
