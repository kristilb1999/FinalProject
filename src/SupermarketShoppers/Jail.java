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
    
    /**
     * Add a shopper to the jail.
     * 
     * @param Shopper criminal. The shopper that is 
     *        going to be arrested.
     */
    public void getArrested(Shopper criminal) 
    {
        shoppersInJail.add(criminal);
    }
     
    /**
     * Return the number of shoppers in jail.
     * 
     * @return The amount of shoppers in jail.
     */
    public int getNumInJail() {
        //RETURN THE NUMBER OF SHOPPERS IN JAIL
        return shoppersInJail.size();
    }
    
    /**
     * Gives the information about the shoppers in jail.
     * 
     * @return The information of the jail.
     */
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
