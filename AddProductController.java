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
 * class controls the logic and function of the add product scene.
 *
 * @author Anthony Collins.
 */

public class AddProductController implements Initializable {

    /**
     * declares Stage.
     */
    private Stage stage;

    /**
     * declares Scene.
     */
    private Scene scene;

    /**
     * declares the root
     */
    private Parent root;

    /**
     * declaring a array list of parts to be associated with a product.
     */
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /**
     * declares a table view for all parts list.
     */
    @FXML
    public TableView<Part> allPartTableView;

    /**
     * declares a text field for product ID.
     */

    @FXML
    public TextField idTextField;

    /**
     * declares a text field for product name.
     */
    @FXML
    public TextField nameTextField;

    /**
     * declares a text field for product inventory in stock.
     */
    @FXML
    public TextField inventoryTextField;

    /**
     * declares a text field for product price.
     */
    @FXML
    public TextField priceTextField;

    /**
     * declares a text field for max quantity allowed in stock.
     */
    @FXML
    public TextField maxTextField;

    /**
     * declares a text field for minimum quantity allowed in stock.
     */
    @FXML
    public TextField minTextField;

    /**
     * declares a button for adding associated parts to a product.
     */

    @FXML
    public Button addAssociatedPartButton;

    /**
     * declares a button for deleting associated parts from a product.
     */

    public Button deleteProductButton;

    /**
     * declares a table column to display part Ids from the all parts table.
     */

    @FXML
    public TableColumn<Part, Integer> partIdColumn;

    /**
     * declares a table column to display part names from the all parts list.
     */

    @FXML
    public TableColumn<Part, String> partNameColumn;

    /**
     * declares a table column to display inventory level of parts from the all parts list.
     */
    @FXML
    public TableColumn<Part, Integer> partInventoryLvlColumn;

    /**
     * declares a table column to display the cost per part form the all parts list.
     */
    @FXML
    public TableColumn<Part, Integer> partCostPerUnitColumn;

    /**
     * declares a table view for all parts list.
     */
    public TableView<Part> associatedPartTableView;

    /**
     * declares a table column to display part Ids from the associated parts table.
     */
    @FXML
    public TableColumn<Part, Integer> associatedPartIdColumn;

    /**
     * declares a table column to display part names from the associated parts list.
     */

    @FXML
    public TableColumn<Part, String> associatedPartNameColumn;


    /**
     * declares a table column to display part inventory level from the associated parts list.
     */
    @FXML
    public TableColumn<Part, Integer> associatedPartInventoryLvlColumn;

    /**
     * declares a table column to display part cost per unit from the associated parts list.
     */
    @FXML
    public TableColumn<Part, Integer> associatedPartCostPerUnitColumn;

    /**
     * declared to set a baseline for product Id numbers.
     */

    private static int id = 200;

    /**
     * boolean to confirm the user wishes to save the product they have added.
     */
    private boolean saveConfirm = false;

    /**
     * boolean to determin the user has confirmed the action they attempted to perform.
     */

    private boolean confirmed = false;

    /**
     * declares a text field to be used as the search bar.
     */
    @FXML
    TextField partSearchBar;

    /**
     * method used to create a unique Id for each product made.
     * @return a unique uneditable by the user product ID.
     */

    public static int idGenerator() {

        return id++;
    }

    /**
     * method to return a earch when text is put into the search bar.
     * @param keyEvent text is placed in the search bar.
     */
    public void partSearch(KeyEvent keyEvent) {
        // pulls the list of all parts.
        ObservableList<Part> allParts = Inventory.getAllParts();
        //creates a new list of part being searched by pulling the text from the search bar.
        ObservableList<Part> searchedParts = FXCollections.observableArrayList();
        String search = partSearchBar.getText();

        // for loop takes the text in the search bar and searches if Ids contain  that text or if part names contain the tect within the
        //all parts list. if they do the part is added to the searched parts list.
        for (Part part : allParts) {
            if (String.valueOf(part.getId()).contains(search) || part.getName().contains(search)) {
                searchedParts.add(part);
            }
        }
        //all part tableview displays the list of parts in the searched parts list.
        allPartTableView.setItems(searchedParts);

        callAlert(10);

    }

