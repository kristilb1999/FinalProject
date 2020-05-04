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
 * This enum is used to hold the different morality values.
 *
 * @author Kristi Boardman, Cameron Costello, Will Skelly, Jake Burch
 * @version Spring 2020
 */
enum Morality
{
    WILL(1),CAMERON(2),KRISTI(3),JACOB(4);
    
    private final int VALUE;
    
    //CONSTRUCTOR
    Morality(int value){
     this.VALUE = value;
    }
   
    /**
     * Returns the morality value.
     * 
     * @return the morality value
     */
    public int getValue(){
        return this.VALUE;
    }
}