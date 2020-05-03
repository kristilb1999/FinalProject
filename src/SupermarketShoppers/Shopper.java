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
    
    public static final int ONE_HUNDRED = 100;

    protected Inventory inventory;

    protected Jail jail;

    protected Vector<Item> shoppingList;

    protected Random random;

    protected double cash;

    protected double jailedProb;

    protected int morality;

    protected boolean done;

    protected int shopperNumber;

    protected double minimumPrice;

    protected ShopperManager supermarketManager;

    /**
     * Constructor for objects of class Shoppers
     */
    public Shopper(Vector<Item> shoppingList, Inventory inventory, int number, Jail jail, ShopperManager supermarketManager) {
        shopperNumber = number;
        this.inventory = inventory;
        this.shoppingList = shoppingList;
        this.jail = jail;
        this.supermarketManager = supermarketManager;
        random = new Random();
        minimumPrice = Double.MAX_VALUE;
    }

    abstract public void run();

    public boolean done() {
        return done;
    }

    public double getCash() {
        return cash;
    }

    public void setCash(double cash) {
        this.cash = cash;
    }

    public double getJailedProb() {
        return jailedProb;
    }

    public void setJailedProb(double jailedProb) {
        this.jailedProb = jailedProb;
    }

    public int getMorality() {
        return morality;
    }

    public void setMorality(int morality) {
        this.morality = morality;
    }

    public Vector<Item> getShoppingList() {
        return shoppingList;
    }

    protected void increaseList() {
        int numItemsToAdd = random.nextInt(3) + 1;
        for (int k = 0; k < numItemsToAdd; k++) {
            Item itemToAdd = inventory.getList().get(random.nextInt(inventory.getList().size()));
            itemToAdd.setQuantity(random.nextInt(5) + 1);
            shoppingList.add(itemToAdd);
        }
    }

    protected void setMinimumPrice() {
        for (Item item : shoppingList) {
            minimumPrice = (item.getPrice() < minimumPrice
                    ? item.getPrice() : minimumPrice);
        }
    }

    protected double getMinimumPrice(){
        return minimumPrice;
    }
    
    public boolean accept(ShopperVisitor shopperVisitor){
        return shopperVisitor.visit(this);
    }
    
    protected void shopperSleep(){
        try {
            sleep(ONE_HUNDRED);
        } catch (InterruptedException e) {
            System.err.println(e);
        }
    }
    
    @Override
    public String toString() {

        return "\n\n" + getShopperName() + "\'s Shopping List\n"
                + "Shopper number " + shopperNumber + "\n"
                + "Cash left in wallet: " + String.format("%.2f", cash) + "\n"
                + "Items the shopper was unable to purchase:\n"
                + shoppingList.toString();

    }
    
    abstract public String getShopperName();
}
