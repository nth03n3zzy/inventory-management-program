package com.example.c482software1project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * controller for the main scene.
 *
 * @author Anthony Collins.
 */
public class MainController implements Initializable {
    /**
     * declares stage.
     */
        private Stage stage;

    /**
     * declares scene.
     */
    private Scene scene;

    /**
     * declares root.
     */
    private Parent root;
    /**
     * declares product that will be used as the product to modify to pass the product data to the modify product scene.
     */
    public static Product productToModify;
    /**
     * declares part that will be used as the part to modify to pass the data to the modify part scene.
     */
    public static Part modifyPart;
    /**
     * declares a table view for all product table.
     */
    @FXML public TableView <Product> productTableView;
    /**
     * declares a table column to display product IDs.
     */

    @FXML public TableColumn<Product, Integer> productIdColumn;
    /**
     * declares a table column to display product names.
     */
    @FXML public TableColumn<Product, String> productNameColumn;
    /**
     * declares a table column to display inventory level of products.
     */
    @FXML public TableColumn<Product, Integer> productInventoryLvlColumn;
    /**
     * declares a table column to display cost per unit of products.
     */
    @FXML public TableColumn<Product, Integer> productCostPerUnitColumn;
    /**
     * declares a table view for all parts list.
     */

    @FXML public  TableView<Part> partTableView;
    /**
     * declares a table column to display part IDs.
     */
    @FXML public TableColumn<Part, Integer> partIdColumn;
    /**
     * declares a table column to display part names.
     */
    @FXML public TableColumn<Part, String> partNameColumn;
    /**
     * declares a table column to display part inventory.
     */
    @FXML public TableColumn<Part, Integer> partInventoryLvlColumn;
    /**
     * declares a table column to display cost per unit of parts.
     */
    @FXML public TableColumn<Part, Integer> partCostPerUnitColumn;
    /**
     * declares a text field for part search bar.
     */
    @FXML TextField partSearchBar;
    /**
     * declares a text field for product search bar.
     */
    @FXML TextField productSearchBar;


    /**
     * fills parts and products tables with all parts and all products upon initializing.
     * @param url used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */


    public void initialize(URL url, ResourceBundle resourceBundle){

            partIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            partInventoryLvlColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
            partCostPerUnitColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

            partTableView.setItems(Inventory.getAllParts());
            partTableView.getColumns().addAll(partIdColumn, partNameColumn, partInventoryLvlColumn, partCostPerUnitColumn);

           productIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            productInventoryLvlColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
            productCostPerUnitColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

            productTableView.setItems(Inventory.getAllProducts());
            productTableView.getColumns().addAll(productIdColumn,productNameColumn, productInventoryLvlColumn, productCostPerUnitColumn);



        }


