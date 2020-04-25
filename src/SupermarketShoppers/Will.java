package SupermarketShoppers;

import java.util.Vector;
import java.util.Random;
/**
 * Write a description of class Shoppers here.
 *
 * @author Cameron Costello, Kristi Boardman, Will Skelly, Jacob Burch
 * @version Spring 2020
 */
public class Will extends Shopper
{

    public static final int MORALITY_NUM = 1;

    public static final int MAX_CASH = 600;

    public static final double ONE_HUNDRED= 100;
    /**
     * Constructor for objects of class Shoppers
     */
    public Will(Vector<Item> shoppingList)
    {
        super(shoppingList);

        morality = MORALITY_NUM;
        cash = random.nextInt(MAX_CASH / MORALITY_NUM) + 1;
        jailedProb = (random.nextDouble() * ONE_HUNDRED) * MORALITY_NUM;
        panicShopping = (random.nextDouble() * ONE_HUNDRED) / MORALITY_NUM;
    }

    @Override
    public void run()
    {

    }
}
