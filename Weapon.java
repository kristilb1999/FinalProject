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
    
    protected int strength;
    protected int weight;
    
    protected boolean bounced;
    
    protected boolean done;
    
    /**
     * Constructor for objects of class Weapon
     */
    public Weapon(JComponent container, Point position, int strength, int weight)
    {
        this.container = container;
        this.position = position;
        this.strength = strength;
        this.weight = weight;
        done = false;
    }
    
    abstract public void run();
    
    abstract public void paint(Graphics g);
    
    public boolean done() {
        return done;
    }
}
