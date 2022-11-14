package clothingStorage.ui;

import clothingStorage.core.Clothing;
import clothingStorage.core.Storage;
import com.fasterxml.jackson.core.JsonProcessingException;
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controller for JavaFX app.
 */
public class PricePageController implements Initializable {

    /**
     * Storage containing Clothing and corresponding quantity.
     */
    private Storage storage;
    /**
     * Current errormessage as shown in ui.
     */
    private String errorMessage;
    /**
     * Choicebox for what to filter on.
     */
    @FXML
    private ChoiceBox<String> filters;
    /**
     * Choicebox for what type of clothing to filter on.
     */
    @FXML
    private ChoiceBox<String> typeOfClothingFilter;
    /**
     * Choicebox for what brand to filter on.
     */
    @FXML
    private ChoiceBox<String> brands;
    /**
     * Button for storage-page.
     */
    @FXML
    private Button storagePageButton;
    /**
     * Button for statistics-page.
     */
    @FXML
    private Button statisticsPageButton;
    /**
     * Listview with all clothing-items and their prices.
     */
    @FXML
    private ListView<String> priceList;
    /**
     * Button for confirming new price.
     */
    @FXML
    private Button confirmNewPrice;
    /**
     * Button for confirming discount.
     */
    @FXML
    private Button confirmDiscount;
    /**
     * Textfield for new price.
     */
    @FXML
    private TextField newPrice;
    /**
     * Textfield for discount to add.
     */
    @FXML
    private TextField discount;
    /**
     * Button to confirm filter.
     */
    @FXML
    private Button confirmFilter;

    private Access access;
    
    /**
     * Constructor for StorageController initializing it with empty storage.
     *
     * @throws URISyntaxException if string could not be parsed as URI reference
     */
    public PricePageController() throws URISyntaxException {
    }

