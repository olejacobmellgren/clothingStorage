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

    private Storage storage;
    private Clothing clothing;
    private String errorMessage;

    public StorageController() {
        this.storage = new Storage();
    }

    public void setStorage(Storage storage) {
    if (this.storage != null) {
        storageList.getItems().clear();
    }
    this.storage = storage;
    updateStorageList();
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }


    @FXML private ChoiceBox<String> brand;
    @FXML private ChoiceBox<Character> size;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        brand.getItems().addAll("Nike", "Adidas", "H&M", "Lacoste", "Louis Vuitton", "Supreme", "Levi's");
        size.getItems().addAll('S', 'M', 'L');
    }

    public void updateStorageList() {
        if (this.storageList == null || storageList.getItems().isEmpty()) {
            List<String> clothingDisplays = storage.storageDisplay();
            storageList.getItems().setAll(clothingDisplays);
        } else {
            storageList.getItems().clear();
            updateStorageList();
        }
    }

    public void updatePriceList() {
        if (this.priceList == null || priceList.getItems().isEmpty()) {
            List<String> clothingPriceDisplays = storage.priceDisplay();
            priceList.getItems().setAll(clothingPriceDisplays);
        } else {
            priceList.getItems().clear();
            updatePriceList();
        }
    }


    @FXML private Button storagePageButton, pricePageButton;
    @FXML private Pane storagePane, pricePane;

    @FXML private void handlePricePageButton() {
        if (!pricePane.isVisible()) {
            storagePane.setVisible(false);
            pricePane.setVisible(true);
            pricePageButton.setDisable(true);
            storagePageButton.setDisable(false);
        }
        
    }

    @FXML private void handleStoragePageButton() {
        if (!storagePane.isVisible()) {
            storagePane.setVisible(true);
            pricePane.setVisible(false);
            pricePageButton.setDisable(false);
            storagePageButton.setDisable(true);
        }
    }

    // Storage Page

    @FXML private Button addQuantity, removeQuantity, newClothingItem, increaseByOne, decreaseByOne, loadFromFile, writeToFile;
    @FXML private ListView<String> storageList;
    @FXML private TextField price, quantity, typeOfClothing, fileToWriteOrRead, newQuantity;
    @FXML private Pane newClothingPane;

    private IFiles storageFileHandler = new FileReader();

    private void showErrorMessage(String errorMessage) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("ERROR OCCURRED");
        alert.setHeaderText("Error!");
        alert.setContentText(errorMessage);
        this.errorMessage = errorMessage;
        alert.showAndWait();
    }

    private void showConfirmedMessage(String confirmedMessage) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("SUCCESS");
        alert.setHeaderText("Success!");
        alert.setContentText(confirmedMessage);
        alert.showAndWait();
    }

    @FXML private void handleReset() {
        typeOfClothing.clear();
        brand.getSelectionModel().clearSelection();
        size.getSelectionModel().clearSelection();
        price.clear();
        quantity.clear();
    }

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

    @FXML private void handleRemoveClothingItem() {
        try {
            int index = storageList.getSelectionModel().getSelectedIndex();
            storage.removeClothing(storage.getClothing(index));
            updateStorageList();
        } catch (IndexOutOfBoundsException e) {
            showErrorMessage("You need to select an item from the list");
        }
    }

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

    @FXML private ListView<String> priceList;
    @FXML private Button confirmNewPrice, confirmDiscount;
    @FXML private TextField newPrice, discount;

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
