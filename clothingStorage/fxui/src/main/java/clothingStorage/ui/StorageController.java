package clothingStorage.ui;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import clothingStorage.core.Storage;
import clothingStorage.core.Clothing;
import clothingStorage.core.IFiles;
import clothingStorage.core.FileReader;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;

public class StorageController implements Initializable{

    /**
     * Storage containing Clothing and corresponding quantity
     */
    private Storage storage;
    /**
     * Current errormessage as shown in ui
     */
    private String errorMessage;

    /**
     * Constructor for StorageController initializing it with empty storage
     */
    public StorageController() {
        this.storage = new Storage();
    }

    /**
     * Sets storage to the given storage, helps controller test-class
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
     * Gets error-message for controller as shown in ui, helps controller test-class
     * 
     * @return the error-message
     */
    public String getErrorMessage() {
        return this.errorMessage;
    }

    /**
     * Choicebox of valid brands
     */
    @FXML private ChoiceBox<String> brand;
    /**
     * Choicebox for valid sizes
     */
    @FXML private ChoiceBox<Character> size;

    /**
     * Initializes controller with the choiceboxes
     * 
     * @param location to initialize
     * @param resources locale-specific objects
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        brand.getItems().addAll("Nike", "Adidas", "H&M", "Lacoste", "Louis Vuitton", "Supreme", "Levi's");
        size.getItems().addAll('S', 'M', 'L');
    }

    /**
     * Updates StorageList after change has been made
     */
    public void updateStorageList() {
        if (this.storageList == null || storageList.getItems().isEmpty()) {
            List<String> clothingDisplays = storage.storageDisplay();
            storageList.getItems().setAll(clothingDisplays);
        } else {
            storageList.getItems().clear();
            updateStorageList();
        }
    }

    /**
     * Updates PriceList after change has been made
     */
    public void updatePriceList() {
        if (this.priceList == null || priceList.getItems().isEmpty()) {
            List<String> clothingPriceDisplays = storage.priceDisplay();
            priceList.getItems().setAll(clothingPriceDisplays);
        } else {
            priceList.getItems().clear();
            updatePriceList();
        }
    }


    /**
     * Button for storage-page
     */
    @FXML private Button storagePageButton;
    /**
     * Button for price-page
     */
    @FXML private Button pricePageButton;
    /**
     * Pane for storage-page
     */
    @FXML private Pane storagePane;
    /**
     * Pane for price-page
     */
    @FXML private Pane pricePane;

    /**
     * Changes ui-view to the price-page
     */
    @FXML private void handlePricePageButton() {
        if (!pricePane.isVisible()) {
            storagePane.setVisible(false);
            pricePane.setVisible(true);
            pricePageButton.setDisable(true);
            storagePageButton.setDisable(false);
        }
        
    }

    /**
     * Changes ui-view to the storage-page
     */
    @FXML private void handleStoragePageButton() {
        if (!storagePane.isVisible()) {
            storagePane.setVisible(true);
            pricePane.setVisible(false);
            pricePageButton.setDisable(false);
            storagePageButton.setDisable(true);
        }
    }

    // Storage Page

    /**
     * Button for adding quantity
     */
    @FXML private Button addQuantity;
    /**
     * Button for removing quantity
     */
    @FXML private Button removeQuantity;
    /**
     * Button for adding new clothing-item
     */
    @FXML private Button newClothingItem;
    /**
     * Button for increasing quantity by one
     */
    @FXML private Button increaseByOne;
    /**
     * Button for decreasing quantity by one
     */
    @FXML private Button decreaseByOne;
    /**
     * Button for loading from file
     */
    @FXML private Button loadFromFile;
    /**
     * Button for writing to file
     */
    @FXML private Button writeToFile;
    /**
     * Listview with all clothing-items and their quantities
     */
    @FXML private ListView<String> storageList;
    /**
     * Textfield for price for clothing
     */
    @FXML private TextField price;
    /**
     * Textfield for quantity of clothing
     */
    @FXML private TextField quantity;
    /**
     * Textfield for type of clothing
     */
    @FXML private TextField typeOfClothing;
    /**
     * Textfield for file to be written or read
     */
    @FXML private TextField fileToWriteOrRead;
    /**
     * Textfield for quantity to be added
     */
    @FXML private TextField newQuantity;
    /**
     * Pane for adding a new clothing-item
     */
    @FXML private Pane newClothingPane;

    /**
     * storageFileHandler that handles reading and writing
     */
    private IFiles storageFileHandler = new FileReader();

