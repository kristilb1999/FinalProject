// going to be lazy about imports in this class
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.geom.*;

/**
 * The Pirate Soldier.
 *
 * @author Cameron Costello, Kristi Boardman, Jacob Burch, Will Skelly
 * @version Spring 2020
 */
public class Pirate extends Soldier
{
    //THE SIZE OF THE PIRATE SOLDIER
    private static final int SIZE = 80;
    
    //THE STRENGTH OF THE PIRATE SOLDIER
    private static final int STRENGTH = 5;

    //THE SPEED OF THE PIRATE SOLDIER
    private static final int SPEED = 10;
    
    //THE AMOUNT OF POINTS THE PIRATE SOLDIER IS WORTH
    private static final int POINTS = 500;
    
    /**
     * Creates a Pirate Soldier object.
     * 
     * @param position The position of the soldier.
     * @param container The container to repaint the soldier in.
     * @param tower The reference to the tower in the game.
     */
    public Pirate(Point2D.Double position, JComponent container, TowerDefense tower)
    {
        //CALLS THE SUPER CONTRUCTOR FOR THE SOLDIER
        super(position, container, tower);
        
        //CREATES THE IMAGE OF THE PIRATE SOLDIER AND GIVES IT A SIZE
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        typeFilePath = "soldierTypeFour.png";
        type = toolkit.getImage(typeFilePath).getScaledInstance(SIZE,SIZE + SIZE/2,0);
        
        //SETS THE HEALTH OF THE SOLDIER
        hitsUntilDeath = 4;
        
        //SETS THE SPEED OF THE SOLDIER
        speed = SPEED;
        
        //SETS THE LOCATION OF THE SOLDIER AT ITS POSITION
        position.setLocation(position.x, position.y - SIZE);
    }

    /**
     * Paints the Pirate Soldier on the container at it's position.
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
     * Returns the strength of the Pirate Soldier.
     * 
     * @return The strength of this Pirate soldier.
     */
    @Override
    public int getStrength() 
    {
        return STRENGTH;
    }
    
    /**
     * Returns the size of the Pirate Soldier.
     * 
     * @return The size of this Pirate soldier.
     */
    @Override
    public int getSize() 
    {
        return SIZE;
    }
    
}

