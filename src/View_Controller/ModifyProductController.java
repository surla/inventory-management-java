package View_Controller;

import Model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

public class ModifyProductController {
    private Product selectedProduct;

    @FXML private TextField productIdTextField;
    @FXML private TextField nameTextField;
    @FXML private TextField inventoryTextField;
    @FXML private TextField priceTextField;
    @FXML private TextField maxTextField;
    @FXML private TextField minTextField;

    //TableView Part
    @FXML private TableView<Part> partTableView;
    @FXML private TableColumn<Part, Integer> partIdColumn;
    @FXML private TableColumn<Part, String> partNameColumn;
    @FXML private TableColumn<Part, String> partInvColumn;
    @FXML private TableColumn<Part, Double> partPriceColumn;

    public void initData(Product product) throws IOException {
        selectedProduct = product;
        String id = String.valueOf(selectedProduct.getId());
        String name = selectedProduct.getName();
        String stock = String.valueOf(selectedProduct.getStock());
        String price = String.valueOf(selectedProduct.getPrice());
        String max = String.valueOf(selectedProduct.getMax());
        String min = String.valueOf(selectedProduct.getMin());

        productIdTextField.setText(id);
        nameTextField.setText(name);
        inventoryTextField.setText(stock);
        priceTextField.setText(price);
        maxTextField.setText(max);
        minTextField.setText(min);

    }

    @FXML
    private void onClickCancelButton(ActionEvent event) throws IOException {
        Parent addPartParent = FXMLLoader.load(getClass().getResource("/View_Controller/InventoryMain.fxml"));
        Scene addPartScene = new Scene(addPartParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(addPartScene);
        window.show();
    }

    public void initialize() {

    }
}