package application;

import java.sql.*;

/**
 * This is the database driver class. It handles all database interactions.
 *
 * @author Joshua Colicchio
 */
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
  public static int addProduct(String prodName, String manuName, String itemType) {
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
        ResultSet rs = pstmt.getGeneratedKeys();

        while (rs.next()) {
          return rs.getInt(1);
        }

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
    return -1;
  }

  public static ResultSet loadProducts() {
    try {
      // establish connection
      Class.forName(H2_DRIVER);
      Connection connection = DriverManager.getConnection(H2_URL);

      // ensure valid connection
      if (connection != null) {
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM PRODUCT");

        return rs;
      }
    } catch (Exception ex) {
      System.out.println(ex.toString());
    }
    return null;
  }

  public static void saveProductionRecord(String quantity, String productId, String serialNum) {
    try {
      // establish connection
      Class.forName(H2_DRIVER);
      Connection connection = DriverManager.getConnection(H2_URL);

      // ensure valid connection
      if (connection != null) {
        PreparedStatement pstmt = connection.prepareStatement(
                "INSERT INTO PRODUCTIONRECORD (PRODUCTION_NUM, PRODUCT_ID, SERIAL_NUM, DATE_PRODUCED)" +
                     " VALUES (?,?,?,?)");
        pstmt.setString(1, quantity);
        pstmt.setString(2, productId);
        pstmt.setString(3, serialNum);
        pstmt.setString(4, java.util.Calendar.getInstance().getTime().toString());

        pstmt.executeUpdate();
      }
    } catch (Exception ex) {
      System.out.println(ex.toString());
    }
  }

  public static ResultSet loadProductionRecord() {
    try {
      // establish connection
      Class.forName(H2_DRIVER);
      Connection connection = DriverManager.getConnection(H2_URL);

      // ensure valid connection
      if (connection != null) {
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM PRODUCTIONRECORD");

        return rs;
      }
    } catch (Exception ex) {
      System.out.println(ex.toString());
    }
    return null;
  }
}
