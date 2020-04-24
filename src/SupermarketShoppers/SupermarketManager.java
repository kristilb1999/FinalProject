package SupermarketShoppers;
import java.util.Random;

import java.util.Vector;
/**
 * Starts the simulation and passes in the data used.
 *
 * @author Cameron Costello, Kristi Boardman, Will Skelly, Jacob Burch
 * @version Spring 2020
 */
public class SupermarketManager extends Thread
{
    private static final int WILL = 1;
    private static final int CAMERON = 2;
    private static final int KRISTI = 3;
    private static final int JACOB = 4;

    private Random random = new Random();

    private final int MAX_PEOPLE = 20;
    private int numShoppers;

    private Vector<Shopper> shoppers;

    public void run(){
        numShoppers = random.nextInt(MAX_PEOPLE) + 1;
        int shopType;
        Shopper newShopper;
        for (int i = 0; i <numShoppers; i++){
            shopType = random.nextInt(4) + 1;
            switch(shopType){
                case WILL :
                newShopper = new Will();
                break;
                case CAMERON :
                newShopper = new Cameron();
                break;
                case KRISTI :
                newShopper = new Kristi();
                break;
                case JACOB :
                newShopper = new Jacob();
                break;
                default :
                newShopper = new Will();
            }
            shoppers.add(newShopper);
        }

    }

    public static void main(String args[]){

    }
}
