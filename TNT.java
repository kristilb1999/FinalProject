// going to be lazy about imports in this class
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.geom.*;

/**
 * TNT is an inelastic weapon, it cannot bounce.
 *
 * @author Kristi Boardman, Cameron Costello, Will Skelly, Jake Burch
 * @version Spring 2020
 */
public class TNT extends InelasticWeapon
{

    //THE WEIGHT OF EVERY TNT WEAPON
    private static final int WEIGHT = 10;

    //THE SIZE OF EVERY TNT WEAPON
    private static final int SIZE = 50;

    /**
     * Creates a TNT InelasticWeapon.
     * 
     * @param container The container to paint it in.
     * @param position The weapon's current position.
     * @param inertia Determines object velocity.
     */
    public TNT(JComponent container, Point2D.Double position, Point2D.Double inertia)
    {
        //CALL THE SUPER CONSTRUCTOR FOR INELASTICWEAPOM
        super(container, position, inertia);
        
        //CREATE THE TNT IMAGE AND GIVE IT A SIZE
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        typeFilePath = "weaponTypeThree.png";
        type = toolkit.getImage(typeFilePath).getScaledInstance(SIZE,SIZE,0);
    }

    /**
     * Paints the TNT weapon on the container at its position.
     * 
     * @param g The graphics object.
     */
    @Override
    public void paint(Graphics g) {
        //IF THE TNT HAS NOT HIT ANYTHING, DRAW IT.
        if (!weaponHit)
        {
            g.drawImage(type, (int)position.x, (int)position.y, null);
        }
        else
        {
            //DRAW THE EXPLOSION
            g.setColor(Color.ORANGE);
            g.fillOval((int)position.x - SIZE/4, (int)position.y - SIZE/2, SIZE * 3/2, SIZE * 3/2);
            g.setColor(Color.BLACK);
            g.drawOval((int)position.x - SIZE/4, (int)position.y - SIZE/2, SIZE * 3/2, SIZE * 3/2);

            g.setColor(Color.ORANGE);
            g.fillOval((int)position.x + SIZE/16, (int)position.y - SIZE, SIZE, SIZE);
            g.setColor(Color.BLACK);
            g.drawOval((int)position.x + SIZE/16, (int)position.y - SIZE, SIZE, SIZE);         

            g.setColor(Color.ORANGE);
            g.fillOval((int)position.x + SIZE, (int)position.y + SIZE/10, SIZE, SIZE);
            g.setColor(Color.BLACK);
            g.drawOval((int)position.x + SIZE, (int)position.y + SIZE/10, SIZE, SIZE);

            g.setColor(Color.ORANGE);
            g.fillOval((int)position.x - SIZE, (int)position.y + SIZE/10, SIZE, SIZE);
            g.setColor(Color.BLACK);
            g.drawOval((int)position.x - SIZE, (int)position.y + SIZE/10, SIZE, SIZE);

        }
    }

    /**
     * Returns the weight of the TNT weapon.
     * 
     * @return The weight of the TNT weapon object.
     */
    @Override
    public int getWeight(){
        return WEIGHT;
    }

    /**
     * Returns the size of the TNT weapon.
     * 
     * @return The size of the TNT weapon object.
     */
    @Override
    public int getSize(){
        return SIZE;
    }
}
