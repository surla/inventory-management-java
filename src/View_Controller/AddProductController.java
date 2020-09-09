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
import java.util.Random;

public class AddProductController {
    private ObservableList<Part> addParts = FXCollections.observableArrayList();
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
     * This method redirects users to InventoryMain.fxml after clicking button
     */
    @FXML
    private void onClickCancelButton(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/View_Controller/InventoryMain.fxml"));
        Scene mainScene = new Scene(parent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(mainScene);
        window.show();
    }

    /**
     * This method allows searching of data in parts table. Table will auto-populate as users type.
     * When search text field is empty shows all parts available.
     * Displays message "Part not found" if part does not exist.
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
     * Method adds part to product. Part is added to a temporary ObservableList until Product object
     * instantiated. After click, added part will show in associatedPart table.
     */
    @FXML
    private void onClickAddPartButton(ActionEvent event) {
        addParts.add(partTableView.getSelectionModel().getSelectedItem());
        System.out.println(addParts);
    }

    /**
     * Method instantiates Product object.
     * Parts from addParts ObservableList is added as associatedParts to object.
     * Creates error of inappropriate data is added.
     */
    public void saveProductButtonAction(ActionEvent event) throws IOException {
        try {
            Random random = new Random();

            int id = random.nextInt(200);
            String name = nameTextField.getText();
            int stock = Integer.parseInt(inventoryTextField.getText());
            double price = Double.parseDouble(priceTextField.getText());
            int max = Integer.parseInt(maxTextField.getText());
            int min = Integer.parseInt(minTextField.getText());

            if (min > max) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Add Product");
                alert.setHeaderText("Error. Change min and/or max.");
                alert.setContentText("Make sure Min is less than Max");

                alert.showAndWait();
                return;
            }

            if (stock < min || stock > max) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Add Product");
                alert.setHeaderText("Error. Change Inv.");
                alert.setContentText("Make sure Inv is in-between Min and Max.");

                alert.showAndWait();
                return;
            }

            Product newProduct = new Product(id, name, price, stock, min, max);

            for (Part part : addParts) {
                newProduct.addAssociatedPart(part);
            }

            Inventory.addProduct(newProduct);

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
     * Removes associated part from product. Confirmation alert shows to confirm deletion.
     */
    @FXML
    private void removeAssociatedPart(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Associated Part");
        alert.setHeaderText("Remove associated part");
        alert.setContentText("Do you want to remove this part?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            Part part = partTableView.getSelectionModel().getSelectedItem();
            addParts.remove(part);
        }
    }


    /**
     * Initailize method sets call values for both parts and associated parts tableview.
     * Included listener for parts search text field.
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
        associatedPartTableView.setItems(addParts);
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
