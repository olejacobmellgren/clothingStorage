package clothingStorage.ui;

import clothingStorage.core.Storage;
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
import javafx.scene.control.TextField;
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
     * Button for storage-page.
     */
    @FXML 
    private Button storagePageButton;
    /**
     * Button for storage-page.
     */
    @FXML 
    private BarChart<String, Integer> quantityChart;

    /**
     * Constructor for StorageController initializing it with empty storage.
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
        XYChart.Series<String, Integer> series1 = new XYChart.Series<>();
        series1.getData().add(new XYChart.Data<String, Integer>("Socks", 60));
        //getQuantityForType(storage, "Socks"))
        

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
        Stage stage = (Stage) storagePageButton.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Clothing Prices");
        stage.show();
    }

    
}


