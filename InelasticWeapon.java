// going to be lazy about imports in this class
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.geom.*;

/**
 * This class contains the run method for the weapons that cannot bounce.
 *
 * @author Kristi Boardman, Cameron Costello, Will Skelly, Jake Burch
 * @version Spring 2020
 */
abstract public class InelasticWeapon extends Weapon
{
    /**
     * Creates a weapon with the inability to bounce.
     */
    public InelasticWeapon(JComponent container, Point2D.Double position, Point2D.Double inertia)
    {
        //CALL THE CONSTRUCTOR FOR THE WEAPON
        super(container,position,inertia);
    }

    /**
     * Our inelastic weapon will move across the screen, and will finish when it
     * hits the floor.
     */
    @Override
    public void run(){
        while (!done) {
            //SLEEP BETWEEN FRAMES
            try {
                sleep(DELAY_TIME);
            }
            catch (InterruptedException e) {
            }

            //EVERY ITERATION, UPDATE THE COORDINATES
            position.x += velocity.x;
            position.y += velocity.y;

            //WHEN THIS PART IS COMMENTED OUT, THE WEAPON WILL FLY PAST THE BORDER
            //OF THE SCREEN AND COME BACK DOWN TOWARD THE GRAVITY ONCE ITS VELOCITY
            //BECOMES NEGATIVE -- DO WE LIKE THIS? I LIKE THIS.
            
            // //BREAK WHEN THE WEAPON REACHES THE CIELING
            // if (position.y < 0) {
                // position.y = 0;
                // done = true;
                // velocity.x = 0;
                // velocity.y = 0;
            // }

            //BREAK WHEN THE WEAPON REACHES THE FLOOR
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
