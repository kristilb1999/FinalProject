package SupermarketShoppers;

import java.util.Vector;
/**
 * 
 *
 * @author Cameron Costello, Kristi Boardman, Will Skelly, Jacob Burch.
 * @version Spring 2020
 */
public class Supermarket extends Thread
{
    private Inventory itemsInStore;
    
    private Vector<Shopper> shoppers;
    
    private SupermarketManager storeManager;
    
    private Jail jail;

    /**
     * Constructor for objects of class Supermarket
     */
    public Supermarket()
    {
        itemsInStore = new Inventory();
        itemsInStore.readInItems();
        storeManager = new SupermarketManager(itemsInStore);
    }
    
    public void run() {
        
    }
    
//    public boolean containsItem(Item other){
//        return itemsInStore.contains(other);
//    }
    
    public static void main(String args[]){
        
    }
}
