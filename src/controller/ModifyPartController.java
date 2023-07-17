package controller;

import model.Part;
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
import model.Outsourced;

/** This method calls up the modify part window.
 * 
 * @author komun
 */

public class ModifyPartController implements Initializable {

    @FXML private RadioButton outsourced;
    @FXML private RadioButton inHouse;
    @FXML private Label inhouseoroutsourced;
    @FXML private TextField ID;
    @FXML private TextField Name;
    @FXML private TextField Inventory;
    @FXML private TextField Price;
    @FXML private TextField Maximum;
    @FXML private TextField Minimum;
    @FXML private TextField companyORmachineID;

    private Stage stage;
    private Object scene;
    public Part selectedPart;
    private int partID;

    /** This method calls the radio button.
     *  for switching between in-house or outsourced
     */
    public void radioadd()
    {
        if (outsourced.isSelected())
            this.inhouseoroutsourced.setText("Company Name");
        else
            this.inhouseoroutsourced.setText("Machine ID");
    }

    
    /** This method calls the set part.
     * 
     * @param selectedPart add a selected part to a product in inventory
     */
    public void setParts(Part selectedPart) 
    {
        this.selectedPart = selectedPart;
        partID = model.Inventory.getAllParts().indexOf(selectedPart);
        ID.setText(Integer.toString(selectedPart.getId()));
        Name.setText(selectedPart.getName());
        Inventory.setText(Integer.toString(selectedPart.getStock()));
        Price.setText(Double.toString(selectedPart.getPrice()));
        Maximum.setText(Integer.toString(selectedPart.getMax()));
        Minimum.setText(Integer.toString(selectedPart.getMin()));
        if(selectedPart instanceof InHouse ih)
        {
            inHouse.setSelected(true);
            this.inhouseoroutsourced.setText("Machine ID");
            companyORmachineID.setText(Integer.toString(ih.getMachineId()));
        }
        else
        {
           Outsourced os = (Outsourced) selectedPart;
            outsourced.setSelected(true);
            this.inhouseoroutsourced.setText("Company Name");
            companyORmachineID.setText(os.getCompanyName());
        }
    }

    /** This method calls the save.
     * 
     * @param event action to save the modified part.
     * @throws IOException 
     */
    @FXML void onActionSave(ActionEvent event) throws IOException 
    {
        int partInventory = Integer.parseInt(Inventory.getText());
        int partMin = Integer.parseInt(Minimum.getText());
        int partMax = Integer.parseInt(Maximum.getText());
        if (MainFormController.confirmDialog("Save?", "Would you like to save this part?"))
            if (partMax < partMin) {
                MainFormController.infoDialog("Input Error", "Error in min and max field", "Check Min and Max value.");
            } 
            else if (partInventory < partMin || partInventory > partMax) 
            {
                MainFormController.infoDialog("Input Error", "Error in inventory field", "Inventory must be between Minimum and Maximum");
            } 
            else 
            {
                int id = Integer.parseInt(ID.getText());
                String name = Name.getText();
                double price = Double.parseDouble(Price.getText());
                int stock = Integer.parseInt(Inventory.getText());
                int min = Integer.parseInt(Minimum.getText());
                int max = Integer.parseInt(Maximum.getText());
                if (inHouse.isSelected()) {
                    try {
                        int machineID = Integer.parseInt(companyORmachineID.getText());
                        InHouse temp = new InHouse(id,name, price, stock, min, max, machineID);
                        model.Inventory.updatePart(partID, temp);
                        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                        scene = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
                        stage.setTitle("Inventory Management System");
                        stage.setScene(new Scene((Parent) scene));
                        stage.show();
                }
                    catch (NumberFormatException e)
                    {
                        MainFormController.infoDialog("Input Error", "Check Machine ID ", "Machine ID can only contain numbers 0-9");
                    }
                }
                else {
                    String companyName = companyORmachineID.getText();
                    Outsourced temp = new Outsourced(id, name, price, stock, min, max, companyName);
                    model.Inventory.updatePart(partID, temp);
                    stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
                    stage.setTitle("Inventory Management System");
                    stage.setScene(new Scene((Parent) scene));
                    stage.show();
                }
            }
    }
    
    /** This method calls the cancel.
     * 
     * @param event action to cancel modify part, information will be lost.
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
}