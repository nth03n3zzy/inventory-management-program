package com.example.c482software1project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {

    /**
     *observable list of all parts used to maintain a list of all parts and to populate table views in scene controllers
     */
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    /**
     *observable list of all parts used to maintain a list of all parts and to populate table views in scene controllers
     */
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();


    /**
     * method to add a part to the all parts list.
     * @param newPart is used to be added to the observable list of all parts.
     */
    public static void addPart(Part newPart){
        allParts.add(newPart);
    }

    /**
     * method to add a product to the all products list.
     * @param newProduct is used to be added to the observable list of all parts.
     */
    public static void addProduct(Product newProduct){
        allProducts.add(newProduct);
        System.out.println(newProduct + " added into product List!");
    }

    /**
     * method used to pull a part by its ID.
     * @param partID
     * @return
     */
    public static Part lookUpPart(int partID){
        return allParts.get(partID);
    }

    /**
     * method used to look up products by thier ID#
     * @param productID
     * @return
     */

    public static Product lookUpProduct(int productID){
        return allProducts.get(productID);
    }

    /**
     * method to look up parts by their name.
     * @param name
     * @return
     */

    public static int  lookUpPart(String name) {
        return allParts.indexOf(name);

    }



    public static ObservableList<Product> lookUpProduct(String productName){
        ObservableList<Product> productsFound = FXCollections.observableArrayList();

        for (Product product : allProducts) {
            if (product.getName().equals(productName)) {
                productsFound.add(product);
            }
        }
        return productsFound;
    }

    /**
     * method used to update parts
     * @param index
     * @param selectedPart
     */
    public static void updatePart(int index, Part selectedPart){
        Part update = allParts.get(index);
        update.setMax(selectedPart.getMax());
        update.setPrice(selectedPart.getPrice());
        update.setName(selectedPart.getName());
        update.setStock(selectedPart.getStock());
        update.setMin(selectedPart.getMin());
    }

    /** method used to update products
     * @param index
     * @param selectedProduct
     */

    public static void updateProduct (int index, Product selectedProduct){
        Inventory.getAllProducts().get(index).setId(selectedProduct.getId());
        Inventory.getAllProducts().get(index).setName(selectedProduct.getName());
        Inventory.getAllProducts().get(index).setStock(selectedProduct.getStock());
        Inventory.getAllProducts().get(index).setPrice(selectedProduct.getPrice());
        Inventory.getAllProducts().get(index).setMin(selectedProduct.getMin());
        Inventory.getAllProducts().get(index).setMax(selectedProduct.getMax());
    }

    /**
     * method used to delete part from the all parts list.
     * @param selectedPart
     * @return
     */
    public static boolean deletePart (Part selectedPart){
        return allParts.remove(selectedPart);
    }

    /**
     * method to delete products from the all products list.
     * @param selectedProduct
     * @return
     */
    public static boolean deleteProduct (Product selectedProduct){
        return allProducts.remove(selectedProduct);
    }

    /**
     * method to return the list of all parts.
     * @return
     */
    public static ObservableList<Part> getAllParts(){
        return allParts;
    }

    /**
     * method to return the list of all products.
     * @return
     */
    public static ObservableList<Product> getAllProducts(){
        return allProducts;
    }


}
