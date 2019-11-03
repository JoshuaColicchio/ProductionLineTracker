package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This is the database driver class. It handles all database interactions.
 *
 * @author Joshua Colicchio
 */
class DatabaseManager {

  private static final HashMap<Integer, Product> allProducts = new HashMap<>();

  /**
   * Method to add a new product to the database.
   *
   * @param prodName Name of the new product.
   * @param manuName Name of the manufacturer of the new product.
   * @param itemType Item type of the new product.
   */
  public static int addProduct(String prodName, String manuName, ItemType itemType) {
    return insert(
        "INSERT INTO PRODUCT (NAME, TYPE, MANUFACTURER) VALUES (?,?,?)",
        prodName,
        itemType.toString(),
        manuName);
  }

  /**
   * Method to load all existing products from the database.
   *
   * @return ResultSet containing all products in the database.
   */
  public static List<Product> loadProducts() {
    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;
    List<Product> products = new ArrayList<>();

    try {
      // establish connection
      Class.forName("org.h2.Driver");
      connection = DriverManager.getConnection("jdbc:h2:./res/productiontracker");
      statement = connection.createStatement();
      resultSet = statement.executeQuery("SELECT * FROM PRODUCT");
      while (resultSet.next()) {
        Product temp = null;
        if (resultSet.getString("TYPE").compareTo("Audio") == 0) {
          temp =
              new AudioPlayer(
                  resultSet.getInt("ID"),
                  resultSet.getString("NAME"),
                  resultSet.getString("MANUFACTURER"),
                  resultSet.getString("TYPE"),
                  "MP3");
        } else if (resultSet.getString("TYPE").compareTo("Visual") == 0) {
          temp =
              new MoviePlayer(
                  resultSet.getInt("ID"),
                  resultSet.getString("NAME"),
                  resultSet.getString("MANUFACTURER"),
                  new Screen("1920x1080", 144, 22),
                  MonitorType.LED);
        } else if (resultSet.getString("TYPE").compareTo("AudioMobile") == 0) {
          System.out.println("audio mobile has not been implemented yet");
        } else if (resultSet.getString("TYPE").compareTo("VisualMobile") == 0) {
          System.out.println("visual mobile has not been implemented yet");
        }

        if (temp != null) {
          products.add(temp);
          // I'd like to eliminate the need for this, but I haven't taken the time to think of
          // another way to do this yet.
          allProducts.put(temp.getId(), temp);
        }
      }
    } catch (Exception ex) {
      System.out.println(
          "Exception in DatabaseManager.loadProducts.\nReason: " + ex.getLocalizedMessage());
      ex.printStackTrace();
    } finally {
      closeConnection(connection, statement, resultSet);
    }
    return products;
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
  public static List<ProductionRecord> loadProductionRecords() {
    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;
    List<ProductionRecord> productionRecords = new ArrayList<>();

    try {
      // establish connection
      Class.forName("org.h2.Driver");
      connection = DriverManager.getConnection("jdbc:h2:./res/productiontracker");
      statement = connection.createStatement();
      resultSet = statement.executeQuery("SELECT * FROM PRODUCTION_RECORD");
      while (resultSet.next()) {
        ProductionRecord record =
            new ProductionRecord(
                resultSet.getInt("QUANTITY_PRODUCED"),
                resultSet.getString("SERIAL_NUM"),
                resultSet.getLong("DATE_PRODUCED"),
                allProducts.get(resultSet.getInt("PRODUCT_ID")));

        productionRecords.add(record);
      }
    } catch (Exception ex) {
      System.out.println(
          "Exception in DatabaseManager.loadProductionRecords.\nReason: "
              + ex.getLocalizedMessage());
      ex.printStackTrace();
    } finally {
      closeConnection(connection, statement, resultSet);
    }
    return productionRecords;
  }

  /**
   * General method to insert data into the database. For internal use only.
   *
   * @param query Query String to execute.
   * @param params Any values that should be inserted into the PreparedStatement upon creation.
   * @return ResultSet containing the generated keys.
   */
  private static int insert(String query, Object... params) {
    Connection connection = null;
    PreparedStatement pstmt = null;

    try {
      // establish connection
      Class.forName("org.h2.Driver");
      connection = DriverManager.getConnection("jdbc:h2:./res/productiontracker");

      // ensure valid connection
      if (connection != null) {
        pstmt = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);

        for (int i = 0; i < params.length; i++) {
          if (params[i] instanceof String) {
            pstmt.setString(i + 1, (String) params[i]);
          } else if (params[i] instanceof Integer) {
            pstmt.setInt(i + 1, (int) params[i]);
          } else if (params[i] instanceof Long) {
            pstmt.setLong(i + 1, (long) params[i]);
          }
        }

        pstmt.executeUpdate();
        ResultSet rs = pstmt.getGeneratedKeys();
        if (rs.next()) {
          return rs.getInt(1);
        }
      } else {
        throw new Exception("Could not establish connection.");
      }
    } catch (Exception ex) {
      System.out.println(
          "Exception in DatabaseManager.insert\nReason: " + ex.getLocalizedMessage() + "\n");
      ex.printStackTrace();
    } finally {
      if (pstmt != null) {
        try {
          pstmt.close();
        } catch (Exception ex) {
          System.out.println(
              "Error in DatabaseManager.insert. Could not close PreparedStatement.\nReason: "
                  + ex.getLocalizedMessage());
          ex.printStackTrace();
        }
      }
      if (connection != null) {
        try {
          connection.close();
        } catch (Exception ex) {
          System.out.println(
              "Error in DatabaseManager.insert. Could not close Connection.\nReason: "
                  + ex.getLocalizedMessage());
          ex.printStackTrace();
        }
      }
    }
    return -1;
  }

  private static void closeConnection(
      Connection connection, Statement statement, ResultSet resultSet) {
    if (resultSet != null) {
      try {
        resultSet.close();
      } catch (SQLException ex) {
        System.out.println(
            "Error closing ResultSet in DatabaseManager.select.\nReason: "
                + ex.getLocalizedMessage());
      }
    }
    if (statement != null) {
      try {
        statement.close();
      } catch (SQLException ex) {
        System.out.println(
            "Error closing Statement in DatabaseManager.select.\nReason: "
                + ex.getLocalizedMessage());
      }
    }
    if (connection != null) {
      try {
        connection.close();
      } catch (SQLException ex) {
        System.out.println(
            "Error closing Connection in DatabaseManager.select.\nReason: "
                + ex.getLocalizedMessage());
      }
    }
  }
}
