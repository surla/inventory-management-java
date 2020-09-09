package View_Controller;

import java.io.IOException;
import java.util.Optional;

import Model.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;


public class InventoryMainController {

    private String search;

    @FXML private Button exitButton;
    @FXML private TextField partSearchTextField;
    @FXML private Label partSearchLabel;
    @FXML private TextField productSearchTextField;
    @FXML private Label productSearchLabel;


    //TableView Part
    @FXML private TableView<Part> partTableView;
    @FXML private TableColumn<Part, Integer> partIdColumn;
    @FXML private TableColumn<Part, String> partNameColumn;
    @FXML private TableColumn<Part, String> partInvColumn;
    @FXML private TableColumn<Part, Double> partPriceColumn;

    //TableView Products
    @FXML private TableView<Product> productTableView;
    @FXML private TableColumn<Product, Integer> productIdColumn;
    @FXML private TableColumn<Product, String> productNameColumn;
    @FXML private TableColumn<Product, String> productInvColumn;
    @FXML private TableColumn<Product, Double> productPriceColumn;



    /**
     * Method will exit program.
     */
    @FXML
    public void handleCloseButtonAction(ActionEvent event) {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    /**
     * This method when called will change the Scene to Add Part form
     *
     */

    @FXML
    private void addPartSceneOnClick(ActionEvent event) throws IOException {
        Parent addPartParent = FXMLLoader.load(getClass().getResource("/View_Controller/AddPart.fxml"));
        Scene addPartScene = new Scene(addPartParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(addPartScene);
        window.show();
    }

    /**
     * Method populates modify parts form with part object. Allows users to change is information.
     * Alerts users if no part is selected before clicking button.
     */
    @FXML
    private void modifyPartSceneOnClick(ActionEvent event) throws IOException {
        if (partTableView.getSelectionModel().getSelectedItem() != null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/View_Controller/ModifyPart.fxml"));
            Parent modifyPartParent = loader.load();
            Scene modifyPartScene = new Scene(modifyPartParent);

            //Accesses the controller and passed in the selected part
            ModifyPartController controller = loader.getController();
            controller.initData(partTableView.getSelectionModel().getSelectedItem());

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(modifyPartScene);
            window.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Modify Part");
            alert.setHeaderText("No part selected.");
            alert.setContentText("Please select part to modify.");

            alert.showAndWait();
        }
    }

    /**
     * Method deletes part object. Confirmation alerts users before deletion.
     */
    @FXML
    private void deletePartButtonPushed() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Part");
        alert.setHeaderText("Delete Part");
        alert.setContentText("Do you want to delete part?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            Part selectedPart = partTableView.getSelectionModel().getSelectedItem();
            Inventory.deletePart(selectedPart);
        }
    }


    /**
     * Below are methods for Product table
     */

    /**
     * Method to add new product object. Redirects to add product form.
     */

    @FXML
    private void onClickAddProductButton(ActionEvent event) throws IOException {
        Parent addProductParent = FXMLLoader.load(getClass().getResource("/View_Controller/AddProduct.fxml"));
        Scene addProductScene = new Scene(addProductParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(addProductScene);
        window.show();
    }

    /**
     * This method redirects users to modify product form.
     * If no product is select, alert message pops up.
     */
    @FXML
    private void onClickModifyProductButton(ActionEvent event) throws IOException {
        if (productTableView.getSelectionModel().getSelectedItem() != null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/View_Controller/ModifyProduct.fxml"));
            Parent modifyProductParent = loader.load();
            Scene modifyProductScene = new Scene(modifyProductParent);

            //Accesses the controller and passed in the selected part
            ModifyProductController controller = loader.getController();
            controller.initData(productTableView.getSelectionModel().getSelectedItem());

            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(modifyProductScene);
            window.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Modify Product");
            alert.setHeaderText("No product selected.");
            alert.setContentText("Please select product to modify.");

            alert.showAndWait();
        }

    }

    /**
     * Method deletes selected product. Alerts user if no product is selected.
     * User cannot delete product if associated parts exists. Users will be alerted to delete
     * associated parts first.
     */
    @FXML
    private void onClickDeleteProductButton(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Product");
        alert.setHeaderText("Delete Product");
        alert.setContentText("Do you want to delete product?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            Product selectedProduct = productTableView.getSelectionModel().getSelectedItem();
            if (selectedProduct.getAllAssociatedParts().isEmpty()) {
                Inventory.deleteProduct(selectedProduct);
            } else {
                Alert alert2 = new Alert(Alert.AlertType.WARNING);
                alert2.setTitle("Product");
                alert2.setHeaderText("Cannot Delete Product ");
                alert2.setContentText("Please remove associated part before deleting.");

                alert2.showAndWait();
            }

        }
    }

    /**
     * Method to search parts table. Message 'Part not found' if users enters an invalid part.
     * Table will automatically populate if part is found.
     * Shows all products when parts search text field is empty.
     */
    @FXML
    private void partSearchData() {
        ObservableList<Part> partSearchResults = FXCollections.observableArrayList();

        for (Part part: Inventory.getAllParts()) {
            if (part.getName().toLowerCase().equals(search.toLowerCase())) {
                partSearchResults.add(part);
            }

            if (String.valueOf(part.getId()).equals(search)) {
                partSearchResults.add(part);
            }
        }

        if (partSearchResults.isEmpty()) {
            partSearchLabel.setText("Part not found!");
        } else {
            partSearchLabel.setText(" ");
        }

        if (search.equals("")) {
            partTableView.setItems(Inventory.getAllParts());
            partSearchLabel.setText(" ");
        } else {
            partTableView.setItems(partSearchResults);
        }
    }

    /**
     * Method to search product table. Message 'Produc not found' if users enters an invalid product name.
     * Table will automatically populate if part is found.
     * Shows all products when product search text field is empty.
     */

    @FXML
    private void productSearchData() {
        ObservableList<Product> productSearchResults = FXCollections.observableArrayList();

        for (Product product: Inventory.getAllProducts()) {
            if (product.getName().toLowerCase().equals(search.toLowerCase())) {
                productSearchResults.add(product);
            }

            if (String.valueOf(product.getId()).equals(search)) {
                productSearchResults.add(product);
            }
        }

        if (productSearchResults.isEmpty()) {
            productSearchLabel.setText("Part not found!");
        } else {
            partSearchLabel.setText(" ");
        }

        if (search.equals("")) {
            productTableView.setItems(Inventory.getAllProducts());
            productSearchLabel.setText(" ");
        } else {
            productTableView.setItems(productSearchResults);
        }
    }

    /**
     * initialize method will set cell values for both parts and product tableview.
     * Listeners added to parts and product search text field to allow users to search
     * for an available part.
     */
    @FXML
    public void initialize() {

        //TableView Parts
        partTableView.setItems(Inventory.getAllParts());
        partIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        //TableView Products
        productTableView.setItems(Inventory.getAllProducts());
        productIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInvColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        partSearchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            search = newValue;
        });
        productSearchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            search = newValue;
        });

        partSearchLabel.setText("");
        productSearchLabel.setText("");
    }
}
