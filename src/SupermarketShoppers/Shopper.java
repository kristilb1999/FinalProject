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
import java.util.Random;

/**
 * An abstract class of people who will be going shopping. Includes many basic
 * methods shared by the different types of shoppers.
 *
 * @author Cameron Costello, Kristi Boardman, Will Skelly, Jacob Burch
 * @version Spring 2020
 */
abstract public class Shopper extends Thread {

    //VARIABLE FOR THE SHOPPER CLASS
    public static final int ONE_HUNDRED = 100;
    public static final int MAX_CASH = 1000;
    protected Inventory inventory;
    protected Jail jail;
    protected Vector<Item> shoppingList;
    protected Random random;
    protected double cash;
    protected double jailedProb;
    protected boolean done;
    protected int shopperNumber;
    protected double minimumPrice;
    protected String name;
    protected ShopperManager shopperManager;

    /**
     * Constructor for objects of class Shoppers.
     * 
     * @param shoppingList the shopping list for this Shopper
     * @param inventory the inventory that the Shopper will purchase items from
     * @param number the number of the shopper
     * @param jail the jail that shopper will go to if they are caught stealing
     * @param shopperManager the ShopperManager that is managing this Shopper
     */
    public Shopper(Vector<Item> shoppingList, Inventory inventory, int number, Jail jail, ShopperManager shopperManager) {
        shopperNumber = number;
        this.inventory = inventory;
        this.shoppingList = shoppingList;
        this.jail = jail;
        this.shopperManager = shopperManager;
        random = new Random();
        minimumPrice = Double.MAX_VALUE;
    }

    /**
     * Abstract run method for Shopper classes.
     * 
     */
    @Override
    abstract public void run();

    /**
     * Returns whether the Shopper is done.
     * 
     * @return whether the Shopper is done
     */
    public boolean done() {
        return done;
    }

    /**
     * Returns the Shopper's cash.
     * 
     * @return the Shopper's cash
     */
    public double getCash() {
        return cash;
    }

    /**
     * Sets the Shopper's cash.
     * 
     * @param cash the amount to set the Shopper's cash to
     */
    public void setCash(double cash) {
        this.cash = cash;
    }

    /**
     * Return's this Shopper's probability of going to jail.
     * 
     * @return this Shopper's probability of going to jail
     */
    public double getJailedProb() {
        return jailedProb;
    }

    /**
     * Sets this Shopper's probability of going to jail.
     * 
     * @param jailedProb the probability to set the Shopper's jail probability to
     */
    public void setJailedProb(double jailedProb) {
        this.jailedProb = jailedProb;
    }

    /**
     * Returns this Shopper's shopping list.
     * 
     * @return this Shopper's shopping list
     */
    public Vector<Item> getShoppingList() {
        return shoppingList;
    }

    /**
     * Adds items to the Shopper's list, 
     * such as if the Shopper starts stealing or panicking
     * 
     */
    protected void increaseList() {
        int numItemsToAdd = random.nextInt(3) + 1;
        for (int k = 0; k < numItemsToAdd; k++) {
            Item itemToAdd = inventory.getList().get(random.nextInt(inventory.getList().size()));
            itemToAdd.setQuantity(random.nextInt(5) + 1);
            shoppingList.add(itemToAdd);
        }
    }

    /**
     * Sets the minimum price for the Shopper based on
     * the price of the cheapest item on the Shopper's shopping list.
     * 
     */
    protected void setMinimumPrice() {
        for (Item item : shoppingList) {
            minimumPrice = (item.getPrice() < minimumPrice
                    ? item.getPrice() : minimumPrice);
        }
    }

    /**
     * Gets the minimum price for the Shopper based on
     * the price of the cheapest item on the Shopper's shopping list.
     * 
     * @return the minimum price of the Shopper 
     */
    protected double getMinimumPrice() {
        return minimumPrice;
    }

    /**
     * Causes the Shopper to sleep for 100 milliseconds.
     * 
     */
    protected void shopperSleep() {
        try {
            sleep(ONE_HUNDRED);
        } catch (InterruptedException e) {
            System.err.println(e);
        }
    }

    /**
     * Removes items from the Shopper's shopping list if the Shopper
     * has acquired the required quantity of that item.
     * 
     */
    protected void crossItemsOffList() {
        int j = 0;
        while (j < shoppingList.size()) {
            if (shoppingList.get(j).getItemQuantity() <= 0) {
                shoppingList.remove(j);
                j++;
            } else {
                j++;
            }
        }
    }

    /**
     * Returns a string of information relating to the Shopper.  
     * 
     * @return 
     */
    @Override
    public String toString() {

        return "\n\n" + name + "\'s Shopping List\n"
                + "Shopper number " + shopperNumber + "\n"
                + "Cash left in wallet: " + String.format("%.2f", cash) + "\n"
                + "Items the shopper was unable to purchase:\n"
                + shoppingList.toString();

    }

    /**
     * Accepts a ShopperVisitor to visit with this Shopper.
     * 
     * @param shopperVisitor the shopperVisitor to visit
     * @return the result of visiting the ShopperVisitor
     */
    public boolean accept(ShopperVisitor shopperVisitor) {
        return shopperVisitor.visit(this);
    }
}