    /**
     * switches to the add part scene.
     * @param event add part button clicked.
     * @throws IOException from FXML Loader.
     */
    public void switchToAddPart(ActionEvent event) throws IOException {
            root = FXMLLoader.load(getClass().getResource("/AddPartForm.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }


    /**
     * switches to the add product scene.
     * @param event add product button clicked.
     * @throws IOException from FXML Loader.
     */
    public void switchToAddProduct(ActionEvent event) throws IOException {
            root = FXMLLoader.load(getClass().getResource("/AddproductForm.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();


        }


    /**
     * switches to modify part scene.
     * @param event modify part button clicked
     * @throws IOException from FXML loader.
     */
    public void switchToModifyPart(ActionEvent event) throws IOException {
        //grabs the part that has been selected and sets it to part to modify
        //when the modify part scene is initialized the MainController.modifyPart
        //will be set to the loadPart. which will be used to populate the fields on the modify part page.
        //this is how we are passing the data across scenes.

            modifyPart = partTableView.getSelectionModel().getSelectedItem();

            //if no part is selected a alert will appear telling the user to select a part.
            if(modifyPart == null){
                callAlert(6);
            }

                Parent root = FXMLLoader.load(getClass().getResource("/ModifyPartForm.fxml"));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
        }


    /**
     * switches to modify product scene.
     * @param event modify product button clicked.
     * @throws IOException from FXML loader.
     */
    public void switchToModifyProduct(ActionEvent event) throws IOException {
        //grabs the product that has been selected and sets it to product to modify
        //when the modify product is initialized the MainController.productToModify
        //will be set to the loadProduct. which will be used to populate the fields on the modify product page.
        //this is how we are passing the data across scenes.
            productToModify = productTableView.getSelectionModel().getSelectedItem();

            // if no product is selected call alert telling user to select a product.
            if(productToModify == null){
                callAlert(7);
            }

            root = FXMLLoader.load(getClass().getResource("/ModifyProductForm.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        }

    /**
     * method for searching parts.
     * @param keyEvent key entered in part search bar.
     */
        public void partSearch(KeyEvent keyEvent){
            ObservableList<Part> allParts = Inventory.getAllParts();
            //list of searched parts created.
            ObservableList<Part> searchedParts = FXCollections.observableArrayList();
            String search = partSearchBar.getText();

            //text in search part bar is used to see if any part Ids contain the text or part names.
            //all matches are added to the searched parts list.
            for(Part part : allParts){
                if(String.valueOf(part.getId()).contains(search) || part.getName().contains(search)){
                    searchedParts.add(part);

                }
            }
            //part tableview displays parts in the searched parts list.
            partTableView.setItems(searchedParts);
            if(searchedParts.isEmpty()){
                callAlert(10);
            }
        }

    /**
     * method for searching products.
     * @param keyEvent key entered in product search bar.
     */

    public void productSearch(KeyEvent keyEvent){
            ObservableList<Product> allProducts = Inventory.getAllProducts();
            //searched product list created.
            ObservableList<Product> searchedProducts = FXCollections.observableArrayList();
            String search = productSearchBar.getText();

            //for loop takes text in search bar and searches all product list for Ids that contain the text or names that contain the text
            // parts that match are added to the searched products list.
            for(Product product : allProducts){
                if(String.valueOf(product.getId()).contains(search) || product.getName().contains(search)){
                    searchedProducts.add(product);
                }
            }
            //product table view display the list of searched products.
            productTableView.setItems(searchedProducts);
            if(searchedProducts.isEmpty()){
                callAlert(11);
            }
        }

    /**
     * method for exiting program.
     * @param actionEvent exit button clicked.
     */
    public void exitButtonClicked(ActionEvent actionEvent){
        //alert asking the user if they are sure they wish to exit the program.
            callAlert(5);
        }

    /**
     * method to delete a part.
     * @param actionEvent delete part button clicked.
     */
        public void partDeleteButtonClicked(ActionEvent actionEvent){
            //if no part is selected a alert will tell the user to select a part.
            if(partTableView.getSelectionModel().getSelectedItem() == null){
                callAlert(1);

            }else
                // a;ert asking the user to confirm they wish to delete the part.
                callAlert(2);
        }

    /**
     * method to delete a product.
     * @param actionEvent delete product button clicked.
     */
    public  void productDeleteButtonClicked(ActionEvent actionEvent){
        //if no product is selected the user will have an alert telling them they need to select a product.
            if(productTableView.getSelectionModel().getSelectedItem() == null){
                callAlert(3);
            }else if(productTableView.getSelectionModel().getSelectedItem().getAssociatedParts().isEmpty()) {

                //alert asking user if they are sure they wish to delete the product selected.
                callAlert(4);
            } else
                callAlert(12);

        }

    /**
     * method with all alerts for various situations
     * @param alertType depending on the situation a switch case is used to call the appropriate alert.
     */
        public void callAlert(int alertType) {
            switch (alertType) {
                case (1) -> {
                    Alert deleteNoneSelected = new Alert(Alert.AlertType.ERROR);
                    deleteNoneSelected.setTitle("ERROR");
                    deleteNoneSelected.setHeaderText("Error");
                    deleteNoneSelected.setContentText("please select a part from the table to delete.");
                    deleteNoneSelected.showAndWait();
                }
                case (2) -> {
                    Alert deleteConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
                    deleteConfirmation.setTitle("WARNING");
                    deleteConfirmation.setHeaderText("ARE YOU SURE?");
                    deleteConfirmation.setContentText("please confirm you wish to delete the selected part.");
                    Optional<ButtonType> confirm = deleteConfirmation.showAndWait();
                    if (confirm.isPresent() && confirm.get() == ButtonType.OK) {
                        Inventory.deletePart(partTableView.getSelectionModel().getSelectedItem());
                        partTableView.setItems(Inventory.getAllParts());
                    }
                }
                case (3) -> {
                    Alert deleteNoneSelected = new Alert(Alert.AlertType.ERROR);
                    deleteNoneSelected.setTitle("ERROR");
                    deleteNoneSelected.setHeaderText("Error");
                    deleteNoneSelected.setContentText("please select a product from the table to delete.");
                    deleteNoneSelected.showAndWait();
                }
                case (4) -> {
                    Alert deleteConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
                    deleteConfirmation.setTitle("WARNING");
                    deleteConfirmation.setHeaderText("ARE YOU SURE?");
                    deleteConfirmation.setContentText("please confirm you wish to delete the selected product.");
                    Optional<ButtonType> confirm = deleteConfirmation.showAndWait();
                    if (confirm.isPresent() && confirm.get() == ButtonType.OK) {
                        Inventory.deleteProduct(productTableView.getSelectionModel().getSelectedItem());
                        productTableView.setItems(Inventory.getAllProducts());
                    }
                }
                case (5) -> {
                    Alert deleteConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
                    deleteConfirmation.setTitle("WARNING");
                    deleteConfirmation.setHeaderText("ARE YOU SURE?");
                    deleteConfirmation.setContentText("please confirm you wish to EXIT the program");
                    Optional<ButtonType> confirm = deleteConfirmation.showAndWait();
                    if (confirm.isPresent() && confirm.get() == ButtonType.OK) {
                        System.exit(0);
                    }


                }
                case (6) -> {
                    Alert deleteNoneSelected = new Alert(Alert.AlertType.ERROR);
                    deleteNoneSelected.setTitle("ERROR");
                    deleteNoneSelected.setHeaderText("Error");
                    deleteNoneSelected.setContentText("please select a part from the table to modify.");
                    deleteNoneSelected.showAndWait();
                }
                case (7) -> {
                    Alert deleteNoneSelected = new Alert(Alert.AlertType.ERROR);
                    deleteNoneSelected.setTitle("ERROR");
                    deleteNoneSelected.setHeaderText("Error");
                    deleteNoneSelected.setContentText("please select a product from the table to modify.");
                    deleteNoneSelected.showAndWait();
                }
                case(10) -> {
                    Alert noSearchFound = new Alert(Alert.AlertType.WARNING);
                    noSearchFound.setTitle("WARNING");
                    noSearchFound.setHeaderText("NO SEARCH FOUND");
                    noSearchFound.setContentText("no search found containing the text ' " + partSearchBar.getText() + " ' please check your search.");
                    noSearchFound.showAndWait();
                }
                case(11) -> {
                    Alert noSearchFound = new Alert(Alert.AlertType.WARNING);
                    noSearchFound.setTitle("WARNING");
                    noSearchFound.setHeaderText("NO SEARCH FOUND");
                    noSearchFound.setContentText("no search found containing the text ' " + productSearchBar.getText() + " ' please check your search.");
                    noSearchFound.showAndWait();
                }
                case(12) -> {
                    Alert productHasParts = new Alert(Alert.AlertType.WARNING);
                    productHasParts.setTitle("WARNING");
                    productHasParts.setHeaderText("this product has associated parts");
                    productHasParts.setContentText("the product you have selected has associated parts. these parts must be removed from the product prior to deletion");
                    productHasParts.showAndWait();
                }
            }
        }
    }