package SupermarketShoppers;

import java.util.Vector;
import java.util.Random;
/**
 * Write a description of class Shoppers here.
 *
 * @author Cameron Costello, Kristi Boardman, Will Skelly, Jacob Burch
 * @version Spring 2020
 */
public class Kristi extends Shopper
{

    public static final int MORALITY_NUM = 3;

    public static final int MAX_CASH = 1000;

    public static final int INCREASE_PROB = 5;

    public static final int SENT_TO_JAIL = 65;
    
    public static final int ONE_HUNDRED = 100;

    public boolean startedStealing;

    private Object lock = new Object();

    /**
     * Constructor for objects of class Shoppers
     */
    public Kristi(Vector<Item> shoppingList, Inventory inventory, int number, Jail jail, SupermarketManager supermarket)
    {
        super(shoppingList, inventory, number, jail, supermarket);

        morality = MORALITY_NUM;
        cash = random.nextInt(MAX_CASH / MORALITY_NUM) + 1;
        jailedProb = (random.nextDouble() * ONE_HUNDRED) * MORALITY_NUM;
    }

    @Override
    public void run()
    {
        try{
                sleep(500);
            } catch (InterruptedException e){
                System.err.println(e);
            } 
        
        int i = 0;
        int listSize = shoppingList.size();
        while(!done) {
            Item currentItem = shoppingList.get(i);

            int index = inventory.containsItem(currentItem);

            if(index > -1){
                synchronized (lock) {
                    Item itemToCheck = inventory.getList().get(index);

                    int itemQuantity = currentItem.getItemQuantity();
                    int numInInventory = itemToCheck.getItemQuantity();

                    if (numInInventory >= itemQuantity && i > listSize)
                    {
                        startedStealing = true;
                        itemToCheck.updateQuantity(itemQuantity);
                        currentItem.setQuantity(0);
                        shoppingList.remove(i); 
                    }else if (numInInventory < itemQuantity && i > listSize){
                        startedStealing = true;
                        itemToCheck.setQuantity(0);
                        currentItem.updateQuantity(numInInventory);
                    }
                    else if(numInInventory >= itemQuantity){
                        itemToCheck.updateQuantity(itemQuantity);
                        cash -= itemQuantity * currentItem.getPrice();
                        currentItem.setQuantity(0);
                        shoppingList.remove(i);
                    }else{
                        cash -= numInInventory * currentItem.getPrice();
                        itemToCheck.setQuantity(0);
                        currentItem.updateQuantity(numInInventory);
                    }
                }

            }else{
                int numItemsToAdd = random.nextInt(3) + 1;
                for(int k = 0; k < numItemsToAdd; k++) {
                    Item itemToAdd = inventory.getList().get(random.nextInt(inventory.getList().size()));
                    itemToAdd.setQuantity(random.nextInt(5) + 1);
                    shoppingList.add(itemToAdd);
                }  
            }

            i++;

            done = shoppingList.isEmpty() || i >= shoppingList.size();

        }
        
        try{
                sleep(ONE_HUNDRED);
            } catch (InterruptedException e){
                System.err.println(e);
            } 

    }

    public boolean isStealing()
    {
        return startedStealing;
    }

    public void increaseJailProb()
    {
        if(jailedProb < SENT_TO_JAIL)
        {
            jailedProb += INCREASE_PROB;
        }
        else
        {
            done = true;
            jail.getArrested(this);
            supermarket.removeShopper(shopperNumber);
        }
    }

    @Override
    public String toString(){

        return "Kristi's Shopping List\n" +
        "Shopper number " + shopperNumber + "\n" +
        "Cash left in wallet: " + cash +
        shoppingList.toString() + "\n";

    }
}
