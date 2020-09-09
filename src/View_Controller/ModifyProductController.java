package View_Controller;

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
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class ModifyProductController {
    private Product selectedProduct;
    private String search;

    @FXML private Label partSearchLabel;
    @FXML private TextField partSearchTextField;

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

    /**
     * This method accepts an argument of the selected product from InventoryMain.fxml.
     * Populated form with product object data.
     */
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

    /**
     * This method searches parts in partsTableView. When text field is empty, shows all parts.
     * If part not found, message will show 'Part not found'.
     */

    @FXML
    private void partSearchData() {
        ObservableList<Part> partSearchResults = FXCollections.observableArrayList();
        System.out.println(search);

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
     * Method saves modify product object. After save redirects users to main scene.
     * Error alerts user if inappropriate data is entered.
     */

    @FXML
    private void saveModifyProductButtonAction(ActionEvent event) throws IOException {
        try {
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
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Add Product");
            alert.setHeaderText("Error. Inappropriate data entered.");
            alert.setContentText("Please try again.");

            alert.showAndWait();
        }
    }

    /**
     * Method add parts from parts table to associated part.
     *
     */
    @FXML
    private void onClickAddPartButton(ActionEvent event) {
        selectedProduct.addAssociatedPart(partTableView.getSelectionModel().getSelectedItem());
    }

    /**
     * Method removed associated part from product. Alert confirms the users want to delete.
     */
    @FXML
    private void removeAssociatedPart(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Associated Part");
        alert.setHeaderText("Remove associated part");
        alert.setContentText("Do you want to remove this part?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            Part part = associatedPartTableView.getSelectionModel().getSelectedItem();
            selectedProduct.deleteAssociatedPart(part);
        }


    }

    /**
     * Redirects to main scene.
     */
    @FXML
    private void onClickCancelButton(ActionEvent event) throws IOException {
        Parent addPartParent = FXMLLoader.load(getClass().getResource("/View_Controller/InventoryMain.fxml"));
        Scene addPartScene = new Scene(addPartParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(addPartScene);
        window.show();
    }

    /**
     * initialize method set cell values for part and associated part tableviews.
     * Disabled productId textfield.
     */
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

        partSearchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            search = newValue;
        });

        partSearchLabel.setText("");
    }
}