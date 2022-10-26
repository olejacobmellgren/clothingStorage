package clothingStorage.ui;

import clothingStorage.core.Storage;
import clothingStorage.core.StorageStatistics;
import clothingStorage.json.ClothingStoragePersistence;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * Controller for JavaFX app.
 */
public class StatisticsPageController implements Initializable {

    /**
     * Storage containing Clothing and corresponding quantity.
     */
    private Storage storage;
    /**
     * ClothingStoragePersistence handeling local persistence.
     */
    private ClothingStoragePersistence storagePersistence;
    /**
     * Current errormessage as shown in ui.
     */
    private String errorMessage;

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
     * Button for statistics-page.
     */
    @FXML 
    private Button pricePageButton;
    /**
     * Button for storage-page.
     */
    @FXML 
    private BarChart<String, Integer> quantityChart;

    /**
     * Constructor for StatisticsPageController initializing it with empty storage.
     */
    public StatisticsPageController() {
        this.storage = new Storage();
    }

    /**
     * Initializes controller with the choiceboxes.
     */
    @FXML
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            if (Thread.currentThread().getStackTrace()[5].getClassName()
                != "clothingStorage.ui.StatisticsPageControllerTest"
                && Thread.currentThread().getStackTrace()[5].getClassName()
                != "clothingStorage.ui.PricePageControllerTest"
                && Thread.currentThread().getStackTrace()[5].getClassName()
                != "clothingStorage.ui.StoragePageControllerTest") {
                this.storagePersistence = new ClothingStoragePersistence();
                this.storagePersistence.setSaveFile("storage.json");
                this.setStorage(storagePersistence.loadClothingStorage());
            }
        } catch (Exception e) {
            //ignore
        }
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        series.setName("Quantity");
        series.getData().add(new XYChart.Data<String, Integer>("Jeans",
                        StorageStatistics.getQuantityForType(storage, "Jeans")));
        series.getData().add(new XYChart.Data<String, Integer>("T-shirt",
                        StorageStatistics.getQuantityForType(storage, "T-shirt")));
        series.getData().add(new XYChart.Data<String, Integer>("Socks",
                        StorageStatistics.getQuantityForType(storage, "Socks")));
        series.getData().add(new XYChart.Data<String, Integer>("Sweater",
                        StorageStatistics.getQuantityForType(storage, "Sweater")));
        series.getData().add(new XYChart.Data<String, Integer>("Jacket",
                        StorageStatistics.getQuantityForType(storage, "Jacket")));
        series.getData().add(new XYChart.Data<String, Integer>("Shorts",
                        StorageStatistics.getQuantityForType(storage, "Shorts")));          
        series.getData().add(new XYChart.Data<String, Integer>("Other",
                        StorageStatistics.getQuantityForType(storage, "Other")));
        quantityChart.getData().add(series);

        setTotalQuantityLabel();
        setTotalValueLabel();
    }

    /**
     * Sets label for total quantity.
     */
    private void setTotalQuantityLabel() {
        int totalQuantity = StorageStatistics.getTotalQuantity(storage);
        totalQuantityLabel.setText(String.valueOf(totalQuantity));
    }

    /**
     * Sets label for total quantity.
     */
    private void setTotalValueLabel() {
        double totalValue = StorageStatistics.getTotalValue(storage);
        totalValueLabel.setText(String.valueOf(totalValue));
    }

    /**
     * Sets storage to the given storage.
     *
     * @param storage to be set as storage for the controller
     */
    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    /**
     * Gets error-message for controller as shown in ui, helps controller test-class.
     *
     * @return the error-message
     */
    public String getErrorMessage() {
        return this.errorMessage;
    }

    /**
     * Shows alert with error-message.
     *
     * @param errorMessage to be shown in alert
     */
    private void showErrorMessage(String errorMessage) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("ERROR OCCURRED");
        alert.setHeaderText("Error!");
        alert.setContentText(errorMessage);
        this.errorMessage = errorMessage;
        alert.showAndWait();
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
     * Changes ui-view to the storage-page.
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

    
}


