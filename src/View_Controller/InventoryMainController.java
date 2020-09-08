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
    private Button addPartButton;

    @FXML
    private Button modifyPartButton;

    @FXML
    private Button deletePartButton;

    @FXML
    private Button searchPartButton;

    @FXML
    private Button exitButton;

    @FXML
    private TextField searchPartField;

    //TableView Part
    @FXML private TableView<Part> partTableView;
    @FXML private TableColumn<Part, Integer> partIdColumn;
    @FXML private TableColumn<Part, String> partNameColumn;
    @FXML private TableColumn<Part, String> partInvColumn;
    @FXML private TableColumn<Part, Double> partPriceColumn;




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
        Parent addPartParent = FXMLLoader.load(getClass().getResource("/View_Controller/ModifyPart.fxml"));
        Scene addPartScene = new Scene(addPartParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(addPartScene);
        window.show();
    }

    @FXML
    void initialize() {
        /**
         * Adds test data to populate tables
         */

        Inventory inv = new Inventory();

        //Add InHouse parts
        Part item1 = new InHouse(1, "Part 1", 5.99, 6, 2, 50, 60);
        Part item2 = new InHouse(2, "Part 2", 4.00, 15, 8, 30, 40);
        Part item3 = new InHouse(3, "Part 3", 9.99, 20, 5, 60, 80);
        inv.addPart(item1);
        inv.addPart(item2);
        inv.addPart(item3);
        inv.addPart(new InHouse(4, "Part 4", 2.99, 10, 2,20, 10));
        inv.addPart(new InHouse(5, "Part 5", 6.19, 13, 4,35, 25));

        //Add OutSourced Parts
        Part p1 = new Outsourced(6, "Part A", 3.99, 8, 1, 40, "Yokohama Co.");
        Part p2 = new Outsourced(7, "Part B", 14.99, 14,5, 80, "Kanagawa Co.");
        Part p3 = new Outsourced(8, "Part C", 18.99, 7, 6, 35, "Ikebukuro Co.");
        inv.addPart(p1);
        inv.addPart(p2);
        inv.addPart(p3);
        inv.addPart(new Outsourced(9, "Part D", 29.99, 15, 10, 100, "Kinugasa Co."));
        inv.addPart(new Outsourced(10, "Part E", 25.99, 9, 5, 85, "Yokosuka Co."));


        partTableView.setItems(inv.getAllParts());

        partIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}
