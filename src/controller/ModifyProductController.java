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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Product;


/** This method calls the modify product window.
 * 
 * @author komun
 */
public class ModifyProductController implements Initializable {

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

    //Modify Fields
    @FXML private TextField Name;
    @FXML private TextField Inventory;
    @FXML private TextField Price;
    @FXML private TextField Maximum;
    @FXML private TextField Minimum;
    @FXML private TextField ID;

    //Other Buttons/Fields
    @FXML private TextField SearchField;
    private Product selectedProduct;
    private Product Product;
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int productID;

    /** This method calls the set product.
     * 
     * @param selectedProduct to set new information into correct fields
     *  for modifying a product
     */
    public void setProduct(Product selectedProduct) {
        this.selectedProduct = selectedProduct;
        productID = model.Inventory.getAllProducts().indexOf(selectedProduct);
        ID.setText(Integer.toString(selectedProduct.getId()));
        Name.setText(selectedProduct.getName());
        Inventory.setText(Integer.toString(selectedProduct.getStock()));
        Price.setText(Double.toString(selectedProduct.getPrice()));
        Maximum.setText(Integer.toString(selectedProduct.getMax()));
        Minimum.setText(Integer.toString(selectedProduct.getMin()));
        associatedParts.addAll(selectedProduct.getAllAssociatedParts());
    }

    /** This method calls the search.
     * 
     * @param event action tp search for a part within a specific product.
     */
    @FXML public void searchPartButton(ActionEvent event) {
        String term = SearchField.getText();
        ObservableList foundParts = model.Inventory.lookupPart(term);
        if(foundParts.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setTitle("NO MATCH");
            alert.setHeaderText("Unable to locate a Part name with: " + term);
            alert.showAndWait();
        } else {
            PartsTableView.setItems(foundParts);
        }
    }
    
    /** This method calls the cancel method.
     * 
     * @param event action to cancel modify product, information will be lost.
     * @throws IOException 
     */

    @FXML public void modifyProductCancel(ActionEvent event) throws IOException {
        if (MainFormController.confirmDialog("Cancel?", "Are you sure?")) {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
            stage.setTitle("Inventory Management System");
            stage.setScene(new Scene((Parent) scene));
            stage.show();
        }
    }

    /** This method calls the save method.
     * 
     * @param event action to save current parts to a product. 
     * @throws IOException 
     */
    @FXML void modifyProductSave(ActionEvent event) throws IOException 
    {
        int productInventory = Integer.parseInt(Inventory.getText());
        int productMinimum = Integer.parseInt(Minimum.getText());
        int productMaximum = Integer.parseInt(Maximum.getText());
        if(MainFormController.confirmDialog("Save?", "Would you like to save this part?"))
            if(productMaximum < productMinimum) {
                MainFormController.infoDialog("Input Error", "Error in min and max field", "Check Min and Max value." );
            }
            else if(productInventory < productMinimum || productInventory > productMaximum) {
                MainFormController.infoDialog("Input Error", "Error in inventory field", "Inventory must be between Minimum and Maximum" );
            }
            else 
            {
                this.Product = selectedProduct;
                selectedProduct.setId(Integer.parseInt(ID.getText()));
                selectedProduct.setName(Name.getText());
                selectedProduct.setStock(Integer.parseInt(Inventory.getText()));
                selectedProduct.setPrice(Double.parseDouble(Price.getText()));
                selectedProduct.setMax(Integer.parseInt(Maximum.getText()));
                selectedProduct.setMin(Integer.parseInt(Minimum.getText()));
                selectedProduct.getAssociatedPart().clear();
                selectedProduct.addAssociatedPart(associatedParts);
                model.Inventory.updateProduct(productID, selectedProduct);

                //Back to Main Screen
                stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
                stage.setTitle("Inventory Management System");
                stage.setScene(new Scene((Parent) scene));
                stage.show();
            }
        
    }

    /** This method calls the add method.
     * 
     * @param event action to add part to a selected or new product.
     */
    @FXML void addPartToProduct(ActionEvent event) 
    {
        Part selectedPart = PartsTableView.getSelectionModel().getSelectedItem();

        if(selectedPart != null) {
            associatedParts.add(selectedPart);
            updateAssociatedPartTable();
        }

        else 
        {
            MainFormController.infoDialog("Select a part","Select a part", "Select a part to add to the Product");
        }
    }

    /** This method calls the delete method.
     * 
     * @param event action to delete a part from a product.
     */
    @FXML
    void deletePartFromProduct(ActionEvent event) 
    {
        Part selectedPart = AssociatedPartsTableView.getSelectionModel().getSelectedItem();

        if(selectedPart != null) 
        {
            MainFormController.confirmDialog("Deleting Parts","Are you sure you want to delete " + selectedPart.getName() + " from the Product?");
            associatedParts.remove(selectedPart);
            updateAssociatedPartTable();
        }

        else 
        {
            MainFormController.infoDialog("No Selection","No Selection", "Please choose something to remove");
        }
    }

    /** This method calls the update method.
     * 
     */
    public void updatePartTable() 
    {
        PartsTableView.setItems(model.Inventory.getAllParts());
    }

    /** This calls the update associated part.
     * 
     */
    private void updateAssociatedPartTable() 
    {
        AssociatedPartsTableView.setItems(associatedParts);
    }

    
    /** 
     * 
     * @param url
     * @param rb 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {

        //Columns and Table for un-associated parts.
        PartsIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        PartsNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        PartsInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        PartsCostColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        PartsTableView.setItems(model.Inventory.getAllParts());

        //Columns and Table for associated parts
        AssociatedPartsIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        AssociatedPartsNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        AssociatedPartsInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        AssociatedPartsCostColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        AssociatedPartsTableView.setItems(associatedParts);

        updatePartTable();
        updateAssociatedPartTable();
    }
}
