package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Class that is the main driver for this application.
 *
 * @author Joshua Colicchio
 */
public class Main extends Application {
  @Override
  public void start(Stage primaryStage) throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("productionLineTracker.fxml"));
    primaryStage.setTitle("Production Tracker");
    Scene scene = new Scene(root, 800, 400);
    scene.getStylesheets().add(Main.class.getResource("stylesheet.css").toExternalForm());
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
