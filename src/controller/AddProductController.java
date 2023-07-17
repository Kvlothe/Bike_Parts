package controller;

import model.Part;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Product;

/** Add Product Window opens. */

public class AddProductController implements Initializable {

    private Stage stage;
    private Object scene;

    //Un Associated Parts Table
   
    @FXML private TableView<Part> PartsTableView;
    @FXML private TableColumn<Part, Integer> PartsIDColumn;
    @FXML private TableColumn<Part, String> PartsNameColumn;
    @FXML private TableColumn<Part, Integer> PartsInventoryColumn;
    @FXML private TableColumn<Part, Double> PartsCostColumn;

    //Associated Parts Table
    @FXML private TableView<Part> AssociatedPartsTableView;
    @FXML private TableColumn<Product, Integer> AssociatedPartsIDColumn;
    @FXML private TableColumn<Product, String> AssociatedPartsNameColumn;
    @FXML private TableColumn<Product, Integer> AssociatedPartsInventoryColumn;
    @FXML private TableColumn<Product, Double> AssociatedPartsCostColumn;

    //Add Fields
    @FXML private TextField Name;
    @FXML private TextField Inventory;
    @FXML private TextField Price;
    @FXML private TextField Maximum;
    @FXML private TextField Minimum;

    //Other Buttons/Fields
    @FXML private TextField SearchField;
    
    
    private ObservableList<Part> associatedPart = FXCollections.observableArrayList();
    private ObservableList<Part> originalParts = FXCollections.observableArrayList();

    // Search part in Product window
    @FXML public void searchPartButton(ActionEvent event) 
    {
        String term = SearchField.getText();
        ObservableList foundParts = model.Inventory.lookupPart(term);
        if(foundParts.isEmpty()) {
            MainFormController.confirmDialog("No Match", "Unable to locate a Part name with: " + term);
        } 
        else 
        {
            PartsTableView.setItems(foundParts);
        }
    }
    
    // Add part to product window

    @FXML void addPartToProduct(ActionEvent event) 
    {
        Part selectedPart = PartsTableView.getSelectionModel().getSelectedItem();

        if(selectedPart != null) 
        {
            associatedPart.add(selectedPart);
            updatePartTable();
            updateAssociatedPartTable();
        }

        else 
        {
            MainFormController.infoDialog("Select a part","Select a part", "Select a part to add to the Product");
        }
    }
    
    /** This method calls the delete part from product.
     * 
     * @param event action to delete the selected part from the product menu. 
     */

    @FXML
    void deletePartFromProduct(ActionEvent event) 
    {
        Part selectedPart = AssociatedPartsTableView.getSelectionModel().getSelectedItem();

        if(selectedPart != null) 
        {
            MainFormController.confirmDialog("Deleting Parts","Are you sure you want to delete " + selectedPart.getName() + " from the Product?");
            associatedPart.remove(selectedPart);
            updatePartTable();
            updateAssociatedPartTable();
            }

        else 
        {
            MainFormController.infoDialog("No Selection","No Selection", "Please choose something to remove");
        }
    }

    
    /** This method calls the cancel.
     * 
     * @param event action that will cancel the add product to menu.
     * @throws IOException 
     */
    @FXML public void cancelAddProduct(ActionEvent event) throws IOException 
    {
        if (MainFormController.confirmDialog("Cancel?", "Are you sure?")) 
        {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
            stage.setTitle("Inventory Management System");
            stage.setScene(new Scene((Parent) scene));
            stage.show();
        }
    }

    /** This method calls the save.
     * 
     * @param event action to save the current product with added parts.
     * @throws IOException 
     */
    @FXML void saveProduct(ActionEvent event) throws IOException 
    {

        //Test if valid product
        if (associatedPart.isEmpty()){
            MainFormController.infoDialog("Input Error", "Please add one ore more parts", "A product must have one or more parts associated with it.");
            return;
        } 
        else 
        {
        }

        if (this.Name.getText().isEmpty() || Minimum.getText().isEmpty() || Maximum.getText().isEmpty() || Maximum.getText().isEmpty() || Price.getText().isEmpty()) {
            MainFormController.infoDialog("Input Error", "Cannot have blank fields", "Check all the fields.");
            return;
        }

        Integer inv = Integer.parseInt(this.Inventory.getText());
        Integer min = Integer.parseInt(this.Minimum.getText());
        Integer max = Integer.parseInt(this.Maximum.getText());

        if (max < min) 
        {
            MainFormController.infoDialog("Input Error", "Error in min and max field", "Check Min and Max value.");
            return;
        }

        if (inv < min || inv > max) 
        {
            MainFormController.infoDialog("Input Error", "Error in inventory field", "Inventory must be between Minimum and Maximum");
            return;
        }

        //Add Product
        if (MainFormController.confirmDialog("Save?", "Would you like to save this part?")) 
        {
            Product product = new Product();
            product.setId(getNewID());
            product.setName(this.Name.getText());
            product.setStock(Integer.parseInt(this.Inventory.getText()));
            product.setMin(Integer.parseInt(this.Minimum.getText()));
            product.setMax(Integer.parseInt(this.Maximum.getText()));
            product.setPrice(Double.parseDouble(this.Price.getText()));
            product.addAssociatedPart(associatedPart);
            model.Inventory.addProduct(product);

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
            stage.setTitle("Inventory Management System");
            stage.setScene(new Scene((Parent) scene));
            stage.show();
        }

    }


    /** This method calls the new id.
     * 
     * @return new id.
     */
    private int getNewID()
    {
        int newID = 1;
        for (int i = 0; i < model.Inventory.getAllProducts().size(); i++) {
            if (model.Inventory.getAllProducts().get(i).getId() == newID) {
                newID++;
            }
        }
        return newID;
    }

    /** This method calls the update part menu.
     * 
     */
    public void updatePartTable() 
    {
        PartsTableView.setItems(model.Inventory.getAllParts());
    }

    /** this method calls the update associated part.
     * 
     */
    private void updateAssociatedPartTable() 
    {
        AssociatedPartsTableView.setItems(associatedPart);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {

        //originalParts = Inventory.getAllParts();

        //Columns and Table for un-associated parts.
        PartsIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        PartsNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        PartsInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        PartsCostColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        PartsTableView.setItems(originalParts);

        //Columns and Table for associated parts
        AssociatedPartsIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        AssociatedPartsNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        AssociatedPartsInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        AssociatedPartsCostColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        AssociatedPartsTableView.setItems(associatedPart);

        updatePartTable();
        updateAssociatedPartTable();
    }
}