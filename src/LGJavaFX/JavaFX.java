package lgjavafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Inventory;
import model.InHouse;
import model.Outsourced;
import model.Product;


/**
 *
 * @author Ladd Gillies JR
 */

/** This is the main method. That gets called when the program runs
 * 
 * FUTURE ENHANCEMENT - I would add associated parts for each product already 
 * included in the main menu or product menu rather than having to go in and
 * add them after the program has started up. Could possibly add associated
 * pictures for each part and product.
 */
public class JavaFX extends Application
{

    @Override
    public void start(Stage stage) throws Exception
    {
        
        /** Data to populate parts in main menu. */
               
        Inventory.addPart(new InHouse(1, "Tire", 15.99, 3, 2, 1, 15));
        Inventory.addPart(new InHouse(2, "Rim", 12.99, 5, 2, 1, 20));
        Inventory.addPart(new InHouse(3, "Seat", 5.99, 1, 8, 6, 5));
        Inventory.addPart(new InHouse(4, "Basket", 7.99, 6, 3, 1, 4));
        Inventory.addPart(new InHouse(5, "Bell", 3.99, 2, 6, 4, 9));
        
        Inventory.addPart(new Outsourced(6, "Spinners", 99.99, 8, 5, 9, "Gigitty"));
        Inventory.addPart(new Outsourced(7, "Blow Horn", 99.99, 8, 5, 9, "Gigitty"));
        Inventory.addPart(new Outsourced(8, "Light Bar", 99.99, 8, 5, 9, "Gigitty"));
        Inventory.addPart(new Outsourced(9, "Boom Box", 99.99, 8, 5, 9, "Gigitty"));
        Inventory.addPart(new Outsourced(20, "Spiked Wheels", 99.99, 8, 5, 9, "Gigitty"));
        
        Inventory.addProduct(new Product(1, "Unicycle", 299.99, 2, 1, 10));
        Inventory.addProduct(new Product(2, "Monster Bike", 2299.99, 8, 3, 20));
        Inventory.addProduct(new Product(3, "Rocket Car", 299.99, 4, 2, 9));
        Inventory.addProduct(new Product(4, "Electric Bike", 199.99, 5, 1, 12));
        Inventory.addProduct(new Product(5, "Electic Car", 999.99, 2, 1, 11));
        
        
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
}
        public static void main(String[] args) 
        {
        launch(args);
    
    }
}