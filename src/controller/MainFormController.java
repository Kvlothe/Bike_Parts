
package controller;

import model.Part;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Inventory;
import model.Product;

/** this method brings up the first view able window that will serve as my main form */

public class MainFormController implements Initializable {

    /** Parts Table
     * 
     */
    @FXML private TableView<Part> partsTableView;
    @FXML private TableColumn<Part, Integer> partIDColumn;
    @FXML private TableColumn<Part, String> partNameColumn;
    @FXML private TableColumn<Part, Integer> partInvLevelColumn;
    @FXML private TableColumn<Part, Double> partCostColumn;
    @FXML private TextField partSearchArea;

    /** Products Table
     * 
     */
    @FXML private TableView<Product> productsTableView;
    @FXML private TableColumn<Product, Integer> productIDColumn;
    @FXML private TableColumn<Product, String> productNameColumn;
    @FXML private TableColumn<Product, Integer> productInvLevelColumn;
    @FXML private TableColumn<Product, Double> productCostColumn;
    @FXML private TextField productSearchArea;

    
    private Parent scene;

     /** This method calls the add part window.
      * 
      * @param event switches window.
      * @throws IOException 
      */
    public void addpartbuttonpushed(ActionEvent event) throws IOException 
    {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddPart.fxml"));
        stage.setTitle("Add Part");
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** This method calls the Modify Part window
     * 
     * @param event switches menu.
     */
    public void modifypartbuttonpushed(ActionEvent event){
        try {
            Part selectedPart = partsTableView.getSelectionModel().getSelectedItem();
            if (selectedPart == null) {
                return;
            }
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ModifyPart.fxml"));
            scene = loader.load();
            ModifyPartController controller = loader.getController();
            controller.setParts(selectedPart);
            stage.setTitle("Modify Part");
            stage.setScene(new Scene(scene));
            stage.show();

        } 
        catch (IOException e) 
        {
            
        }
    }
    
    /** This method calls the modify product window
     * 
     * @param event switches menu.
     */

    public void modifyproductbuttonpushed(ActionEvent event){
        try {
            Product selectedProduct = productsTableView.getSelectionModel().getSelectedItem();
            if (selectedProduct == null) {
                return;
            }
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ModifyProduct.fxml"));
            scene = loader.load();
            ModifyProductController controller = loader.getController();
            controller.setProduct(selectedProduct);
            stage.setTitle("Modify Product");
            stage.setScene(new Scene(scene));
            stage.show();

        } 
        catch (IOException e) 
        {
            
        }
    }
    
    /** Search part button.
     @param event call search part window.
     */

    public void searchPartButton(ActionEvent event) 
    {
        String term = partSearchArea.getText();
        ObservableList foundParts = Inventory.lookupPart(term);
        if(foundParts.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setTitle("No Match");
            alert.setHeaderText("Unable to locate a part name with: " + term);
            alert.showAndWait();
        } 
        else 
        {
            partsTableView.setItems(foundParts);
        }
    }

    /** Search product button
     @param event call search product.
     */
    public void searchProductButton(ActionEvent event) 
    {
        String term = productSearchArea.getText();
        ObservableList foundProducts = Inventory.lookupProduct(term);
        if(foundProducts.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setTitle("No Match");
            alert.setHeaderText("Unable to locate a product name with: " + term);
            alert.showAndWait();
        } 
        else 
        {
            productsTableView.setItems(foundProducts);
        }
    }

    
    /** This method calls the delete part window.
     * 
     * @param event switches window.
     */
    public void deletePartButton(ActionEvent event) 
    {
        if (partsTableView.getSelectionModel().isEmpty())
        {
            infoDialog("Warning!", "No Part Selected","Please choose part from the above list");
            return;
        }
        if (confirmDialog("Warning!", "Would you like to delete this part?"))
        {
            int selectedPart = partsTableView.getSelectionModel().getSelectedIndex();
            partsTableView.getItems().remove(selectedPart);
        }
    }
    
    /** Delete product button.
     * 
     * @param event to call up delete product window.
     * Will check to make sure associated parts are removed 
     */

    public void deleteProductButton(ActionEvent event) 
    {

        Product selectedProduct = productsTableView.getSelectionModel().getSelectedItem();

        if (selectedProduct == null) {
            infoDialog("Please make a selection","Nothing Selected","Try Again");
        } else {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alert");
            alert.setContentText("Do you want to delete the selected product?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {

                ObservableList<Part> assocParts = selectedProduct.getAllAssociatedParts();

                if (assocParts.size() >= 1) {
                    infoDialog("Cannot Delete","Associated Parts","Please remove Parts First");
                } else {
                    Inventory.deleteProduct(selectedProduct);
                }
            }
        }
    }
    
    
    
    /** This method displays a confirm message.
     * 
     * @param title
     * @param content message to ask for confirmation on event
     * @return 
     */

    static boolean confirmDialog(String title, String content)
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText("Confirm");
        alert.setContentText(content);
        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.OK;
    }
    
    /** This method calls the information window
     * 
     * @param title 
     * @param header
     * @param content 
     */

    static void infoDialog(String title, String header, String content)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
    
    /** this method calls the add product button.
     * 
     * @param event action to add the product to the product menu
     * @throws IOException 
     */

    @FXML public void addProductButtonPushed(ActionEvent event) throws IOException 
    {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddProduct.fxml"));
        stage.setTitle("Add Product");
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** This method displays exit message.
     * 
     * @param event Exit message.
     * @throws IOException 
     */
    @FXML public void exitButton(ActionEvent event) throws IOException
    {
        confirmDialog("Exit", "Are you sure you would like to exit the program?");
        {
                System.exit(0);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        partsTableView.setItems(Inventory.getAllParts());
        partIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partCostColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        productsTableView.setItems((Inventory.getAllProducts()));
        productIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInvLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productCostColumn.setCellValueFactory(new PropertyValueFactory<>("price"));


    }

}