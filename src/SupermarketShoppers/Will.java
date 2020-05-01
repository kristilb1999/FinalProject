package SupermarketShoppers;

import java.util.Vector;
/**
 * Write a description of class Shoppers here.
 *
 * @author Cameron Costello, Kristi Boardman, Will Skelly, Jacob Burch
 * @version Spring 2020
 */
public class Will extends Shopper
{
    public static final int MORALITY_NUM = 1;

    public static final int MAX_CASH = 1000;

    public static final double ONE_HUNDRED = 100;

    public boolean startedSnitching;
    
    public boolean panicking;

    private Object lock = new Object();

    /**
     * Constructor for objects of class Shoppers
     */
    public Will(Vector<Item> shoppingList, Inventory inventory, int number, Jail jail, SupermarketManager supermarket)
    {
        super(shoppingList, inventory, number, jail, supermarket);

        morality = MORALITY_NUM;
        cash = random.nextInt(MAX_CASH / MORALITY_NUM) + 1;
        panicShopping = (random.nextDouble() * ONE_HUNDRED) / MORALITY_NUM;
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
        while(!done) {
            Item currentItem = shoppingList.get(i);

            int index = inventory.containsItem(currentItem);

            if(index > -1){
                synchronized (lock) {
                    Item itemToCheck = inventory.getList().get(index);

                    int itemQuantity = currentItem.getItemQuantity();
                    int numInInventory = itemToCheck.getItemQuantity();

                    if(numInInventory >= itemQuantity){
                        itemToCheck.updateQuantity(itemQuantity);
                        currentItem.setQuantity(0);
                        currentItem.setQuantity(0);
                        shoppingList.remove(i);
                    }else{
                        cash -= numInInventory * currentItem.getPrice();
                        itemToCheck.setQuantity(0);
                        currentItem.updateQuantity(numInInventory);
                    }
                }

            }else{
                panicking = true;
                int numItemsToAdd = random.nextInt(3) + 1;
                for(int k = 0; k < numItemsToAdd; k++) {
                    Item itemToAdd = inventory.getList().get(random.nextInt(inventory.getList().size()));
                    itemToAdd.setQuantity(random.nextInt(5) + 1);
                    shoppingList.add(itemToAdd);
                }  
            }

            i++;
            checkStealers();
            done = shoppingList.isEmpty() || i >= shoppingList.size() || cash <= 0;

        }

    }

    public void checkStealers()
    {
        for (int i = 0; i < supermarket.getShoppers().size(); i++)
        {
            Shopper toCheck = supermarket.getShoppers().get(i);
            if (toCheck instanceof Kristi)
            {
                Kristi kristi = (Kristi) toCheck;
                if (kristi.isStealing())
                {
                    kristi.increaseJailProb();
                    startedSnitching = true;
                }

            }
            else if(toCheck instanceof Jacob)
            {
                Jacob jacob = (Jacob) toCheck;
                if (jacob.isStealing())
                {
                    jacob.increaseJailProb();
                    startedSnitching = true;
                }
            }

        }
    }
    
    public boolean isPanicking()
    {
        return panicking;
    }

    @Override
    public String toString(){

        return "Will's Shopping List\n" +
        "Shopper number " + shopperNumber + "\n" +
        shoppingList.toString() + "\n";

    }
}
