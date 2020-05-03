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
 * This class creates a jail for shoppers. Once Shoppers are in the jail, they cannot get out.
 * The class counts the number of shoppers in jail and outputs it as a toString.
 *
 * @author Cameron Costello, Kristi Boardman, Will Skelly, Jacob Burch.
 * @version Spring 2020
 */
public class Jail
{
    //A LIST FOR THE SHOPPERS IN JAIL
    private Vector<Shopper> shoppersInJail;
    
    /**
     * Constructor for objects of class Jail
     */
    public Jail()
    {
        shoppersInJail = new Vector<Shopper>();
    }
    
    /**
     * Add a shopper to the jail.
     * 
     * @param Shopper criminal. The shopper that is 
     *        going to be arrested.
     */
    public void getArrested(Shopper criminal) 
    {
        shoppersInJail.add(criminal);
    }
     
    /**
     * Return the number of shoppers in jail.
     * 
     * @return The amount of shoppers in jail.
     */
    public int getNumInJail() {
        //RETURN THE NUMBER OF SHOPPERS IN JAIL
        return shoppersInJail.size();
    }
    
    /**
     * Gives the information about the shoppers in jail.
     * 
     * @return The information of the jail.
     */
    @Override
    public String toString()
    {
        //RETURN THE STRING FOR THE SHOPPERS IN JAIL
        return 
            "Shoppers in Jail:\n" +
            "Number of criminals: " + shoppersInJail.size() + "\n" +
            shoppersInJail.toString() + "\n";
    }

}