    /**
     * simple logic to determin min is less than max and inventory is between min and max.
     * @param min is the minimum quantity allowed in inventory.
     * @param max is the maximum quantity allowed in inventory.
     * @param inventory is the total quantity allowed in inventory.
     * @return boolean based on is min is less than max and inventory is between min and max.
     */
    private boolean minMaxInventoryValidation(int min, int max, int inventory) {
        if (min > max) {
            callAlert(3);
            return false;
        }
        if ((inventory < min) || inventory > max) {
            callAlert(4);
            return false;
        }
        return true;
    }

    /**
     * method containing logic verifying all info about a part is filled out appropriately when the save button is clicked.
     * @param event save button clicked.
     * @throws IOException from FXML loader.
     */

    public void SaveButtonClicked(ActionEvent event) throws IOException {
        // boolean declared to determine if a process should continue or not.
        boolean proceed = true;

        try {
            //asks the user if to confirm they wish to save the product they have added.
            callAlert(2);
            if(saveConfirm == true) {

                // if the user wishes to save the name text field is verified to not be empty.
                if (nameTextField.getText().isEmpty()) {
                    //if the name text field is empty an alert is called telling the user to fill it out.
                    callAlert(1);
                    // proceed is set to false and the save is stopped.
                    proceed = false;
                }
                //if the name text field is not empty min, max, and inventory are checked for correctness.
                if (minMaxInventoryValidation(Integer.parseInt(minTextField.getText()),
                        Integer.parseInt(maxTextField.getText()),
                        Integer.parseInt(inventoryTextField.getText())) && proceed == true) {
                    //if min, max, inventory, the name, and the user wishes to save are all correct a new product is created.
                    Product newProduct = new Product(Integer.parseInt(idTextField.getText()),
                            nameTextField.getText(),
                            Double.parseDouble(priceTextField.getText()),
                            Integer.parseInt(inventoryTextField.getText()),
                            Integer.parseInt(minTextField.getText()),
                            Integer.parseInt(maxTextField.getText()));
                    // the associated part are added to the associated parts list for the product.
                    for (Part part : associatedParts) {
                        newProduct.addAssociatedPart(part);
                    }

                    // the product is added to the all products list.
                    Inventory.addProduct(newProduct);


                }
                    // the main scene is loaded.
                    if(proceed) {

                        root = FXMLLoader.load(getClass().getResource("/MainForm.fxml"));
                        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                    }
                }


        }
        //generic alert telling the user to check all fields for correctness.
        catch(Exception e){
            callAlert(5);
        }


    }

    /**
     * method with logic for removing a associated part form being associated with that product.
     * @param event remove associated part button is clicked.
     */

    public void removeAssociatedPartButtonClicked(ActionEvent event) {
        // alert is called verifying the user wishes to remove the associated part.
        callAlert(7);
        // if confirmed the part is removed from the associated parts list.
        if (confirmed) {
            associatedParts.remove(associatedPartTableView.getSelectionModel().getSelectedItem());
            associatedPartTableView.setItems(associatedParts);
            //confirmed boolean reset to false.
            confirmed = false;
        }
    }

    /**
     * method for adding a part form the all parts list to the associated parts list for the product being created.
     * @param actionEvent add button clicked.
     */
    public void addPartButtonClicked(ActionEvent actionEvent) {
        //alert asking if the user to confirm they wish to add the part to the product.
        callAlert(6);
        //if confirmed the part is added to the associated parts table.
        if(confirmed) {
            associatedParts.add(allPartTableView.getSelectionModel().getSelectedItem());
            associatedPartTableView.setItems(associatedParts);
            //confirm boolean reset to false.
            confirmed = false;
        }

    }


