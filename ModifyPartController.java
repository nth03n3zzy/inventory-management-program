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
 * class sets up logic and control for the modify part page.
 *
 * @author  Anthony Collins.
 */
public class ModifyPartController implements Initializable {
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
     * label for text field that changes between machine ID and company name depending on if the part is outsourced or in   house.
     */
    @FXML
    Label inHouseOutsource;
    /**
     * radio button to select if the part is an in house part.
     */
    @FXML
    RadioButton inHouseRadio;
    /**
     * radio button to select if the part is an outsourced part.
     */
    @FXML RadioButton outSourceRadio;

    /**
     * text field for part ID.
     */
    @FXML TextField id;
    /**
     * text field used for machine ID or company name depending on if the part is in house or outsource.
     */
    @FXML
    TextField machineIdCompany;
    @FXML
     TextField name;
    /**
     * text field used for the current quantity instock.
     */
    @FXML  TextField inventory;
    /**
     * text field used for the price per unit.
     */
    @FXML TextField price;
    /**
     * text field used for minimum quantity allowed in stock.
     */
    @FXML  TextField min;
    /**
     * text field used for the maximum quantity allowed in stock.
     */
    @FXML  TextField max;

    /**
     * button used as the save button
     */
    @FXML
    Button saveButton;

    /**
     * boolean used to confirm user intends to cancel the changes they have made.
     */
    private boolean cancelConfirm = false;

    /**
     * boolean used to determin the user intends to save changes made to the part.
     */
    private boolean saveConfirm = false;

    /**
     * declares a boolean that is used to veify the name field is not empty.
     */
    private boolean nameIsNotEmpty = false;

    /**
     * declares a part that will be used as the part that is loaded into the modify part scene.
     */

    private static Part loadPart;

