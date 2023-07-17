package model;
/** Class for In-house parts.
 *
 * @author Ladd Gillies Jr
 */
public class InHouse extends Part{
    
    private int machineId;

    /**
     * 
     * @param id Get id.
     * @param name Get name.
     * @param price Get price.
     * @param stock Get stock.
     * @param min Get min.
     * @param max Get Max.
     * @param machineId Get machine ID.
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) 
    {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /** calls getMachineId
     * 
     * @return machine id
     */
    public int getMachineId() {
        return machineId;
    }

    /** Calls setMachineId method
     * 
     * @param machineId Set id
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
    
}
