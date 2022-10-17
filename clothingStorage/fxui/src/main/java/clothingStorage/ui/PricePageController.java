package clothingStorage.ui;

import clothingStorage.core.Storage;
import clothingStorage.json.ClothingStoragePersistence;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

/**
 * Controller for JavaFX app.
 */
public class PricePageController implements Initializable {

    /**
     * Storage containing Clothing and corresponding quantity.
     */
    private Storage storage;
    /**
     * Storage containing Clothing and corresponding quantity.
     */
    private ClothingStoragePersistence storagePersistence;
    /**
     * Current errormessage as shown in ui.
     */
    private String errorMessage;

    /**
     * Constructor for StorageController initializing it with empty storage.
     */
    public PricePageController() {
        this.storage = new Storage();
    }

    /**
     * Initializes controller with the choiceboxes.
     */
    @FXML
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println(Thread.currentThread().getStackTrace()[5].getClassName());
        try {
            if (Thread.currentThread().getStackTrace()[5].getClassName()
                != "clothingStorage.ui.PricePageControllerTest" &&
                Thread.currentThread().getStackTrace()[5].getClassName()
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
     * Sets storage to the given storage, helps controller test-class.
     *
     * @param storage to be set as storage for the controller
     */
    public void setStorage(Storage storage) {
        if (this.storage != null) {
            priceList.getItems().clear();
        }
        this.storage = storage;
        updatePriceList();
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
     * Autosaves storage to json-file.
     */
    private void fireAutoSaveStorage() {    
        if (storagePersistence != null) {
            try {
                storagePersistence.saveClothingStorage(storage);
            } catch (Exception e) {
                System.err.println("Fikk ikke lagret storage: " + e.getMessage());
            }
        }
    }

    /**
     * Updates PriceList after change has been made.
     */
    public void updatePriceList() {
        while (!(priceList.getItems().isEmpty())) {
            priceList.getItems().clear();
        }
        List<String> priceDisplays = storage.priceDisplay();
        priceList.getItems().setAll(priceDisplays);
        fireAutoSaveStorage(); 
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
     * Button for storage-page.
     */
    @FXML private Button storagePageButton;
    /**
     * Listview with all clothing-items and their prices.
     */
    @FXML private ListView<String> priceList;
    /**
     * Button for confirming new price.
     */
    @FXML private Button confirmNewPrice;
    /**
     * Button for confirming discount.
     */
    @FXML private Button confirmDiscount;
    /**
     * Textfield for new price.
     */
    @FXML private TextField newPrice;
    /**
     * Textfield for discount to add.
     */
    @FXML private TextField discount;

    /**
     * Changes ui-view to the storage-page.
     */
    @FXML private void handleStoragePageButton() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("StoragePage.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage)storagePageButton.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Clothing Storage");
        stage.show();
    }

    /**
     * Updates price-list with new price for selected clothing-item.
     */
    @FXML private void handleConfirmNewPrice() {
        try {
            int index = priceList.getSelectionModel().getSelectedIndex();
            double price = Double.parseDouble(newPrice.getText());
            storage.getClothing(index).setPrice(price, true);
            updatePriceList();
        } catch (NumberFormatException e) {
            if (newPrice.getText().isEmpty()) {
                showErrorMessage("Specify price first in textfield");
            } else {
                showErrorMessage("Input must be a number");
            }
        } catch (IndexOutOfBoundsException e) {
            showErrorMessage("You need to select an item from the list");
        }
    }

    /**
     * Updates price-list with new price after adding discount.
     */
    @FXML private void handleConfirmDiscount() {
        try {
            int index = priceList.getSelectionModel().getSelectedIndex();
            double discountToAdd = Double.parseDouble(discount.getText());
            storage.getClothing(index).setDiscount(discountToAdd / 100);
            updatePriceList();
        } catch (NumberFormatException e) {
            if (newPrice.getText().isEmpty()) {
                showErrorMessage("Specify price first in textfield");
            } else {
                showErrorMessage("Input must be a number");
            }
        } catch (IndexOutOfBoundsException e) {
            showErrorMessage("You need to select an item from the list");
        } catch (IllegalArgumentException e) {
            showErrorMessage(e.getMessage());
        } catch (IllegalStateException e) {
            showErrorMessage(e.getMessage());
        }
    }

    /**
     * Updates price-list with new price after removing discount.
     */
    @FXML private void handleRemoveDiscount() {
        try {
            int index = priceList.getSelectionModel().getSelectedIndex();
            storage.getClothing(index).removeDiscount();
            updatePriceList();
        } catch (IndexOutOfBoundsException e) {
            showErrorMessage("You need to select an item from the list");
        } catch (IllegalStateException e) {
            showErrorMessage(e.getMessage());
        }
    }
}

