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

    private boolean done;

    /**
     * Constructor for objects of class Supermarket
     */
    public Supermarket()
    {
        itemsInStore = new Inventory();
        itemsInStore.readInItems();
        storeManager = new SupermarketManager(itemsInStore, jail);
        jail = new Jail();
    }

    public void run() {

        storeManager.start();

    }

    public String toString()
    {
        return "Supermarket1 " + storeManager.toString();
    }

    public static void main(String args[]){
        java.awt.EventQueue.invokeLater(new Supermarket());

    }
}
