package SupermarketShoppers;

import java.util.Vector;
/**
 * Write a description of class Jail here.
 *
 * @author Cameron Costello, Kristi Boardman, Will Skelly, Jacob Burch.
 * @version Spring 2020
 */
public class Jail extends Thread
{
    
    public static final int JAIL_TIME = 20;
    
    private Vector<Shopper> shoppersInJail;
    
    private int timeInJail;
    
    private boolean done;
    
    /**
     * Constructor for objects of class Jail
     */
    public Jail()
    {
        shoppersInJail = new Vector<Shopper>();
    }
    
    public void run()
    {
        
    }
    
    public void getArrested(Shopper criminal) 
    {
        shoppersInJail.add(criminal);
    }
    
    private boolean done()
    {
        return done;
    }
    
    @Override
    public String toString()
    {
        String toPrint = "Shoppers in Jail:\n";
        toPrint += shoppersInJail.toString();
        return toPrint;
    }

}
