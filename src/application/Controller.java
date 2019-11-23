package application;

import java.util.List;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * Class that manages all GUI object interactions.
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
  private static Alert toolTipBox;

  /** Method that is called when the "Add Product" button is pressed. */
  @FXML
  public void addProductBtnPushed() {
    if (productNameTextField.getText().trim().isEmpty()) {
      throwAlert(Alert.AlertType.WARNING, "\"Product Name\" must not be empty!", false);
      return;
    }

    if (manufacturerTextField.getText().trim().isEmpty()) {
      throwAlert(Alert.AlertType.WARNING, "\"Manufacturer\" must not be empty!", false);
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
      throwAlert(Alert.AlertType.WARNING, "No product selected!", false);
      return;
    }

    try {
      // Make sure the quantity specified is a valid integer.
      int quantity = Integer.parseInt(chooseQuantityComboBox.getValue());
      if (quantity < 0) {
        throw new NumberFormatException("Negative number not allowed.");
      }

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
      throwAlert(Alert.AlertType.WARNING, "Invalid Quantity!\n" + nfe.getLocalizedMessage(), false);
      return;
    }

    // Clear selection & reset quantity to 1
    chooseProductListView.getSelectionModel().selectFirst();
    chooseQuantityComboBox.setValue("1");
  }

  /** Method that is called when the 'Submit' button is pressed on the Employee tab. */
  @FXML
  public void onNewEmployeeSubmit() {
    // Ensure name is filled in
    if (newEmpName.getText().trim().isEmpty()) {
      throwAlert(Alert.AlertType.WARNING, "Name cannot be empty!", false);
      return;
    }
    // Ensure password is filled in
    if (newEmpPassword.getText().trim().isEmpty()) {
      throwAlert(Alert.AlertType.WARNING, "Password cannot be empty!", false);
      return;
    }

    Employee emp = new Employee(newEmpName.getText(), newEmpPassword.getText());
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

  /**
   * Method that handles all errors/warnings that may occur during runtime.
   *
   * @param alertType Type of the alert.
   * @param context The message to display on the alert.
   * @param isFatal If true, the application will close when the alert box is closed.
   */
  public static void throwAlert(Alert.AlertType alertType, String context, boolean isFatal) {
    toolTipBox = new Alert(alertType);
    toolTipBox.setContentText(context);

    // Force alert to the front
    ((Stage) toolTipBox.getDialogPane().getScene().getWindow()).setAlwaysOnTop(true);

    if (isFatal) {
      toolTipBox.setContentText(context + "\n\n\nThe application will now close.");
      toolTipBox.setOnCloseRequest(
          e -> {
            Platform.exit();
            System.exit(0);
          });
    } else {
      // reset the Alert for the next time
      toolTipBox.setOnCloseRequest(e -> toolTipBox = null);
    }
    toolTipBox.show();
  }
}
