package app;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;

public class MarketController {

    @FXML private Button profile, market;
    @FXML private Pane profilePane, marketPane;

    @FXML private void handleMarket() {
        if (!marketPane.isVisible()) {
            profilePane.setVisible(false);
            marketPane.setVisible(true);
            market.setDisable(true);
            profile.setDisable(false);
        }
        
    }

    @FXML private void handleProfile() {
        if (!profilePane.isVisible()) {
            profilePane.setVisible(true);
            marketPane.setVisible(false);
            market.setDisable(false);
            profile.setDisable(true);
        }
    }

    @FXML private Button addItem, removeItem, suggestTrade;
    @FXML private ListView<String> itemList;

    @FXML private void handleSuggestTrade() {

    }

    @FXML private void handleAddItem() {

    }

    @FXML private void handleRemoveItem() {

    }




    
}
