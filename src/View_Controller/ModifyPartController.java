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

public class ModifyPartController {
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

    private Part selectedPart;

    /**
     * This method accepts an argument of the selected part from InventoryMain.fxml.
     * Populated form with object data.
     */
    public void initData (Part part) throws IOException {
        selectedPart = part;

        String id = String.valueOf(selectedPart.getId());
        String name = selectedPart.getName();
        String stock = String.valueOf(selectedPart.getStock());
        String price = String.valueOf(selectedPart.getPrice());
        String max = String.valueOf(selectedPart.getMax());
        String min = String.valueOf(selectedPart.getMin());

        if (selectedPart instanceof InHouse) {
            InHouseRadioButton.setSelected(true);
            sourceLabel.setText("Machine ID");

            partIdTextField.setText(id);
            nameTextField.setText(name);
            inventoryTextField.setText(stock);
            priceTextField.setText(price);
            maxTextField.setText(max);
            minTextField.setText(min);
            sourceTextField.setText(String.valueOf(((InHouse) selectedPart).getMachineId()));
        }

        if (selectedPart instanceof Outsourced) {
            OutsourcedRadioButton.setSelected(true);
            sourceLabel.setText("Company Name");

            partIdTextField.setText(id);
            nameTextField.setText(name);
            inventoryTextField.setText(stock);
            priceTextField.setText(price);
            maxTextField.setText(max);
            minTextField.setText(min);
            sourceTextField.setText(String.valueOf(((Outsourced) selectedPart).getCompanyName()));
        }

    }

    /**
     * Saves modified part object.
     * It will update part object or create a new part objects depending if user changes from
     *  of part (InHouse or Outsourced).
     * If inappropriate data is added, error alert will show.
     */
    @FXML
    public void saveModifyPartButtonAction(ActionEvent event) throws IOException {
        try {
            boolean InHouse = this.sourceToggleGroup.getSelectedToggle().equals(this.InHouseRadioButton);
            boolean OutSourced = this.sourceToggleGroup.getSelectedToggle().equals(this.OutsourcedRadioButton);

            int id = Integer.parseInt(partIdTextField.getText());
            String name = nameTextField.getText();
            int stock = Integer.parseInt(inventoryTextField.getText());
            double price = Double.parseDouble(priceTextField.getText());
            int max = Integer.parseInt(maxTextField.getText());
            int min = Integer.parseInt(minTextField.getText());
            String source = sourceTextField.getText();

            if (min > max) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Modify Part");
                alert.setHeaderText("Error. Change min and/or max.");
                alert.setContentText("Make sure Min is less than Max");

                alert.showAndWait();
                return;
            }

            if (stock < min || stock > max) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Modify Part");
                alert.setHeaderText("Error. Change Inv.");
                alert.setContentText("Make sure Inv is in-between Min and Max.");

                alert.showAndWait();
                return;
            }

            if (selectedPart instanceof InHouse && InHouse) {
                selectedPart.setName(name);
                selectedPart.setPrice(price);
                selectedPart.setMax(max);
                selectedPart.setMin(min);
                ((InHouse) selectedPart).setMachineId(Integer.parseInt(source));
            }

            if (selectedPart instanceof InHouse && OutSourced) {
                String companyName = source;

                Inventory.addPart(new Outsourced(id,
                        name,
                        price,
                        stock,
                        min,
                        max,
                        companyName));

                //Removes part
                Inventory.deletePart(selectedPart);
            }

            if (selectedPart instanceof Outsourced && OutSourced) {
                selectedPart.setName(name);
                selectedPart.setPrice(price);
                selectedPart.setMax(max);
                selectedPart.setMin(min);
                ((Outsourced) selectedPart).setCompanyName(source);
            }

            if (selectedPart instanceof Outsourced && InHouse) {
                int machineId = Integer.parseInt(source);

                Inventory.addPart(new InHouse(id,
                        name,
                        price,
                        stock,
                        min,
                        max,
                        machineId));

                Inventory.deletePart(selectedPart);
            }


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
     * Redirects users to InventoryMain.fxml
     */

    @FXML
    private void cancelButtonOnClick(ActionEvent event) throws IOException {
        Parent addPartParent = FXMLLoader.load(getClass().getResource("/View_Controller/InventoryMain.fxml"));
        Scene addPartScene = new Scene(addPartParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
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

    public void initialize() {
        partIdTextField.setDisable(true);
        this.InHouseRadioButton.setToggleGroup(sourceToggleGroup);
        this.OutsourcedRadioButton.setToggleGroup(sourceToggleGroup);
    }
}
