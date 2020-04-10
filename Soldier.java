// going to be lazy about imports in this class
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;

/**
 * Write a description of class Soldier here.
 *
 * @author Kristi Boardman, Cameron Costello, Will Skelly, Jake Burch
 * @version Spring 2020
 */
abstract public class Soldier extends Thread
{
    protected int speed;
    protected int damage;
    protected int hitsUntilDeath;
    
    protected Image type;
    
    protected String typeFilePath;
    
    protected Point position;
    
    protected boolean done;
    
    protected JComponent container;
    
    /**
     * Constructor for objects of class Soldier
     */
    public Soldier(int speed, int damage, int hitsUntilDeath, Image type, String typeFilePath, Point position, JComponent container)
    {
        this.speed = speed;
        this.damage = damage;
        this.hitsUntilDeath = hitsUntilDeath;
        this.type = type;
        this.typeFilePath = typeFilePath;
        this.position = position;
        this.container = container;
        done = false;
    } 
    
    abstract public void run();
    
    abstract public void paint(Graphics g);
    
    public boolean done() {
        return done;
    }
    
}
