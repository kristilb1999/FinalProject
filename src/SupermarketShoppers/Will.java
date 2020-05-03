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
 * Write a description of class Shoppers here.
 *
 * @author Cameron Costello, Kristi Boardman, Will Skelly, Jacob Burch
 * @version Spring 2020
 */
public class Will extends Shopper {

    public static final int MORALITY_NUM = 1;

    public static final int MAX_CASH = 1000;

    public boolean startedSnitching;

    public boolean panicking;

    private Object lock = new Object();

    /**
     * Constructor for objects of class Shoppers
     */
    public Will(Vector<Item> shoppingList, Inventory inventory, int number, Jail jail, ShopperManager supermarketManager) {
        super(shoppingList, inventory, number, jail, supermarketManager);

        morality = MORALITY_NUM;
        cash = random.nextInt(MAX_CASH / MORALITY_NUM) + 1;
    }

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
            supermarketManager.checkStealers();
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

    public boolean isPanicking() {
        return panicking;
    }

    @Override
    public String toString() {

        return "\n\nWill's Shopping List\n"
                + "Shopper number " + shopperNumber + "\n"
                + "Cash left in wallet: " + String.format("%.2f", cash) + "\n"
                + "Items the shopper was unable to purchase:\n"
                + shoppingList.toString();

    }
    
    public boolean accept(ShopperVisitor shopperVisitor){
        return shopperVisitor.visit(this);
    }
}
