package com.example.c482software1project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * main class used to run program. comments about a road block I had and future program improvements are contained in the following lines.
 *
 * @author Anthony Collins
 *
 *           I ran into a major road block setting up the modify part scene. to pass data of the selected part and then have that
 *           part initialize on the modify part scene. it took me about 4 hours to get it to work, I had to do alot of research,
 *           I even watched the "passing the football Webinar" and tried all three examples given. But for some reason I kept
 *           getting a "null pointer exception" for the text fields. what I was doing was I was declaring within the class
 *           a Part loadPart = MainController.modifyPart. as its own variable outside of the initialize method. once I moved
 *           the declaration to inside the initialization method it finally worked. I am not sure why the way I was trying to do it was
 *           not working, the only thing I could really come with up is that is probably a scope issue. The initialization method was
 *           running before that declaration of the "loadPart" was being run.
 *
 *           As for the future of this program and how to increase its functionality I have a few ideas. The first of which would be to
 *           add a feature where when a product or part is sold the change in inventory is automatically updated.
 *           the second would be to add a feature for ordering parts and products with an approval process, for example you would have
 *           user accounts with veriying levels of access. for instance a floor employee would only have access to update the inventory of
 *           parts and products and order new parts and products. a shift manager would have the authorization to approve part and product
 *           orders and update inventory levels as well as being able to change associated parts. and then a store manager will be the
 *           final approver on all part and product orders, as well they would have the authorization to change minimum and maximum
 *           quantites allowed in stock and prices. the program would interact with a databse to save all changes amd tracking.
 *
 *
 */

public class   Main extends Application {

    /**
     * initial method to start program and launch main window.
     * @param stage
     * @throws Exception
     */

    @Override
    public void start(Stage stage) throws Exception {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/MainForm.fxml"));
            Scene scene = new Scene(root);
            stage.setTitle("Inventory Management System");
            stage.setScene(scene);
            stage.show();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * main method launches program when ran and instantiates example data.
     * @param args
     */
    public static void main(String[] args) {
        Product bicycle = new Product(3,"schwinn Bike", 150,20,4,200);
        Inventory.addProduct(bicycle);
        Product nikeTirePump = new Product(1,"Tire Pump", 15,15,5,20);
        Inventory.addProduct(nikeTirePump);
        Product pumaShoes = new Product(2,"cycling shoes",90,10,3,40);
        Inventory.addProduct(pumaShoes);

        InHousePart screw = new InHousePart( 2, "screw", 30,300,3,3000,1000);
        OutsourcedPart chair = new OutsourcedPart(3, "chair", 30,2,1,10, "Big Chair Company");
        InHousePart brakes = new InHousePart(4,"Race Brakes", 90.04,40,20,100,10901);
        OutsourcedPart shoeLaces = new OutsourcedPart(5, "Shoe laces", .20,100,90,1000, "puma");
        Inventory.addPart(brakes);
        Inventory.addPart(shoeLaces);
        Inventory.addPart(screw);
        Inventory.addPart(chair);
        launch(args);

    }
}
