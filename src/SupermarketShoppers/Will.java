package SupermarketShoppers;

import java.util.Vector;

/**
 * Write a description of class Shoppers here.
 *
 * @author Cameron Costello, Kristi Boardman, Will Skelly, Jacob Burch
 * @version Spring 2020
 */
public class Will extends Shopper {

    public static final int MORALITY_NUM = 1;

    public static final int MAX_CASH = 1000;

    public static final int ONE_HUNDRED = 100;

    public boolean startedSnitching;

    public boolean panicking;

    private Object lock = new Object();

    /**
     * Constructor for objects of class Shoppers
     */
    public Will(Vector<Item> shoppingList, Inventory inventory, int number, Jail jail, SupermarketManager supermarketManager) {
        super(shoppingList, inventory, number, jail, supermarketManager);

        morality = MORALITY_NUM;
        cash = random.nextInt(MAX_CASH / MORALITY_NUM) + 1;
    }

    @Override
    public void run() {
        setMinimumPrice();

        int i = 0;
        while (!done) {
            Item currentItem = shoppingList.get(i);

            int index = inventory.containsItem(currentItem);

            Item itemToCheck = inventory.getList().get(index);

            int itemQuantity = currentItem.getItemQuantity();
            if (cash > 0) {

                int qPurchased = itemToCheck.attemptToBuy(itemQuantity, cash);

                if (qPurchased == 0) {
                    panicking = true;
                    increaseList();
                }

                cash -= qPurchased * currentItem.getPrice();
            }

            i++;
            checkStealers();
            done = i >= shoppingList.size() || cash <= getMinimumPrice();

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

    public void checkStealers() {
        ShopperSnitchingVisitor snitching = new ShopperSnitchingVisitor();
        
        for(Shopper shopper : supermarketManager.getShoppers()){
            shopper.accept(snitching);
        }
    }

    public boolean isPanicking() {
        return panicking;
    }

    @Override
    public String toString() {

        return "\n\nWill's Shopping List\n"
                + "Shopper number " + shopperNumber + "\n"
                + "Cash left in wallet: " + cash + "\n"
                + "Items the shopper was unable to purchase:\n"
                + shoppingList.toString();

    }
}
