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
@SuppressWarnings("WeakerAccess")
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

  @FXML
  private void addProductBtnPushed() {
    if (productNameTextField.getText().trim().isEmpty()) {
      throwAlert(Alert.AlertType.WARNING, "\"Product Name\" must not be empty!", false);
    } else if (manufacturerTextField.getText().trim().isEmpty()) {
      throwAlert(Alert.AlertType.WARNING, "\"Manufacturer\" must not be empty!", false);
    } else {
      int prodId = DatabaseManager.addProduct(productNameTextField.getText(), manufacturerTextField.getText(),
              itemTypeChoiceBox.getValue());

      addToExistingProducts(
              Product.createProduct(prodId, productNameTextField.getText(), manufacturerTextField.getText(),
              itemTypeChoiceBox.getValue()));

      // Clear out the fields after use.
      productNameTextField.setText("");
      manufacturerTextField.setText("");
      itemTypeChoiceBox.getSelectionModel().selectFirst();
    }
  }

  @FXML
  private void recordProductBtnPushed() {
    if (chooseProductListView.getSelectionModel().getSelectedItem() == null) {
      throwAlert(Alert.AlertType.WARNING, "No product selected!", false);
      return;
    }

    try {
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

  @FXML
  private void onNewEmployeeSubmit() {
    if (newEmpName.getText().trim().isEmpty()) {
      throwAlert(Alert.AlertType.WARNING, "Name cannot be empty!", false);
    } else if (newEmpPassword.getText().trim().isEmpty()) {
      throwAlert(Alert.AlertType.WARNING, "Password cannot be empty!", false);
    } else {
      newEmpResultDisplay.setText(new Employee(newEmpName.getText(), newEmpPassword.getText()).toString());
    }
  }

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

  private void addToExistingProducts(Product product) {
    existingProductsTableView.getItems().add(product);
    chooseProductListView.getItems().add(product);
  }

  public static void throwAlert(Alert.AlertType alertType, String context, boolean isFatal) {
    toolTipBox = new Alert(alertType);
    toolTipBox.setContentText(context);

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
