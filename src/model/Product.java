package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** Class for Product.
 *
 * @author Ladd Gillies Jr
 */
public class Product {
    
   
    private ObservableList<Part> associatedPart = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     * 
     * @param id Get product id.
     * @param name Get product name.
     * @param price Get product price.
     * @param stock Get product stock.
     * @param min Get product min.
     * @param max Get product max.
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }
    
    
    public Product()
    {
        this(0, null, 0.00, 0, 0, 0);
    }

    /** Calls get ID for product.
     * 
     * @return returns product ID
     */
    public int getId() 
    {
        return id;
    }

    /** Calls set ID for product.
     * 
     * @param id Get product ID to set.
     */
    public void setId(int id) 
    {
        this.id = id;
    }

    /** Calls get name for product.
     * 
     * @return Returns product name.
     */
    public String getName() 
    {
        return name;
    }

    /** Calls set name for product.
     * 
     * @param name get product name to set.
     */
    public void setName(String name) 
    {
        this.name = name;
    }

    /** Calls get price for product.
     * 
     * @return Returns product price.
     */
    public double getPrice() 
    {
        return price;
    }

    /** Calls set price for product.
     * 
     * @param price Get product price to set.
     */
    public void setPrice(double price) 
    {
        this.price = price;
    }

    /** Calls get stock for product.
     * 
     * @return Returns product stock.
     */
    public int getStock() 
    {
        return stock;
    }

    /** Calls set stock for product.
     * 
     * @param stock Get product stock to set.
     */
    public void setStock(int stock) 
    {
        this.stock = stock;
    }

    /** Calls get min for prodcut.
     * 
     * @return Returns product min.
     */
    public int getMin() 
    {
        return min;
    }

    /** Calls set min for product.
     * 
     * @param min Get product min to set.
     */
    public void setMin(int min) 
    {
        this.min = min;
    }

    /** Calls get max for product.
     * 
     * @return Returns product max.
     */
    public int getMax() 
    {
        return max;
    }

    /** Calls set max for product.
     * 
     * @param max Get product max to set.
     */
    public void setMax(int max) 
    {
        this.max = max;
    }

    /** Calls the observable list with associated parts.
     * 
     * @return Returns associated parts list.
     */
    public ObservableList<Part> getAssociatedPart() 
    {
        return associatedPart;
    }

    /** Calls set associated part for product.
     * 
     * @param associatedPart set associated part to a product.
     */
    public void setAssociatedPart(ObservableList<Part> associatedPart) 
    {
        this.associatedPart = associatedPart;
    }
    
    /** Call add associated part for a product.
     * 
     * @param part get and add a part to an associated product.
     */
    public void addAssociatedPart(ObservableList<Part> part)
    {
        this.associatedPart.addAll(part);
    }
    
    /** Call get associated part for a product.
     * 
     * @return Returns all associated parts for a product.
     */
    public ObservableList<Part> getAllAssociatedParts()
    {
        return associatedPart;
    }
    
    
    
}
