package app;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

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

    public StorageController() {
        storage = new Storage();
    }

    @FXML private ChoiceBox<String> brand;
    @FXML private ChoiceBox<Character> size;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        brand.getItems().addAll("Nike", "Adidas", "H&M", "Lacoste", "Louis Vuitton", "Supreme", "Levi's");
        size.getItems().addAll('S', 'M', 'L');
    }
    private void updateQuantitiesList() {
        if (this.quantitiesList == null || quantitiesList.getItems().isEmpty()) {
            List<String> clothingDisplays = storage.homepageDisplay();
            quantitiesList.getItems().addAll(clothingDisplays);
        } else {
            quantitiesList.getItems().clear();
            updateQuantitiesList();
        }
    }

    private void updateMarketList() {
        if (this.marketList == null || marketList.getItems().isEmpty()) {
            List<String> clothingPriceDisplays = storage.marketDisplay();
            marketList.getItems().addAll(clothingPriceDisplays);
        } else {
            marketList.getItems().clear();
            updateMarketList();
        }
    }


    @FXML private Button profileButton, marketButton;
    @FXML private Pane profilePane, marketPane;

    @FXML private void handleMarket() {
        if (!marketPane.isVisible()) {
            profilePane.setVisible(false);
            marketPane.setVisible(true);
            marketButton.setDisable(true);
            profileButton.setDisable(false);
        }
        
    }

    @FXML private void handleProfile() {
        if (!profilePane.isVisible()) {
            profilePane.setVisible(true);
            marketPane.setVisible(false);
            marketButton.setDisable(false);
            profileButton.setDisable(true);
        }
    }

    // Profile Page

    @FXML private Button addQuantity, removeQuantity, newClothingItem, increaseByOne, decreaseByOne, loadFromFile, writeToFile;
    @FXML private ListView<String> quantitiesList;
    @FXML private TextField price, quantity, typeOfClothing, fileToWriteOrRead;
    @FXML private Pane newClothingPane;

    private IFiles storageFileHandler = new FileReader();

    private void showErrorMessage(String errorMessage) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("ERROR OCCURRED");
        alert.setHeaderText("Error!");
        alert.setContentText(errorMessage);
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
        quantitiesList.setVisible(false);
        addQuantity.setDisable(true);
        removeQuantity.setDisable(true);
        newClothingItem.setDisable(true);
        increaseByOne.setDisable(true);
        decreaseByOne.setDisable(true);
        loadFromFile.setDisable(true);
        writeToFile.setDisable(true);
    }

    @FXML private void handleCancel() {
        newClothingPane.setVisible(false);
        quantitiesList.setVisible(true);
        addQuantity.setDisable(false);
        removeQuantity.setDisable(false);
        newClothingItem.setDisable(false);
        increaseByOne.setDisable(false);
        decreaseByOne.setDisable(false);
        loadFromFile.setDisable(false);
        writeToFile.setDisable(false);
    }

    @FXML private void handleOK() {
        try {
            String type = typeOfClothing.getText();
            String selectedBrand = brand.getValue();
            char selectedSize = size.getValue();
            Double selectedPrice = Double.parseDouble(price.getText());

            Clothing clothing = new Clothing(type, selectedBrand, selectedSize, selectedPrice);

            int selectedQuantity = Integer.parseInt(quantity.getText());
            storage.addNewClothing(clothing, selectedQuantity);
            updateQuantitiesList();
            updateMarketList();
            handleCancel();
            showConfirmedMessage("You successfully added the following: " + clothing.toString());
        } catch (NumberFormatException e) {
            showErrorMessage("Price must be a positive decimal number \nQuantity must a positive integer");
        } catch (IllegalArgumentException e) {
            showErrorMessage(e.getMessage());
        } 
        
    }

    @FXML private void handleIncreaseByOne() {

    }

    @FXML private void handleDecreaseByOne() {
        
    }


    @FXML private void handleAddQuantity() {
        String[] selectedClothing = quantitiesList.getSelectionModel().getSelectedItem().split(",");
        String type = selectedClothing[0];
        //char size = String.valueOf(selectedClothing[1]);
        //TODO
    }

    @FXML private void handleRemoveQuantity() {
        //String item = myList.getSelectionModel().getSelectedItem();
        //market.removeItemMyList(item);
        //updateMyList();
        //TODO
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
            updateQuantitiesList();
            updateMarketList();
            showConfirmedMessage("Storage of Clothing successfully loaded from file:" + filename);
        } catch (FileNotFoundException e) {
            showErrorMessage("File not found!");
        }
    }

    // Market Page

    @FXML private ListView<String> marketList;

    

  







    
}
