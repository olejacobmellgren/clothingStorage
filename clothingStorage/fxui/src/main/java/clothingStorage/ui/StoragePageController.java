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
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controller for JavaFX app.
 */
public class StoragePageController implements Initializable {

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
    public StoragePageController() {
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
                != "clothingStorage.ui.StoragePageControllerTest"
                && Thread.currentThread().getStackTrace()[5].getClassName()
                != "clothingStorage.ui.PricePageControllerTest"
                && Thread.currentThread().getStackTrace()[5].getClassName()
                != "clothingStorage.ui.NewClothingPageControllerTest") {

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
            storageList.getItems().clear();
        }
        this.storage = storage;
        updateStorageList();
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
     * Updates StorageList after change has been made.
     */
    public void updateStorageList() {
        List<String> clothingDisplays = storage.storageDisplay();
        storageList.getItems().setAll(clothingDisplays);
        fireAutoSaveStorage();   
    }
    
    /**
     * Button for price-page.
     */
    @FXML private Button pricePageButton;

    /**
     * Changes ui-view to the price-page.
     */
    @FXML private void handlePricePageButton() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("PricePage.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) pricePageButton.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Clothing Prices");
        stage.show();
    }

    /**
     * Resets inputs and shows pane for adding a new clothing-item.
     */
    @FXML private void handleNewClothingItem() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("NewClothingPage.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) pricePageButton.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("New Clothing");
        stage.show();
    }

    /**
     * Button for adding quantity.
     */
    @FXML private Button addQuantity;
    /**
     * Button for removing quantity.
     */
    @FXML private Button removeQuantity;
    /**
     * Button for adding new clothing-item.
     */
    @FXML private Button newClothingItem;
    /**
     * Button for increasing quantity by one.
     */
    @FXML private Button increaseByOne;
    /**
     * Button for decreasing quantity by one.
     */
    @FXML private Button decreaseByOne;
    /**
     * Listview with all clothing-items and their quantities.
     */
    @FXML private ListView<String> storageList;
    /**
     * Textfield for quantity to be added.
     */
    @FXML private TextField quantity;

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
     * Removes clothing-item for the list.
     */
    @FXML private void handleRemoveClothingItem() {
        try {
            int index = storageList.getSelectionModel().getSelectedIndex();
            storage.removeClothing(storage.getClothing(index));
            updateStorageList();
        } catch (IndexOutOfBoundsException e) {
            showErrorMessage("You need to select an item from the list");
        }
    }

    /**
     * Increases quantity for a selected clothing-item by one.
     */
    @FXML private void handleIncreaseByOne() {
        try {
            int index = storageList.getSelectionModel().getSelectedIndex();
            storage.increaseQuantityByOne(storage.getClothing(index));
            updateStorageList();
        } catch (IndexOutOfBoundsException e) {
            if (storage.getAllClothes().isEmpty()) {
                showErrorMessage("Add a new clothing to storage first");
            } else {
                showErrorMessage("Select a clothing before increasing quantity");
            }
        }
    }

    /**
     * Decreases quantity for a selected clothing-item by one.
     */
    @FXML private void handleDecreaseByOne() {
        try {
            int index = storageList.getSelectionModel().getSelectedIndex();
            storage.decreaseQuantityByOne(storage.getClothing(index));
            updateStorageList();
        } catch (IndexOutOfBoundsException e) {
            if (storage.getAllClothes().isEmpty()) {
                showErrorMessage("Add a new clothing to storage first");
            } else {
                showErrorMessage("Select a clothing before decreasing quantity");
            }
        }
    }

    /**
     * Increases quantity for a selected clothing-item by amount specified in textfield.
     */
    @FXML private void handleAddQuantity() {
        int index = storageList.getSelectionModel().getSelectedIndex();
        try {
            if (storage.getAllClothes().isEmpty() || index == -1) {
                throw new IndexOutOfBoundsException();
            }
            int addQuantity = Integer.parseInt(quantity.getText());
            storage.increaseQuantity(storage.getClothing(index), addQuantity);
            updateStorageList();
        } catch (NumberFormatException e) {
            if (quantity.getText().isEmpty()) {
                showErrorMessage("Specify quantity first in textfield");
            } else {
                showErrorMessage("Input must be a number");
            }
        } catch (IndexOutOfBoundsException e) {
            if (storage.getAllClothes().isEmpty()) {
                showErrorMessage("Add a new clothing to storage first");
            } else {
                showErrorMessage("Select a clothing before increasing quantity");
            }
        }
    }

    /**
     * Decreases quantity for a selected clothing-item by amount specified in textfield.
     */
    @FXML private void handleRemoveQuantity() {
        int index = storageList.getSelectionModel().getSelectedIndex();
        try {
            if (storage.getAllClothes().isEmpty() || index == -1) {
                throw new IndexOutOfBoundsException();
            }
            int decQuantity = Integer.parseInt(quantity.getText());
            storage.decreaseQuantity(storage.getClothing(index), decQuantity);
            updateStorageList();
        } catch (NumberFormatException e) {
            if (quantity.getText().isEmpty()) {
                showErrorMessage("Specify quantity first in textfield");
            } else {
                showErrorMessage("Input must be a number");
            }
        } catch (IndexOutOfBoundsException e) {
            if (storage.getAllClothes().isEmpty()) {
                showErrorMessage("Add a new clothing to storage first");
            } else {
                showErrorMessage("Select a clothing before decreasing quantity");
            }
        }
    }
}