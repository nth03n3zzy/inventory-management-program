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
 * controller sets up logic and controls for the modify product page.
 *
 * @author Anthony Collins.
 */
public class ModifyProductController implements Initializable {
        /**
         * local variable sets stage
         */
    private Stage stage;
    /**
     * local veriable sets scene
     */
    private Scene scene;
    /**
     * local variable sets parent variable.
     */
    private Parent root;

    /**
     * local variable holds a list of parts associated with a specific product.
     */
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /**
     *  declares product ID text field.
     */
    @FXML
    TextField productId;
    /**
     *declares product name text field.
     */
    @FXML
    TextField productName;
    /**
     *declares product inventory text field,
     */
    @FXML
    TextField productInventory;
    /**
     *declares product price text field
     */
    @FXML
    TextField productPrice;
    /**
     *declares minimum quantity of product text field
     */
    @FXML
    TextField productMin;

    /**
     *declares maximum quantity of product text field.
     */
    @FXML
    TextField productMax;

    /**
     *declares a tableView that will be used to display all parts.
     */
    @FXML
    TableView<Part> allPartsTableView;

    /**
     *declares table column for part IDs.
     */
    @FXML
    TableColumn<Part, Integer> allPartIdColumn;

    /**
     *declares table column for part names.
     */
    @FXML
    TableColumn<Part, String> allPartNameColumn;

    /**
     *declares table column for quantity in inventory
     */
    @FXML
    TableColumn<Part, Integer> allPartInventoryColumn;

    /**
     *declares table column for the price per unit of a part.
     */
    @FXML
    TableColumn<Part, Integer> allPartCostColumn;

    /**
     *declares a table view for parts associated with a product
     */
    @FXML
    TableView<Part> associatedPartsTableView;

    /**
     *declares a table colum for part IDs of associated parts.
     */
    @FXML
    TableColumn<Part, Integer> associatedPartIdColumn;

    /**
     *declares a table column for associated parts names.
     */
    @FXML
    TableColumn<Part, String> associatedPartNameColumn;

    /**
     *declares a table column for the quantity of inventory of an associated part.
     */
    @FXML
    TableColumn<Part, Integer> associatedPartInventoryColumn;
    /**
     *declares a table column for cost per unit of an associated part.
     */
    @FXML
    TableColumn<Part, Integer> associatedPartCostColumn;

    /**
     *declares a textfield that is used as the search bar for looking up parts.
     */
    @FXML
    TextField partSearchBar;

    /**
     *declares an instance of product used in the initalization of the scene to pull a product that has been selected from the
     * all products list.
     */
    private static Product loadProduct;
    /**
     *declares a boolean that is used to verify the user intends to save the changes they have ,ade to the part.
     */
    private boolean saveConfirm = false;

    /**
     *declares a boolean that is used to verify the user has confirmed they intend to add a part or remove a part.
     */
    private boolean confirmed = false;
    /**
     *declares a boolean that is used to verify that the user intends to cancel the modification they have begun to
     * do on a product.
     */
    private boolean cancelConfirm = false;

    /**
     *method used to give the search bar logic to search parts by Name and ID.
     */
    public void partSearch(KeyEvent keyEvent) {
        ObservableList<Part> allParts = Inventory.getAllParts();
        ObservableList<Part> searchedParts = FXCollections.observableArrayList();
        String search = partSearchBar.getText();

//loop searches for parts via matching ID numbers and name characters.

        for (Part part : allParts) {
            if (String.valueOf(part.getId()).contains(search) || part.getName().contains(search)) {
                searchedParts.add(part);
            }
        }
        //sets tableview to parts that pass the search.
        allPartsTableView.setItems(searchedParts);

        if(searchedParts.isEmpty()){
            callAlert(10);
        }

    }

    /**
     *method used to set the loadProduct instance of product we declared earlier to be a copy of the product that was selected to be modified.
     */
    public static void setLoadProduct(Product loadProduct) {
        ModifyProductController.loadProduct = loadProduct;
    }

