package app;


import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

//import java.util.Arrays;
//import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
//import javafx.scene.control.TextField;
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
        brand.getItems().addAll("nike", "adidas", "h&m", "lacoste", "louis vuitton", "supreme", "levi's");
        size.getItems().addAll('S', 'M', 'L');
        //quantitiesList = new ListView<>();
        //quantitiesList.getItems().addAll("Hei", "thea");
        //priceList = new ListView<>();
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
            //List<String> clothingDisplays = storage.MarketDisplay();
            //marketList.getItems().addAll(clothingDisplays);
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

    @FXML private Button addQuantity, removeQuantity, newClothingItem;
    @FXML private ListView<String> quantitiesList;
    @FXML private TextField price, quantity, typeOfClothing;
    @FXML private Pane newClothingPane;

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

    @FXML private void handleNewClothingItem() {
        newClothingPane.setVisible(true);
        addQuantity.setDisable(true);
        removeQuantity.setDisable(true);
        newClothingItem.setDisable(true);
    }

    @FXML private void handleCancel() {
        newClothingPane.setVisible(false);
        addQuantity.setDisable(false);
        removeQuantity.setDisable(false);
        newClothingItem.setDisable(false);
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


    @FXML private void handleAddQuantity() {
        String[] selectedClothing = quantitiesList.getSelectionModel().getSelectedItem().split(",");
        String type = selectedClothing[0];
        //char size = String.valueOf(selectedClothing[1]);
    }

    @FXML private void handleRemoveQuantity() {
        //String item = myList.getSelectionModel().getSelectedItem();
        //market.removeItemMyList(item);
        //updateMyList();
    }

    // Market Page

    //@FXML private Button search;
    //@FXML private TextField searchbar;
    @FXML private ListView<String> marketList;
    @FXML private CheckBox filterBook, filterPlant;

    //TODO Startet på search-bar funksjon, men vet ikke om det ble for komplisert/unødvendig

    @FXML private void handleSearch() {
        //itemsList.getItems().clear();
        //itemsList.getItems().addAll(searchList(searchbar.getText()), itemsList.getItems());
    }

    //private List<String> searchList(String searchWords, List<String> stringsList) {
    //    
    //    List<String> searchWordsList = Arrays.asList(searchWords.trim().split(" "));
//
    //    return stringsList.stream().filter(input -> searchWordsList.stream().allMatch(word -> input.toLowerCase().contains(word.toLowerCase())))
    //
    //private void filterItems(String type) {
    //    for (Item item : market.getMarketList()) { //getItemsList() to be implemented
    //        if (item.getType().equals(type)) {
    //            marketList.getItems().add(item.getTitle());
    //        }
    //    }
    //}

    @FXML private void handleFilterItems() {
        //if (filterBook.isSelected()) {
        //    marketList.getItems().clear();
        //    filterItems("book");
        //}
        //else if (filterPlant.isSelected()) {
        //    marketList.getItems().clear();
        //    filterItems("plant");
        //}
    }

    

  







    
}
