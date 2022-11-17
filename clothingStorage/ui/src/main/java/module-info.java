module clothingStorage.ui {
    
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires clothingStorage.core;
    requires clothingStorage.json;
    requires clothingStorage.client;

    opens clothingStorage.ui to javafx.graphics, javafx.fxml;
}