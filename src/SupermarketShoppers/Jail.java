package SupermarketShoppers;

import java.util.Vector;
/**
 * Write a description of class Jail here.
 *
 * @author Cameron Costello, Kristi Boardman, Will Skelly, Jacob Burch.
 * @version Spring 2020
 */
public class Jail
{
    private Vector<Shopper> shoppersInJail;
    
    /**
     * Constructor for objects of class Jail
     */
    public Jail()
    {
        shoppersInJail = new Vector<Shopper>();
    }
    
    public void getArrested(Shopper criminal) 
    {
        shoppersInJail.add(criminal);
    }
    
    @Override
    public String toString()
    {
        return 
            "Shoppers in Jail:\n" +
            "Number of criminals: " + shoppersInJail.size() + "\n" +
            shoppersInJail.toString() + "\n";
    }

}
