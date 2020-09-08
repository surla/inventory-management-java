package Main;

import Model.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("/View_Controller/InventoryMain.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    private void addTestData(Inventory inv) {



    }

    public static void main(String[] args) {
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

        launch(args);
    }
}
