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

    private static final InputStream ITEM_STREAM = Inventory.class.getResourceAsStream("/itemsInStore.txt");

    private static final Vector<Item> totalItemList = new Vector<Item>();
    
    static{
        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(ITEM_STREAM));
            String line;
            while((line = br.readLine()) != null)
            {
                StringTokenizer tokenizer = new StringTokenizer(line);
                Item item = new Item(tokenizer.nextToken(), Integer.parseInt(tokenizer.nextToken()), Integer.parseInt(tokenizer.nextToken()), Double.parseDouble(tokenizer.nextToken()));
                totalItemList.add(item);
            }
        }catch(FileNotFoundException e){
            System.err.println("Resource Not Found: " + ITEM_STREAM.toString());
        }catch(IOException e){
            System.err.println("IO Exception: " + e);
        }
    }
    
    private final Vector<Item> itemList = new Vector<Item>();
    
    {
        for(Item item : totalItemList){
            itemList.add(item.getCopy());
        }
    }

    /**
     * Constructor for objects of class ShoppingList
     */
    public Inventory()
    {
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
