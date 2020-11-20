package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import javafx.scene.control.Alert;

/**
 * Class that handles all database functionality.
 *
 * @author Joshua Colicchio
 */
class DatabaseManager {

  private static final HashMap<Integer, Product> allProducts = new HashMap<>();

  public static int addProduct(String prodName, String manuName, ItemType itemType) {
    return insert(
        "INSERT INTO PRODUCT (NAME, TYPE, MANUFACTURER) VALUES (?,?,?)",
        prodName,
        itemType.toString(),
        manuName);
  }

  public static List<Product> loadProducts() {
    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;
    List<Product> products = new ArrayList<>();

    try {
      connection = getConnection();
      if (connection != null) {
        statement = connection.createStatement();
        resultSet = statement.executeQuery("SELECT * FROM PRODUCT");
        while (resultSet.next()) {
          Product temp = Product.createProduct(resultSet);
          if (temp != null) {
            products.add(temp);
            allProducts.put(temp.getId(), temp);
          }
        }
      }
    } catch (Exception ex) {
      Controller.throwAlert(
          Alert.AlertType.ERROR,
          "Exception in DatabaseManager.loadProducts.\nReason: " + ex.getLocalizedMessage(),
          true);
    } finally {
      closeConnection(connection, null, statement, resultSet);
    }
    return products;
  }

  public static void saveProductionRecord(ProductionRecord prodRec) {
    insert(
        "INSERT INTO PRODUCTION_RECORD (PRODUCT_ID, QUANTITY_PRODUCED, SERIAL_NUM, DATE_PRODUCED) "
            + "VALUES (?,?,?,?)",
        prodRec.getProductID(),
        prodRec.getQuantityProduced(),
        prodRec.getSerialNumber(),
        prodRec.getDateProduced().getTime());
  }

  public static List<ProductionRecord> loadProductionRecords() {
    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;
    List<ProductionRecord> productionRecords = new ArrayList<>();

    try {
      Class.forName("org.h2.Driver");
      connection = getConnection();
      if (connection != null) {
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
      }
    } catch (Exception ex) {
      Controller.throwAlert(
          Alert.AlertType.ERROR,
          "Exception in DatabaseManager.loadProductionRecords.\nReason: "
              + ex.getLocalizedMessage(),
          true);
      ex.printStackTrace();
    } finally {
      closeConnection(connection, null, statement, resultSet);
    }
    return productionRecords;
  }

  private static int insert(String query, Object... params) {
    Connection connection = null;
    PreparedStatement pstmt = null;

    try {
      Class.forName("org.h2.Driver");
      connection = getConnection();

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
      }
    } catch (Exception ex) {
      Controller.throwAlert(
          Alert.AlertType.ERROR,
          "Exception in DatabaseManager.insert\nReason: " + ex.getLocalizedMessage(),
          false);
    } finally {
      closeConnection(connection, pstmt, null, null);
    }
    return -1;
  }

  private static Connection getConnection() {
    try {
      Properties prop = new Properties();
      prop.load(new FileInputStream("res/properties"));
      String password = reverseString(prop.getProperty("password"));

      // Check if the database is valid.
      new FileInputStream("res/ProductionTracker.mv.db");

      Class.forName("org.h2.Driver");
      return DriverManager.getConnection("jdbc:h2:./res/productiontracker", "ADMIN", password);
    } catch (NullPointerException npe) {
      Controller.throwAlert(
          Alert.AlertType.ERROR,
          "Unable to connect to the database."
              + "\nCould not read password property from the Database properties "
              + "file (res/properties).",
          true);
    } catch (FileNotFoundException fnf) {
      Controller.throwAlert(
          Alert.AlertType.ERROR,
          "Unable to connect to the database."
              + "\nPlease ensure that both the database (res/ProductionTracker.mv.db) and the properties "
              + "(res/properties) files are not missing.",
          true);
    } catch (Exception ex) {
      Controller.throwAlert(
          Alert.AlertType.ERROR,
          "Unable to connect to the database due to an unexpected error. ¯\\_(ツ)_/¯",
          true);
      ex.printStackTrace();
    }
    return null;
  }

  private static String reverseString(String pw) {
    String temp = pw.substring(pw.length() - 1);
    if (pw.length() > 1) {
      pw = pw.substring(0, pw.length() - 1);
      return temp + reverseString(pw);
    }
    return temp;
  }

  private static void closeConnection(
      Connection connection,
      PreparedStatement preparedStatement,
      Statement statement,
      ResultSet resultSet) {
    if (resultSet != null) {
      try {
        resultSet.close();
      } catch (SQLException ex) {
        System.out.println(
            "Error closing ResultSet in DatabaseManager.select.\nReason: "
                + ex.getLocalizedMessage());
      }
    }
    if (preparedStatement != null) {
      try {
        preparedStatement.close();
      } catch (SQLException ex) {
        System.out.println(
            "Error closing PreparedStatement in DatabaseManager.select.\nReason: "
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
