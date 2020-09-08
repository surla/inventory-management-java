package View_Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Model.InHouse;
import Model.Part;
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
import javafx.stage.Stage;
import javafx.scene.control.TableView;

public class InventoryMainController<closeButton> {

    @FXML
    private TableColumn<?, ?> partTable;

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

    /**
     * This method will return an ObservableList of People objects
     */

    public ObservableList<Part> getParts() {
        ObservableList<Part> parts = FXCollections.observableArrayList();
        parts.add(new InHouse(1, "Part 1", 3.99,5, 30,50, 1));
        parts.add(new InHouse(2, "Part 2", 5.99,10, 40,60, 2));
        parts.add(new InHouse(2, "Part 3", 7.99,3, 10,60, 3));

        return parts;
    }

    @FXML
    void initialize() {

    }
}
