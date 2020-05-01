package SupermarketShoppers;

import java.util.Vector;
import java.util.Random;

/**
 * An abstract class of people who will be going shopping. Includes many basic
 * methods shared by the different types of shoppers.
 *
 * @author Cameron Costello, Kristi Boardman, Will Skelly, Jacob Burch
 * @version Spring 2020
 */
abstract public class Shopper extends Thread {

    protected Inventory inventory;

    protected Jail jail;

    protected Vector<Item> shoppingList;

    protected Random random;

    protected double cash;

    protected double jailedProb;

    protected int morality;

    protected boolean done;

    protected int shopperNumber;

    protected double minimumPrice;

    protected SupermarketManager supermarketManager;

    /**
     * Constructor for objects of class Shoppers
     */
    public Shopper(Vector<Item> shoppingList, Inventory inventory, int number, Jail jail, SupermarketManager supermarketManager) {
        shopperNumber = number;
        this.inventory = inventory;
        this.shoppingList = shoppingList;
        this.jail = jail;
        this.supermarketManager = supermarketManager;
        random = new Random();
        minimumPrice = Double.MAX_VALUE;
    }

    abstract public void run();

    public boolean done() {
        return done;
    }

    public double getCash() {
        return cash;
    }

    public void setCash(double cash) {
        this.cash = cash;
    }

    public double getJailedProb() {
        return jailedProb;
    }

    public void setJailedProb(double jailedProb) {
        this.jailedProb = jailedProb;
    }

    public int getMorality() {
        return morality;
    }

    public void setMorality(int morality) {
        this.morality = morality;
    }

    public Vector<Item> getShoppingList() {
        return shoppingList;
    }

    protected void increaseList() {
        int numItemsToAdd = random.nextInt(3) + 1;
        for (int k = 0; k < numItemsToAdd; k++) {
            Item itemToAdd = inventory.getList().get(random.nextInt(inventory.getList().size()));
            itemToAdd.setQuantity(random.nextInt(5) + 1);
            shoppingList.add(itemToAdd);
        }
    }

    protected void setMinimumPrice() {
        for (Item item : shoppingList) {
            System.out.println(item.getPrice());
            minimumPrice = (item.getPrice() < minimumPrice
                    ? item.getPrice() : minimumPrice);
        }
    }

    protected double getMinimumPrice(){
        return minimumPrice;
    }
}
