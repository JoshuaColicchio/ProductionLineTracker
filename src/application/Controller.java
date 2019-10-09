package application;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Controller class used to manage GUI object interactions.
 *
 * @author Joshua Colicchio
 */
@SuppressWarnings("WeakerAccess")
public class Controller {

  @FXML private TabPane tabPaneOuter;

  @FXML private TextField productNameTextField;

  @FXML private TextField manufacturerTextField;

  @FXML private ChoiceBox<String> itemTypeChoiceBox;

  @FXML private Button addProductBtn;

  @FXML private TableView<Product> existingProductsTableView;

  @FXML private TableColumn existingProdId;

  @FXML private TableColumn existingProdName;

  @FXML private TableColumn existingProdManu;

  @FXML private TableColumn existingProdType;

  @FXML private ListView<String> chooseProductListView;

  @FXML private ComboBox<String> chooseQuantityComboBox;

  @FXML private Button recordProductBtn;

  @FXML private TextArea productionLogTextArea;

  private Alert toolTipBox;

  private ArrayList<Product> productionCollection = new ArrayList<>();

  /** Method that is called when the "Add Product" button is pressed. */
  @FXML
  public void addProductBtnPushed() {
    // Check to ensure all fields filled
    if (productNameTextField.getText().trim().length() == 0) {
      toolTipBox.setContentText("\"Product Name\" must not be empty!");
      toolTipBox.show();
    } else if (manufacturerTextField.getText().trim().length() == 0) {
      toolTipBox.setContentText("\"Manufacturer\" must not be empty!");
      toolTipBox.show();
    } else { // We don't have to check the itemType selection because it defaults to Audio, so it'll
      // never be null.
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
    }

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

    Product prod =
        productionCollection.get(chooseProductListView.getSelectionModel().getSelectedIndex());

    System.out.println(prod.getName() + " " + prod.getId());

    if (productionLogTextArea.getText().isEmpty()) {
      productionLogTextArea.setText(
          "Product ID: "
              + prod.getId()
              + " | Serial Number: "
              + "0"
              + " | Quantity Produced: "
              + chooseQuantityComboBox.getValue()
              + " | Date Produced: "
              + java.util.Calendar.getInstance().getTime().toString());
    } else {
      productionLogTextArea.appendText(
          "\nProduct ID: "
              + prod.getId()
              + " | Serial Number: 0"
              + " | Quantity Produced: "
              + chooseQuantityComboBox.getValue()
              + " | Date Produced: "
              + java.util.Calendar.getInstance().getTime().toString());
    }
    DatabaseManager.saveProductionRecord(
        chooseQuantityComboBox.getValue(),
        Integer.toString(
            productionCollection
                .get(
                    chooseProductListView
                        .getSelectionModel()
                        .getSelectedItem()
                        .indexOf(chooseProductListView.getSelectionModel().getSelectedItem()))
                .getId()),
        "0");
  }

  /** Method that is called when the program launches which initializes some default values. */
  public void initialize() {
    // <editor-fold desc="LOADING FROM DATABASE">
    // <editor-fold desc="Load all existing products">
    ResultSet allProducts = DatabaseManager.loadProducts();
    if (allProducts != null) {
      try {
        while (allProducts.next()) {
          Product temp;
          switch (allProducts.getString("TYPE")) {
            case "Audio":
              temp =
                  new AudioPlayer(
                      allProducts.getInt("ID"),
                      allProducts.getString("NAME"),
                      allProducts.getString("MANUFACTURER"),
                      allProducts.getString("TYPE"),
                      "unknown atm");
              addToExistingProducts(temp);
              productionCollection.add(temp);
              break;
            case "Visual":
              temp =
                  new MoviePlayer(
                      allProducts.getInt("ID"),
                      allProducts.getString("NAME"),
                      allProducts.getString("MANUFACTURER"),
                      null,
                      null);
              addToExistingProducts(temp);
              productionCollection.add(temp);
              break;
            case "AudioMobile":
              System.out.println("audiomobile");
              break;
            case "VisualMobile":
              System.out.println("visualmobile");
              break;
            default:
              break;
          }
        }
      } catch (Exception ex) {
        System.out.println(ex.toString());
      }
    }
    // </editor-fold>
    // <editor-fold desc="Load Production Record">
    ResultSet productionRecord = DatabaseManager.loadProductionRecord();
    if (productionRecord != null) {
      try {
        while (productionRecord.next()) {
          if (productionLogTextArea.getText().isEmpty()) {
            productionLogTextArea.setText(
                "Product ID: "
                    + productionRecord.getString("PRODUCT_ID")
                    + " | Serial Number: "
                    + productionRecord.getString("SERIAL_NUM")
                    + " | Quantity Produced: "
                    + productionRecord.getString("PRODUCTION_NUM")
                    + " | Date Produced: "
                    + productionRecord.getString("DATE_PRODUCED"));
            continue;
          }
          productionLogTextArea.appendText(
              "\nProduct ID: "
                  + productionRecord.getString("PRODUCT_ID")
                  + " | Serial Number: "
                  + productionRecord.getString("SERIAL_NUM")
                  + " | Quantity Produced: "
                  + productionRecord.getString("PRODUCTION_NUM")
                  + " | Date Produced: "
                  + productionRecord.getString("DATE_PRODUCED"));
        }
      } catch (Exception ex) {
        System.out.println(ex.toString());
      }
    }
    // </editor-fold>
    // </editor-fold>
    // <editor-fold desc="PRODUCT LINE TAB">
    // set up item type choice box
    for (ItemType type : ItemType.values()) {
      itemTypeChoiceBox.getItems().add(type.toString());
    }
    itemTypeChoiceBox.getSelectionModel().selectFirst();

    // set up existing products
    existingProdId.setCellValueFactory(new PropertyValueFactory("id"));
    existingProdName.setCellValueFactory(new PropertyValueFactory("name"));
    existingProdManu.setCellValueFactory(new PropertyValueFactory("manufacturer"));
    existingProdType.setCellValueFactory(new PropertyValueFactory("type"));
    // </editor-fold>
    // <editor-fold desc="PRODUCE TAB">
    // set up combo box items
    chooseQuantityComboBox.setItems(
        FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10"));
    chooseQuantityComboBox.setEditable(true);
    chooseQuantityComboBox.getSelectionModel().selectFirst();
    // </editor-fold>
    // set up alert box
    toolTipBox = new Alert(Alert.AlertType.WARNING);
  }

  public void addToExistingProducts(Product product) {
    existingProductsTableView.getItems().add(product);
    productionCollection.add(product);
    chooseProductListView.getItems().add(product.getManufacturer() + " " + product.getName());
  }
}
