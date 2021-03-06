package application;

import george.controller.ColorController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/george/vue/ColorView.fxml"));
        Parent root = fxmlLoader.load();
        primaryStage.setTitle("ColorApp");
        ColorController controller = fxmlLoader.getController();
        controller.setStage(primaryStage);
        controller.drawShapes();
        primaryStage.setScene(new Scene(root, 600, 600));
        primaryStage.show();
    }


    public static void main(String[] args) {

        launch(args);
    }
}
