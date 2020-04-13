// going to be lazy about imports in this class
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

/**
 * Write a description of class Soldier here.
 *
 * @author Kristi Boardman, Cameron Costello, Will Skelly, Jake Burch
 * @version Spring 2020
 */
abstract public class Soldier extends Thread 
{
    protected double speed;
    protected int damage;
    protected int hitsUntilDeath;

    protected Image type;

    protected String typeFilePath;

    protected Point2D.Double position;

    protected boolean done;

    protected JComponent container;
    
    public static final int DELAY_TIME = 33;
    
    public static final File imageIn = new File("towerImage.png");
    //public static final BufferedImage tower =  ImageIO.read(imageIn);
    
    //public static final int IMAGE_SIZE;

    /**
     * Constructor for objects of class Soldier
     */
    public Soldier(Point2D.Double position, JComponent container)
    {
        this.position = position;
        this.container = container;
        done = false;
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
            position.x += speed;

            if (position.x < container.getWidth() - 325)
            {
                done = true;
            }
            
            container.repaint();
        }

    }

    abstract public void paint(Graphics g);
    
    abstract public int getStrength();
    
    abstract public int getSize();
   
    public boolean done() {
        return done;
    }

}
