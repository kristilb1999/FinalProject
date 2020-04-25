package SupermarketShoppers;
import java.util.Random;

import java.util.Vector;
/**
 * Starts the simulation and passes in the data used.
 *
 * @author Cameron Costello, Kristi Boardman, Will Skelly, Jacob Burch
 * @version Spring 2020
 */
public class SupermarketManager extends Thread
{
    private static final int WILL = 1;
    private static final int CAMERON = 2;
    private static final int KRISTI = 3;
    private static final int JACOB = 4;

    private Random random;

    private final int MAX_PEOPLE = 20;
    private int numShoppers;

    private Vector<Shopper> shoppers;
    
    private Inventory inventory;

    SupermarketManager(Inventory itemsInStore) {
        this.inventory = itemsInStore;
    }

    public void run(){
        numShoppers = random.nextInt(MAX_PEOPLE) + 1;
        int shopType;
        Shopper newShopper;
        for (int i = 0; i <numShoppers; i++){
            
            Vector<Item> newList = new Vector<Item>();
            
            Vector<Item> inventoryList = inventory.getList();
            
            int numOfItems;
            
            shopType = random.nextInt(4) + 1;
            switch(shopType){
                case WILL :
                    random = new Random(3);
                    numOfItems = random.nextInt(7) + 1;
                    
                    for(int j = 0; j < numOfItems; j++){
                        Item itemToAdd = inventoryList.get(j);
                        itemToAdd.setQuantity(random.nextInt(10) + 1);
                        newList.add(itemToAdd);
                    }
                    
                newShopper = new Will(newList, inventory);
                break;
                case CAMERON :
                    random = new Random(7);
                    numOfItems = random.nextInt(7) + 1;
                    
                    for(int j = 0; j < numOfItems; j++){
                        Item itemToAdd = inventoryList.get(j);
                        itemToAdd.setQuantity(random.nextInt(10) + 1);
                        newList.add(itemToAdd);
                    }
                    
                newShopper = new Cameron(newList, inventory);
                break;
                case KRISTI :
                    random = new Random(9);
                    numOfItems = random.nextInt(7) +1;
                    
                    for(int j = 0; j < numOfItems; j++){
                        Item itemToAdd = inventoryList.get(j);
                        itemToAdd.setQuantity(random.nextInt(10) + 1);
                        newList.add(itemToAdd);
                    }
                    
                newShopper = new Kristi(newList, inventory);
                break;
                case JACOB :
                    random = new Random(2);
                    numOfItems = random.nextInt(7) +1;
                    
                    for(int j = 0; j < numOfItems; j++){
                        Item itemToAdd = inventoryList.get(j);
                        itemToAdd.setQuantity(random.nextInt(10) + 1);
                        newList.add(itemToAdd);
                    }
                    
                newShopper = new Jacob(newList, inventory);
                break;
                default :
                    random = new Random(3);
                    numOfItems = random.nextInt(7) +1;
                    
                    for(int j = 0; j < numOfItems; j++){
                        Item itemToAdd = inventoryList.get(j);
                        itemToAdd.setQuantity(random.nextInt(10) + 1);
                        newList.add(itemToAdd);
                    }
                    
                newShopper = new Will(newList, inventory);
            }
            shoppers.add(newShopper);
        }

    }
}
