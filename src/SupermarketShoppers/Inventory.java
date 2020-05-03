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
    
    private final Vector<Item> itemList = new Vector<Item>();
    
    {
        for(Item item : totalItemList){
            itemList.add(item.getCopy());
        }
    }
    
    public int containsItem(Item toCheck){
        for(int i = 0; i < itemList.size(); i++){
            if(itemList.get(i).equals(toCheck)){
                return i;
            }
        }
        return -1;
    }

    @Override
    public String toString(){
        String out = "";
        for(Item item : itemList){
            out += item.toString();
        }
        return out;
    }

    public Vector<Item> getList(){
        return itemList;
    }

    public boolean contains(Item other){
        boolean out = false;
        int i = 0;

        while(!out && i < itemList.size()){
            if (itemList.get(i).equals(other) && itemList.get(i).getItemQuantity() > 0){
                out = true;
            }
            i++;
        }
        return out;
    }
}
