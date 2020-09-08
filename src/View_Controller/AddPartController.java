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
    //Radio Button
    @FXML private RadioButton InHouseRadioButton;
    @FXML private RadioButton OutsourcedRadioButton;
    private ToggleGroup sourceToggleGroup = new ToggleGroup();

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

    @FXML
    private void onClickCancelButton(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/View_Controller/InventoryMain.fxml"));
        Scene mainScene = new Scene(parent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(mainScene);
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

    /**
     * Method to save new part to Inventory
     */

    public void savePartButtonAction(ActionEvent event) throws IOException {
        Random random = new Random();

        int id = random.nextInt(200);
        String name = nameTextField.getText();
        int stock = Integer.parseInt(inventoryTextField.getText());
        double price = Double.parseDouble(priceTextField.getText());
        int max = Integer.parseInt(maxTextField.getText());
        int min = Integer.parseInt(minTextField.getText());
        String source = sourceTextField.getText();

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
    }

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
