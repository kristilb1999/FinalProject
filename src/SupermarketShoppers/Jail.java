package SupermarketShoppers;

import java.util.Vector;
/**
 * This class creates a jail for shoppers. Once Shoppers are in the jail, they cannot get out.
 * The class counts the number of shoppers in jail and outputs it as a toString.
 *
 * @author Cameron Costello, Kristi Boardman, Will Skelly, Jacob Burch.
 * @version Spring 2020
 */
public class Jail
{
    //A LIST FOR THE SHOPPERS IN JAIL
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
     
    public int getNumInJail() {
        //RETURN THE NUMBER OF SHOPPERS IN JAIL
        return shoppersInJail.size();
    }
    
    @Override
    public String toString()
    {
        //RETURN THE STRING FOR THE SHOPPERS IN JAIL
        return 
            "Shoppers in Jail:\n" +
            "Number of criminals: " + shoppersInJail.size() + "\n" +
            shoppersInJail.toString() + "\n";
    }

}
