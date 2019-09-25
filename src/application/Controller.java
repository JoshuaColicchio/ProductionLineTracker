package application;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

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

  @FXML private TableColumn existingProdName;

  @FXML private TableColumn existingProdManu;

  @FXML private TableColumn existingProdType;

  @FXML private ListView<?> chooseProductListView;

  @FXML private ComboBox<String> chooseQuantityComboBox;

  @FXML private Button recordProductBtn;

  @FXML private TextArea productionLogTextArea;

  private Alert toolTipBox;

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
      /*DatabaseManager.addProduct(
          productNameTextField.getText(),
          manufacturerTextField.getText(),
          itemTypeChoiceBox.getValue()); */
      addToExistingProducts(new Widget(productNameTextField.getText(),
              manufacturerTextField.getText(),
              itemTypeChoiceBox.getValue()));
    }
  }

  /** Method that is called when the "Record Product" button is pressed. */
  @FXML
  public void recordProductBtnPushed() {
    System.out.println("recordProduct");
  }

  /** Method that is called when the program launches which initializes some default values. */
  public void initialize() {
    // PRODUCT LINE TAB
    // set up item type choice box
    for (ItemType type : ItemType.values()) {
      itemTypeChoiceBox.getItems().add(type.toString());
    }
    itemTypeChoiceBox.getSelectionModel().selectFirst();

    // set up existing products
    existingProdName.setCellValueFactory(new PropertyValueFactory("name"));
    existingProdManu.setCellValueFactory(new PropertyValueFactory("manufacturer"));
    existingProdType.setCellValueFactory(new PropertyValueFactory("type"));

    // PRODUCE TAB
    // set up combo box items
    chooseQuantityComboBox.setItems(
        FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10"));
    chooseQuantityComboBox.setEditable(true);
    chooseQuantityComboBox.getSelectionModel().selectFirst();

    // UNIVERSAL
    // set up alert box
    toolTipBox = new Alert(Alert.AlertType.WARNING);
  }

  public void addToExistingProducts(Product product) {
      existingProductsTableView.getItems().add(product);
  }
}
