package SupermarketShoppers;

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

    public static final int MORALITY_NUM = 4;

    public static final int MAX_CASH = 1000;

    public static final int INCREASE_PROB = 5;

    public static final int ONE_HUNDRED = 100;

    public boolean startedStealing;

    public boolean startedSnitching;

    private Object lock = new Object();
    /**
     * Constructor for objects of class Shoppers
     */
    public Jacob(Vector<Item> shoppingList, Inventory inventory, int number, Jail jail, SupermarketManager supermarketManager)
    {
        super(shoppingList, inventory, number, jail, supermarketManager);

        morality = MORALITY_NUM;
        cash = random.nextInt(MAX_CASH / MORALITY_NUM) + 1;
        jailedProb = (random.nextDouble() * ONE_HUNDRED) * MORALITY_NUM;
    }

    @Override
    public void run()
    {

        int i = 0;
        int listSize = shoppingList.size();
        while(!done && i < shoppingList.size()) {
            Item currentItem = shoppingList.get(i);

            int index = inventory.containsItem(currentItem);

            synchronized (lock) {
                Item itemToCheck = inventory.getList().get(index);

                int itemQuantity = currentItem.getItemQuantity();
                int numInInventory = itemToCheck.getItemQuantity();

                if(numInInventory > 0) {
                    if(cash > 0) {
                        if (numInInventory >= itemQuantity && i > listSize)
                        {
                            startedStealing = true;
                            itemToCheck.updateQuantity(itemQuantity);
                            currentItem.setQuantity(0);
                        }else if (numInInventory < itemQuantity && i > listSize){
                            startedStealing = true;
                            itemToCheck.setQuantity(0);
                            currentItem.updateQuantity(numInInventory);
                        }
                        else if(numInInventory >= itemQuantity){
                            itemToCheck.updateQuantity(itemQuantity);
                            cash -= itemQuantity * currentItem.getPrice();
                            currentItem.setQuantity(0);
                        }else{
                            cash -= numInInventory * currentItem.getPrice();
                            itemToCheck.setQuantity(0);
                            currentItem.updateQuantity(numInInventory);
                        }
                    }
                    if (cash <= 0)
                    {
                        cash = 0;
                    }
                } else {
                    startedStealing = true;
                    int numItemsToAdd = random.nextInt(3) + 1;
                    for(int k = 0; k < numItemsToAdd; k++) {
                        Item itemToAdd = inventory.getList().get(random.nextInt(inventory.getList().size()));
                        itemToAdd.setQuantity(random.nextInt(5) + 1);
                        shoppingList.add(itemToAdd);
                    }  
                }

            }  

            i++;
            checkStealers();
            done = shoppingList.isEmpty() || i >= shoppingList.size();

        }
        int j = 0;
        while(j < shoppingList.size()) {
            if(shoppingList.get(j).getItemQuantity() <= 0) {
                shoppingList.remove(j);
                j++;
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

    public boolean isStealing()
    {
        return startedStealing;
    }

    public void checkStealers() {
        ShopperSnitchingVisitor snitching = new ShopperSnitchingVisitor();
        for (int i = 0; i < supermarketManager.getShoppers().size(); i++) {
            if (snitching.visit(supermarketManager.getShoppers().get(i))) {
                startedSnitching = true;
            }
        }
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
            supermarketManager.removeShopper(shopperNumber);
        }
    }

    @Override
    public String toString(){

        return
        "\n\nJacob's Shopping List\n" +
        "Shopper number " + shopperNumber + "\n" +
        "Cash left in wallet: " + cash + "\n" +
        "Items the shopper was unable to purchase:\n" +
        shoppingList.toString();

    }

}
