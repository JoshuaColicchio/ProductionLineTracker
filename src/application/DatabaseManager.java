package application;

import java.sql.*;

/**
 * This is the database driver class. It handles all database interactions.
 *
 * @author Joshua Colicchio
 */
class DatabaseManager {

  /**
   * Method to add a new product to the database.
   *
   * @param prodName Name of the new product.
   * @param manuName Name of the manufacturer of the new product.
   * @param itemType Item type of the new product.
   */
  public static int addProduct(String prodName, String manuName, ItemType itemType) {
    ResultSet rs =
        insert(
            "INSERT INTO PRODUCT (NAME, TYPE, MANUFACTURER) VALUES (?,?,?)",
            prodName,
            itemType.toString(),
            manuName);

    try {
      if (rs != null && rs.next()) return rs.getInt(1);
    } catch (Exception ex) {
      System.out.println(
          "Exception in DatabaseManager.addProduct\nReason: " + ex.getLocalizedMessage());
    }
    return -1;
  }

  /**
   * Method to load all existing products from the database.
   *
   * @return ResultSet containing all products in the database.
   */
  public static ResultSet loadProducts() {
    return select("SELECT * FROM PRODUCT");
  }

  /**
   * Method to save a ProductionRecord to the database.
   *
   * @param prodRec ProductionRecord object that you want to be saved to the database.
   */
  public static void saveProductionRecord(ProductionRecord prodRec) {
    insert(
        "INSERT INTO PRODUCTION_RECORD (PRODUCT_ID, QUANTITY_PRODUCED, SERIAL_NUM, DATE_PRODUCED) "
            + "VALUES (?,?,?,?)",
        prodRec.getProductID(),
        prodRec.getQuantityProduced(),
        prodRec.getSerialNumber(),
        prodRec.getDateProduced().getTime());
  }

  /**
   * Method to load all existing production records from the database.
   *
   * @return ResultSet containing all production records in the database.
   */
  public static ResultSet loadProductionRecord() {
    return select("SELECT * FROM PRODUCTION_RECORD");
  }

  /**
   * General method to insert data into the database. For internal use only.
   *
   * @param query Query String to execute.
   * @param params Any values that should be inserted into the PreparedStatement upon creation.
   * @return ResultSet containing the generated keys.
   */
  private static ResultSet insert(String query, Object... params) {
    try {
      // establish connection
      Class.forName("org.h2.Driver");
      Connection connection = DriverManager.getConnection("jdbc:h2:./res/productiontracker");

      // ensure valid connection
      if (connection != null) {
        PreparedStatement pstmt =
            connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);

        for (int i = 0; i < params.length; i++) {
          if (params[i] instanceof String) pstmt.setString(i + 1, (String) params[i]);
          else if (params[i] instanceof Integer) pstmt.setInt(i + 1, (int) params[i]);
          else if (params[i] instanceof Long) pstmt.setLong(i + 1, (long) params[i]);
        }

        pstmt.executeUpdate();
        return pstmt.getGeneratedKeys();
      } else throw new Exception("Could not establish connection.");
    } catch (Exception ex) {
      System.out.println(
          "Exception in DatabaseManager.insert\nReason: " + ex.getLocalizedMessage() + "\n");
      ex.printStackTrace();
    }
    return null;
  }

  /**
   * Method to select data from the database.
   *
   * @param query Query string to execute.
   * @return ResultSet containing the result of the query.
   */
  private static ResultSet select(String query) {
    try {
      // establish connection
      Class.forName("org.h2.Driver");
      Connection connection = DriverManager.getConnection("jdbc:h2:./res/productiontracker");

      // ensure valid connection
      if (connection != null) {
        return connection.createStatement().executeQuery(query);
      } else throw new Exception("Could not establish connection.");

    } catch (Exception ex) {
      System.out.println(
          "Exception in DatabaseManager.select\nReason: " + ex.getLocalizedMessage());
    }
    return null;
  }
}