    /**
     * Shows alert with error-message
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
     * Shows alert with confirmed-message
     * 
     * @param confirmedMessage to be shown in alert
     */
    private void showConfirmedMessage(String confirmedMessage) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("SUCCESS");
        alert.setHeaderText("Success!");
        alert.setContentText(confirmedMessage);
        alert.showAndWait();
    }

    /**
     * Resets inputs for making a new clothing-item
     */
    @FXML private void handleReset() {
        typeOfClothing.clear();
        brand.getSelectionModel().clearSelection();
        size.getSelectionModel().clearSelection();
        price.clear();
        quantity.clear();
    }

    /**
     * Resets inputs and shows pane for adding a new clothing-item
     */
    @FXML private void handleNewClothingItem() {
        handleReset();
        newClothingPane.setVisible(true);
        storageList.setVisible(false);
        addQuantity.setDisable(true);
        removeQuantity.setDisable(true);
        newClothingItem.setDisable(true);
        increaseByOne.setDisable(true);
        decreaseByOne.setDisable(true);
        loadFromFile.setDisable(true);
        writeToFile.setDisable(true);
    }

     /**
     * Removes clothing-item for the list
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
     * Cancels the adding of clothing-item
     */
    @FXML private void handleCancel() {
        newClothingPane.setVisible(false);
        storageList.setVisible(true);
        addQuantity.setDisable(false);
        removeQuantity.setDisable(false);
        newClothingItem.setDisable(false);
        increaseByOne.setDisable(false);
        decreaseByOne.setDisable(false);
        loadFromFile.setDisable(false);
        writeToFile.setDisable(false);
    }

    /**
     * Confirms the adding of a new clothing-item and adds it to list if succesful
     */
    @FXML private void handleOk() {
        try {
            String name = typeOfClothing.getText();
            String selectedBrand = brand.getValue();
            char selectedSize = size.getValue();
            Double selectedPrice = Double.parseDouble(price.getText());

            Clothing clothing = new Clothing(name, selectedBrand, selectedSize, selectedPrice);

            int selectedQuantity = Integer.parseInt(quantity.getText());
            storage.addNewClothing(clothing, selectedQuantity);
            updateStorageList();
            updatePriceList();
            handleCancel();
            showConfirmedMessage("You successfully added the following: " + clothing.toString());
        } catch (NumberFormatException e) {
            showErrorMessage("Price must be a positive decimal number \nQuantity must a positive integer");
        } catch (IllegalArgumentException e) {
            showErrorMessage(e.getMessage());
        } catch (NullPointerException e) {
            showErrorMessage("Fill in all fields");
        }
    }

    /**
     * Increases quantity for a selected clothing-item by one
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
     * Decreases quantity for a selected clothing-item by one
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
     * Increases quantity for a selected clothing-item by amount specified in textfield
     */
    @FXML private void handleAddQuantity() {
        int index = storageList.getSelectionModel().getSelectedIndex();
        try {
            if (storage.getAllClothes().isEmpty() || index==-1) throw new IndexOutOfBoundsException();
            int addQuantity = Integer.parseInt(newQuantity.getText());
            storage.increaseQuantity(storage.getClothing(index), addQuantity);
            updateStorageList();
        } catch (NumberFormatException e) {
            if (newQuantity.getText().isEmpty()) {
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
     * Decreases quantity for a selected clothing-item by amount specified in textfield
     */
    @FXML private void handleRemoveQuantity() {
        int index = storageList.getSelectionModel().getSelectedIndex();
        try {
            if (storage.getAllClothes().isEmpty() || index==-1) throw new IndexOutOfBoundsException();
            int decQuantity = Integer.parseInt(newQuantity.getText());
            storage.decreaseQuantity(storage.getClothing(index), decQuantity);
            updateStorageList();
        } catch (NumberFormatException e) {
            if (newQuantity.getText().isEmpty()) {
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

    /**
     * Writes to text-file specified in textfield
     */
    @FXML private void handleWriteToFile() {
        if (storage.getAllClothes().isEmpty()) {
            showErrorMessage("There has not been added any clothes to storage");
            return;
        }
        try {
            String filename = fileToWriteOrRead.getText();
            storageFileHandler.writeToFile(filename, storage);
            showConfirmedMessage("Storage of clothing successfully saved to file:" + filename);
        } catch (IOException e) {
            showErrorMessage("Error occurred while trying to save to file");
        }
    }

    /**
     * Loads from text-file specified in textfield
     */
    @FXML private void handleLoadFromFile() {
        try {
            String filename = fileToWriteOrRead.getText();
            storage = storageFileHandler.readFromFile(filename);
            updateStorageList();
            updatePriceList();
            showConfirmedMessage("Storage of Clothing successfully loaded from file:" + filename);
        } catch (FileNotFoundException e) {
            showErrorMessage("File not found!");
        }
    }

    // Price Page

    /**
     * Listview with all clothing-items and their prices
     */
    @FXML private ListView<String> priceList;
    /**
     * Button for confirming new price
     */
    @FXML private Button confirmNewPrice;
    /**
     * Button for confirming discount
     */
    @FXML private Button confirmDiscount;
    /**
     * Textfield for new price
     */
    @FXML private TextField newPrice;
    /**
     * Textfield for discount to add
     */
    @FXML private TextField discount;

    /**
     * Updates price-list with new price for selected clothing-item
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
     * Updates price-list with new price after adding discount
     */
    @FXML private void handleConfirmDiscount() {
        try {
            int index = priceList.getSelectionModel().getSelectedIndex();
            double discountToAdd = Double.parseDouble(discount.getText());
            storage.getClothing(index).setDiscount(discountToAdd/100);
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
     * Updates price-list with new price after removing discount
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
