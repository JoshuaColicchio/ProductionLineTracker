package application;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Controller class used to manage GUI object interactions.
 *
 * @author Joshua Colicchio
 */
public class Controller {

  @FXML private TextField productNameTextField;
  @FXML private TextField manufacturerTextField;
  @FXML private ChoiceBox<ItemType> itemTypeChoiceBox;
  @FXML private TableView<Product> existingProductsTableView;
  @FXML private ListView<String> chooseProductListView;
  @FXML private ComboBox<String> chooseQuantityComboBox;
  @FXML private TextArea productionLogTextArea;
  private Alert toolTipBox;
  private ArrayList<Product> productionArrayList = new ArrayList<>();
  private HashMap<Integer, Product> productionHashMap = new HashMap<>();

  /** Method that is called when the "Add Product" button is pressed. */
  @FXML
  public void addProductBtnPushed() {
    if (productNameTextField.getText().trim().isEmpty()) {
      toolTipBox.setContentText("\"Product Name\" must not be empty!");
      toolTipBox.show();
      return;
    }

    if (manufacturerTextField.getText().trim().isEmpty()) {
      toolTipBox.setContentText("\"Manufacturer\" must not be empty!");
      toolTipBox.show();
      return;
    }

    int prodId =
        DatabaseManager.addProduct(
            productNameTextField.getText(),
            manufacturerTextField.getText(),
            itemTypeChoiceBox.getValue());
    addToExistingProducts(
        new Widget(
            prodId,
            productNameTextField.getText(),
            manufacturerTextField.getText(),
            itemTypeChoiceBox.getValue()));

    // Clear out the fields after use.
    productNameTextField.setText("");
    manufacturerTextField.setText("");
    itemTypeChoiceBox.getSelectionModel().selectFirst();
  }

  /** Method that is called when the "Record Product" button is pressed. */
  @FXML
  public void recordProductBtnPushed() {
    if (chooseProductListView.getSelectionModel().getSelectedItem() == null) {
      toolTipBox.setContentText("No product selected!");
      toolTipBox.show();
      return;
    }

    try {
      ProductionRecord productionRecord =
          new ProductionRecord(
              Integer.parseInt(chooseQuantityComboBox.getValue()),
              productionArrayList.get(
                  chooseProductListView.getSelectionModel().getSelectedIndex()));

      productionRecord.setProductionNumber(DatabaseManager.saveProductionRecord(productionRecord));

      if (productionLogTextArea.getText().trim().isEmpty()) {
        productionLogTextArea.setText(productionRecord.toString());
      } else productionLogTextArea.appendText("\n" + productionRecord.toString());
    } catch (NumberFormatException nfe) {
      toolTipBox.setContentText("Invalid Quantity!");
      toolTipBox.show();
    }
  }

  /** Method that is called when the program launches which initializes all default values. */
  public void initialize() {
    // Load all existing products from the database.
    ResultSet products = DatabaseManager.loadProducts();
    if (products != null) {
      try {
        while (products.next()) {
          Product temp = null;
          switch (products.getString("TYPE")) {
            case "Audio":
              temp =
                  new AudioPlayer(
                      products.getInt("ID"),
                      products.getString("NAME"),
                      products.getString("MANUFACTURER"),
                      products.getString("TYPE"),
                      "unknown atm");
              break;
            case "Visual":
              temp =
                  new MoviePlayer(
                      products.getInt("ID"),
                      products.getString("NAME"),
                      products.getString("MANUFACTURER"),
                      null,
                      null);
              break;
            case "AudioMobile":
              System.out.println("audiomobile has not been implemented yet");
              return;
            case "VisualMobile":
              System.out.println("visualmobile has not been implemented yet");
              return;
            default:
              System.out.println("ERROR: Unknown product type: " + products.getString("TYPE"));
              return;
          }
          addToExistingProducts(temp);
        }
      } catch (Exception ex) {
        System.out.println(ex.toString());
      }
    }

    // Load all existing product records from the database.
    ResultSet records = DatabaseManager.loadProductionRecord();
    if (records != null) {
      try {
        while (records.next()) {
          // Get the corresponding product & create a ProductionRecord instance.
          Product prod = productionHashMap.get(records.getInt("PRODUCT_ID"));
          ProductionRecord pr =
              new ProductionRecord(
                  records.getInt("QUANTITY_PRODUCED"),
                  records.getString("SERIAL_NUM"),
                  records.getLong("DATE_PRODUCED"),
                  prod);
          pr.setProductRef(productionHashMap.get(pr.getProductID()));
          if (productionLogTextArea.getText().isEmpty())
            productionLogTextArea.setText(pr.toString());
          else productionLogTextArea.appendText("\n" + pr.toString());
        }
      } catch (Exception ex) {
        System.out.println(ex.toString());
      }
    }

    // set up item type choice box
    for (ItemType type : ItemType.values()) itemTypeChoiceBox.getItems().add(type);
    itemTypeChoiceBox.getSelectionModel().selectFirst();

    // set up combo box items
    chooseQuantityComboBox.getItems().addAll("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
    chooseQuantityComboBox.getSelectionModel().selectFirst();

    // set up alert box
    toolTipBox = new Alert(Alert.AlertType.WARNING);
  }

  /**
   * Method that takes a product, stores it, and adds it to the existing products TableView, and the
   * products ListView
   *
   * @param product Product to store and add.
   */
  public void addToExistingProducts(Product product) {
    productionArrayList.add(product);
    productionHashMap.put(product.getId(), product);
    existingProductsTableView.getItems().add(product);
    chooseProductListView.getItems().add(product.getManufacturer() + " " + product.getName());
  }
}
