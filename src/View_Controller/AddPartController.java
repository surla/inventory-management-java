package View_Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class AddPartController {
    //Radio Button
    @FXML private RadioButton InHouseRadioButton;
    @FXML private RadioButton OutsourcedRadioButton;
    private ToggleGroup sourceToggleGroup = new ToggleGroup();

    @FXML private TextField idTextField;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField InventoryTextField;

    @FXML
    private TextField PriceCostTextField;

    @FXML
    private TextField maxTextField;

    @FXML
    private Label sourceLabel;

    @FXML
    private TextField sourceTextField;

    @FXML
    private TextField minTextField;

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;

    @FXML
    private void onClickCancelButton(ActionEvent event) throws IOException {
        Parent addPartParent = FXMLLoader.load(getClass().getResource("/View_Controller/InventoryMain.fxml"));
        Scene addPartScene = new Scene(addPartParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(addPartScene);
        window.show();
    }


    /**
     * Method to update sourceLabel when a RadioButton is selected
     *
     */
    public void setSourceLabel(ActionEvent event) {
        if (this.sourceToggleGroup.getSelectedToggle().equals(this.InHouseRadioButton)) {
            sourceLabel.setText("Machine ID");
        }
        if (this.sourceToggleGroup.getSelectedToggle().equals(this.OutsourcedRadioButton)) {
            sourceLabel.setText("Company Name");
        }
    }

    @Override
    public void initialize() {
        //Configuring RadioButtons
        InHouseRadioButton.setSelected(true);
        sourceLabel.setText("Machine Id");
        this.InHouseRadioButton.setToggleGroup(sourceToggleGroup);
        this.OutsourcedRadioButton.setToggleGroup(sourceToggleGroup);

    }
}
