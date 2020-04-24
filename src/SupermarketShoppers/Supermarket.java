package SupermarketShoppers;

import java.util.Vector;
/**
 * 
 *
 * @author Cameron Costello, Kristi Boardman, Will Skelly, Jacob Burch.
 * @version Spring 2020
 */
public class Supermarket
{
    private Inventory itemsInStore;
    
    private Vector<Shopper> shoppers;
    
    private Jail jail;

    /**
     * Constructor for objects of class Supermarket
     */
    public Supermarket()
    {
        itemsInStore = new Inventory();
        itemsInStore.readInItems();
    }
    public boolean containsItem(Item other){
        boolean hasIt = false;
        return itemsInStore.contains(other);
    }
}
