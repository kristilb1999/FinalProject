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
 * This class models a Shopper that can snitch on other
 * shoppers that are stealing, but not steal.
 *
 * @author Cameron Costello, Kristi Boardman, Will Skelly, Jacob Burch
 * @version Spring 2020
 */
public class Will extends PanickingShopper implements SnitchingShopper {

    /**
     * Constructor for objects of class Shoppers
     *
     * @param shoppingList
     * @param inventory
     * @param number
     * @param jail
     * @param shopperManager
     */
    public Will(Vector<Item> shoppingList, Inventory inventory, int number, Jail jail, ShopperManager shopperManager) {
        super(shoppingList, inventory, number, jail, shopperManager);

        //SETTING NAME AND STARTING CASH
        name = "Will";
        cash = random.nextInt(MAX_CASH / Morality.WILL.getValue()) + 1;
    }

    /**
     * Run method for the Will that controls this Will's shopping.
     * 
     */
    @Override
    public void run() {
        shopperSleep();

        setMinimumPrice();

        int i = 0;

        while (!done) {
            Item currentItem = shoppingList.get(i);

            int index = inventory.containsItem(currentItem);

            Item itemToCheck = inventory.getList().get(index);

            int itemQuantity = currentItem.getItemQuantity();
            if (cash > 0) {

                int qPurchased = itemToCheck.attemptToBuy(itemQuantity, cash);

                if (qPurchased == 0) {
                    panicking = true;
                    increaseList();
                }

                cash -= qPurchased * currentItem.getPrice();
            }

            i++;
            snitchOnStealers();
            done = i >= shoppingList.size() || cash <= getMinimumPrice();

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
