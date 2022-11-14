package clothingStorage.ui;

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
     * Current errormessage as shown in ui.
     */
    private String errorMessage;
    /**
     * Access, either direct or remote
     */
    private Access access;

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

    @FXML
    String isRemote;

    

    /**
     * Constructor for StorageController initializing it with empty storage.
     *
     */
    public StoragePageController() {
    }

    /**
     * Initializes with storage from file.
     */
    @FXML
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        boolean isTest = true;
        if (isRemote != null) {
            RemoteAccess remoteAccess;
            try {
                remoteAccess = new RemoteAccess();
                System.out.println("Using remote access");
                access = remoteAccess;
                isTest = false;
            } catch (URISyntaxException e) {
                System.err.println(e);
            }
        }
        if (access == null) {
            if (Thread.currentThread().getStackTrace()[5].getClassName()
                != "clothingStorage.ui.StoragePageControllerTest"
                && Thread.currentThread().getStackTrace()[5].getClassName()
                != "clothingStorage.ui.PricePageControllerTest"
                && Thread.currentThread().getStackTrace()[5].getClassName()
                != "clothingStorage.ui.NewClothingPageControllerTest"
                && Thread.currentThread().getStackTrace()[5].getClassName()
                != "clothingStorage.ui.StatisticsPageControllerTest") {
                access = new DirectAccess();
                System.out.println("Using direct access");
                isTest = false;
            } else {
                // do nothing
            }
        }
        if (isTest == false) {
            updateStorageList(access.getStorageDisplay());
        }
    }

    /**
     * Sets storage to the given storage, helps controller test-class.
     *
     * @param storage to be set as storage for the controller
     */
    public void setStorage(Storage storage) {
        access = new DirectAccess(storage);
        updateStorageList(access.getStorageDisplay());
    }

    public void setAccess(Access access) {
        this.access = access;
        updateStorageList(access.getStorageDisplay());;
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("PricePage.fxml"));
        Parent root = loader.load();

        PricePageController controller = loader.getController();
        controller.setAccess(access);

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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("StatisticsPage.fxml"));
        Parent root = loader.load();

        StatisticsPageController controller = loader.getController();
        controller.setAccess(access);

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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("NewClothingPage.fxml"));
        Parent root = loader.load();

        NewClothingPageController controller = loader.getController();
        controller.setAccess(access);

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
            List<String> names = access.getNames();
            String name = names.get(index);
            boolean removed = access.removeClothing(name);
            if (removed == true) {
                updateStorageList(access.getStorageDisplay());
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
            List<String> names = access.getNames();
            String name = names.get(index);
            boolean updated = access.increaseQuantityByOne(name);
            if (updated == true) {
                updateStorageList(access.getStorageDisplay());
            } else {
                throw new IllegalArgumentException("Something went wrong");
            }
        } catch (IndexOutOfBoundsException e) {
            if (access.getNames().isEmpty()) {
                showErrorMessage("Add a new clothing to storage first");
            } else {
                showErrorMessage("Select a clothing before increasing quantity");
            }
        } catch (IllegalArgumentException e) {
            showErrorMessage(e.getMessage());
        }
    }

    /**
     * Decreases quantity for a selected clothing-item by one.
     */
    @FXML
    private void handleDecreaseByOne() {
        try {
            int index = storageList.getSelectionModel().getSelectedIndex();
            List<String> names = access.getNames();
            String name = names.get(index);
            boolean updated = access.decreaseQuantityByOne(name);
            if (updated == true) {
                updateStorageList(access.getStorageDisplay());
            } else {
                // do nothing
            }
        } catch (IndexOutOfBoundsException e) {
            if (access.getNames().isEmpty()) {
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
            if (access.getNames().isEmpty() || index == -1) {
                throw new IndexOutOfBoundsException();
            }
            List<String> names = access.getNames();
            String name = names.get(index);
            int addQuantity = Integer.parseInt(quantity.getText());
            boolean updated = access.increaseQuantity(name, addQuantity);
            if (updated == true) {
                updateStorageList(access.getStorageDisplay());
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
            if (access.getNames().isEmpty()) {
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
            if (access.getNames().isEmpty() || index == -1) {
                throw new IndexOutOfBoundsException();
            }
            List<String> names = access.getNames();
            String name = names.get(index);
            int decQuantity = Integer.parseInt(quantity.getText());
            boolean updated = access.decreaseQuantity(name, decQuantity);
            if (updated == true) {
                updateStorageList(access.getStorageDisplay());
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
            if (access.getNames().isEmpty()) {
                showErrorMessage("Add a new clothing to storage first");
            } else {
                showErrorMessage("Select a clothing before decreasing quantity");
            }
        } catch (IllegalStateException e) {
            showErrorMessage(e.getMessage());
        }
    }
}