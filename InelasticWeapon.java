// going to be lazy about imports in this class
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.geom.*;

/**
 * description
 *
 * @author Kristi Boardman, Cameron Costello, Will Skelly, Jake Burch
 * @version Spring 2020
 */
abstract public class InelasticWeapon extends Weapon
{
    /**
     * Constructor for objects of class InelasticWeapon
     */
    public InelasticWeapon(JComponent container, Point2D.Double position, Point2D.Double inertia)
    {
        super(container,position,inertia);
    }

    @Override
    public void run(){
        while (!done) {

            try {
                sleep(DELAY_TIME);
            }
            catch (InterruptedException e) {
            }

            // every iteration, update the coordinates
            position.x += velocity.x;
            position.y += velocity.y;

            if (position.y < 0) {
                position.y = 0;
                done = true;
                velocity.x = 0;
                velocity.y = 0;
            }

            if (position.y > yMax) {
                position.y = yMax;
                done = true;
                velocity.x = 0;
                velocity.y = 0;
            }

            // gravity factor also
            velocity.y += GRAVITY;
        }

    }
}
