package SupermarketShoppers;

import java.util.Vector;
import java.util.Random;

/**
 * Write a description of class Shoppers here.
 *
 * @author Cameron Costello, Kristi Boardman, Will Skelly, Jacob Burch
 * @version Spring 2020
 */
public class Jacob extends Shopper {

    public static final int MORALITY_NUM = 4;

    public static final int MAX_CASH = 1000;

    public static final int INCREASE_PROB = 5;

    public boolean startedStealing;

    public boolean startedSnitching;

    private Object lock = new Object();
    private Object jailProbLock = new Object();

    /**
     * Constructor for objects of class Shoppers
     */
    public Jacob(Vector<Item> shoppingList, Inventory inventory, int number, Jail jail, SupermarketManager supermarketManager) {
        super(shoppingList, inventory, number, jail, supermarketManager);

        morality = MORALITY_NUM;
        cash = random.nextInt(MAX_CASH / MORALITY_NUM) + 1;
        jailedProb = (random.nextDouble() * ONE_HUNDRED) * MORALITY_NUM;
    }

    @Override
    public void run() {
        shopperSleep();
        
        setMinimumPrice();

        int i = 0;
        while (!done) {
            Item currentItem = shoppingList.get(i);

            int index = inventory.containsItem(currentItem);

            Item itemToCheck = inventory.getList().get(index);

            int itemQuantity = currentItem.getItemQuantity();
            if (cash > 0) {

                int qPurchased = itemToCheck.attemptToBuy(itemQuantity, Double.MAX_VALUE);

                if (!startedStealing && qPurchased == 0) {
                    startedStealing = true;
                    increaseList();
                }

                if (!startedStealing) {
                    cash -= qPurchased * currentItem.getPrice();
                }
            }

            i++;
            supermarketManager.checkStealers();
            done = i >= shoppingList.size();
        }

        int j = 0;
        while (j < shoppingList.size()) {
            if (shoppingList.get(j).getItemQuantity() <= 0) {
                shoppingList.remove(j);
                j++;
            } else {
                j++;
            }
        }

        try {
            sleep(ONE_HUNDRED);
        } catch (InterruptedException e) {
            System.err.println(e);
        }
    }

    public boolean isStealing() {
        return startedStealing;
    }

    public boolean increaseJailProb() {
        if (startedStealing) {
            synchronized (jailProbLock) {
                if (jailedProb < ONE_HUNDRED) {
                    jailedProb += INCREASE_PROB;
                } else {
                    done = true;
                    jail.getArrested(this);
                    supermarketManager.removeShopper(shopperNumber);
                }
            }
        }
        return startedStealing;
    }

    @Override
    public String toString() {

        return "\n\nJacob's Shopping List\n"
                + "Shopper number " + shopperNumber + "\n"
                + "Cash left in wallet: " + cash + "\n"
                + "Items the shopper was unable to purchase:\n"
                + shoppingList.toString();

    }

}
