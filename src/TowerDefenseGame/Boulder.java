package TowerDefenseGame;

 
//imports
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.geom.Point2D;
import javax.swing.JComponent;

/**
 * Boulder is a Weapon, it can bounce off of the floor.
 *
 * @author Kristi Boardman, Cameron Costello, Will Skelly, Jake Burch
 * @version Spring 2020
 */
public class Boulder extends Weapon
{

    //THE WEIGHT OF EVERY BOULDER WEAPON
    private static final int WEIGHT = 8;

    //THE SIZE OF EVERY BOULDER WEAPON
    private static final int SIZE = 50;

    /**
     * Creates a Boulder Weapon.
     * 
     * @param container The container to paint it in.
     * @param position The weapon's current position.
     * @param inertia Determines object velocity.
     */
    public Boulder(JComponent container, Point2D.Double position, Point2D.Double inertia)
    {
        //CALL THE SUPER CONSTRUCTOR OF THE WEAPON CLASS
        super(container, position, inertia);
        
        //CREATES THE BOULDER IMAGE
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        typeFilePath = "weaponTypeFour.png";
        type = toolkit.getImage(typeFilePath).getScaledInstance(SIZE,SIZE,0);
    }

    /**
     * Paints the Boulder weapon on the container at its position.
     * 
     * @param g The graphics object.
     */
    @Override
    public void paint(Graphics g) {
        
            g.drawImage(type, (int)position.x, (int)position.y, null);
        
    }

    /**
     * Returns the weight of the Boulder weapon.
     * 
     * @return The weight of the Boulder weapon object.
     */
    @Override
    public int getWeight(){
        return WEIGHT;
    }

    /**
     * Returns the size of the Boulder weapon.
     * 
     * @return The size of the Boulder weapon object.
     */
    @Override
    public int getSize(){
        return SIZE;
    }
}
