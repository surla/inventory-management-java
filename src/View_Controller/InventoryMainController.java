package View_Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.control.TableView;

public class InventoryMainController {
    @FXML
    private Button exitButton;

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




    public InventoryMainController() {
    }

    /**
     * Method will exit program.
     *
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

    @FXML
    private void modifyPartSceneOnClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View_Controller/ModifyPart.fxml"));
        Parent modifyPartParent = loader.load();
        Scene modifyPartScene = new Scene(modifyPartParent);

        //Accesses the controller and passed in the selected part
        ModifyPartController controller = loader.getController();
        controller.initData(partTableView.getSelectionModel().getSelectedItem());

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(modifyPartScene);
        window.show();
    }

    @FXML
    private void deletePartButtonPushed() {
        Part selectedPart = partTableView.getSelectionModel().getSelectedItem();
        Inventory.deletePart(selectedPart);
        
    }


    /**
     * Below are methods for Product table
     */

    @FXML
    private void onClickAddProductButton(ActionEvent event) throws IOException {
        Parent addProductParent = FXMLLoader.load(getClass().getResource("/View_Controller/AddProduct.fxml"));
        Scene addProductScene = new Scene(addProductParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(addProductScene);
        window.show();
    }

    @FXML
    private void onClickModifyProductButton(ActionEvent event) throws IOException {
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
    }

    @FXML
    void initialize() {

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
    }
}
