package TowerDefenseGame;

 
//imports
import java.awt.Toolkit;
import java.awt.geom.Point2D;
import javax.swing.JComponent;

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
     * @param army The reference to the army that this soldier will belong to.
     */
    public Pirate(Point2D.Double position, JComponent container, SoldierArmy army)
    {
        //CALLS THE SUPER CONTRUCTOR FOR THE SOLDIER
        super(position, container, army);
        
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

