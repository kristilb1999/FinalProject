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
            // by a pixel
            position.x += velocity.x;
            position.y += velocity.y;

            // bounce off the walls
            if (position.x  < 0) {
                position.x  = 0;
                done = true;
                velocity.x = 0;
                velocity.y = 0;
            }

            if (position.x > xMax) {
                position.x = xMax;
                done = true;
                velocity.x = 0;
                velocity.y = 0;
            }

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
            
            //remove this? FIXME
            // if we've almost stopped moving, let's say we're done
            done = (position.y == yMax &&
                Math.abs(velocity.y) < ALMOST_STOPPED &&
                Math.abs(velocity.x) < ALMOST_STOPPED);

            // gravity factor also
            velocity.y += GRAVITY;
        }

    }
}