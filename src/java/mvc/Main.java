package mvc;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent rootLayout = FXMLLoader.load(
                Objects.requireNonNull(getClass().getResource("/mvc/view/Main.fxml")));

        rootLayout.getStylesheets().add(
                Objects.requireNonNull(getClass().getResource("/mvc/css/style.css")).toExternalForm());

        Scene scene = new Scene(rootLayout);
        primaryStage.setTitle("MVC");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
