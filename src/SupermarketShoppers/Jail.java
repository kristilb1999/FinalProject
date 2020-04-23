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

    }
    
    public void run()
    {
        
    }
    
    private boolean done()
    {
        return done;
    }

}
