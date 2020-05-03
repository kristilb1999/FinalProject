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

/**
 * This class gives information about each item in the store.
 *
 * @author Cameron Costello, Kristi Boardman, Will Skelly, Jacob Burch
 * @version Spring 2020
 */
public class Item {

    //QUANTITY LOCK TO ENSURE THREAD SAFETY
    private Object quantityLock = new Object();

    //THE NAME OF THE ITEM
    private String name;

    //THE ITEM VALUE
    private int value;

    //THE ITEM QUANTITY
    private int itemQuant;

    //THE ITEM PRICE
    private double price;

    /**
     * Constructor for objects of class Item
     */
    public Item(String name, int value, int itemQuant, double price) {
        this.name = name;
        this.value = value;
        this.price = price;
        this.itemQuant = itemQuant;
    }

    /**
     * Return the item name.
     * 
     * @return Name of the item.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the item.
     * 
     * @param name The name to change to.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Return the item value.
     * 
     * @return Value of the item.
     */
    public int getValue() {
        return value;
    }

    /**
     * Set the value of the item.
     * 
     * @param value The value to change to.
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * Return the item price.
     * 
     * @return Price of the item.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Set the price of the item.
     * 
     * @param price The price to change to.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Return the item quantity.
     * 
     * @return Quantity of the item.
     */
    public int getItemQuantity() {
        return itemQuant;
    }

    /**
     * Set the quantity of the item.
     * 
     * @param quantity The quantity to change to.
     */
    public void setQuantity(int itemQuant) {
        this.itemQuant = itemQuant;
    }

    /**
     * Update the quantity of the item.
     * 
     * @param quantity The quantity to change to.
     */
    public void updateQuantity(int quantity) {
        this.itemQuant -= quantity;
    }

    /**
     * This is used to attempt to buy an item. 
     * 
     * @param quantity The quantity of the item.
     *        cash How much cash is available.
     * @return the amount purchased.
     */
    public int attemptToBuy(int quantity, double cash) {
        int qPurchased = 0;
        
        //LOCK TO ENSURE THREAD SAFETY
        synchronized (quantityLock) {

            //WHILE THERE IS MORE CASH THAN PRICE QUANTITY IS GREATER THAN 0,
            //PURCHASE THE ITEM
            while(cash > this.price && quantity > 0 && itemQuant > 0){
                cash -= this.price;
                qPurchased++;
                quantity--;
            }
        }
        
        //RETURN AMOUNT PURCHASED
        return qPurchased;
    }

    /**
     * This method overrides the equals method to determine when
     * an item is equal to the input.
     * 
     * @param other The object that will be converted to an item.
     * @return true or false Return whether or not the item equals the 
     *          parameter item.
     */
    @Override
    public boolean equals(Object other) {
        //CHECK IF OTHER IS AN INSTANCE OF ITEM
        if (other instanceof Item) {
            return name.equals(((Item) other).getName());
        } else {
            return false;
        }

    }

    public Item getCopy(){
        return new Item(this.name, this.value, this.itemQuant, this.price);
    }

    @Override
    public String toString() {
        return "\nItem Name: " + name
        + ", Item Value: " + value
        + ", Item Quantity: " + itemQuant
        + ", Item Price: " + price;
    }
}
