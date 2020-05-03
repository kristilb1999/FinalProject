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

import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class controls the supermarket managers. This class also keeps track
 * of how many shoppers are stealing or panic shopping.
 *
 * @author Cameron Costello, Kristi Boardman, Will Skelly, Jacob Burch.
 * @version Spring 2020
 */
public class Supermarket extends Thread {

    //ALL ITEMS IN THE STORE
    private Inventory itemsInStore;

    //WILL MANAGE SHOPPERS IN THE STORE
    private ShopperManager shopperManager;
    
    //USED TO COUND CRIMINALS AND PANIC SHOPPERS
    private Vector<Shopper> shoppers;

    //HOLDS ALL SHOPPERS THAT HAVE STOLEN
    private Jail jail;

    //COUNTS FOR DIFFERENT SHOPPERS 
    private int numCustomers;
    private int numStealing;
    private int numPanicShopping;

    //EACH STORE HAS A UNIQUE NUMBER
    private int storeNumber;

    //WHEN IS THIS CLASS FINISHED
    //private boolean done;

    /**
     * Constructor for objects of class Supermarket
     *
     * @param storeNumber The number of the store.
     */
    public Supermarket(int storeNumber) {
        this.itemsInStore = new Inventory();
        this.jail = new Jail();
        this.shopperManager = new ShopperManager(itemsInStore, jail);
        this.storeNumber = storeNumber;
    }

    /**
     * The run method that starts the super market manager and counts
     * up the number of panicking and the number of stealing shoppers.
     */
    @Override
    public void run() {
        //START THE SHOPPER MANAGER
        this.shopperManager.start();

        //WAIT HERE UNTIL THE SHOPPER MANAGER IS FINISHED RUNNING
        try {
            this.shopperManager.join();
        } catch (InterruptedException e) {
            System.err.println(e);
        }

        //THE NUMBER OF SHOPPERS COUNTED BY THE MANAGER
        numCustomers = shopperManager.getNumShoppers();

        //THE SHOPPERS THAT WERE IN THE STORE 
        shoppers = shopperManager.getShoppers();

        //VISITOR CLASSES TO KEEP TRACK OF STEALING OR PANICKING SHOPPERS
        ShopperStealingVisitor stealing = new ShopperStealingVisitor();
        ShopperPanickingVisitor panicking = new ShopperPanickingVisitor();

        //COUNT UP ALL PANICKING AND STEALING SHOPPERS
        for (Shopper shopper : shoppers) {
            if (shopper.accept(stealing)) {
                numStealing++;
            }
            if (shopper.accept(panicking)) {
                numPanicShopping++;
            }
        }

    }

    /**
     * Returns the number of shoppers that stole from the store.
     * @return The number of shoppers that stole.
     */
    public int getNumStealing() {
        return numStealing;
    }

    /**
     * Returns the number of panicking shoppers in the store.
     * @return The number of panicking shoppers.
     */
    public int getNumPanicShopping() {
        return numPanicShopping;
    }
    
//
//    /**
//     * Returns whether or not the store is finished.
//     * @return 
//     */
//    public boolean done() {
//        return this.done;
//    }

    /**
     * Returns the number of shoppers that were put in jail.
     * @return The number of people in jail.
     */
    public int numShoppersInJail() {
        return jail.getNumInJail();
    }

    /**
     * Returns the number of shoppers that visited the store.
     * @return The number of shoppers in the store.
     */
    public int getNumCustomers() {
        return numCustomers;
    }

    /**
     * Returns the store number.
     * @return The store number.
     */
    public int getStoreNumber() {
        return storeNumber;
    }

    /**
     * Returns a string describing the Supermarket by printing it's number 
     * and all of the shoppers that were in the supermarket
     * and all of the shoppers that were in jail (some may appear in both).
     * @return The string describing the Supermarket.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("\nSupermarket ")
                .append(this.storeNumber)
                .append("\n")
                .append(this.shopperManager.toString())
                .append("\n\n ")
                .append(this.jail.toString());
        
        return sb.toString();
    }

}
