package clothingStorage.ui;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.InputStreamReader;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.control.LabeledMatchers;

import clothingStorage.core.Storage;
import clothingStorage.json.ClothingStoragePersistence;
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
    public void testAllClothesChart() {
        clickOn("#typeForDiagram").clickOn(LabeledMatchers.hasText("Pants"));
        clickOn("#typeForDiagram").clickOn("All Clothes");

        BarChart<String, Integer> actualBarchart = lookup("#quantityChart").query();

        assertEquals(5, actualBarchart.getData().get(0).getData().get(0).getYValue());
        assertEquals(8, actualBarchart.getData().get(0).getData().get(6).getYValue());
        assertEquals(4, actualBarchart.getData().get(0).getData().get(3).getYValue());
    }

    @Test
    public void testTypeChart() {
        clickOn("#typeForDiagram").clickOn(LabeledMatchers.hasText("Pants"));

        BarChart<String, Integer> actualBarchart = lookup("#quantityChart").query();
        
        assertEquals(5, actualBarchart.getData().get(0).getData().get(0).getYValue());
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
