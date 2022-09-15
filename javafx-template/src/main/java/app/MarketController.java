package app;


//import java.util.Arrays;
//import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
//import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class MarketController {

    //private Market market;
//
    //public MarketController() {
    //    market = new Market();
    //}
//
    //private void updateMyList() {
    //    myList.getItems().clear();
    //    for (Item item : market.getMyList()) {
    //        myList.getItems().add(item.getTitle());
    //    }
    //}
//
    //private void updateMarketList() {
    //    marketList.getItems().clear();
    //    for (Item item : market.getMarketList()) {
    //        myList.getItems().add(item.getTitle());
    //    }
    //}


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

    @FXML private Button addItem, removeItem, suggestTrade;
    @FXML private ListView<String> myList;

    @FXML private void handleSuggestTrade() {

    }

    @FXML private void handleAddItem() {
        //String item = new Item(title, type);
        //market.addItemMyList(item);
        //updateMyList();
    }

    @FXML private void handleRemoveItem() {
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
