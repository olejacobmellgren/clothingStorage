package clothingStorage.ui;

import clothingStorage.client.StorageClient;
import clothingStorage.core.Storage;
import clothingStorage.core.StorageStatistics;
import clothingStorage.json.ClothingStoragePersistence;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * Controller for JavaFX app.
 */
public class StatisticsPageController implements Initializable {

    /**
     * ClothingStoragePersistence handeling local persistence.
     */
    private ClothingStoragePersistence storagePersistence;
    /** 
     * Valid types for Clothing object.
    */
    private final String[] validTypes = {"Pants", "Shirt", "Underwear",
                                         "Socks", "Sweater", "Jacket",
                                         "Shorts"}; /*May be expanded*/
    /**
     * StorageClient for the session.
     */
    private StorageClient storageClient;

    /**
     * Label for total quantity in storage.
     */
    @FXML 
    private Label totalQuantityLabel;
    /**
     * Label for total value of storage.
     */
    @FXML 
    private Label totalValueLabel;
    /**
     * Button for storage-page.
     */
    @FXML 
    private Button storagePageButton;
    /**
     * Button for price-page.
     */
    @FXML 
    private Button pricePageButton;
    /**
     * Choicebox for type for diagram on statistics-page.
     */
    @FXML 
    private ChoiceBox<String> typeForDiagram;
    /**
     * Bar-chart for statistics-page.
     */
    @FXML 
    private BarChart<String, Integer> quantityChart;
    /**
     * Category-Axis for statistics-page.
     */
    @FXML 
    private CategoryAxis categoryAxis;

    private Access access;

    /**
     * Constructor for StatisticsPageController initializing it with empty storage.
     *
     * @throws URISyntaxException if string could not be parsed as URI reference
     * 
     */
    public StatisticsPageController() throws URISyntaxException {
        this.storageClient = new StorageClient();
    }

    /**
     * Initializes controller with the choiceboxes and initial diagram.
     */
    @FXML
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        typeForDiagram.getItems().add("All Clothes");
        typeForDiagram.setValue("All Clothes");
        for (int i = 0; i < validTypes.length; i++) {
            if ((access.getQuantityForType(validTypes[i]).stream()
                .reduce(0, Integer::sum) > 0)) {
                typeForDiagram.getItems().add(validTypes[i]);
            }
        }
    }

    public void setAccess(Access access) {
        this.access = access;
        setDiagramForAllClothes();
        setTotalQuantityLabel();
        setTotalValueLabel();
    }

    /**
     * Sets storage to the given storage.
     *
     * @param storage to be set as storage for the controller
     */
    public void setStorage(Storage storage) {
        typeForDiagram.getItems().add("All Clothes");
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                // Update UI here.
                typeForDiagram.setValue("All Clothes");
                for (int i = 0; i < validTypes.length; i++) {
                    if ((StorageStatistics.getQuantityForType(storage, validTypes[i]) > 0)) {
                        typeForDiagram.getItems().add(validTypes[i]);
                    }
                }
                setDiagramForAllClothes();
                setTotalQuantityLabel();
                setTotalValueLabel();
            }
        });        
    }

    /**
     * Sets bar-chart for all types of clothing.
     */
    public void setDiagramForAllClothes() {
        quantityChart.setTitle("Quantities for all types");
        categoryAxis.setLabel("Type");
        quantityChart.getData().clear();
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        series.setName("Quantity");

        series.getData().add(new XYChart.Data<String, Integer>("Pants",
            storageClient.getQuantitiesForTypeAndSizes("Pants").stream()
                                                                    .reduce(0, Integer::sum)));
        series.getData().add(new XYChart.Data<String, Integer>("Shirt",
            storageClient.getQuantitiesForTypeAndSizes("Shirt").stream()
                                                                    .reduce(0, Integer::sum)));
        series.getData().add(new XYChart.Data<String, Integer>("Underwear",
            storageClient.getQuantitiesForTypeAndSizes("Underwear").stream()
                                                                        .reduce(0, Integer::sum)));
        series.getData().add(new XYChart.Data<String, Integer>("Socks",
            storageClient.getQuantitiesForTypeAndSizes("Socks").stream()
                                                                   .reduce(0, Integer::sum)));
        series.getData().add(new XYChart.Data<String, Integer>("Sweater",
            storageClient.getQuantitiesForTypeAndSizes("Sweater").stream()
                                                                    .reduce(0, Integer::sum)));
        series.getData().add(new XYChart.Data<String, Integer>("Jacket",
            storageClient.getQuantitiesForTypeAndSizes("Jacket").stream()
                                                                    .reduce(0, Integer::sum)));
        series.getData().add(new XYChart.Data<String, Integer>("Shorts",
            storageClient.getQuantitiesForTypeAndSizes("Shorts").stream()
                                                                    .reduce(0, Integer::sum)));   
              
        quantityChart.setAnimated(false);
        quantityChart.getData().add(series);
    }

    /**
     * Sets label for total quantity.
     */
    private void setTotalQuantityLabel() {
        int totalQuantity = storageClient.getTotalQuantity();
        totalQuantityLabel.setText(String.valueOf("Total Quantity in Storage: " + totalQuantity));
    }

    /**
     * Sets label for total value.
     */
    private void setTotalValueLabel() {
        double totalValue = storageClient.getTotalValue();
        totalValueLabel.setText("Total Value of Storage: " + totalValue + ",-");
    }

    /**
     * Changes ui-view to the storage-page.
     */
    @FXML 
    private void handleStoragePageButton() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("StoragePage.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) storagePageButton.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Clothing Storage");
        stage.show();
    }

    /**
     * Changes ui-view to the price-page.
     */
    @FXML 
    private void handlePricePageButton() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("PricePage.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) pricePageButton.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Clothing Prices");
        stage.show();
    }

    /**
     * Changes bar-chart to diagram for type of Clothing chosen in choicebox.
     */
    @FXML 
    private void handleTypeForDiagram() throws IOException {
        if (typeForDiagram.getValue() == "All Clothes") {
            setDiagramForAllClothes();
        } else {
            String type = typeForDiagram.getValue();
            quantityChart.setTitle("Quantity of sizes for " + type);
            categoryAxis.setLabel("Sizes");
            quantityChart.getData().clear();
            XYChart.Series<String, Integer> series = new XYChart.Series<>();
            series.setName("Quantity");
            List<Integer> quantities = storageClient.getQuantitiesForTypeAndSizes(type);
            series.getData()
                  .add(new XYChart.Data<String, Integer>("S", quantities.get(0)));
            series.getData()
                  .add(new XYChart.Data<String, Integer>("M", quantities.get(1)));
            series.getData()
                  .add(new XYChart.Data<String, Integer>("L", quantities.get(2)));
            quantityChart.setAnimated(false);
            quantityChart.getData().add(series);
        }
    }  
}