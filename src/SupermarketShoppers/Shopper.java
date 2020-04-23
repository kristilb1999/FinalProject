package SupermarketShoppers;

import java.util.Vector;
import java.util.Random;
/**
 * Write a description of class Shoppers here.
 *
 * @author Cameron Costello, Kristi Boardman, Will Skelly, Jacob Burch
 * @version Spring 2020
 */
abstract public class Shopper extends Thread
{

    protected Vector<Item> shoppingList;

    protected Random random;

    protected double cash;

    protected double jailedProb;
    
    protected double panicShopping;

    protected int morality;

    protected boolean done;

    /**
     * Constructor for objects of class Shoppers
     */
    public Shopper(ShoppingList item)
    {
        shoppingList = new Vector<Item>();
        new Random();
    }

    abstract public void run();

    public boolean done()
    {
        return done;
    }

    public double getCash()
    {
        return cash;
    }

    public void setCash(double cash)
    {
        this.cash = cash;
    }

    public double getJailedProb()
    {
        return jailedProb;
    }

    public void setJailedProb(double jailedProb)
    {
        this.jailedProb = jailedProb;
    }

    public int getMorality()
    {
        return morality;
    }

    public void setMorality(int morality)
    {
        this.morality = morality;
    }

    public Vector<Item> getShoppingList()
    {
        return shoppingList;
    }

}
