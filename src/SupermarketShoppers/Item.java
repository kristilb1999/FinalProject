package SupermarketShoppers;


/**
 * This class gives information about each item in the store.
 *
 * @author Cameron Costello, Kristi Boardman, Will Skelly, Jacob Burch
 * @version Spring 2020
 */
public class Item
{
    private String name;
    
    private int value;
    
    private int itemQuant;
    
    private double price;
    
    /**
     * Constructor for objects of class Item
     */
    public Item(String name, int value, int itemQuant, double price)
    {
        this.name = name;
        this.value = value;
        this.price = price;
        this.itemQuant = itemQuant;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getValue()
    {
        return value;
    }

    public void setValue(int value)
    {
        this.value = value;
    }
    
    public double getPrice()
    {
        return price;
    }

    public void setPrice(double price)
    {
        this.price = price;
    }
    
    public double getItemQuantity()
    {
        return itemQuant;
    }

    public void setQuantity(int itemQuant)
    {
        this.itemQuant = itemQuant;
    }

}
