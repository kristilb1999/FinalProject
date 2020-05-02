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
    
    private int numCustomers;
    
    private int numStealing;
    
    private int numPanicShopping;

    private SupermarketManager storeManager;

    private Jail jail;

    private int storeNumber;

    private boolean done;
    
    private static final int DELAY_TIME = 250;

    /**
     * Constructor for objects of class Supermarket
     * @param storeNumber
     */
    public Supermarket(int storeNumber)
    {
        this.itemsInStore = new Inventory();
        this.jail = new Jail();
        this.storeManager = new SupermarketManager(itemsInStore, jail);
        this.storeNumber = storeNumber;
    }

    @Override
    public void run() {

        this.storeManager.start();
        
        while(!done) {
            if(storeManager.done()){
                numCustomers = storeManager.getNumShoppers();
                
                shoppers = storeManager.getShoppers();
                
                numStealing = jail.getNumInJail();
                
                ShopperStealingVisitor stealing = new ShopperStealingVisitor();
                ShopperPanickingVisitor panicking = new ShopperPanickingVisitor();
                
                for(Shopper shopper : shoppers) {
                    if(shopper.accept(stealing)) {
                            numStealing++;
                    }
                    if(shopper.accept(panicking)) {
                            numPanicShopping++;
                    }
                }
                
                this.done = true;
            }
            
            try{
                sleep(DELAY_TIME);
            } catch (InterruptedException e){
                System.err.println(e);
            } 
        }

    }
    
    public int getNumStealing() {
        return numStealing;
    }
    
    public int getNumPanicShopping() {
        return numPanicShopping;
    }

    public boolean done() {
        return this.done;
    }
     
    public int numShoppersInJail() {
        return jail.getNumInJail();
    }
    
    public int getNumCustomers() {
        return numCustomers;
    }
    
    public int getStoreNumber() {
        return storeNumber;
    }

    @Override
    public String toString()
    {
        return "\nSupermarket " + this.storeNumber + "\n" + this.storeManager.toString() +
            "\n\n " + this.jail.toString();
    }

}
