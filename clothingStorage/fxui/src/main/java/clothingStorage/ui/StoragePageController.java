package clothingStorage.ui;

import clothingStorage.client.StorageClient;
import clothingStorage.core.Storage;
import java.io.IOException;
import java.net.URISyntaxException;
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
     * Current errormessage as shown in ui.
     */
    private String errorMessage;
    /**
     * StorageClient for the session.
     */
    private StorageClient storageClient;

    /**
     * Button for price-page.
     */
    @FXML
    private Button pricePageButton;
    /**
     * Button for statistics-page.
     */
    @FXML
    private Button statisticsPageButton;
    /**
     * Button for adding quantity.
     */
    @FXML
    private Button addQuantity;
    /**
     * Button for removing quantity.
     */
    @FXML
    private Button removeQuantity;
    /**
     * Button for adding new clothing-item.
     */
    @FXML
    private Button newClothingItem;
    /**
     * Button for increasing quantity by one.
     */
    @FXML
    private Button increaseByOne;
    /**
     * Button for decreasing quantity by one.
     */
    @FXML
    private Button decreaseByOne;
    /**
     * Listview with all clothing-items and their quantities.
     */
    @FXML
    private ListView<String> storageList;
    /**
     * Textfield for quantity to be added.
     */
    @FXML
    private TextField quantity;


    /**
     * Constructor for StorageController initializing it with empty storage.
     * @throws URISyntaxException
     */
    public StoragePageController() throws URISyntaxException {
        this.storage = new Storage();
        this.storageClient = new StorageClient();
    }

    /**
     * Initializes with storage from file.
     */
    @FXML
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateStorageList(storageClient.getStorageDisplay());  
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
        updateStorageList(storageClient.getStorageDisplay());
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
     * Updates StorageList after change has been made.
     */
    private void updateStorageList(List<String> storageDisplay) {
        storageList.getItems().setAll(storageDisplay);
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
     * Changes ui-view to the statistics-page.
     */
    @FXML
    private void handleStatisticsPageButton() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("StatisticsPage.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) statisticsPageButton.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Statistics");
        stage.show();
    }

    /**
     * Resets inputs and shows pane for adding a new clothing-item.
     */
    @FXML
    private void handleNewClothingItem() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("NewClothingPage.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) pricePageButton.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("New Clothing");
        stage.show();
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
     * Removes clothing-item for the list.
     */
    @FXML
    private void handleRemoveClothingItem() {
        try {
            int index = storageList.getSelectionModel().getSelectedIndex();
            List<String> names = storageClient.getNames();
            String name = names.get(index);
            boolean removed = storageClient.removeClothing(name);
            if (removed == true) {
                updateStorageList(storageClient.getStorageDisplay());
            } else {
                // do nothing
            }
        } catch (IndexOutOfBoundsException e) {
            showErrorMessage("You need to select an item from the list");
        }
    }

    /**
     * Increases quantity for a selected clothing-item by one.
     */
    @FXML
    private void handleIncreaseByOne() {
        try {
            int index = storageList.getSelectionModel().getSelectedIndex();
            List<String> names = storageClient.getNames();
            String name = names.get(index);
            int previousQuantity = storageClient.getQuantity(name);
            int newQuantity = previousQuantity + 1;
            boolean updated = storageClient.putQuantity(name, newQuantity);
            if (updated == true) {
                updateStorageList(storageClient.getStorageDisplay());
            } else {
                // do nothing
            }
        } catch (IndexOutOfBoundsException e) { // vet ikke hvordan exception skal håndteres helt enda
            if (storageClient.getNames().isEmpty()) {
                showErrorMessage("Add a new clothing to storage first");
            } else {
                showErrorMessage("Select a clothing before increasing quantity");
            }
        }
    }

    /**
     * Decreases quantity for a selected clothing-item by one.
     */
    @FXML
    private void handleDecreaseByOne() {
        try {
            int index = storageList.getSelectionModel().getSelectedIndex();
            List<String> names = storageClient.getNames();
            String name = names.get(index);
            int previousQuantity = storageClient.getQuantity(name);
            int newQuantity = previousQuantity - 1;
            if (newQuantity < 0) {
                throw new IllegalStateException("Can not have negative quantity");
            }
            boolean updated = storageClient.putQuantity(name, newQuantity);
            if (updated == true) {
                updateStorageList(storageClient.getStorageDisplay());
            } else {
                // do nothing
            }
        } catch (IndexOutOfBoundsException e) {
            if (storageClient.getNames().isEmpty()) {
                showErrorMessage("Add a new clothing to storage first");
            } else {
                showErrorMessage("Select a clothing before decreasing quantity");
            }
        } catch (IllegalStateException e) {
            showErrorMessage(e.getMessage());
        }
    }

    /**
     * Increases quantity for a selected clothing-item by amount specified in textfield.
     */
    @FXML
    private void handleAddQuantity() {
        int index = storageList.getSelectionModel().getSelectedIndex();
        try {
            if (storageClient.getNames().isEmpty() || index == -1) {
                throw new IndexOutOfBoundsException();
            }
            List<String> names = storageClient.getNames();
            String name = names.get(index);
            int previousQuantity = storageClient.getQuantity(name);
            int addQuantity = Integer.parseInt(quantity.getText());
            int newQuantity = previousQuantity + addQuantity;
            boolean updated = storageClient.putQuantity(name, newQuantity);
            if (updated == true) {
                updateStorageList(storageClient.getStorageDisplay());
            } else {
                // do nothing
            }
        } catch (NumberFormatException e) {
            if (quantity.getText().isEmpty()) {
                showErrorMessage("Specify quantity first in textfield");
            } else {
                showErrorMessage("Input must be a number");
            }
        } catch (IndexOutOfBoundsException e) {
            if (storageClient.getNames().isEmpty()) {
                showErrorMessage("Add a new clothing to storage first");
            } else {
                showErrorMessage("Select a clothing before increasing quantity");
            }
        }
    }

    /**
     * Decreases quantity for a selected clothing-item by amount specified in textfield.
     */
    @FXML
    private void handleRemoveQuantity() {
        int index = storageList.getSelectionModel().getSelectedIndex();
        try {
            if (storageClient.getNames().isEmpty() || index == -1) {
                throw new IndexOutOfBoundsException();
            }
            List<String> names = storageClient.getNames();
            String name = names.get(index);
            int previousQuantity = storageClient.getQuantity(name);
            int decQuantity = Integer.parseInt(quantity.getText());
            int newQuantity = previousQuantity - decQuantity;
            if (newQuantity < 0) {
                throw new IllegalStateException("Can not have negative quantity");
            }
            boolean updated = storageClient.putQuantity(name, newQuantity);
            if (updated == true) {
                updateStorageList(storageClient.getStorageDisplay());
            } else {
                // do nothing
            }
        } catch (NumberFormatException e) {
            if (quantity.getText().isEmpty()) {
                showErrorMessage("Specify quantity first in textfield");
            } else {
                showErrorMessage("Input must be a number");
            }
        } catch (IndexOutOfBoundsException e) {
            if (storageClient.getNames().isEmpty()) {
                showErrorMessage("Add a new clothing to storage first");
            } else {
                showErrorMessage("Select a clothing before decreasing quantity");
            }
        } catch (IllegalStateException e) {
            showErrorMessage(e.getMessage());
        }
    }
}