    /**
     * method used to set the selected part to be modified from the main screen to the [art that is loaded in the modify part screen.
     * @param part part that is passed to the method.
     */
    public static void setPart(Part part){
        loadPart = part;
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
     * initialization method loads controller and populates fields with the part that was selected.
     * @param url used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    public void initialize(URL url, ResourceBundle resourceBundle){
        /*
          setting the part in the initializer fixed the null pointer exception i was getting when setting the text fields
          before I was calling the set part method in the ModifyPart button click method in the main controller.
          im not sure why this fixed the issue im sure it has something to with when the initializer is called and implemented
          but my smooth brain is clueless and just happy it worked after 8 hours of troubleshooting and googling.
         */
        //loads the part as the selected part
        setPart(MainController.modifyPart);

        //fields are filed out with the parts info.
        if(loadPart instanceof InHousePart){
            inHouseRadio.setSelected(true);
            outSourceRadio.setSelected(false);
            machineIdCompany.setText(String.valueOf(((InHousePart) loadPart).getMachineId()));
            inHouseOutsource.textProperty().setValue("Machine ID");
        }
        if (loadPart instanceof OutsourcedPart){
            outSourceRadio.setSelected(true);
            inHouseRadio.setSelected(false);
            machineIdCompany.setText((((OutsourcedPart) loadPart).getCompanyName()));
            inHouseOutsource.textProperty().setValue("Company");
        }


        name.setText((loadPart).getName());
        inventory.setText(String.valueOf(loadPart.getStock()));
        min.setText(String.valueOf(loadPart.getMin()));
        max.setText(String.valueOf(loadPart.getMax()));
        price.setText(String.valueOf(loadPart.getPrice()));
        id.setText(String.valueOf(loadPart.getId()));
    }

    /**
     * logic method for verifiying all fields are correct when the save button is clicked.
     * @param event save button being clicked.
     * @throws IOException from FXML loader.
     */

    public void save(ActionEvent event)throws IOException{
        //checks if name field is filled out.
        if(name.getText().isEmpty()){
            //calls alert telling user to fill out name field.
            callAlert(7);
        }
        //if name is not empty the rest of the code executes.
        nameIsNotEmpty = true;
        // validates if min is less than max and inventory is between min and max.
        if (minMaxInventoryValidation(Integer.parseInt(min.getText()),
                Integer.parseInt(max.getText()),
                Integer.parseInt(inventory.getText()))) {

            //if min, max and inventory are good, name is  not empty outsourced or inhouse radio buttons are checked
            //and then the part is added as a new part and the original part that was loaded in is deleted from the
            //all parts list.
            if (outSourceRadio.isSelected() && (nameIsNotEmpty == true)) {
                OutsourcedPart modifiedPart = new OutsourcedPart(loadPart.getId(),
                        name.getText(),
                        Double.parseDouble(price.getText()),
                        Integer.parseInt(inventory.getText()) ,
                        Integer.parseInt(min.getText()),
                        Integer.parseInt(max.getText()),
                        machineIdCompany.getText());
                Inventory.deletePart(loadPart);
                Inventory.addPart(modifiedPart);

                //alert confirming the user wishes to save the modified part.
                callAlert(2);

                root = FXMLLoader.load(getClass().getResource("/MainForm.fxml"));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }

            if (inHouseRadio.isSelected() && (nameIsNotEmpty)) {
                InHousePart modifiedPart = new InHousePart(loadPart.getId(),
                        name.getText(),
                        Double.parseDouble(price.getText()),
                        Integer.parseInt(inventory.getText()) ,
                        Integer.parseInt(min.getText()),
                        Integer.parseInt(max.getText()),
                        Integer.parseInt(machineIdCompany.getText()));
                Inventory.deletePart(loadPart);
                Inventory.addPart(modifiedPart);
                callAlert(2);

                if(saveConfirm == true) {
                    root = FXMLLoader.load(getClass().getResource("Main Form.fxml"));
                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                }
            }
        }
        else
            // generic alert informing user to check all fields for correctness.
            callAlert(4);
    }



    /** switches to Main Scene upon hitting cancel button  calls on alert to confirm you intend to cancel.*/

    public void switchToMain(ActionEvent event) throws IOException {
        //alert called to verify user wishes to cancel.
        callAlert(5);
        //executed if cancel is confirmed.
        if(cancelConfirm == true) {
            root = FXMLLoader.load(getClass().getResource("/MainForm.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        }
    }

    /** method to make machine ID appear on label and text field when clicking the in house radio button
     * need to add functionality for data input into machine ID int
     */

    public void inHouseRadio(ActionEvent event) throws IOException{
        inHouseOutsource.textProperty().setValue("Machine ID");
        outSourceRadio.setSelected(false);
        inHouseRadio.setSelected(true);
        machineIdCompany.setPromptText("Machine ID #");
    }

    /** methaod to make company appear in text field and on label when clicking the outsource radio button
     * need to add functionality for data input into company String
     */

    public void outsourcedRadio(ActionEvent event) throws IOException {
        inHouseOutsource.textProperty().setValue("Company");
        inHouseRadio.setSelected(false);
        outSourceRadio.setSelected(true);
        machineIdCompany.setPromptText("Company Name");
    }

    /**
     * method containing all the various alerts that may get called depending on what action is occuring.
     * @param alertType dependent on the cause for the alert simple switch case used to call the different alerts.
     */
    public void callAlert(int alertType) {
        switch (alertType) {
            case (2) -> {
                Alert saveConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
                saveConfirmation.setTitle("WARNING");
                saveConfirmation.setHeaderText("ARE YOU SURE?");
                saveConfirmation.setContentText("please confirm you wish to modify this part.");
                Optional<ButtonType> confirm = saveConfirmation.showAndWait();
                if (confirm.isPresent() && confirm.get() == ButtonType.OK) {
                    saveConfirm = true;
                }
                break;
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
                Alert deleteConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
                deleteConfirmation.setTitle("WARNING");
                deleteConfirmation.setHeaderText("ARE YOU SURE?");
                deleteConfirmation.setContentText("please confirm you wish to cancel adding this part.");
                Optional<ButtonType> confirm = deleteConfirmation.showAndWait();
                if (confirm.isPresent() && confirm.get() == ButtonType.OK) {
                    cancelConfirm = true;
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
