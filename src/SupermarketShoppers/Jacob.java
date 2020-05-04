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

/**
 * This class models a Shopper that can steal and snitch on other
 * shoppers that are stealing.
 *
 * @author Cameron Costello, Kristi Boardman, Will Skelly, Jacob Burch
 * @version Spring 2020
 */
public class Jacob extends StealingShopper implements SnitchingShopper {

    private Object doneLock = new Object();

    /**
     * Constructor for objects of class Shoppers
     *
     * @param shoppingList the shopping list for this Shopper
     * @param inventory the inventory that the Shopper will purchase items from
     * @param number the number of the shopper
     * @param jail the jail that shopper will go to if they are caught stealing
     * @param shopperManager the ShopperManager that is managing this Shopper
     */
    public Jacob(Vector<Item> shoppingList, Inventory inventory, int number, Jail jail, ShopperManager shopperManager) {
        super(shoppingList, inventory, number, jail, shopperManager);

        //SETTING NAME, STARTING CASH, AND STARTING PROBABILITY OF GOING TO JAIL
        name = "Jacob";
        cash = random.nextInt(MAX_CASH / Morality.JACOB.getValue()) + 1;
        jailedProb = (random.nextDouble() * ONE_HUNDRED) * Morality.JACOB.getValue();
    }

    /**
     * Run method for the Jacob that controls this Jacob's shopping.
     *
     */
    @Override
    public void run() {
        //SLEEP SO SHOPPER IS NOT ALONE WHILE SHOPPING SO IT CAN SNITCH
        shopperSleep();
        //SET THE AMOUNT FOR THE SHOPPER TO BE UNABLE TO PURCHASE MORE ITEMS
        setMinimumPrice();

        //LOOP TO GO SHOPPING
        int i = 0;
        synchronized (doneLock) {
            while (!done) {
                Item currentItem = shoppingList.get(i);

                //ATTEMPT TO PURCHASE ITEM FROM SHOPPING LIST
                int index = inventory.containsItem(currentItem);
                Item itemToCheck = inventory.getList().get(index);
                int itemQuantity = currentItem.getItemQuantity();
                if (cash > 0) {

                    int qPurchased = itemToCheck.attemptToBuy(itemQuantity, cash);
                    if (!startedStealing && qPurchased == 0) {
                        startedStealing = true;
                        increaseList();
                    }

                    if (!startedStealing) {
                        cash -= qPurchased * currentItem.getPrice();
                    }
                }

                //SNITCH ON OTHER SHOPPERS
                snitchOnStealers();
                //CHECK IF SHOPPER SHOULD GO TO JAIL
                checkJailProb();
                i++;
                done = i >= shoppingList.size();
            }
        }

        //REMOVE ITEMS FROM SHOPPING LIST IF ACQUIRED
        crossItemsOffList();
    }

    /**
     * Calls the checkStealers() method of the ShopperManager class
     * to check if other Shoppers are stealing.
     * 
     */
    @Override
    public void snitchOnStealers() {
        shopperManager.checkStealers();
    }
}
