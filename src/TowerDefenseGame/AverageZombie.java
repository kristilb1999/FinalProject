package TowerDefenseGame;

 
// going to be lazy about imports in this class
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.geom.*;

/**
 * The AverageZombie Soldier.
 *
 * @author Cameron Costello, Kristi Boardman, Jacob Burch, Will Skelly
 * @version Spring 2020
 */
public class AverageZombie extends Soldier
{
    //THE SIZE OF THE AVERAGEZOMBIE SOLDIER
    private static final int SIZE = 80;
    
    //THE STRENGTH OF THE AVERAGEZOMBIE SOLDIER
    private static final int STRENGTH = 1;
    
    //THE SPEED OF THE AVERAGEZOMBIE SOLDIER
    private static final int SPEED = 2;
    
    //THE AMOUNT OF POINTS THE AVERAGEZOMBIE SOLDIER IS WORTH
    private static final int POINTS = 100;

    /**
     * Creates a AverageZombie Soldier object.
     * 
     * @param position The position of the soldier.
     * @param container The container to repaint the soldier in.
     * @param tower The reference to the tower in the game.
     */
    public AverageZombie(Point2D.Double position, JComponent container, SoldierArmy army)
    {
        //CALLS THE SUPER CONTRUCTOR FOR THE SOLDIER
        super(position, container, army);
        
        //CREATES THE IMAGE OF THE AVERAGEZOMBIE SOLDIER AND GIVES IT A SIZE
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        typeFilePath = "soldierTypeOne.png";
        type = toolkit.getImage(typeFilePath).getScaledInstance(SIZE,SIZE + SIZE/2,0);
        
        //SETS THE HEALTH OF THE SOLDIER
        hitsUntilDeath = 1;
        
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
     * Returns the strength of the AverageZombie Soldier.
     * 
     * @return The strength of this AverageZombie soldier.
     */
    @Override
    public int getStrength() 
    {
        return STRENGTH;
    }
    
    /**
     * Returns the size of the AverageZombie Soldier.
     * 
     * @return The size of this AverageZombie soldier.
     */
    @Override
    public int getSize() 
    {
        return SIZE;
    }
    
}
