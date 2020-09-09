package View_Controller;

import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import Model.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Random;

public class AddProductController {
    @FXML private TextField productIdTextField;
    @FXML private TextField nameTextField;
    @FXML private TextField inventoryTextField;
    @FXML private TextField priceTextField;
    @FXML private TextField maxTextField;
    @FXML private TextField minTextField;

    @FXML
    private void onClickCancelButton(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/View_Controller/InventoryMain.fxml"));
        Scene mainScene = new Scene(parent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(mainScene);
        window.show();
    }

    public void saveProductButtonAction(ActionEvent event) throws IOException {
        Random random = new Random();

        int id = random.nextInt(200);
        String name = nameTextField.getText();
        int stock = Integer.parseInt(inventoryTextField.getText());
        double price = Double.parseDouble(priceTextField.getText());
        int max = Integer.parseInt(maxTextField.getText());
        int min = Integer.parseInt(minTextField.getText());


        Inventory.addProduct(new Product(id, name, price, stock, min, max));

        Parent parent = FXMLLoader.load(getClass().getResource("/View_Controller/InventoryMain.fxml"));
        Scene mainScene = new Scene(parent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(mainScene);
        window.show();
    }

    public void initialize() {
        //Set default text for partIdTextField
        productIdTextField.setDisable(true);
        productIdTextField.setText("Auto-generated");
    }
}
