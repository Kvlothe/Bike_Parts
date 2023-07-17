package model;
/** Class for part.
 *
 * @author Ladd Gillies JR
 */
public abstract class Part 
{
    
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;    
    
    /**
     * 
     * @param id Get part id.
     * @param name Get part name.
     * @param price Get part price. 
     * @param stock Get part stock.
     * @param min Get part min.
     * @param max Get part max.
     */
    public Part(int id, String name, double price, int stock, int min, int max) 
    {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * @return Returns the part id.
     */
    public int getId() {
        return id;
    }

    /**
     * @param id get part id to set.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return Returns the part name.
     */
    public String getName() {
        return name;
    }

    /**
     * @param name get part name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return Returns the part price.
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price get part price to set.
     */
    public void setPrice(double price) {
        this.price = price;
    }
    
    /**
     * @return Returns the part stock.
     */
    public int getStock() {
        return stock;
    }

    /**
     * @param stock get part stock to set.
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * @return Returns the part min.
     */
    public int getMin() {
        return min;
    }

    /**
     * @param min get part min to set.
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * @return Returns the part max.
     */
    public int getMax() {
        return max;
    }

    /**
     * @param max get max to set.
     */
    public void setMax(int max) {
        this.max = max;
    }
    
}