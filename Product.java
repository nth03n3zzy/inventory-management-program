package com.example.c482software1project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * product class gives baseline code for all products
 *
 * @author Anthony Collins.
 */

public class Product  {
    /**\
     * declares an int used as products identfying number.
     */
    private  int id = 0;

    /**
     * declares a string that will be used as a products name.
     */
    private String name;

    /**
     * declares double to be used as product price per unit.
     */
    private double price;

    /**
     * declares a int to be used as current stock of a product.
     */
    private int stock;

    /**
     * declares a int to be used as the minimum amount of a product allowed in stock.
     */
    private int min;

    /**
     * declares a int to be used as the maximum amount of a unit allowed in stock.
     */
    private int max;

    /**
     * declars a array list of parts that are to be associated with a product.
     */
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();


    /**
     * method for creating a new product.
     * @param id int used for products identifying number.
     * @param name String used for products name.
     * @param price double used for the price of the product.
     * @param stock int used for current quantity of product in stock.
     * @param min int used for minimum quantity allowed in stock.
     * @param max int used for maximum quantitys allowed in stock.
     */
    public Product( int id, String name, double price, int stock, int min, int max) {
        this.id = id + 1;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * getter for a prodcuts ID
     * @return a products ID.
     */
    public int getId() {
        return id;
    }

    /**
     * setter for a products ID.
     * @param id sets the ID.
     */

    public void setId(int id) {
        this.id = id;
    }

    /**
     * getter for a products name.
     * @return a products name.
     */

    public String getName() {
        return name;
    }

    /**
     * setter for a products name sets a products name.
     * @param name the name to set a products name too.
     */

    public void setName(String name) {
        this.name = name;
    }

    /**
     * getter for a products price.
     * @return a products price.
     */

    public double getPrice() {
        return price;
    }

    /**
     * setter for a products price.
     * @param price becomes the products price.
     */

    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * getter for a products stock.
     * @return a products stock.
     */
    public int getStock() {
        return stock;
    }

    /**
     * setter for a products stock
     * @param stock sets the stock of product to this value.
     */

    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * getter for min of a product.
     * @return a products min.
     */

    public int getMin() {
        return min;
    }

    /**
     * setter for a products min.
     * @param min sets min to this value.
     */

    public void setMin(int min) {
        this.min = min;
    }

    /**
     * getter for a products max.
     * @return a products max.
     */

    public int getMax() {
        return max;
    }

    /**
     * setter for a products max.
     * @param max becomes the products max.
     */

    public void setMax(int max) {
        this.max = max;
    }

    /**
     * method to add a part to the associated part list.
     * @param part part that is specified to be added to the list.
     */

    public void addAssociatedPart(Part part){
        associatedParts.add(part);
    }

    /**
     * method to remove an associated part from the associated part list
     * @param selectedAssociatedPart part that is selected to be removed.
     * @return a boolean indicating the action has been carried out.
     */

    public boolean deleteAssociatedPart(Part selectedAssociatedPart){
        associatedParts.remove(selectedAssociatedPart);
        return true;
    }

    /**
     * method used to return the list of associated parts with a product.
     * @return the list of a products associated parts.
     */
    public ObservableList<Part> getAssociatedParts() {
        return associatedParts;
    }
}
