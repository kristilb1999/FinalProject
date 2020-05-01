package SupermarketShoppers;

import java.util.ConcurrentModificationException;
import java.util.Random;

import java.util.Vector;

/**
 * Starts the simulation and passes in the data used.
 *
 * @author Cameron Costello, Kristi Boardman, Will Skelly, Jacob Burch
 * @version Spring 2020
 */
public class SupermarketManager extends Thread {

    private static final int DELAY_TIME = 33;

    private static final int WILL = 1;
    private static final int CAMERON = 2;
    private static final int KRISTI = 3;
    private static final int JACOB = 4;

    private Random random;

    private final int MAX_PEOPLE = 20;
    private int numShoppers;

    private Vector<Shopper> shoppers;

    private Inventory inventory;

    private Jail jail;

    private boolean done;

    private Object shoppersLock = new Object();

    SupermarketManager(Inventory itemsInStore, Jail jail) {
        this.inventory = itemsInStore;
        this.jail = jail;
        this.shoppers = new Vector<Shopper>();
        this.random = new Random();
    }

    public void run() {
        numShoppers = random.nextInt(MAX_PEOPLE) + 1;
        int shopType;
        int shopperID = 0;
        Shopper newShopper;

        for (int i = 0; i < numShoppers; i++) {

            Vector<Item> newList = new Vector<Item>();

            Vector<Item> inventoryList = inventory.getList();

            int numOfItems;
            int itemIndex;

            shopType = random.nextInt(4) + 1;
            switch (shopType) {
                case WILL:
                    numOfItems = random.nextInt(7) + 1;

                    for (int j = 0; j < numOfItems; j++) {
                        itemIndex = random.nextInt(inventoryList.size());
                        Item itemToAdd = inventoryList.get(itemIndex);
                        itemToAdd.setQuantity(random.nextInt(10) + 1);
                        newList.add(itemToAdd);
                    }

                    newShopper = new Will(newList, inventory, shopperID, jail, this);
                    break;
                case CAMERON:
                    numOfItems = random.nextInt(7) + 1;

                    for (int j = 0; j < numOfItems; j++) {
                        itemIndex = random.nextInt(inventoryList.size());
                        Item itemToAdd = inventoryList.get(itemIndex);
                        itemToAdd.setQuantity(random.nextInt(10) + 1);
                        newList.add(itemToAdd);
                    }

                    newShopper = new Cameron(newList, inventory, shopperID, jail, this);
                    break;
                case KRISTI:
                    numOfItems = random.nextInt(7) + 1;

                    for (int j = 0; j < numOfItems; j++) {
                        itemIndex = random.nextInt(inventoryList.size());
                        Item itemToAdd = inventoryList.get(itemIndex);
                        itemToAdd.setQuantity(random.nextInt(10) + 1);
                        newList.add(itemToAdd);
                    }

                    newShopper = new Kristi(newList, inventory, shopperID, jail, this);
                    break;
                case JACOB:
                    numOfItems = random.nextInt(7) + 1;

                    for (int j = 0; j < numOfItems; j++) {
                        itemIndex = random.nextInt(inventoryList.size());
                        Item itemToAdd = inventoryList.get(itemIndex);
                        itemToAdd.setQuantity(random.nextInt(10) + 1);
                        newList.add(itemToAdd);
                    }

                    newShopper = new Jacob(newList, inventory, shopperID, jail, this);

                    break;
                default:
                    numOfItems = random.nextInt(7) + 1;

                    for (int j = 0; j < numOfItems; j++) {
                        itemIndex = random.nextInt(inventoryList.size());
                        Item itemToAdd = inventoryList.get(itemIndex);
                        itemToAdd.setQuantity(random.nextInt(10) + 1);
                        newList.add(itemToAdd);
                    }

                    newShopper = new Will(newList, inventory, shopperID, jail, this);
            }
            newShopper.start();
            synchronized (shoppersLock) {
                this.shoppers.add(newShopper);
            }
            shopperID++;
        }

        while (!done) {
            int numDone = 0;

            synchronized (shoppersLock) {
                for (Shopper customer : shoppers) {
                    if (customer.done()) {
                        numDone++;
                    }
                }
            }

            if (numDone == numShoppers) {
                this.done = true;
            }

            try {
                sleep(250);
            } catch (InterruptedException e) {
                System.err.println(e);
            }
        }

    }

    public boolean done() {
        return this.done;
    }

    public Vector<Shopper> getShoppers() throws ConcurrentModificationException {
        if (!done) {
            throw new ConcurrentModificationException();
        }
        return this.shoppers;
    }

    public int getNumShoppers() {
        synchronized (shoppersLock) {
            return this.shoppers.size();
        }
    }

    public void removeShopper(int shopperToRemove) {
        synchronized (shoppersLock) {
            for (int i = 0; i < this.shoppers.size(); i++) {
                if (shopperToRemove == this.shoppers.get(i).shopperNumber) {
                    this.shoppers.remove(i);
                }

            }
        }
    }

    @Override
    public String toString() {
        return "Number of shoppers in the store: " + this.shoppers.size() + "\n"
                + this.shoppers.toString();

    }

    public void checkStealers() {
        ShopperSnitchingVisitor snitching = new ShopperSnitchingVisitor();
        synchronized (shoppersLock) {
            for (Shopper shopper : shoppers) {
                shopper.accept(snitching);
            }
        }
    }
}
