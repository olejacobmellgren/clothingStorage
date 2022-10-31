package clothingStorage.ui;

import clothingStorage.core.Clothing;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controller for JavaFX app.
 */
public class NewClothingPageController implements Initializable {

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
     * Currernt confirm message as shown in ui.
     */
    private String confirmMessage;

    /**
     * Choicebox of valid types.
     */
    @FXML 
    private ChoiceBox<String> type;
    /**
     * Choicebox of valid brands.
     */
    @FXML
    private ChoiceBox<String> brand;
    /**
     * Choicebox for valid sizes.
     */
    @FXML
    private ChoiceBox<Character> size;
    /**
     * Textfield for price for clothing.
     */
    @FXML
    private TextField price;
    /**
     * Textfield for quantity of clothing.
     */
    @FXML
    private TextField quantity;
    /**
     * Textfield for quantity to be added.
     */
    @FXML
    private TextField newQuantity;
    /**
     * Button for canceling.
     */
    @FXML
    private Button cancel;

    /**
     * Constructor for StorageController initializing it with empty storage.
     */
    public NewClothingPageController() {
        this.storage = new Storage();
    }

    /**
     * Initializes controller with the choiceboxes.
     */
    @FXML
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        type.getItems().addAll("Pants", "Shirt", "Underwear",
            "Socks", "Sweater", "Jacket", "Shorts", "Other");
        brand.getItems().addAll("Nike", "Adidas", "H&M", 
            "Lacoste", "Louis Vuitton", "Supreme", "Levi's");
        size.getItems().addAll('S', 'M', 'L');
        try {
            if (Thread.currentThread().getStackTrace()[5].getClassName()
                != "clothingStorage.ui.NewClothingPageControllerTest"
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
     * Sets storage to the given storage, helps controller test-class.
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
     * Gets confirm-message for controller as shown in ui, helps controller test-class.
     *
     * @return the confirm-message
     */
    public String getConfirmMessage() {
        return this.confirmMessage;
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
     * Shows alert with confirmed-message.
     *
     * @param confirmedMessage to be shown in alert
     */
    private void showConfirmedMessage(String confirmedMessage) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("SUCCESS");
        alert.setHeaderText("Success!");
        alert.setContentText(confirmedMessage);
        this.confirmMessage = confirmedMessage;
        alert.showAndWait();
    }

    /**
     * Resets inputs for making a new clothing-item.
     */
    @FXML
    private void handleReset() {
        type.getSelectionModel().clearSelection();
        brand.getSelectionModel().clearSelection();
        size.getSelectionModel().clearSelection();
        price.clear();
        quantity.clear();
    }

    /**
     * Cancels the adding of clothing-item.
     */
    @FXML
    private void handleCancel() throws IOException {
        handleReset();
        Parent root = FXMLLoader.load(getClass().getResource("StoragePage.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Clothing Storage");
        stage.show();
    }

    /**
     * Confirms the adding of a new clothing-item and adds it to list if succesful.
     */
    @FXML
    private void handleOk() throws IOException {
        try {
            if (type.getValue() == null || brand.getValue() == null 
                || size.getValue() == null || price.getText() == null) {
                showErrorMessage("Fill in all fields");
                return;
            }
            String selectedType = type.getValue();
            String selectedBrand = brand.getValue();
            Character selectedSize = size.getValue();
            Double selectedPrice = Double.parseDouble(price.getText());

            Clothing clothing = new Clothing(selectedType, selectedBrand,
                selectedSize, selectedPrice);

            int selectedQuantity = Integer.parseInt(quantity.getText());
            storage.addNewClothing(clothing, selectedQuantity);
            fireAutoSaveStorage();
            handleReset();
            showConfirmedMessage("You successfully added the following: " + clothing.toString());
            handleCancel();
        } catch (NumberFormatException e) {
            showErrorMessage("Price must be a positive decimal number" +  "\n" 
                + "Quantity must a positive integer");
        } catch (IllegalArgumentException e) {
            showErrorMessage(e.getMessage());
        } catch (IllegalStateException e) {
            showErrorMessage(e.getMessage());
        }
    }
}

