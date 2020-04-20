// going to be lazy about imports in this class
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.geom.*;

/**
 * The Hunchback Soldier.
 *
 * @author Cameron Costello, Kristi Boardman, Jacob Burch, Will Skelly
 * @version Spring 2020
 */
public class Hunchback extends Soldier
{
    //THE SIZE OF THE HUNCHBACK SOLDIER
    private static final int SIZE = 80;
    
    //THE STRENGTH OF THE HUNCHBACK SOLDIER
    private static final int STRENGTH = 2;
    
    //THE SPEED OF THE HUNCHBACK SOLDIER
    private static final int SPEED = 5;
    
    //THE AMOUNT OF POINTS THE HUNCHBACK SOLDIER IS WORTH
    private static final int POINTS = 200;

    /**
     * Creates a Hunchback Soldier object.
     * 
     * @param position The position of the soldier.
     * @param container The container to repaint the soldier in.
     * @param tower The reference to the tower in the game.
     */
    public Hunchback(Point2D.Double position, JComponent container, TowerDefense tower)
    {
        //CALLS THE SUPER CONTRUCTOR FOR THE SOLDIER
        super(position, container, tower);
        
        //CREATES THE IMAGE OF THE HUNCHBACK SOLDIER AND GIVES IT A SIZE
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        typeFilePath = "soldierTypeThree.jpg";
        type = toolkit.getImage(typeFilePath).getScaledInstance(SIZE,SIZE + SIZE/2,0);
        
        //SETS THE HEALTH OF THE SOLDIER
        hitsUntilDeath = 3;
        
        //SETS THE SPEED OF THE SOLDIER
        speed = SPEED;
        
        //SETS THE LOCATION OF THE SOLDIER AT ITS POSITION
        position.setLocation(position.x, position.y - SIZE);
    }

    /**
     * Paints the Hunchback Soldier on the container at it's position.
     * 
     * @param g The graphics object.
     */
    @Override
    public void paint(Graphics g) 
    {
            if(!done) {
            g.drawImage(type, (int)position.x, (int)position.y, null);
        }
    }
    
    /**
     * Gets the amount of points each soldier is worth.
     * 
     * @return The amount of points each soldier is worth.
     */
    @Override
    public int getPoints() {
        return POINTS;
    }
    
    /**
     * Returns the strength of the Hunchback Soldier.
     * 
     * @return The strength of this Hunchback soldier.
     */
    @Override
    public int getStrength() 
    {
        return STRENGTH;
    }
    
    /**
     * Returns the size of the Hunchback Soldier.
     * 
     * @return The size of this Hunchback soldier.
     */
    @Override
    public int getSize() 
    {
        return SIZE;
    }
    
}
