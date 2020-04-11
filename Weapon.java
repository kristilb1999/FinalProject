// going to be lazy about imports in this class
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;

/**
 * Write a description of class Weapon here.
 *
 * @author Kristi Boardman, Cameron Costello, Will Skelly, Jake Burch
 * @version Spring 2020
 */
abstract public class Weapon extends Thread
{
    // delay time between frames of animation (ms)
    public static final int DELAY_TIME = 33;

    protected JComponent container;

    protected Point position;
    protected Point velocity;

    protected Image type;

    protected String typeFilePath;

    protected boolean bounced;

    protected boolean done;

    // what is slow enough to consider to be stopped?
    public static final double ALMOST_STOPPED = 0.4;

    // what to add to ySpeed to simulate gravity?
    public static final double GRAVITY = 0.3;

    // how much momentum to lose on a bounce
    public static final double DAMPING = 0.9;

    // max allowed coordinates of the upper left corner
    protected int xMax, yMax;

    /**
     * Constructor for objects of class Weapon
     */
    public Weapon(JComponent container, Point position, Point inertia)
    {
        this.container = container;
        this.position = position;
        done = false;

        this.position = new Point(position.x - getSize()/2 , position.y / getSize()/2);
        this.yMax = container.getHeight() - getSize();
        this.xMax = container.getWidth() - getSize();

        velocity = new Point( inertia.x / getWeight() , inertia.y / getWeight() );
    }

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

            boolean bounced = false;
            // bounce off the walls
            if (position.x  < 0) {
                position.x  = 0;
                bounced = true;
                velocity.x = -velocity.x;
            }

            if (position.x > xMax) {
                position.x = xMax;
                bounced = true;
                velocity.x = -velocity.x;
            }

            if (position.y < 0) {
                position.y = 0;
                bounced = true;
                velocity.y = -velocity.y;
            }

            if (position.y > yMax) {
                position.y = yMax;
                bounced = true;
                velocity.y = -velocity.y;
            }

            // if we bounced, we're going to dampen speed in both dimensions
            if (bounced) {
                velocity.x *= DAMPING;
                velocity.y *= DAMPING;
            }

            // if we've almost stopped moving, let's say we're done
            done = (position.y == yMax &&
                Math.abs(velocity.y) < ALMOST_STOPPED &&
                Math.abs(velocity.x) < ALMOST_STOPPED);

            // gravity factor also
            velocity.y += GRAVITY;
        }
    }

    abstract public int getSize();

    abstract public void paint(Graphics g);

    abstract public int getStrength();

    abstract public int getWeight();

    public boolean done() {
        return done;
    }
}
