/*
 * Copyright (C) 2020 William Skelly, Kristi Boardman, Cameron Costello, and Jacob Burch
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package SupermarketShoppers;

import java.util.ConcurrentModificationException;
import java.util.Random;

import java.util.Vector;

/**
 * Starts the simulation and passes in the data in the inventory.
 *
 * @author Cameron Costello, Kristi Boardman, Will Skelly, Jacob Burch
 * @version Spring 2020
 */
public class ShopperManager extends Thread {

    //CONSTANTS FOR EACH SHOPPER
    private static final int WILL = 1;
    private static final int CAMERON = 2;
    private static final int KRISTI = 3;
    private static final int JACOB = 4;

    //THE MAXIMUM AMOUNT OF PEOPLE ALLOWED IN ONE STORE
    private static final int MAX_PEOPLE = 20;
    
    //THE AMOUNT OF TIME TO WAIT
    private static final int DELAY_TIME = 250;
    
    //RANDOM NUMBER GENERATOR
    private Random random;

    //A LIST OF THE SHOPPERS IN THE STORE
    private Vector<Shopper> shoppers;
    
    //THE INVENTORY OF THE STORE
    private Inventory inventory;
    
    //THE JAIL THAT STEALING SHOPPERS THAT GET CAUGHT GO TO
    private Jail jail;
    
    //THE NUMBER OF SHOPPERS IN THE STORE
    private int numShoppers;

    //WHEN IS THE SHOPPER MANAGER FINSIHED WORKING
    private boolean done;

    //A LOCK FOR THE SHOPPERS LIST
    private Object shoppersLock = new Object();

    /**
     * Constructs a ShopperManager object.
     * @param itemsInStore The inventory of the store.
     * @param jail The jail the shoppers might end up in.
     */
    ShopperManager (Inventory itemsInStore, Jail jail) {
        this.inventory = itemsInStore;
        this.jail = jail;
        this.shoppers= new Vector<Shopper>();
        this.random = new Random();
    }

