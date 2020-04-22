import java.util.Vector;
import java.util.Random;
/**
 * Write a description of class Shoppers here.
 *
 * @author Cameron Costello, Kristi Boardman, Will Skelly, Jacob Burch
 * @version Spring 2020
 */
public class Jacob extends Shopper
{

    public static final int MORALITY_NUM =4;

    public static final int MAX_CASH = 600;

    public static final double JAIL_PROB = 20;
    /**
     * Constructor for objects of class Shoppers
     */
    public Jacob(ShoppingList item)
    {
        super(item);

        morality = MORALITY_NUM;
        cash = random.nextInt(MAX_CASH / MORALITY_NUM) + 1;
        jailedProb = random.nextInt((int) JAIL_PROB * MORALITY_NUM);
    }

    @Override
    public void run()
    {

    }
}