    /**
     *declares method called on when the add button is clicked it will take the selected part ask the user if they are sure they want to
     * add it to the products associated parts with an alert. if the user clicks confirm the part is added to the associated parts list.
     */
    public void addButtonClicked(ActionEvent actionEvent) {
        Part selectedPart = allPartsTableView.getSelectionModel().getSelectedItem();

// calls on confirmation alert to ask user if they are sure they wish to add the part to the product.
        if (selectedPart == null) {
            callAlert(8);
        }
//if nothing is selected displays alert to tell user they must select a part.
        if (selectedPart != null) {
            callAlert(6);
            if (confirmed == true) {
                associatedParts.add(selectedPart);
                associatedPartsTableView.setItems(associatedParts);
                //resets confirmed boolean to false so if a second part is added it doesnt add the part if the user clicks cancel.
                confirmed = false;
            }
        }

    }

    /**
     *declares method to remove button when clicked the user will be asked if they are sure they wish to remove the associated part from
     * the product.
     */
    public void removeButtonClicked(ActionEvent actionEvent) {
        Part selectedPart = associatedPartsTableView.getSelectionModel().getSelectedItem();

            //calls alert to ask user if they are sure they wish to remove the product.
        if (selectedPart == null) {
            callAlert(8);
        }

        if (selectedPart != null) {
            // if null a alert will pop up informing the user they have not selected any part to remove.
            callAlert(7);
            //if the user did click confirm for removing the part the confirm boolean is set to true and the part is removed
            //the confirm boolean is then reset to false.
            if (confirmed == true) {
                associatedParts.remove(selectedPart);
                associatedPartsTableView.setItems(associatedParts);
                confirmed = false;
            }
        }
    }

    /**
     * simple logic to verify min is less than max, and inventory is between min and max.
     * @param min is the minimum quantity of product that can be in stock.
     * @param max is the maximum quantity of a product that can be in stock.
     * @param inventory is the current quantity of a product in stock.
     * @return a boolean true if min is less than max and inventory is between the two, else it returns false.
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
     * method for logic when the save button is clicked for saving a product.
     * @param event is the save button being clicked.
     * @throws IOException simple IOException.
     */
    public void SaveButtonClicked(ActionEvent event) throws IOException {

        //the following code block will be attempted
        try {
            // alert will be called to confirm the user intends to save the product at this point.
            callAlert(2);
            if (saveConfirm) {
                //boolean declared to check if the name field is filled out.
                boolean hasName = true;
                if (productName.getText().isEmpty()) {
                    // alert called to inform user they must fill out the name block.
                    callAlert(1);
                    hasName = false;
                }
                // if the name field is filled out and the user confirmed they wish to save the following executes.
                if (hasName) {
                    //updated product is declared.
                    Product updatedProduct = new Product(loadProduct.getId() - 1,
                            productName.getText(),
                            Double.parseDouble(productPrice.getText()),
                            Integer.parseInt(productInventory.getText()),
                            Integer.parseInt(productMin.getText()),
                            Integer.parseInt(productMax.getText()));
                    //min max and inventory fields are checked.
                    if (minMaxInventoryValidation(updatedProduct.getMin(), updatedProduct.getMax(), updatedProduct.getStock())) {

                        //associated parts are added to the product.
                        for (Part part : associatedParts) {
                            updatedProduct.addAssociatedPart(part);
                        }
                        // new updated product is added to the all products list.
                        Inventory.addProduct(updatedProduct);
                        //old original product is deleted form all products list.
                        Inventory.deleteProduct(loadProduct);
                        //final check to ensure the all product list has been updated with the updated product. if its in the list the main
                        //screen is loaded.
                        if (Inventory.getAllProducts().contains(updatedProduct)) {
                            root = FXMLLoader.load(getClass().getResource("/MainForm.fxml"));
                            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                            scene = new Scene(root);
                            stage.setScene(scene);
                            stage.show();
                        }
                    }
                }
            }
            //generic alert telling the user to ensure all data entered in the fields needs to be checked if something non-specific
            //is wrong IE. wrong data type in a field.
        } catch (Exception e) {
            callAlert(5);
        }

    }

    /**
     * initialization method loads controller and populates fields with the part that was selected.
     * @param url used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */

