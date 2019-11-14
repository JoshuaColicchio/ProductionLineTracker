package application;

import java.util.List;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;

/**
 * Controller class used to manage GUI object interactions.
 *
 * @author Joshua Colicchio
 */
// Despite what IntelliJ says, this CANNOT be package private. The FXML page will be unable to load.
@SuppressWarnings("WeakerAccess") // So I suppress this warning.
public class Controller {

  @FXML private TextField productNameTextField;
  @FXML private TextField manufacturerTextField;
  @FXML private ChoiceBox<ItemType> itemTypeChoiceBox;
  @FXML private TableView<Product> existingProductsTableView;
  @FXML private ListView<Product> chooseProductListView;
  @FXML private ComboBox<String> chooseQuantityComboBox;
  @FXML private TextArea productionLogTextArea;
  @FXML private TextField newEmpName;
  @FXML private PasswordField newEmpPassword;
  @FXML private TextArea newEmpResultDisplay;
  @FXML private Button newEmpSubmitBtn;
  private Alert toolTipBox;

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

    // Make sure the quantity specified is a valid integer.
    try {
      ProductionRecord productionRecord =
          new ProductionRecord(
              Integer.parseInt(chooseQuantityComboBox.getValue()),
              chooseProductListView.getSelectionModel().getSelectedItem());

      DatabaseManager.saveProductionRecord(productionRecord);

      if (productionLogTextArea.getText().trim().isEmpty()) {
        productionLogTextArea.setText(productionRecord.toString());
      } else {
        productionLogTextArea.appendText("\n" + productionRecord.toString());
      }
    } catch (NumberFormatException nfe) {
      toolTipBox.setContentText("Invalid Quantity!");
      toolTipBox.show();
    }

    // Clear selection & reset quantity to 1
    chooseProductListView.getSelectionModel().selectFirst();
    chooseQuantityComboBox.setValue("1");
  }

  @FXML
  public void onNewEmployeeSubmit() {
    // Ensure name is filled in
    if (newEmpName.getText().trim().isEmpty()) {
      toolTipBox.setContentText("Name cannot be empty!");
      toolTipBox.show();
      return;
    }
    // Ensure password is filled in
    if (newEmpPassword.getText().trim().isEmpty()) {
      toolTipBox.setContentText("Password cannot be empty!");
      toolTipBox.show();
      return;
    }

    Employee emp = new Employee(newEmpName.getText(), newEmpPassword.getText());

    String name = newEmpName.getText();

    String username =
        name.toLowerCase().charAt(0) + name.substring(name.indexOf(' ') + 1).toLowerCase();

    // tim.lee@oracleacademy.Test
    String email = name.replace(' ', '.').toLowerCase() + "@oracleacademy.Test";

    newEmpResultDisplay.setText(emp.toString());
  }

  /** Method that is called when the program launches which initializes all default values. */
  public void initialize() {
    // Load all existing products from the database.
    List<Product> products = DatabaseManager.loadProducts();
    for (Product prod : products) {
      addToExistingProducts(prod);
    }

    // Load all existing product records from the database.
    List<ProductionRecord> records = DatabaseManager.loadProductionRecords();
    for (ProductionRecord record : records) {
      if (productionLogTextArea.getText().isEmpty()) {
        productionLogTextArea.setText(record.toString());
      } else {
        productionLogTextArea.appendText("\n" + record.toString());
      }
    }

    // set up item type choice box
    for (ItemType type : ItemType.values()) {
      itemTypeChoiceBox.getItems().add(type);
    }
    itemTypeChoiceBox.getSelectionModel().selectFirst();

    // set up combo box items
    chooseQuantityComboBox.getItems().addAll("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
    chooseQuantityComboBox.getSelectionModel().selectFirst();

    // set up list view cell factory
    chooseProductListView.setCellFactory(
        new Callback<ListView<Product>, ListCell<Product>>() {
          @Override
          public ListCell<Product> call(ListView<Product> param) {
            return new ListCell<Product>() {
              @Override
              protected void updateItem(Product item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) {
                  setText(item.getManufacturer() + " " + item.getName());
                } else {
                  setText("");
                }
              }
            };
          }
        });

    // set up alert box
    toolTipBox = new Alert(Alert.AlertType.WARNING);
  }

  /**
   * Method that takes a product, stores it, and adds it to the existing products TableView, and the
   * products ListView.
   *
   * @param product Product to store and add.
   */
  private void addToExistingProducts(Product product) {
    existingProductsTableView.getItems().add(product);
    chooseProductListView.getItems().add(product);
  }
}
