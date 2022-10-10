package clothingStorage.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class StorageApp extends Application {

    /**
     * Starting the app
     *
     * @result "Clothing Storage" will be setup and be ready to be launched
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("Storage.fxml"));
        Parent parent = fxmlLoader.load();
        stage.setScene(new Scene(parent));
        stage.setTitle("Clothing Storage");
        stage.show();
    }

    /**
     * Launches the app
     *
     * @result "Clothing Storage" will be openend and ready for interaction
     */
    public static void main(String[] args) {
        launch();
    }
}