package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.*;

/**
 * @brief This is the main driver class for this application.
 * @author Joshua Colicchio
 */
public class Main extends Application {

  final String H2_DRIVER = "org.h2.Driver";
  final String H2_URL = "jdbc:h2:./res/hr";

  final String USER = "";
  final String PASS = "";
  Connection con;
  Statement stmt;

  /**
   * The starting point of this JavaFX application.
   * @param primaryStage The primary stage of the JavaFX application.
   * @throws Exception
   */
  @Override
  public void start(Stage primaryStage) throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
    primaryStage.setTitle("Hello World");
    primaryStage.setScene(new Scene(root, 800, 400));
    primaryStage.show();

    connectToDatabase();

  }

  public void connectToDatabase() {
    try {
      Class.forName(H2_DRIVER);
      con = DriverManager.getConnection(H2_URL, USER, PASS);
      stmt = con.createStatement();
      stmt.close();
      con.close();
    }
    catch (Exception ex) {
      System.out.println("Eception in connectToDatabase: " + ex.toString());
    }
  }

  public static void main(String[] args) {
    launch(args);
  }
}
