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
public class Kristi extends Shopper {

    public static final int MORALITY_NUM = 3;

    public static final int MAX_CASH = 1000;

    public static final int INCREASE_PROB = 5;

    public static final int SENT_TO_JAIL = 65;

    public boolean startedStealing;

    private Object lock = new Object();
    private Object jailProbLock = new Object();

    /**
     * Constructor for objects of class Shoppers
     */
    public Kristi(Vector<Item> shoppingList, Inventory inventory, int number, Jail jail, ShopperManager supermarket) {
        super(shoppingList, inventory, number, jail, supermarket);

        morality = MORALITY_NUM;
        cash = random.nextInt(MAX_CASH / MORALITY_NUM) + 1;
        jailedProb = 100;
        startedStealing = true;
        /**
         * (random.nextDouble() * ONE_HUNDRED) * MORALITY_NUM;
         *
         */
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

                int qPurchased = itemToCheck.attemptToBuy(itemQuantity, Double.MAX_VALUE);

                if (!startedStealing && qPurchased == 0) {
                    startedStealing = true;
                    increaseList();
                }

                if (!startedStealing) {
                    cash -= qPurchased * currentItem.getPrice();
                }
            }

            i++;
            done = i >= shoppingList.size();
            checkJailProb();
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

    public boolean isStealing() {
        return startedStealing;
    }

    public boolean increaseJailProb() {
        if (startedStealing) {
            synchronized (jailProbLock) {
                if (jailedProb < ONE_HUNDRED) {
                    jailedProb += INCREASE_PROB;
                }
            }
        }
        return startedStealing;
    }

    @Override
    public String getShopperName(){
        return "Kristi";
    }

    public boolean accept(ShopperVisitor shopperVisitor) {
        return shopperVisitor.visit(this);
    }
    
    private void checkJailProb(){
        if (startedStealing) {
                synchronized (jailProbLock) {
                    if (jailedProb >= ONE_HUNDRED) {
                        done = true;
                        jail.getArrested(this);
                        //supermarketManager.removeShopper(this);
                        this.done = true;
                    }
                }
            }
    }
}
