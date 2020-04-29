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

    SupermarketManager(Inventory itemsInStore, Jail jail) {
        this.inventory = itemsInStore;
        this.jail = jail;
        shoppers = new Vector<Shopper>();
        random = new Random();
    }

    public void run(){
        numShoppers = MAX_PEOPLE;//random.nextInt(MAX_PEOPLE) + 1;
        int shopType;
        Shopper newShopper;
        for (int i = 0; i < numShoppers; i++){

            Vector<Item> newList = new Vector<Item>();

            Vector<Item> inventoryList = inventory.getList();

            int numOfItems;
            int itemIndex;

            shopType = random.nextInt(4) + 1;
            switch(shopType){
                case WILL :
                random = new Random(3);
                numOfItems = random.nextInt(7) + 1;

                for(int j = 0; j < numOfItems; j++){
                    itemIndex = random.nextInt(inventoryList.size());
                    Item itemToAdd = inventoryList.get(itemIndex);
                    itemToAdd.setQuantity(random.nextInt(10) + 1);
                    newList.add(itemToAdd);
                }

                newShopper = new Will(newList, inventory, i, jail, this);
                break;
                case CAMERON :
                random = new Random(7);
                numOfItems = random.nextInt(7) + 1;

                for(int j = 0; j < numOfItems; j++){
                    itemIndex = random.nextInt(inventoryList.size());
                    Item itemToAdd = inventoryList.get(itemIndex);
                    itemToAdd.setQuantity(random.nextInt(10) + 1);
                    newList.add(itemToAdd);
                }

                newShopper = new Cameron(newList, inventory, i, jail, this);
                break;
                case KRISTI :
                random = new Random(9);
                numOfItems = random.nextInt(7) +1;

                for(int j = 0; j < numOfItems; j++){
                    itemIndex = random.nextInt(inventoryList.size());
                    Item itemToAdd = inventoryList.get(itemIndex);
                    itemToAdd.setQuantity(random.nextInt(10) + 1);
                    newList.add(itemToAdd);
                }

                newShopper = new Kristi(newList, inventory, i, jail, this);
                break;
                case JACOB :
                random = new Random(2);
                numOfItems = random.nextInt(7) +1;

                for(int j = 0; j < numOfItems; j++){
                    itemIndex = random.nextInt(inventoryList.size());
                    Item itemToAdd = inventoryList.get(itemIndex);
                    itemToAdd.setQuantity(random.nextInt(10) + 1);
                    newList.add(itemToAdd);
                }

                newShopper = new Jacob(newList, inventory, i, jail, this);
                
                break;
                default :
                random = new Random(3);
                numOfItems = random.nextInt(7) +1;

                for(int j = 0; j < numOfItems; j++){
                    itemIndex = random.nextInt(inventoryList.size());
                    Item itemToAdd = inventoryList.get(itemIndex);
                    itemToAdd.setQuantity(random.nextInt(10) + 1);
                    newList.add(itemToAdd);
                }

                newShopper = new Will(newList, inventory, i, jail, this);
            }
            newShopper.start();
            shoppers.add(newShopper);
        }
        
        while(!done){
            int numDone = 0;
            for(Shopper customer : shoppers) {
                if(customer.done()){
                    numDone++;
                }
            }
            
            if(numDone == shoppers.size()) {
                done = true;
            }
            
            try{
                        sleep(DELAY_TIME);
                    } catch (InterruptedException e){
                        System.err.println(e);
                    }
        }

    }
    
    public boolean done(){
        return done;
    }

    public Vector<Shopper> getShoppers()
    {
        return shoppers;
    }

    public void removeShopper(int shopperToRemove)
    {

        for (int i = 0; i < shoppers.size(); i++)
        {
            if (shopperToRemove == shoppers.get(i).shopperNumber)
            {
                shoppers.remove(i);
            }

        }
    }
    
    @Override
    public String toString() {
        return "Number of shoppers in the store: " + shoppers.size() + "\n" + 
                shoppers.toString();
        
    }
}
