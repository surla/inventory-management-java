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

    //TableView Associated Part
    @FXML private TableView<Part> associatedPartTableView;
    @FXML private TableColumn<Part, Integer> associatedPartIdColumn;
    @FXML private TableColumn<Part, String> associatedPartNameColumn;
    @FXML private TableColumn<Part, Integer> associatedPartInvColumn;
    @FXML private TableColumn<Part, Integer> associatedPartPriceColumn;

    public void initData(Product product) throws IOException {
        selectedProduct = product;

        associatedPartTableView.setItems(selectedProduct.getAllAssociatedParts());
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
    private void saveModifyProductButtonAction(ActionEvent event) throws IOException {

        String name = nameTextField.getText();
        int stock = Integer.parseInt(inventoryTextField.getText());
        double price = Double.parseDouble(priceTextField.getText());
        int max = Integer.parseInt(maxTextField.getText());
        int min = Integer.parseInt(minTextField.getText());

        selectedProduct.setName(name);
        selectedProduct.setStock(stock);
        selectedProduct.setPrice(price);
        selectedProduct.setMax(max);
        selectedProduct.setMin(min);

        Parent parent = FXMLLoader.load(getClass().getResource("/View_Controller/InventoryMain.fxml"));
        Scene mainScene = new Scene(parent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(mainScene);
        window.show();
    }

    @FXML
    private void onClickAddPartButton(ActionEvent event) {
        selectedProduct.addAssociatedPart(partTableView.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void removeAssociatedPart(ActionEvent event) {
        Part part = associatedPartTableView.getSelectionModel().getSelectedItem();
        selectedProduct.deleteAssociatedPart(part);
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
        //Set default text for partIdTextField
        productIdTextField.setDisable(true);
        productIdTextField.setText("Auto-generated");

        //TableView Parts
        partTableView.setItems(Inventory.getAllParts());
        partIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        //TableView Associated Parts
        associatedPartIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartInvColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}