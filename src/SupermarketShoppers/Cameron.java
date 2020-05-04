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
 * Write a description of class Shoppers here.
 *
 * @author Cameron Costello, Kristi Boardman, Will Skelly, Jacob Burch
 * @version Spring 2020
 */
public class Cameron extends PanickingShopper {

    /**
     * Constructor for objects of class Shoppers
     * @param shoppingList the shopping list for this Shopper
     * @param inventory the 
     * @param number
     * @param jail
     * @param shopperManager the shopping manager that is managing this Shopper
     */
    public Cameron(Vector<Item> shoppingList, Inventory inventory, int number, Jail jail, ShopperManager shopperManager) {
        super(shoppingList, inventory, number, jail, shopperManager);

        name = "Cameron";
        cash = random.nextInt(MAX_CASH / Morality.CAMERON.getValue()) + 1;

    }

    /**
     * Run method for the Cameron that controls this Cameron's shopping.
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
                    int numItemsToAdd = random.nextInt(3) + 1;
                    for (int k = 0; k < numItemsToAdd; k++) {
                        Item itemToAdd = inventory.getList().get(random.nextInt(inventory.getList().size()));
                        itemToAdd.setQuantity(random.nextInt(5) + 1);
                        shoppingList.add(itemToAdd);
                    }
                }

                cash -= qPurchased * currentItem.getPrice();
            }

            i++;
            done = i >= shoppingList.size() || cash <= getMinimumPrice();
        }

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
}
