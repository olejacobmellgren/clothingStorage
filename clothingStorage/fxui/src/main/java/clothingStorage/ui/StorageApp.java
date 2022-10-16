package clothingStorage.ui;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * JavaFX App.
 */
public class StorageApp extends Application {

    /**
     * The scene for the app.
     */
    private static Scene scene;

    /**
     * The app will be setup and be ready to be launched.
     */
    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFxml("Storage.fxml"));
        stage.setScene(scene);
        stage.setTitle("Clothing Storage");
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFxml(fxml));
    }

    private static final Parent loadFxml(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StorageApp.class.getResource(fxml));
        return fxmlLoader.load();
    }

    /**
     * Launches the app.
     */
    public static void main(String[] args) {
        launch();
    }
}