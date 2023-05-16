package com.example.c482software1project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * class controls the functionality for the add part page.
 *
 * @author Anthony Collins.
 */

public class AddPartController implements Initializable {
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
     * label for machineID or company depending if the part is InHouse or Outsorced it will prompt the user
     * for a machine ID or a company name.
     */

    @FXML
    Label inHouseOutsource;
    /**
     * declares radio Butoon for in house parts.
     */
    @FXML
    RadioButton inHouseRadio;

    /**
     * declares a radio button for outsourced parts.
     */
    @FXML
    RadioButton outSourceRadio;

    /**
     * declares a text field for machine ids, or company name.
     */

    @FXML
    TextField machineIdCompany;

    /**
     * declares a text field for the parts name.
     */
    @FXML
    TextField partName;

    /**
     * declares a text field for the quantity in inventory.
     */
    @FXML
    TextField inventory;

    /**
     * declares a text field for the price per unit of a part.
     */
    @FXML
    TextField costPerUnit;

    /**
     * declares a text field for the maximum quantity of a part allowed in stock.
     */
    @FXML
    TextField max;

    /**
     * declares a text field for the minimum quantity of a part allowed in stock.
     */
    @FXML
    TextField min;

    /**
     * declares a text field for the Id number of a part.
     */

    @FXML TextField idTextField;

    /**
     * declares a save button
     */
    @FXML
    Button save;

    /**
     * declares a cancel button.
     */
    @FXML
    Button cancel;

    /**
     * boolean for if a part has been added successfully.
     */
    private boolean partAddedSuccess = false;

    /**
     * boolean to confirm the user wishes to cancel.
     */
    private boolean cancelConfirm = false;

    /**
     * boolean used to confirm the user wishes to save the part.
     */

    private boolean saveConfirm = false;

    /**
     * declares intial ID number for added parts.
     */

    private static int id = 300;

    /**
     * initialization method loads controller.
     * @param url used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */


    public void initialize(URL url, ResourceBundle resourceBundle) {
        inHouseRadio.setSelected(true);
        idTextField.setText(String.valueOf(idGenerator()+ 1));

    }

    /**
     * method for generating a unique part iD
     * @return unique Part ID.
     */

    public int idGenerator() {
        return id++;
    }


    /**
     * method that changes the InHouseOutsource text label to say "machine ID"
     * and set the radio buttons to reflect inHouse has been selected and outsource is not selected.
     * @param event in house radio button selected.
     * @throws IOException from FXML loader.
     */

    public void inHouseRadio(ActionEvent event) throws IOException {
        inHouseOutsource.textProperty().setValue("Machine ID");
        outSourceRadio.setSelected(false);
        inHouseRadio.setSelected(true);
        machineIdCompany.setPromptText("Machine ID #");
    }

    /**
     * method that changes the InHouseOutsource text label to say "company"
     * and set the radio buttons to reflect outsource has been selected and in house  is not selected.
     * @param event in outsource radio button selected.
     * @throws IOException from FXML loader.
     */

    public void outsourcedRadio(ActionEvent event) throws IOException {
        inHouseOutsource.textProperty().setValue("Company");
        inHouseRadio.setSelected(false);
        outSourceRadio.setSelected(true);
        machineIdCompany.setPromptText("Company Name");
    }

    /**
     * switches to main scene.
     * @param event cancel button is clicked.
     * @throws IOException from FXML loader.
     */

