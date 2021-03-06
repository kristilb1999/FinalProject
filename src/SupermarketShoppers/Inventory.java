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
import java.io.File;
import java.io.BufferedReader;
import java.util.StringTokenizer;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
/**
 * This class will read in a text file containing each item that a shopper can buy.
 *
 * @author Cameron Costello, Kristi Boardman, Will Skelly, Jacob Burch
 * @version Spring 2020
 */
public class Inventory
{

    //THE INPUT STREAM TO READ IN
    private static final InputStream ITEM_STREAM = Inventory.class.getResourceAsStream("/itemsInStore.txt");

    //ALL OF THE ITEMS IN THE LIST TO READ IN
    private static final Vector<Item> totalItemList = new Vector<Item>();

    static{
        try{
            //BUFFERED READER FOR THE INPUT STREAM
            BufferedReader br = new BufferedReader(new InputStreamReader(ITEM_STREAM));
            String line;
            //READ EACH LINE OF THE FILE
            while((line = br.readLine()) != null)
            {
                //TOKENIZE EACH LINE AND CREATE A NEW ITEM
                StringTokenizer tokenizer = new StringTokenizer(line);
                Item item = new Item(tokenizer.nextToken(), Integer.parseInt(tokenizer.nextToken()), Integer.parseInt(tokenizer.nextToken()), Double.parseDouble(tokenizer.nextToken()));
                totalItemList.add(item);
            }
        }catch(FileNotFoundException e){
            //PRINT AN ERROR IF THE FILE IS NOT FOUND
            System.err.println("Resource Not Found: " + ITEM_STREAM.toString());
        }catch(IOException e){
            //PRINT AN IO EXCEPTION
            System.err.println("IO Exception: " + e);
        }
    }

    //LIST OF ITEMS 
    private final Vector<Item> itemList = new Vector<Item>();

    //FILL THE ITEM LIST
    {
        for(Item item : totalItemList){
            itemList.add(item.getCopy());
        }
    }

    /**
     * This method checks to see if the item list contains an item.
     * 
     * @param toCheck Check to see if this item is in the list
     * @return The position of the item, otherwise negative 1
     *         if the item does not exist.
     */
    public int containsItem(Item toCheck){
        for(int i = 0; i < itemList.size(); i++){
            if(itemList.get(i).equals(toCheck)){
                return i;
            }
        }
        return -1;
    }

    /**
     * Print out the information of each item in the item list
     * 
     * @return The string of information about the items.
     */
    @Override
    public String toString(){
        String out = "";
        for(Item item : itemList){
            out += item.toString();
        }
        return out;
    }

    /**
     * Return the item list.
     * 
     * @return The list of items.
     */
    public Vector<Item> getList(){
        return itemList;
    }

    /**
     * This method checks to see if the item list contains an item.
     * 
     * @param item The item to check in the item list.
     * @return True or false depending on if the list conatins the item.
     */
    public boolean contains(Item item){
        boolean out = false;
        int i = 0;

        //CHECK IF THE LIST CONTAINS THE ITEM
        while(!out && i < itemList.size()){
            //IF THE ITEM IS IN THE LIST AND THE QUANTITY IS GREATER THAN 0, OUT = TRUE
            if (itemList.get(i).equals(item) && itemList.get(i).getItemQuantity() > 0){
                out = true;
            }
            i++;
        }
        return out;
    }
}
