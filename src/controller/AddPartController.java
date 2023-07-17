package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.InHouse;
import static model.Inventory.getAllParts;
import model.Outsourced;

/** Opens Add Part Window.
 *
 * @author Ladd Gillies JR
 */

public class AddPartController implements Initializable {
    @FXML private RadioButton outsourced;
    @FXML private Label inhouseoroutsourced;
    @FXML private TextField Name;
    @FXML private TextField Inventory;
    @FXML private TextField Price;
    @FXML private TextField Maximum;
    @FXML private TextField Minimum;
    @FXML private TextField companyORmachineID;
    private Stage stage;
    private Object scene;


    /** Add part window opens.
     * 
     * RUNTIME ERROR - originally I had a run time error with the radio button as 
     * I was trying to have the program open up a new window for each of the selections, 
     * in-house and outsourced. I correct this by just changing the text of the final input field.
     * 
     */
    public void radioadd()
    {
        if (outsourced.isSelected())
            this.inhouseoroutsourced.setText("Company Name");
        else
            this.inhouseoroutsourced.setText("Machine ID");
    }
    
    /** This method calls the cancel action.
     * 
     * @param event action to cancel adding a part, will lose any entered information
     * @throws IOException 
     */
    @FXML public void onActionCancel(ActionEvent event) throws IOException {
        if(MainFormController.confirmDialog("Cancel?", "Are you sure?")) {
            stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
            stage.setTitle("Inventory Management System");
            stage.setScene(new Scene((Parent) scene));
            stage.show();
        }
    }
    
    /** This method calls the save part.
     * 
     * @param event cast input to correct variables and saves them in the table.
     */
    @FXML public void onActionSave(ActionEvent event) {
        try {
            int partInventory = Integer.parseInt(Inventory.getText());
            int partMin = Integer.parseInt(Minimum.getText());
            int partMax = Integer.parseInt(Maximum.getText());
            if(MainFormController.confirmDialog("Save?", "Would you like to save this part?"))
            if(partMax < partMin) {
                MainFormController.infoDialog("Input Error", "Error in min and max field", "Check Min and Max value." );
            }
            else if(partInventory < partMin || partInventory> partMax) {
                MainFormController.infoDialog("Input Error", "Error in inventory field", "Inventory must be between Minimum and Maximum" );
            }
            else{
                int newID = getNewID();
                String name = Name.getText();
                int stock = partInventory;
                double price = Double.parseDouble(Price.getText());
                int min = partMin;
                int max = partMax;
                if (outsourced.isSelected()) {
                    String companyName = companyORmachineID.getText();
                    Outsourced temp = new Outsourced(newID, name, price, stock, min, max, companyName);
                    model.Inventory.addPart(temp);
                } else {
                    int machineID = Integer.parseInt(companyORmachineID.getText());
                    InHouse temp = new InHouse(newID, name, price, stock, min, max, machineID);
                    model.Inventory.addPart(temp);
                }
                stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
                stage.setTitle("Inventory Management System");
                stage.setScene(new Scene((Parent) scene));
                stage.show();
            }
        }
        catch (IOException | NumberFormatException e)
        {
            MainFormController.infoDialog("Input Error", "Error in adding part", "Check fields for correct input" );
        }
    }

    /** This method calls the new ID.
     * 
     * @return returns new ID
     */
    public static int getNewID()
    {
        int newID = 1;
        for (Object allPart : getAllParts()) {
            newID++;
        }
        return newID;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
}