    public void switchToMain(ActionEvent event) throws IOException {
        callAlert(5);
        if (cancelConfirm == true) {
            root = FXMLLoader.load(getClass().getResource("/MainForm.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

    }


    /**
     * method adds part.
     * @param event save button clicked
     * @throws IOException from FXML loader.
     */
    public void addPart(ActionEvent event) throws IOException {

        try {
            //name text field is verified to not be empty.
            String name = partName.getText();
            if (name.isEmpty()) {
                //if empty a error alert will pop up telling the user to fill out the name text field.
                callAlert(7);
            }
            double price = Double.parseDouble(costPerUnit.getText());
            int stock = Integer.parseInt(inventory.getText());
            int min = Integer.parseInt(this.min.getText());
            int max = Integer.parseInt(this.max.getText());

            //min field is verified to be less than max and inventory to be between min and max.
            if (minMaxInventoryValidation(min, max, stock) == true && !name.isEmpty()) {

                // alert asking user if they are sure they want to save.
                callAlert(2);
                if(saveConfirm == true) {

                    //if in house is selected a InHouse part is created.
                    if (inHouseRadio.isSelected()) {
                        int machineId = Integer.parseInt(this.machineIdCompany.getText());
                        InHousePart newPart = new InHousePart(idGenerator(), name, price, stock, min, max, machineId);
                        Inventory.addPart(newPart);
                        if (Inventory.getAllParts().contains(newPart)) {
                            partAddedSuccess = true;
                            System.out.println(newPart + " added to parts list!");
                        }
                    }

                    //if outsource is selected an Outsource part is created.
                    if (outSourceRadio.isSelected()) {
                        String companyName = machineIdCompany.getText();
                        OutsourcedPart newPart = new OutsourcedPart(idGenerator(), name, price, stock, min, max, companyName);
                        Inventory.addPart(newPart);
                        if (Inventory.getAllParts().contains(newPart)) {
                            partAddedSuccess = true;
                            System.out.println(newPart + " added to parts list!");
                        }
                    }

                    //if part is successfully added to the all parts list the main screen is loaded.
                    if (partAddedSuccess) {
                        root = FXMLLoader.load(getClass().getResource("/MainForm.fxml"));
                        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                    }
                }
            }
            //generic alert telling the user to check all fields if there is an issue.
        } catch (Exception e) {
            callAlert(1);
        }

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
     * method with all alerts for various situations
     * @param alertType depending on the situation a switch case is used to call the appropriate alert.
     */

    public void callAlert(int alertType) {
        switch (alertType) {
            case (1) -> {
                Alert deleteNoneSelected = new Alert(Alert.AlertType.ERROR);
                deleteNoneSelected.setTitle("ERROR");
                deleteNoneSelected.setHeaderText("Error");
                deleteNoneSelected.setContentText("please select make sure 'InHouse or Outsourced are selected and all fields contain the correct data.");
                deleteNoneSelected.showAndWait();
                break;
            }
            case (2) -> {
                Alert addPartConfirm = new Alert(Alert.AlertType.CONFIRMATION);
                addPartConfirm.setTitle("WARNING");
                addPartConfirm.setHeaderText("ARE YOU SURE?");
                addPartConfirm.setContentText("please confirm you wish to add this part.");
                Optional<ButtonType> confirm = addPartConfirm.showAndWait();
                if (confirm.isPresent() && confirm.get() == ButtonType.OK) {
                    saveConfirm = true;
                }
                break;
            }
            case (3) -> {
                Alert minAndMaxField = new Alert(Alert.AlertType.ERROR);
                minAndMaxField.setTitle("ERROR");
                minAndMaxField.setHeaderText("Error");
                minAndMaxField.setContentText("please select make sure min field is less than max field");
                minAndMaxField.showAndWait();
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
                Alert cancelConfirm = new Alert(Alert.AlertType.CONFIRMATION);
                cancelConfirm.setTitle("WARNING");
                cancelConfirm.setHeaderText("ARE YOU SURE?");
                cancelConfirm.setContentText("please confirm you wish to cancel adding this part.");
                Optional<ButtonType> confirm = cancelConfirm.showAndWait();
                if (confirm.isPresent() && confirm.get() == ButtonType.OK) {
                    this.cancelConfirm = true;
                }
            }

            case (7) -> {
                Alert deleteNoneSelected = new Alert(Alert.AlertType.ERROR);
                deleteNoneSelected.setTitle("ERROR");
                deleteNoneSelected.setHeaderText("Error");
                deleteNoneSelected.setContentText("please enter a part name");
                deleteNoneSelected.showAndWait();
                break;
            }
        }
    }
}