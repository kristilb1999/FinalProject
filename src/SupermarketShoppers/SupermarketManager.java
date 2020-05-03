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
 * Starts the simulation and passes in the data used.
 *
 * @author Cameron Costello, Kristi Boardman, Will Skelly, Jacob Burch
 * @version Spring 2020
 */
public class SupermarketManager extends Thread {

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

    private Object shoppersLock = new Object();

    SupermarketManager(Inventory itemsInStore, Jail jail) {
        this.inventory = itemsInStore;
        this.jail = jail;
        this.shoppers= new Vector<Shopper>();
        this.random = new Random();
    }

    public void run() {
        numShoppers = random.nextInt(MAX_PEOPLE) + 1;
        int shopType;
        int shopperID = 0;
        Shopper newShopper;

        for (int i = 0; i < numShoppers; i++) {

            Vector<Item> newList = new Vector<Item>();

            Vector<Item> inventoryList = inventory.getList();

            int numOfItems;
            int itemIndex;

            shopType = random.nextInt(4) + 1;
            switch (shopType) {
                case WILL:
                    numOfItems = random.nextInt(7) + 1;

                    for (int j = 0; j < numOfItems; j++) {
                        itemIndex = random.nextInt(inventoryList.size());
                        Item itemToAdd = inventoryList.get(itemIndex);
                        itemToAdd.setQuantity(random.nextInt(10) + 1);
                        newList.add(itemToAdd);
                    }

                    newShopper = new Will(newList, inventory, shopperID, jail, this);
                    break;
                case CAMERON:
                    numOfItems = random.nextInt(7) + 1;

                    for (int j = 0; j < numOfItems; j++) {
                        itemIndex = random.nextInt(inventoryList.size());
                        Item itemToAdd = inventoryList.get(itemIndex);
                        itemToAdd.setQuantity(random.nextInt(10) + 1);
                        newList.add(itemToAdd);
                    }

                    newShopper = new Cameron(newList, inventory, shopperID, jail, this);
                    break;
                case KRISTI:
                    numOfItems = random.nextInt(7) + 1;

                    for (int j = 0; j < numOfItems; j++) {
                        itemIndex = random.nextInt(inventoryList.size());
                        Item itemToAdd = inventoryList.get(itemIndex);
                        itemToAdd.setQuantity(random.nextInt(10) + 1);
                        newList.add(itemToAdd);
                    }

                    newShopper = new Kristi(newList, inventory, shopperID, jail, this);
                    break;
                case JACOB:
                    numOfItems = random.nextInt(7) + 1;

                    for (int j = 0; j < numOfItems; j++) {
                        itemIndex = random.nextInt(inventoryList.size());
                        Item itemToAdd = inventoryList.get(itemIndex);
                        itemToAdd.setQuantity(random.nextInt(10) + 1);
                        newList.add(itemToAdd);
                    }

                    newShopper = new Jacob(newList, inventory, shopperID, jail, this);

                    break;
                default:
                    numOfItems = random.nextInt(7) + 1;

                    for (int j = 0; j < numOfItems; j++) {
                        itemIndex = random.nextInt(inventoryList.size());
                        Item itemToAdd = inventoryList.get(itemIndex);
                        itemToAdd.setQuantity(random.nextInt(10) + 1);
                        newList.add(itemToAdd);
                    }

                    newShopper = new Will(newList, inventory, shopperID, jail, this);
            }
            newShopper.start();
            synchronized (shoppersLock) {
                shoppers.add(newShopper);
            }
            shopperID++;
        }

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
                sleep(250);
            } catch (InterruptedException e) {
                System.err.println(e);
            }
        }

    }

    public boolean done() {
        return this.done;
    }

    public Vector<Shopper> getShoppers() throws ConcurrentModificationException {
        if (!done) {
            throw new ConcurrentModificationException();
        }
        return this.shoppers;
    }

    public int getNumShoppers() {
        synchronized (shoppersLock) {
            return this.shoppers.size();
        }
    }

    public void removeShopper(Shopper shopper) {
        synchronized (shoppersLock) {
            shoppers.remove(shopper);
        }
    }

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

    public void checkStealers() {
        ShopperSnitchingVisitor snitching = new ShopperSnitchingVisitor();
        synchronized (shoppersLock) {
            for (Shopper shopper : shoppers) {
                shopper.accept(snitching);
            }
        }
    }
}
