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
    
    private int storeNumber;

    private boolean done;

    /**
     * Constructor for objects of class Supermarket
     */
    public Supermarket(int storeNumber)
    {
        itemsInStore = new Inventory();
        itemsInStore.readInItems();
        storeManager = new SupermarketManager(itemsInStore, jail);
        jail = new Jail();
        this.storeNumber = storeNumber;
    }

    public void run() {

        storeManager.start();
        
        if(storeManager.done()){
            toString();
            jail.toString();
            done = true;
        }

    }
    
    public boolean done() {
        return done;
    }

    public String toString()
    {
        return "Supermarket " + storeNumber + "\n" + storeManager.toString();
    }

    
}
