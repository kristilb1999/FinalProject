// going to be lazy about imports in this class
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.geom.*;

/**
 * Grenade is a Weapon, it can bounce off of the floor.
 *
 * @author Kristi Boardman, Cameron Costello, Will Skelly, Jake Burch
 * @version Spring 2020
 */
public class Grenade extends Weapon
{
    //THE STRENGTH OF EVERY GRENADE WEAPON
    private static final int STRENGTH = 10;

    //THE WEIGHT OF EVERY GRENADE WEAPON
    private static final int WEIGHT = 10;

    //THE SIZE OF EVERY GRENADE WEAPON
    private static final int SIZE = 50;

    /**
     * Creates a Grenade Weapon.
     * 
     * @param container The container to paint it in.
     * @param position The weapon's current position.
     * @param inertia Determines object velocity.
     */
    public Grenade(JComponent container, Point2D.Double position, Point2D.Double inertia)
    {
        //CALL THE SUPER CONSTRUCTOR OF THE WEAPON
        super(container, position, inertia);
        
        //CREATES THE GRENADE IMAGE
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        typeFilePath = "weaponTypeTwo.jpg";
        type = toolkit.getImage(typeFilePath).getScaledInstance(SIZE,SIZE,0);
    }

    /**
     * Paints the Grenade weapon on the container at its position.
     * 
     * @param g The graphics object.
     */
    @Override
    public void paint(Graphics g) {

        g.drawImage(type, (int)position.x, (int)position.y, null);

    }

    /**
     * Returns the strength of the Grenade weapon.
     * 
     * @return The strength of the Grenade weapon.
     */
    @Override
    public int getStrength(){
        return STRENGTH;   
    }

    /**
     * Returns the weight of the Grenade weapon.
     * 
     * @return The weight of the Grenade weapon object.
     */
    @Override
    public int getWeight(){
        return WEIGHT;
    }

    /**
     * Returns the size of the Grenade weapon.
     * 
     * @return The size of the Grenade weapon object.
     */
    @Override
    public int getSize(){
        return SIZE;
    }
}
