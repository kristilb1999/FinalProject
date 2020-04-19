// going to be lazy about imports in this class
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.geom.*;

/**
 * This abstract class outlines exactly what we expect our weapons to be
 * and what we expect them to do to the enemies.
 *
 * @author Kristi Boardman, Cameron Costello, Will Skelly, Jake Burch
 * @version Spring 2020
 */
abstract public class Weapon extends Thread
{
    //DELAY TIME BETWEEN FRAMES OF ANIMATION (MS)
    public static final int DELAY_TIME = 33;

    //WHAT IS SLOW ENOUGH TO CONSIDER A WEAPON "STOPPED"
    public static final double ALMOST_STOPPED = 0.4;

    //AMOUNT TO ADD TO THE VELOCITY IN THE Y DIRECTION TO SIMULATE GRAVITY
    public static final double GRAVITY = 0.5;

    //HOW MUCH MOMENTUM IS LOST ON BOUNCE
    public static final double DAMPING = 0.8;

    //THE CONTAINER TO REPAINT
    protected JComponent container;

    //THE POSITION OF THE WEAPON AND ITS VELOCITY
    protected Point2D.Double position;
    protected Point2D.Double velocity;

    //THE WEAPONS IMAGE
    protected Image type;

    //THE FILE PATH FOR THAT IMAGE
    protected String typeFilePath;

    //WHETHER OR NOT THE IMAGE HAS BOUNCED
    protected boolean bounced;

    //WHETHER OR NOT THE WEAPON HAS COMPLETED ITS FUNCTION
    protected boolean done;

    //MAX COORDINATES THE UPPER LEFT COORDINATE CAN REACH
    protected int xMax, yMax;

    /**
     * Creates a Weapon and puts it in the container.
     * 
     * @param container The container to paint it in.
     * @param position The weapon's current position.
     * @param inertia Determines object velocity.
     */
    public Weapon(JComponent container, Point2D.Double position, Point2D.Double inertia)
    {
        //SET THE CONTAINER TO REPAINT AND ENSURE WEAPON IS NOT FINISHED YET
        this.container = container;
        done = false;

        //SETS POSITION OF WEAPON TO ITS CENTER
        this.position = new Point2D.Double(position.x - getSize()/2 , position.y - getSize()/2);

        //SETS THE MAX COORDINATES TO LESS THAN THE HEIGHT AND WIDTH BY THE SIZE OF THE WEAPON
        this.yMax = container.getHeight() - getSize();

        //SETS VELOCITY OF OBJECT BASED ON ITS INERTIA
        velocity = new Point2D.Double( inertia.x / getWeight() , inertia.y / getWeight() );
    }

    /**
     * Moves the weapon across the screen based on its own velocity and the pull of gravity.
     * Weapons may bounce off of the ceiling and the floor.
     */
    public void run(){
        while (!done) {
            //SLEEP BETWEEN REDRAWING FRAMES
            try {
                sleep(DELAY_TIME);
            }
            catch (InterruptedException e) {
            }

            //EVERY ITERATION, UPDATE COORDINATES
            position.x += velocity.x;
            position.y += velocity.y;

            //bounced = false;

            //BOUNCE OFF OF THE FLOOR
            if (position.y > yMax) {
                position.y = yMax;
                bounced = true;
                velocity.y = -velocity.y;
            }

            //IF WE BOUNCED, WE'RE GOING TO DAMPEN SPEED IN BOTH DIMENSIONS
            if (bounced) {
                velocity.x *= DAMPING;
                velocity.y *= DAMPING;
                
            }

            //IF WE'VE ALMOST STOPPED MOVING, LET'S END
            done = (position.y == yMax &&
                Math.abs(velocity.y) < ALMOST_STOPPED &&
                Math.abs(velocity.x) < ALMOST_STOPPED);

            //ADD IN GRAVITY TO THE VELOCITY
            velocity.y += GRAVITY;
        }
        
    }

    /**
     * Returns the size of the weapon.
     * 
     * @return The size of the weapon object.
     */
    abstract public int getSize();

    /**
     * Paints the object in the panel.
     * 
     * @param g The graphics object.
     */
    abstract public void paint(Graphics g);

    /**
     * Returns the weight of the weapon.
     * 
     * @return The weight of the weapon object.
     */
    abstract public int getWeight();

    /**
     * Returns whether or not the weapon is finished.
     * 
     * @return Whether or not the weapon is done.
     */
    public boolean done() {
        return done;
    }
    
    /**
     * Returns the Weapon's current position.
     * 
     * @return the Weapon's current position.
     */
    public Point2D.Double getPosition(){
        return position;
    }
    
    /**
     * Returns the Weapon's current position.
     * 
     * @return the Weapon's current position.
     */
    public Point2D.Double getUpperLeft(){
        return new Point2D.Double(
        position.x - getSize()/ 2.0 , 
        position.y - getSize() / 2.0);
    }
    
    //Based on:
    //https://www.tutorialspoint.com/design_pattern/visitor_pattern.htm
    /**
     * Accepts a WeaponVisitor to visit this weapon.
     * 
     * @param g The WeaponVisitor to visit this weapon.
     */
    public void accept(WeaponVisitor weaponVisitor){
        weaponVisitor.visit(this);
    }
}