    /**
     * initialize method loads product lists into tables appropriately
     *  @param url used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        partIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryLvlColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partCostPerUnitColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        allPartTableView.setItems(Inventory.getAllParts());

        allPartTableView.getColumns().addAll(partIdColumn, partNameColumn, partInventoryLvlColumn, partCostPerUnitColumn);

        associatedPartIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartInventoryLvlColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartCostPerUnitColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        idTextField.setText(String.valueOf(idGenerator() + 1));


    }

    /**
     * switches to main scene
     * @param event cancel button clicked
     * @throws IOException from FXML loader.
     */

    public void switchToMain(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/MainForm.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    /**
     * method used for all the various alerts that are called within the scene.
     * @param alertType switch case used depending on reason for call to call the correct alert.
     */
    public void callAlert(int alertType) {
        switch (alertType) {
            case (1) -> {
                Alert nameEntered = new Alert(Alert.AlertType.ERROR);
                nameEntered.setTitle("ERROR");
                nameEntered.setHeaderText("Error");
                nameEntered.setContentText("please ensure you have entered a name for your product.");
                nameEntered.showAndWait();
                break;
            }
            case (2) -> {
                Alert saveProductConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
                saveProductConfirmation.setTitle("WARNING");
                saveProductConfirmation.setHeaderText("ARE YOU SURE?");
                saveProductConfirmation.setContentText("please confirm you wish to save theses changes to this product.");
                Optional<ButtonType> confirm = saveProductConfirmation.showAndWait();
                if (confirm.isPresent() && confirm.get() == ButtonType.OK) {
                    this.saveConfirm = true;
                }

            }
            case (3) -> {
                Alert minAndMax = new Alert(Alert.AlertType.ERROR);
                minAndMax.setTitle("ERROR");
                minAndMax.setHeaderText("Error");
                minAndMax.setContentText("please select make sure min field is less than max field");
                minAndMax.showAndWait();
                break;
            }
            case (4) -> {
                Alert minMaxAndInventory = new Alert(Alert.AlertType.ERROR);
                minMaxAndInventory.setTitle("ERROR");
                minMaxAndInventory.setHeaderText("Error");
                minMaxAndInventory.setContentText("please select make sure inventory is greater than or equal to" +
                        " min field and less than or equal to max field");
                minMaxAndInventory.showAndWait();
                break;
            }
            case (5) -> {
                Alert fieldError = new Alert(Alert.AlertType.ERROR);
                fieldError.setTitle("ERROR");
                fieldError.setHeaderText("Error");
                fieldError.setContentText("please ensure all fields have been entered correctly");
                fieldError.showAndWait();
                break;
            }
            case (6) -> {
                Alert addProductToPart = new Alert(Alert.AlertType.CONFIRMATION);
                addProductToPart.setTitle("WARNING");
                addProductToPart.setHeaderText("ARE YOU SURE?");
                addProductToPart.setContentText("please confirm you wish to add this part to this product.");
                Optional<ButtonType> confirm = addProductToPart.showAndWait();
                if (confirm.isPresent() && confirm.get() == ButtonType.OK) {
                    confirmed = true;
                }
            }
            case(7) -> {
                Alert removeProductFromPart = new Alert(Alert.AlertType.CONFIRMATION);
                removeProductFromPart.setTitle("WARNING");
                removeProductFromPart.setHeaderText("ARE YOU SURE");
                removeProductFromPart.setContentText("please confirm you wish to remove this part from the being associated with this product");
                Optional<ButtonType> confirm = removeProductFromPart.showAndWait();
                if(confirm.isPresent() && confirm.get() == ButtonType.OK){
                    confirmed = true;
                }
            }
            case(10) -> {
                Alert noSearchFound = new Alert(Alert.AlertType.WARNING);
                noSearchFound.setTitle("WARNING");
                noSearchFound.setHeaderText("NO SEARCH FOUND");
                noSearchFound.setContentText("no search found containing the text ' " + partSearchBar.getText() + " ' please check your search.");
                noSearchFound.showAndWait();
            }


        }
    }
}


