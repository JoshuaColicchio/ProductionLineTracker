package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

/**
 * This is the database driver class. It handles all database interactions.
 *
 * @author Joshua Colicchio
 */

// I'm aware of the 'Declaration access can be weaker' warning, but felt it wasn't important. Let me know if it is.
public class DatabaseManager {

  private static final String H2_DRIVER = "org.h2.Driver";
  private static final String H2_URL = "jdbc:h2:./res/productiontracker";

  /**
   * Method to add a new product to the database.
   *
   * @param prodName Name of the new product.
   * @param manuName Name of the manufacturer of the new product.
   * @param itemType Item type of the new product.
   */
  public static void addProduct(String prodName, String manuName, String itemType) {
    try {
      // establish connection
      Class.forName(H2_DRIVER);
      Connection connection = DriverManager.getConnection(H2_URL);

      // ensure valid connection
      if (connection != null) {
        // build query using prepared statement object
        PreparedStatement pstmt =
            connection.prepareStatement(
                "INSERT INTO PRODUCT (NAME, TYPE, MANUFACTURER) VALUES (?,?,?)",
                PreparedStatement.RETURN_GENERATED_KEYS);
        pstmt.setString(1, prodName);
        pstmt.setString(2, itemType);
        pstmt.setString(3, manuName);

        // execute the query
        pstmt.executeUpdate();

        // close connections
        pstmt.close();
        connection.close();
      } else {
        throw new Exception("Could not establish connection.");
      }

    } catch (Exception ex) {
      System.out.println(
          "Exception in DatabaseManager.addProduct (Line: "
              + ex.getStackTrace()[0].getLineNumber()
              + ")\nReason: "
              + ex.getLocalizedMessage());
    }
  }
}
