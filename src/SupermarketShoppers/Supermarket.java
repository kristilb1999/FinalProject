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
        this.itemsInStore = new Inventory();
        this.itemsInStore.readInItems();
        this.jail = new Jail();
        this.storeManager = new SupermarketManager(itemsInStore, jail);
        this.storeNumber = storeNumber;
        System.out.println("Supermarket has been created. " + this.storeNumber);
    }

    public void run() {

        this.storeManager.start();
        System.out.println("Store thread has started.");
        
        // try{
                // sleep(250);
            // } catch (InterruptedException e){
                // System.err.println(e);
            // } 
        
        while(!done) {
            System.out.println("Are you reaching this point?");
            if(storeManager.done()){
                System.out.println("Store thread is finished. done == true");
                System.out.println(this.toString());
                System.out.println(this.jail.toString());
                this.done = true;
            }
            
            try{
                sleep(250);
            } catch (InterruptedException e){
                System.err.println(e);
            } 
        }

    }

    public boolean done() {
        return this.done;
    }

    public String toString()
    {
        return "Supermarket " + this.storeNumber + "\n" + this.storeManager.toString();
    }

}
