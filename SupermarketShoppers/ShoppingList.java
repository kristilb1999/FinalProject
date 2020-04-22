
import java.util.Vector;
import java.io.File;
import java.io.BufferedReader;
import java.util.StringTokenizer;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
/**
 * This class will read in a text file containing each item that a shopper can buy.
 *
 * @author Cameron Costello, Kristi Boardman, Will Skelly, Jacob Burch
 * @version Spring 2020
 */
public class ShoppingList
{

    private static final String FILE_NAME = "itemsInStore.txt";

    private Vector<Item> itemList;

    /**
     * Constructor for objects of class ShoppingList
     */
    public ShoppingList()
    {
        this.itemList = new Vector<Item>();
    }

    public void readInItems()
    {
     
        try{
            BufferedReader br = new BufferedReader(new FileReader(new File(FILE_NAME)));
            String line;
            while((line = br.readLine()) != null)
            {
                StringTokenizer tokenizer = new StringTokenizer(line);
                Item item = new Item(tokenizer.nextToken(), Integer.parseInt(tokenizer.nextToken()), Double.parseDouble(tokenizer.nextToken()));
                itemList.add(item);
            }
        }catch(FileNotFoundException e){
            System.err.println("File Not Found: " + FILE_NAME);
        }catch(IOException e){
            System.err.println("IO Exception: " + e);
        }

    }

}
