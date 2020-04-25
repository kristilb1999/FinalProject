package SupermarketShoppers;

import java.util.Vector;
import java.util.Random;
/**
 * Write a description of class Shoppers here.
 *
 * @author Cameron Costello, Kristi Boardman, Will Skelly, Jacob Burch
 * @version Spring 2020
 */
public class Cameron extends Shopper
{

    public static final int MORALITY_NUM = 2;

    public static final int MAX_CASH = 600;

    public static final double ONE_HUNDRED= 100;
    /**
     * Constructor for objects of class Shoppers
     */
    public Cameron(Vector<Item> shoppingList, Inventory inventory, int number)
    {
        super(shoppingList, inventory, number);

        morality = MORALITY_NUM;
        cash = random.nextInt(MAX_CASH / MORALITY_NUM) + 1;
        jailedProb = random.nextDouble() * MORALITY_NUM * ONE_HUNDRED;
        panicShopping = (random.nextDouble() * ONE_HUNDRED) / MORALITY_NUM;
        
    }

    @Override
    public void run()
    {

    }
    
    @Override
    public String toString(){
        
        String toPrint = "Cameron's Shopping List:\n";
        toPrint += "Shopper number: " + shopperNumber + "\n";
        toPrint += shoppingList.toString();
        return toPrint;
        
    }
}

