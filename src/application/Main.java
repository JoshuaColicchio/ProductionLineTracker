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
    Parent root = FXMLLoader.load(getClass().getResource("productionLineTracker.fxml"));
    primaryStage.setTitle("Production Tracker");
    Scene scene = new Scene(root, 800, 400);
    scene.getStylesheets().add(Main.class.getResource("stylesheet.css").toExternalForm());
    primaryStage.setScene(scene);
    primaryStage.show();
    System.out.println(reverseString("helloworld"));
  }

  public static void main(String[] args) {
    launch(args);
  }

  public String reverseString(String pw) {
    String temp = pw.substring(pw.length() - 1);
    if (pw.length() > 1) {
      pw = pw.substring(0, pw.length() - 1);
      return temp + reverseString(pw);
    }
    return temp;
  }
}

/*  Potential solution to issue 12 - reverse dept id
 public void reverseString(String string) {
   String temp = "";
   for (int i = 1; i <= string.length(); i++) {
     temp = temp + string.substring(string.length()-i, string.length()-i + 1);
   }
   System.out.println("Started with: " + string + "\nEnded with: " + temp);
 }
*/
