package main;

import database.DatabaseManager;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

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

  @FXML private TableView<?> existingProductsTableView;

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
      // @TODO add new product to database
      DatabaseManager.addProduct(
          productNameTextField.getText(),
          manufacturerTextField.getText(),
          itemTypeChoiceBox.getValue());
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
    String[] itemTypes = {"Audio", "Visual", "AudioMobile", "VisualMobile"};
    itemTypeChoiceBox.setItems(FXCollections.observableArrayList(itemTypes));
    itemTypeChoiceBox.getSelectionModel().selectFirst();

    // PRODUCE TAB
    chooseQuantityComboBox.setItems(
        FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10"));
    chooseQuantityComboBox.setEditable(true);
    chooseQuantityComboBox.getSelectionModel().selectFirst();

    // UNIVERSAL
    // set up alert box
    toolTipBox = new Alert(Alert.AlertType.WARNING);
  }
}
