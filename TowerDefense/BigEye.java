// going to be lazy about imports in this class
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.geom.*;

/**
 * The BigEye Soldier.
 *
 * @author Cameron Costello, Kristi Boardman, Jacob Burch, Will Skelly
 * @version Spring 2020
 */
public class BigEye extends Soldier
{
    //THE SIZE OF THE BIGEYE SOLDIER
    private static final int SIZE = 80;
    
    //THE STRENGTH OF THE BIGEYE SOLDIER
    private static final int STRENGTH = 3;
    
    //THE SPEED OF THE BIGEYE SOLDIER
    private static final int SPEED = 7;
    
    //THE AMOUNT OF POINTS THE BIGEYE SOLDIER IS WORTH
    private static final int POINTS = 300;

    /**
     * Creates a BigEye Soldier object.
     * 
     * @param position The position of the soldier.
     * @param container The container to repaint the soldier in.
     * @param tower The reference to the tower in the game.
     */
    public BigEye(Point2D.Double position, JComponent container, TowerDefense tower)
    {
        //CALLS THE SUPER CONTRUCTOR FOR THE SOLDIER
        super(position, container, tower);
        
        //CREATES THE IMAGE OF THE BIGEYE SOLDIER AND GIVES IT A SIZE
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        typeFilePath = "soldierTypeTwo.png";
        type = toolkit.getImage(typeFilePath).getScaledInstance(SIZE,SIZE + SIZE/2,0);
        
        //SETS THE HEALTH OF THE SOLDIER
        hitsUntilDeath = 2;
        
        //SETS THE SPEED OF THE SOLDIER
        speed = SPEED;
        
        //SETS THE LOCATION OF THE SOLDIER AT ITS POSITION
        position.setLocation(position.x, position.y - SIZE);
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
     * Returns the strength of the BigEye Soldier.
     * 
     * @return The strength of this BigEye soldier.
     */
    @Override
    public int getStrength() 
    {
        return STRENGTH;
    }
    
    /**
     * Returns the size of the BigEye Soldier.
     * 
     * @return The size of this BigEye soldier.
     */
    @Override
    public int getSize() 
    {
        return SIZE;
    }
    
}