    /**
     * Generates a random number of shoppers to enter the store, 
     * creates their thread, and keeps track of when all of the
     * shoppers have left the store.
     */
    public void run() {
        //RANDOMLY GENERATES NUMBER OF SHOPPERS IN STORE
        numShoppers = random.nextInt(MAX_PEOPLE) + 1;
        
        //CREATED VARIABLES FOR THE LOOP
        int shopType;
        int shopperID = 0;
        Shopper newShopper;

        //FOR EVERY SHOPPER THAT WILL ENTER, RANDOMIZE THE TYPE
        for (int i = 0; i < numShoppers; i++) {
            
            //CREATE A NEW LIST FOR EACH SHOPPERS SHOPPING LIST
            Vector<Item> newList = new Vector<Item>();

            //THE INVENTORY OF THE STORE
            Vector<Item> inventoryList = inventory.getList();

            //CREATE MORE VARIABLES TO CREATE THE SHOPPING LIST
            int numOfItems;
            int itemIndex;

            //RANDOM TYPE OF SHOPPER TO CREATE
            shopType = random.nextInt(4) + 1;
            
            switch (shopType) {
                case WILL:
                    //NUMBER OF ITEMS TO ADD TO SHOPPING LIST
                    numOfItems = random.nextInt(7) + 1;

                    //ADD A NEW ITEM TO ADD AND SET A QUANTITY TO BUY TO SHOPPING LIST
                    for (int j = 0; j < numOfItems; j++) {
                        itemIndex = random.nextInt(inventoryList.size());
                        Item itemToAdd = inventoryList.get(itemIndex);
                        itemToAdd.setQuantity(random.nextInt(10) + 1);
                        newList.add(itemToAdd);
                    }

                    //CREATE THE NEW SHOPPER TO ADD TO THE SHOPPERS LIST
                    newShopper = new Will(newList, inventory, shopperID, jail, this);
                    break;
                    
                case CAMERON:
                    //NUMBER OF ITEMS TO ADD TO SHOPPING LIST
                    numOfItems = random.nextInt(7) + 1;

                    //ADD A NEW ITEM TO ADD AND SET A QUANTITY TO BUY TO SHOPPING LIST
                    for (int j = 0; j < numOfItems; j++) {
                        itemIndex = random.nextInt(inventoryList.size());
                        Item itemToAdd = inventoryList.get(itemIndex);
                        itemToAdd.setQuantity(random.nextInt(10) + 1);
                        newList.add(itemToAdd);
                    }

                    //CREATE THE NEW SHOPPER TO ADD TO THE SHOPPERS LIST
                    newShopper = new Cameron(newList, inventory, shopperID, jail, this);
                    break;
                    
                case KRISTI:
                    //NUMBER OF ITEMS TO ADD TO SHOPPING LIST
                    numOfItems = random.nextInt(7) + 1;

                    //ADD A NEW ITEM TO ADD AND SET A QUANTITY TO BUY TO SHOPPING LIST
                    for (int j = 0; j < numOfItems; j++) {
                        itemIndex = random.nextInt(inventoryList.size());
                        Item itemToAdd = inventoryList.get(itemIndex);
                        itemToAdd.setQuantity(random.nextInt(10) + 1);
                        newList.add(itemToAdd);
                    }

                    //CREATE THE NEW SHOPPER TO ADD TO THE SHOPPERS LIST
                    newShopper = new Kristi(newList, inventory, shopperID, jail, this);
                    break;
                    
                case JACOB:
                    //NUMBER OF ITEMS TO ADD TO SHOPPING LIST
                    numOfItems = random.nextInt(7) + 1;

                    //ADD A NEW ITEM TO ADD AND SET A QUANTITY TO BUY TO SHOPPING LIST
                    for (int j = 0; j < numOfItems; j++) {
                        itemIndex = random.nextInt(inventoryList.size());
                        Item itemToAdd = inventoryList.get(itemIndex);
                        itemToAdd.setQuantity(random.nextInt(10) + 1);
                        newList.add(itemToAdd);
                    }

                    //CREATE THE NEW SHOPPER TO ADD TO THE SHOPPERS LIST
                    newShopper = new Jacob(newList, inventory, shopperID, jail, this);

                    break;
                    
                default:
                    //NUMBER OF ITEMS TO ADD TO SHOPPING LIST
                    numOfItems = random.nextInt(7) + 1;

                    //ADD A NEW ITEM TO ADD AND SET A QUANTITY TO BUY TO SHOPPING LIST
                    for (int j = 0; j < numOfItems; j++) {
                        itemIndex = random.nextInt(inventoryList.size());
                        Item itemToAdd = inventoryList.get(itemIndex);
                        itemToAdd.setQuantity(random.nextInt(10) + 1);
                        newList.add(itemToAdd);
                    }

                    //CREATE THE NEW SHOPPER TO ADD TO THE SHOPPERS LIST
                    newShopper = new Will(newList, inventory, shopperID, jail, this);
            }
            //START THE SHOPPERS RUN METHOD
            newShopper.start();
            
            //ADD SHOPPER TO LIST
            synchronized (shoppersLock) {
                shoppers.add(newShopper);
            }
            
            //INCREMENT THE ID NUMBER
            shopperID++;
        }

        //SHOPPER MANAGER IS FINISHED WHEN ALL SHOPPERS ARE FINISHED
        while (!done) {
            boolean isDone = true;
            synchronized (shoppersLock) {
                for (Shopper shopper : shoppers) {
                    if (!shopper.done()) {
                        isDone = false;
                    }
                }
            }
            done = isDone;

            try {
                sleep(DELAY_TIME);
            } catch (InterruptedException e) {
                System.err.println(e);
            }
        }
    }

    /**
     * Returns the list of shoppers in the store.
     * @return The list of shoppers.
     * @throws ConcurrentModificationException 
     */
    public Vector<Shopper> getShoppers() throws ConcurrentModificationException {
        if (!done) {
            throw new ConcurrentModificationException();
        }
        return this.shoppers;
    }

    /**
     * Returns the number of shoppers in the store.
     * @return The number of shoppers in the store.
     */
    public int getNumShoppers() {
        synchronized (shoppersLock) {
            return this.shoppers.size();
        }
    }

    /**
     * Returns a string of the ShopperManager class that includes
     * stating the number of shoppers in the store followed by a 
     * list of the shoppers that are in the store.
     * @return A string of the shoppers the manager knows about.
     */
    @Override
    public String toString() {
        int size;
        String toString;
        synchronized (shoppersLock) {
            size = this.shoppers.size();
            toString = this.shoppers.toString();
        }

        return "Number of shoppers in the store: " + size + "\n" + toString;
    }

    /**
     * When you snitch, you check to see if there are people stealing in the store.
     * 
     */
    public void checkStealers() {
        ShopperSnitchingVisitor snitching = new ShopperSnitchingVisitor();
        synchronized (shoppersLock) {
            for (Shopper shopper : shoppers) {
                shopper.accept(snitching);
            }
        }
    }
}
