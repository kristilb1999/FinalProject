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

    public static final int MAX_CASH = 1000;

    public static final int ONE_HUNDRED = 100;

    public boolean panicking;

    private Object lock = new Object();
    /**
     * Constructor for objects of class Shoppers
     */
    public Cameron(Vector<Item> shoppingList, Inventory inventory, int number, Jail jail, SupermarketManager supermarket)
    {
        super(shoppingList, inventory, number, jail, supermarket);

        morality = MORALITY_NUM;
        cash = random.nextInt(MAX_CASH / MORALITY_NUM) + 1;

    }

    @Override
    public void run()
    {

        int i = 0;
        while(!done && i < shoppingList.size()) {
            Item currentItem = shoppingList.get(i);

            int index = inventory.containsItem(currentItem);

            synchronized (lock) {
                Item itemToCheck = inventory.getList().get(index);

                int itemQuantity = currentItem.getItemQuantity();
                int numInInventory = itemToCheck.getItemQuantity();

                if(cash > 0) {
                    if(numInInventory > 0) {
                        if(numInInventory >= itemQuantity){
                            itemToCheck.updateQuantity(itemQuantity);
                            cash -= itemQuantity * currentItem.getPrice();
                            currentItem.setQuantity(0);
                        }else{
                            cash -= numInInventory * currentItem.getPrice();
                            itemToCheck.setQuantity(0);
                            currentItem.updateQuantity(numInInventory);
                        }
                    } else {
                        panicking = true;
                        int numItemsToAdd = random.nextInt(3) + 1;
                        for(int k = 0; k < numItemsToAdd; k++) {
                            Item itemToAdd = inventory.getList().get(random.nextInt(inventory.getList().size()));
                            itemToAdd.setQuantity(random.nextInt(5) + 1);
                            shoppingList.add(itemToAdd);

                        } 
                    }
                }else{
                    cash = 0;
                }
            }

            i++;
            done = shoppingList.isEmpty() || i >= shoppingList.size() || cash <= 0;
        }
        int j = 0;
        while(j < shoppingList.size()) {
            if(shoppingList.get(j).getItemQuantity() <= 0) {
                shoppingList.remove(j);
            }else{
                j++;
            }
        }

        try{
            sleep(ONE_HUNDRED);
        } catch (InterruptedException e){
            System.err.println(e);
        } 
    }

    public boolean isPanicking()
    {
        return panicking;
    }

    @Override
    public String toString(){

        return 
        "\n\nCameron's Shopping List\n" +
        "Shopper number " + shopperNumber + "\n" +
        "Cash left in wallet: " + cash + "\n" +
        "Items the shopper was unable to purchase:\n" +
        shoppingList.toString();

    }
}

