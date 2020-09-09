package Main;

import Model.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class Main extends Application {

    private Stage primaryStage;

    /**
     * This method sets the primaryStage of the GUI.

     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("/View_Controller/InventoryMain.fxml"));
        primaryStage.setTitle("Inventory Management System");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        /**
         * Adds test data to populate tables
         */

        //Add InHouse parts
        Part item1 = new InHouse(27, "Part 1", 5.99, 6, 2, 50, 60);
        Part item2 = new InHouse(10, "Part 2", 4.00, 15, 8, 30, 40);
        Inventory.addPart(item1);
        Inventory.addPart(item2);

        //Add OutSourced Parts
        Part p1 = new Outsourced(44, "Part A", 3.99, 8, 1, 40, "Yokohama Co.");
        Part p2 = new Outsourced(80, "Part B", 14.99, 14,5, 80, "Kanagawa Co.");
        Inventory.addPart(p1);
        Inventory.addPart(p2);

        //Add Products
        Product product1 = new Product(22, "Product 1", 1.99, 2, 1, 30);
        product1.addAssociatedPart(item1);
        product1.addAssociatedPart(p1);

        Product product2 = new Product(33, "Product 2", 5.99, 10, 5, 50);
        product2.addAssociatedPart(item2);
        product2.addAssociatedPart(p2);

        Inventory.addProduct(product1);
        Inventory.addProduct(product2);


        launch(args);
    }
}
