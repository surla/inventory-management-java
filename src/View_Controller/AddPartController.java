package View_Controller;

import Model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Random;

public class AddPartController {

    private ToggleGroup sourceToggleGroup = new ToggleGroup();

    //Radio Button
    @FXML private RadioButton InHouseRadioButton;
    @FXML private RadioButton OutsourcedRadioButton;


    @FXML private TextField partIdTextField;
    @FXML private TextField nameTextField;
    @FXML private TextField inventoryTextField;
    @FXML private TextField priceTextField;
    @FXML private TextField maxTextField;
    @FXML private TextField minTextField;
    @FXML private TextField sourceTextField;

    @FXML private Label sourceLabel;

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;


    /**
     * This method will redirect to InventoryMain.fxml.
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
     * Method to update sourceLabel when a RadioButton is selected.
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

    /**
     * Method to save new part. RadioButton determines if an InHouse object or Outsourced object is created.
     * Generates errors if inappropriate data is added.
     * Checks that min is less than max and Inv is in-between min and max.
     *
     */

    public void savePartButtonAction(ActionEvent event) throws IOException {
        try {
            Random random = new Random();

            int id = random.nextInt(200);
            String name = nameTextField.getText();
            int stock = Integer.parseInt(inventoryTextField.getText());
            double price = Double.parseDouble(priceTextField.getText());
            int max = Integer.parseInt(maxTextField.getText());
            int min = Integer.parseInt(minTextField.getText());
            String source = sourceTextField.getText();

            if (min > max) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Add Part");
                alert.setHeaderText("Error. Change min and/or max.");
                alert.setContentText("Make sure Min is less than Max");

                alert.showAndWait();
                return;
            }

            if (stock < min || stock > max) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Add Part");
                alert.setHeaderText("Error. Change Inv.");
                alert.setContentText("Make sure Inv is in-between Min and Max.");

                alert.showAndWait();
                return;
            }

            if (InHouseRadioButton.isSelected()) {
                int machineId = Integer.parseInt(source);

                Inventory.addPart(new InHouse(id,
                        name,
                        price,
                        stock,
                        min,
                        max,
                        machineId));

            }

            if (OutsourcedRadioButton.isSelected()) {
                String companyName = source;

                Inventory.addPart(new Outsourced(id,
                        name,
                        price,
                        stock,
                        min,
                        max,
                        companyName));
            }


            Parent parent = FXMLLoader.load(getClass().getResource("/View_Controller/InventoryMain.fxml"));
            Scene mainScene = new Scene(parent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(mainScene);
            window.show();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Add Part");
            alert.setHeaderText("Error. Inappropriate data entered.");
            alert.setContentText("Please try again.");

            alert.showAndWait();
        }
    }

    /**
     * Radio buttons are set in a group and only one is selected.
     * Sets partIdTextField to disabled and prevents users from creating Id.
     * partId is auto-generated.
     */
    public void initialize() {
        //Set default text for partIdTextField
        partIdTextField.setDisable(true);
        partIdTextField.setText("Auto-generated");
        //Configuring RadioButtons
        InHouseRadioButton.setSelected(true);
        sourceLabel.setText("Machine Id");
        this.InHouseRadioButton.setToggleGroup(sourceToggleGroup);
        this.OutsourcedRadioButton.setToggleGroup(sourceToggleGroup);
    }
}
