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

    public static final double ONE_HUNDRED = 100;

    public boolean startedStealing;
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
        System.out.println(toString());
        int i = 0;
        int listSize = shoppingList.size();
        while(!done) {
            Item currentItem = shoppingList.get(i);

            int index = inventory.containsItem(currentItem);

            if(index > -1){

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

            }else{
                int numItemsToAdd = random.nextInt(3) + 1;
                for(int k = 0; k < numItemsToAdd; k++) {
                    Item itemToAdd = inventory.getList().get(random.nextInt(inventory.getList().size()));
                    itemToAdd.setQuantity(random.nextInt(5) + 1);
                    shoppingList.add(itemToAdd);
                }  
            }

            i++;

            done = shoppingList.isEmpty() || i > shoppingList.size();

        }

        System.out.println(toString());

    }

    public boolean isStealing()
    {
        return startedStealing;
    }

    public void increaseJailProb()
    {
        if(jailedProb < ONE_HUNDRED)
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

        String toPrint = "Kristi's Shopping List:\n";
        toPrint += "Shopper number: " + shopperNumber + "\n";
        //toPrint += shoppingList.toString();
        return toPrint;

    }
}