    /**
     * Initializes controller with the choiceboxes.
     */
    @FXML
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        filters.getItems().addAll("Lowest Price", "Highest Price", "Type", 
            "Brand", "On Sale");
        typeOfClothingFilter.getItems().addAll("Pants", "Shirt", "Underwear",
            "Socks", "Sweater", "Jacket", "Shorts");
        brands.getItems().addAll("Nike", "Adidas", "H&M", "Lacoste", 
            "Louis Vuitton", "Supreme", "Levi's"); 
    }

    public void setAccess(Access access) {
        this.access = access;
        updatePriceList(access.getPriceDisplay());
    }

    /**
     * Sets storage to the given storage, helps controller test-class.
     *
     * @param storage to be set as storage for the controller
     */
    public void setStorage(Storage storage) {
        if (this.storage != null) {
            priceList.getItems().clear();
        }
        this.storage = storage;
        updatePriceList(access.getPriceDisplay());
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
     * Updates PriceList after change has been made.
     */
    private void updatePriceList(List<String> priceDisplay) {
        priceList.getItems().setAll(priceDisplay);
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
     * Changes ui-view to the storage-page.
     */
    @FXML
    private void handleStoragePageButton() throws IOException {
        FXMLLoader loader;
        if (access instanceof RemoteAccess) {
            loader = new FXMLLoader(getClass().getResource("StoragePageRemote.fxml"));
        } else {
            loader = new FXMLLoader(getClass().getResource("StoragePageDirect.fxml"));
        }
        Parent root = loader.load();

        StoragePageController controller = loader.getController();
        controller.setAccess(access);

        Scene scene = new Scene(root);
        Stage stage = (Stage) storagePageButton.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("New Clothing");
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
     * Detects if extra choice boxes needs to be shown or removed.
     */
    @FXML
    private void handleFilterChoice() {
        if (filters.getValue() == "Brand") {
            brands.setVisible(true);
            typeOfClothingFilter.setVisible(false);
        } else if (filters.getValue() == "Type") {
            brands.setVisible(false);
            typeOfClothingFilter.setVisible(true);
        } else {
            brands.setVisible(false);
            typeOfClothingFilter.setVisible(false);
        }
    }
    
    /**
     * Filters price-list with chosen filter.
     */
    @FXML
    private void handleConfirmFilter() {
        if (filters.getValue() == null) {
            showErrorMessage("You must choose a filter in the choice box first");
        } else if (filters.getValue() == "Type" && typeOfClothingFilter.getValue() == null) {
            showErrorMessage("You must choose a type of Clothing in the choice box first");
        } else if (filters.getValue() == "Brand" && brands.getValue() == null) {
            showErrorMessage("You must choose a brand in the choice box first");
        } else if (filters.getValue() == "Lowest Price") {
            updatePriceList(access.getSortedPriceDisplay(0));
        } else if (filters.getValue() == "Highest Price") {
            updatePriceList(access.getSortedPriceDisplay(1));
        } else if (filters.getValue() == "Brand") {
            String brand = brands.getValue();
            updatePriceList(access.getSortedBrandPriceDisplay(brand));
        } else if (filters.getValue() == "Type") {
            String type = typeOfClothingFilter.getValue();
            updatePriceList(access.getSortedTypePriceDisplay(type));
        } else if (filters.getValue() == "On Sale") {
            updatePriceList(access.getSortedPriceDisplay(2));
        }
    }

    /**
     * Resets filter if there is any.
     */
    @FXML
    private void handleResetFilter() {
        try {
            if (access.getSortedNames().size() != 0
                || (access.getSortedNames().size() == 0
                && filters.getValue() != null)) {
                
                filters.setValue(null);
                brands.setValue(null);
                typeOfClothingFilter.setValue(null);
                updatePriceList(access.getPriceDisplay());
            } else {
                throw new IllegalStateException("Filter is not applied");
            }
        } catch (IllegalStateException e) {
            showErrorMessage(e.getMessage());
        }
    }

    /**
     * Updates price-list with new price for selected clothing-item.
     *
     * @throws JsonProcessingException if problems processing JSON content
     */
    @FXML
    private void handleConfirmNewPrice() throws JsonProcessingException {
        try {
            int index = priceList.getSelectionModel().getSelectedIndex();
            if (access.getNames().isEmpty() || index == -1) {
                throw new IndexOutOfBoundsException();
            }
            double price = Double.parseDouble(newPrice.getText());
            boolean updated = false;
            if (access.getStorage().getIsSortedClothes() == true) {
                List<String> names = access.getSortedNames();
                String name = names.get(index);
                Clothing clothing = access.getClothing(name);
                for (Clothing clothing2 : access.getStorage().getSortedClothings()) {
                    if (clothing.equalsButDifferentSize(clothing2)) {
                        updated = access.updatePrice(clothing2, price);
                    }
                }
            } else {
                List<String> names = access.getNames();
                String name = names.get(index);
                Clothing clothing = access.getClothing(name);
                for (Clothing clothing2 : access.getStorage().getAllClothes().keySet()) {
                    if (clothing.equalsButDifferentSize(clothing2)) {
                        updated = access.updatePrice(clothing2, price);
                    }
                }
            }
            
            if (access.getStorage().getIsSortedClothes() == true && updated == true) {
                this.handleConfirmFilter();
            } else if (updated == true) {
                updatePriceList(access.getPriceDisplay());
            }
            newPrice.clear();
        } catch (NumberFormatException e) {
            if (newPrice.getText().isEmpty()) {
                showErrorMessage("Specify price first in textfield");
            } else {
                showErrorMessage("Input must be a number");
            }
        } catch (IndexOutOfBoundsException e) {
            if (access.getNames().isEmpty()) {
                showErrorMessage("Add a new clothing to storage first");
            } else {
                showErrorMessage("Select a clothing before changing price");
            }
        }
    }

    /**
     * Updates price-list with new price after adding discount.
     *
     * @throws JsonProcessingException if problems processing JSON content
     */
    @FXML
    private void handleConfirmDiscount() throws JsonProcessingException {
        try {
            int index = priceList.getSelectionModel().getSelectedIndex();
            if (access.getNames().isEmpty() || index == -1) {
                throw new IndexOutOfBoundsException();
            }
            double discountToAdd = Double.parseDouble(discount.getText());
            boolean updated = false;
            if (access.getStorage().getIsSortedClothes() == true) {
                List<String> names = access.getSortedNames();
                String name = names.get(index);
                Clothing clothing = access.getClothing(name);
                for (Clothing clothing2 : access.getStorage().getSortedClothings()) {
                    if (clothing.equalsButDifferentSize(clothing2)) {
                        updated = access.updateDiscount(clothing2, discountToAdd / 100);
                    }
                }
            } else {
                List<String> names = access.getNames();
                String name = names.get(index);
                Clothing clothing = access.getClothing(name);
                for (Clothing clothing2 : access.getStorage().getAllClothes().keySet()) {
                    if (clothing.equalsButDifferentSize(clothing2)) {
                        updated = access.updateDiscount(clothing2, discountToAdd / 100);
                    }
                }
            }
            if (access.getStorage().getIsSortedClothes() == true && updated == true) {
                this.handleConfirmFilter();
            } else if (updated == true) {
                updatePriceList(access.getPriceDisplay());
            }
            discount.clear();
        } catch (NumberFormatException e) {
            if (discount.getText().isEmpty()) {
                showErrorMessage("Specify discount first in textfield");
            } else {
                showErrorMessage("Input must be a number");
            }
        } catch (IndexOutOfBoundsException e) {
            if (access.getNames().isEmpty()) {
                showErrorMessage("Add a new clothing to storage first");
            } else {
                showErrorMessage("Select a clothing before increasing quantity");
            }
        } catch (IllegalArgumentException e) {
            showErrorMessage(e.getMessage());
        } catch (IllegalStateException e) {
            showErrorMessage(e.getMessage());
        }
    }

    /**
     * Updates price-list with new price after removing discount.
     *
     * @throws JsonProcessingException if problems processing JSON content
     */
    @FXML
    private void handleRemoveDiscount() throws JsonProcessingException {
        try {
            int index = priceList.getSelectionModel().getSelectedIndex();
            if (access.getNames().isEmpty() || index == -1) {
                throw new IndexOutOfBoundsException();
            }
            boolean removed = false;
            if (access.getStorage().getIsSortedClothes() == true) {
                List<String> names = access.getSortedNames();
                String name = names.get(index);
                Clothing clothing = access.getClothing(name);
                for (Clothing clothing2 : access.getStorage().getSortedClothings()) {
                    if (clothing.equalsButDifferentSize(clothing2)) {
                        removed = access.removeDiscount(clothing2);
                    }
                }
            } else {
                List<String> names = access.getNames();
                String name = names.get(index);
                Clothing clothing = access.getClothing(name);
                for (Clothing clothing2 : access.getStorage().getAllClothes().keySet()) {
                    if (clothing.equalsButDifferentSize(clothing2)) {
                        removed = access.removeDiscount(clothing2);
                    }
                }
            }
            if (access.getStorage().getIsSortedClothes() == true && removed == true) {
                this.handleConfirmFilter();
            } else if (removed == true) {
                updatePriceList(access.getPriceDisplay());
            }
        } catch (IndexOutOfBoundsException e) {
            if (access.getNames().isEmpty()) {
                showErrorMessage("Add a new clothing to storage first");
            } else {
                showErrorMessage("Select a clothing before increasing quantity");
            }
        } catch (IllegalStateException e) {
            showErrorMessage(e.getMessage());
        }
    }
}