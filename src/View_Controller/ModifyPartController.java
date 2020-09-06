package View_Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ModifyPartController {
    @FXML
    private void cancelButtonOnClick(ActionEvent event) throws IOException {
        Parent addPartParent = FXMLLoader.load(getClass().getResource("/View_Controller/InventoryMain.fxml"));
        Scene addPartScene = new Scene(addPartParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(addPartScene);
        window.show();
    }
}
