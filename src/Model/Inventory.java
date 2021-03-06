package Model;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

/**
 *
 */
public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

//  Empty methods for Inventory class

//    public Part lookupPart(int partId) {
//
//    }
//
//    public Product lookupProduct(int productId) {
//        //new code
//    }
//
//    public ObservableList<Part> lookupPart(String partName) {
//
//    }
//
//    public ObservableList<Product> lookupPart(String productName) {
//
//    }
//
//    public void updatePart(int index, Part selectedPart) {
//
//    }
//
//    public void updateProduct(int index, Product newProduct) {
//
//    }
//
    public static boolean deletePart(Part selectedPart) {
        return allParts.remove(selectedPart);
    }

    public static boolean deleteProduct(Product selectedProduct) {
        return allProducts.remove(selectedProduct);
    }

    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
