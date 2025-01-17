package hotelProject1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HotelProject1App extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("HotelProject1 App");
        primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("HotelProject1.fxml"))));
        primaryStage.show();
    }
    
}
