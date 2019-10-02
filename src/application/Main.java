package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This is the main driver class for this application.
 *
 * @author Joshua Colicchio
 */
public class Main extends Application {

  /**
   * The starting point of this JavaFX application.
   *
   * @param primaryStage The primary stage of the JavaFX application.
   * @throws Exception Throws exception if an error occurs.
   */
  @Override
  public void start(Stage primaryStage) throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
    primaryStage.setTitle("Production Tracker");
    Scene scene = new Scene(root, 600, 400);
    scene.getStylesheets().add(Main.class.getResource("stylesheet.css").toExternalForm());
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}