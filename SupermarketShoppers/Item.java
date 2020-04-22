
/**
 * Write a description of class Item here.
 *
 * @author Cameron Costello, Kristi Boardman, Will Skelly, Jacob Burch
 * @version Spring 2020
 */
public class Item
{
    private String name;
    
    private int value;
    
    private double price;

    /**
     * Constructor for objects of class Supermarket
     */
    public Item(String name, int value, double price)
    {
        this.name = name;
        this.value = value;
        this.price = price;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int value()
    {
        return value;
    }

    public void setValue(int value)
    {
        this.value = value;
    }
    
    public double price()
    {
        return price;
    }

    public void setPrice(double price)
    {
        this.price = price;
    }

}
