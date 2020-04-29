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

    public static final double ONE_HUNDRED = 100;
    /**
     * Constructor for objects of class Shoppers
     */
    public Cameron(Vector<Item> shoppingList, Inventory inventory, int number, Jail jail, SupermarketManager supermarket)
    {
        super(shoppingList, inventory, number, jail, supermarket);

        morality = MORALITY_NUM;
        cash = random.nextInt(MAX_CASH / MORALITY_NUM) + 1;
        panicShopping = (random.nextDouble() * ONE_HUNDRED) / MORALITY_NUM;
        
    }

    @Override
    public void run()
    {
        System.out.println(toString());
        int i = 0;
        while(!done) {
            Item currentItem = shoppingList.get(i);
            
            int index = inventory.containsItem(currentItem);
            
            if(index > -1){
                
                Item itemToCheck = inventory.getList().get(index);
                
                int itemQuantity = currentItem.getItemQuantity();
                int numInInventory = itemToCheck.getItemQuantity();
                
                if(numInInventory >= itemQuantity){
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
            
            done = shoppingList.isEmpty() || i > shoppingList.size() || cash <= 0;
            
        }
        
        System.out.println(toString());
       
    }
    
    @Override
    public String toString(){
        
        return "Cameron's Shopping List\n" +
                "Shopper number" + shopperNumber + "\n" +
                shoppingList.toString();
        
    }
}

