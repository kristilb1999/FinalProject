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
 *
 *
 * @author Cameron Costello, Kristi Boardman, Will Skelly, Jacob Burch.
 * @version Spring 2020
 */
public class Supermarket extends Thread {

    private Inventory itemsInStore;

    private Vector<Shopper> shoppers;

    private int numCustomers;

    private int numStealing;

    private int numPanicShopping;

    private ShopperManager storeManager;

    private Jail jail;

    private int storeNumber;

    private boolean done;

    private static final int DELAY_TIME = 250;

    /**
     * Constructor for objects of class Supermarket
     *
     * @param storeNumber
     */
    public Supermarket(int storeNumber) {
        this.itemsInStore = new Inventory();
        this.jail = new Jail();
        this.storeManager = new ShopperManager(itemsInStore, jail);
        this.storeNumber = storeNumber;
    }

    @Override
    public void run() {

        this.storeManager.start();

        try {
            this.storeManager.join();
        } catch (InterruptedException e) {
            System.err.println(e);
        }

        numCustomers = storeManager.getNumShoppers();

        shoppers = storeManager.getShoppers();

        numStealing = jail.getNumInJail();

        ShopperStealingVisitor stealing = new ShopperStealingVisitor();
        ShopperPanickingVisitor panicking = new ShopperPanickingVisitor();

        for (Shopper shopper : shoppers) {
            if (shopper.accept(stealing)) {
                numStealing++;
            }
            if (shopper.accept(panicking)) {
                numPanicShopping++;
            }
        }

    }

    public int getNumStealing() {
        return numStealing;
    }

    public int getNumPanicShopping() {
        return numPanicShopping;
    }

    public boolean done() {
        return this.done;
    }

    public int numShoppersInJail() {
        return jail.getNumInJail();
    }

    public int getNumCustomers() {
        return numCustomers;
    }

    public int getStoreNumber() {
        return storeNumber;
    }

    @Override
    public String toString() {
        return "\nSupermarket " + this.storeNumber + "\n" + this.storeManager.toString()
                + "\n\n " + this.jail.toString();
    }

}
