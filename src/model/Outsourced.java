package model;

/** Class for the Outsourced part.
 *
 * @author Ladd Gillies Jr
 */
public class Outsourced extends Part{
    
    /**
     * 
     */
    private String companyName;
    
    /**
     * 
     * @param id Get id.
     * @param name Get name.
     * @param price Get price.
     * @param stock Get stock.
     * @param min Get min.
     * @param max Get max.
     * @param companyName Get company name.
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) 
    {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /** Calls get company name.
     * 
     * @return Returns company name.
     */
    public String getCompanyName() {
        return companyName;
    }

    /** Calls set company name.
     * 
     * @param companyName get company name from input.
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    
    
    
}
