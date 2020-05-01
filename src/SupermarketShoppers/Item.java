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

    public int getItemQuantity()
    {
        return itemQuant;
    }

    public void setQuantity(int itemQuant)
    {
        this.itemQuant = itemQuant;
    }
    
    public void updateQuantity(int quantity){
        this.itemQuant -= quantity;
    }
    
    @Override
    public String toString(){
        return
            "\nItem Name: " + name + 
            ", Item Value: " + value +
            ", Item Quantity: " + itemQuant +
            ", Item Price: " + price + "\n";
    }
    
    @Override
    public boolean equals(Object other){
        if (other instanceof Item){
            return name.equals(((Item)other).getName());
        }else{
            return false;
        }

    }
}
