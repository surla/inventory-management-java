package View_Controller;

import Model.*;
import javafx.beans.binding.ObjectExpression;
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


    @FXML
    private void onClickCancelButton(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/View_Controller/InventoryMain.fxml"));
        Scene mainScene = new Scene(parent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(mainScene);
        window.show();
    }

    @FXML
    private void partSearchData() {
        ObservableList<Part> partSearchResults = FXCollections.observableArrayList();
        System.out.println(search);

        for (Part part: Inventory.getAllParts()) {
            if (part.getName().toLowerCase().equals(search.toLowerCase())) {
                partSearchResults.add(part);
                System.out.println("YAY!");
            }

            if (String.valueOf(part.getId()).equals(search)) {
                partSearchResults.add(part);
                System.out.println("Duece!");
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

    @FXML
    private void onClickAddPartButton(ActionEvent event) {
        addParts.add(partTableView.getSelectionModel().getSelectedItem());
        System.out.println(addParts);
    }

    public void saveProductButtonAction(ActionEvent event) throws IOException {
        Random random = new Random();

        int id = random.nextInt(200);
        String name = nameTextField.getText();
        int stock = Integer.parseInt(inventoryTextField.getText());
        double price = Double.parseDouble(priceTextField.getText());
        int max = Integer.parseInt(maxTextField.getText());
        int min = Integer.parseInt(minTextField.getText());


        Product newProduct = new Product(id, name, price, stock, min, max);

        for (Part part: addParts) {
            newProduct.addAssociatedPart(part);
        }

        Inventory.addProduct(newProduct);

        Parent parent = FXMLLoader.load(getClass().getResource("/View_Controller/InventoryMain.fxml"));
        Scene mainScene = new Scene(parent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(mainScene);
        window.show();
    }

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
