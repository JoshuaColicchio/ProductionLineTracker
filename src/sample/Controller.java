package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class Controller {

    @FXML
    private TextField productNameTextField;

    @FXML
    private TextField manufacturerTextField;

    @FXML
    private ChoiceBox<?> itemTypeChoiceBox;

    @FXML
    private Button addProductButton;

    @FXML
    private TableView<?> existingProductsTableView;

    @FXML
    private ListView<?> chooseProductListView;

    @FXML
    private ComboBox<?> chooseQuantityComboBox;

    @FXML
    private Button recordProductBtn;

    @FXML
    private TextArea productionLogTextArea;

    @FXML
    void addProductBtnPushed(ActionEvent event) {
        System.out.println("addProduct");
    }

    @FXML
    void recordProductButtonPushed(ActionEvent event) {
        System.out.println("recordProduct");
    }

}
