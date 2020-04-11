// going to be lazy about imports in this class
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.geom.*;

/**
 * Write a description of class TNT here.
 *
 * @author Kristi Boardman, Cameron Costello, Will Skelly, Jake Burch
 * @version Spring 2020
 */
public class TNT extends InelasticWeapon
{
    private static final int STRENGTH = 10;

    private static final int WEIGHT = 10;

    private static final int SIZE = 50;

    /**
     * Constructor for objects of class TNT
     */
    public TNT(JComponent container, Point2D.Double position, Point2D.Double inertia)
    {
        super(container, position, inertia);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        typeFilePath = "weaponTypeThree.jpg";
        type = toolkit.getImage(typeFilePath).getScaledInstance(SIZE,SIZE,0);
    }

    @Override
    public void paint(Graphics g) {
        if(!bounced) {
            g.drawImage(type, (int)position.x, (int)position.y, null);
        } else {

        }
    }

    @Override
    public int getStrength(){
        return STRENGTH;   
    }

    @Override
    public int getWeight(){
        return WEIGHT;
    }

    @Override
    public int getSize(){
        return SIZE;
    }
}