    public void initialize(URL url, ResourceBundle resourceBundle) {
        setLoadProduct(MainController.productToModify);
        associatedParts = loadProduct.getAssociatedParts();
        System.out.println(loadProduct + "load product set!");


        productName.setText(String.valueOf(loadProduct.getName()));
        productInventory.setText(String.valueOf(loadProduct.getStock()));
        productPrice.setText(String.valueOf(loadProduct.getPrice()));
        productMin.setText(String.valueOf(loadProduct.getMin()));
        productMax.setText(String.valueOf(loadProduct.getMax()));
        productId.setText(String.valueOf(loadProduct.getId()));


        allPartIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        allPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        allPartInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        allPartCostColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        allPartsTableView.setItems(Inventory.getAllParts());
        allPartsTableView.getColumns().addAll(allPartIdColumn, allPartNameColumn, allPartInventoryColumn, allPartCostColumn);


        associatedPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartCostColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        associatedPartIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        associatedPartsTableView.setItems(associatedParts);
    }


    /**
     * switches to Main Scene upon hitting cancel button
     */


    public void switchToMain(ActionEvent event) throws IOException {
        // confrimation alert ensuring the user wishes to cancel.
        callAlert(9);
        if (cancelConfirm) {
            root = FXMLLoader.load(getClass().getResource("/MainForm.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        }
    }

    /**
     * method containing all the various alerts that may get called depending on what action is occuring.
     * @param alertType dependent on the cause for the alert simple switch case used to call the different alerts.
     */

    public void callAlert(int alertType) {
        switch (alertType) {
            case (1) -> {
                Alert nameNotEntered = new Alert(Alert.AlertType.ERROR);
                nameNotEntered.setTitle("ERROR");
                nameNotEntered.setHeaderText("Error");
                nameNotEntered.setContentText("please ensure you have entered a name for your product.");
                nameNotEntered.showAndWait();
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
                Alert deleteNoneSelected = new Alert(Alert.AlertType.ERROR);
                deleteNoneSelected.setTitle("ERROR");
                deleteNoneSelected.setHeaderText("Error");
                deleteNoneSelected.setContentText("please select make sure min field is less than max field");
                deleteNoneSelected.showAndWait();
                break;
            }
            case (4) -> {
                Alert deleteNoneSelected = new Alert(Alert.AlertType.ERROR);
                deleteNoneSelected.setTitle("ERROR");
                deleteNoneSelected.setHeaderText("Error");
                deleteNoneSelected.setContentText("please select make sure inventory is greater than or equal to" +
                        " min field and less than or equal to max field");
                deleteNoneSelected.showAndWait();
                break;
            }
            case (5) -> {
                Alert deleteNoneSelected = new Alert(Alert.AlertType.ERROR);
                deleteNoneSelected.setTitle("ERROR");
                deleteNoneSelected.setHeaderText("Error");
                deleteNoneSelected.setContentText("please ensure all fields have been entered correctly");
                deleteNoneSelected.showAndWait();
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
            case (7) -> {
                Alert removeProductFromPart = new Alert(Alert.AlertType.CONFIRMATION);
                removeProductFromPart.setTitle("WARNING");
                removeProductFromPart.setHeaderText("ARE YOU SURE");
                removeProductFromPart.setContentText("please confirm you wish to remove this part from the being associated with this product");
                Optional<ButtonType> confirm = removeProductFromPart.showAndWait();
                if (confirm.isPresent() && confirm.get() == ButtonType.OK) {
                    confirmed = true;
                }
            }
            case (8) -> {
                Alert noneSelected = new Alert(Alert.AlertType.ERROR);
                noneSelected.setTitle("ERROR");
                noneSelected.setHeaderText("Error");
                noneSelected.setContentText("please ensure you have seleceted a part to add or remove");
                noneSelected.showAndWait();
                break;
            }
            case (9) -> {
                Alert cancelConfirm = new Alert(Alert.AlertType.CONFIRMATION);
                cancelConfirm.setTitle("WARNING");
                cancelConfirm.setHeaderText("ARE YOU SURE?");
                cancelConfirm.setContentText("please confirm you wish to cancel adding this part.");
                Optional<ButtonType> confirm = cancelConfirm.showAndWait();
                if (confirm.isPresent() && confirm.get() == ButtonType.OK) {
                    this.cancelConfirm = true;
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
