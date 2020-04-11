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
    
    /**
     * Constructor for objects of class Weapon
     */
    public Weapon(JComponent container, Point position, Point inertia)
    {
        this.container = container;
        this.position = position;
        done = false;
        
        velocity = new Point( inertia.x / getWeight() , inertia.y / getWeight() );
    }
    
    abstract public void run();
    
    abstract public void paint(Graphics g);
    
    abstract public int getStrength();
    
    abstract public int getWeight();
    
    public boolean done() {
        return done;
    }
}
