package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** Class for the Inventory.
 *
 * @author Ladd Gillies
 */
public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

   /** Calls add new part method
    * 
    * @param newPart Add new part.
    */
    public static void addPart(Part newPart)
    {
        allParts.add(newPart);
    }

   
    /**
     * 
     * @return all available or added parts
     */
    public static ObservableList<Part> getAllParts() 
    {
        return allParts;
    }

    
    /** Calls add product.
     * 
     * @param newProduct Add new product.
     */
    public static void addProduct(Product newProduct)
    {
        allProducts.add(newProduct);
    }

    /** Calls get all products.
     * 
     * @return All available products.
     */
    public static ObservableList<Product> getAllProducts() 
    {
        return allProducts;
    }

    /** Calls look up part by ID.
     * 
     * @param partID get part id from input and send to search method .
     * @return if found returns part ID that was in search bar.
     */
    
    public static Part lookupPartID(int partID) 
    {
        Part temp = null;
        for (Part parts : allParts) {
            if (partID == parts.getId()){
                temp = parts;
            }
        }
        return temp;
    }

    /** Calls look up product by ID.
     * 
     * @param productID get product id from input and send to search method.
     * @return if found returns product ID that was in search bar.
     */
    
    public static Product lookupProductID(int productID) 
    {
        Product temp = null;
        for (Product product : allProducts){
            if (productID == product.getId()){
                temp = product;
            }
        }
        return temp;
    }


    /** Calls search by name.
     * 
     * @param searchPartName get part name from input and sends to search.
     * @return if found returns part that was in the search bar.
     */
    public static ObservableList lookupPart(String searchPartName)
    {
        ObservableList<Part> foundParts = FXCollections.observableArrayList();

        if(searchPartName.length() == 0) {
            foundParts = allParts;
        }
        else {
            for (int i = 0; i < allParts.size(); i++) 
            {
                if (allParts.get(i).getName().toLowerCase().contains(searchPartName.toLowerCase())) {
                    foundParts.add(allParts.get(i));
                }
            }
        }

        return foundParts;
    }

    /** Calls look up product.
     * 
     * @param searchProductName get product name and sends to search.
     * @return if found returns product name that was in search bar.
     */
    public static ObservableList lookupProduct(String searchProductName)
    {
        ObservableList<Product> foundProducts = FXCollections.observableArrayList();

        if(searchProductName.length() == 0) {
            foundProducts = allProducts;
        }
        else {
            for (int i = 0; i < allProducts.size(); i++) 
            {
                if (allProducts.get(i).getName().toLowerCase().contains(searchProductName.toLowerCase())) {
                    foundProducts.add(allProducts.get(i));
                }
            }
        }

        return foundProducts;
    }

    /** Calls update part.
     * 
     * @param index get input to find correct part.
     * @param selectedPart get input for selected part to updating.
     */
    public static void updatePart(int index, Part selectedPart)
    {
        allParts.set(index, selectedPart);
    }

    /** Calls update product.
     * 
     * @param index get input to find correct product.
     * @param newProduct get input to find correct product.
     */
    public static void updateProduct(int index, Product newProduct)
    {
        allProducts.set(index, newProduct);
    }

    /** Calls delete part.
     * 
     * @param selectedPart get input for selected part to remove.
     * @return returns part to be removed from list.
     */
    public static boolean deletePart(Part selectedPart)
    {
        return allParts.remove(selectedPart);
    }

    /** Calls delete product.
     * 
     * @param selectedProduct get input for selected product to remove.
     * @return returns product to be removed from list.
     */
    public static boolean deleteProduct(Product selectedProduct)
    {
        return allProducts.remove(selectedProduct);
    }
}