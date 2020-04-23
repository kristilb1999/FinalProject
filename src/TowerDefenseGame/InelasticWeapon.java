package TowerDefenseGame;

 
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
    //HOW LONG THE EXPLOSION LASTS FOR
    public static final int EXPLODE_TIME = 250;
    
    //DETERMINES IF A WEAPON HAS HIT AND NEEDS TO EXPLODE
    protected boolean weaponHit;
  
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
        while (!weaponHit) {
            //WHILE THE WEAPON HAS NOT BEEN RELEASED ONTO THE SCREEN
            while(!released) {
                container.repaint();
            }
            
            //SLEEP BETWEEN FRAMES
            try {
                sleep(DELAY_TIME);
            }
            catch (InterruptedException e) {
            }

            //EVERY ITERATION, UPDATE THE COORDINATES
            position.x += velocity.x;
            position.y += velocity.y;
            
            //BREAK WHEN THE WEAPON REACHES THE FLOOR
            if (position.y > yMax) {
                position.y = yMax;
                velocity.x = 0;
                velocity.y = 0;
                weaponHit = true;
            }

            //ADD GRAVITY TO THE VELOCITY
            velocity.y += GRAVITY;
        }
        
        try {
                sleep(EXPLODE_TIME);
            }
            catch (InterruptedException e) {
            }
            
            done = true;

    }
    
    /**
     * Sets when a weapon hits a soldier.
     * 
     * @param hit Whether or not the weapon was hit.
     */
    public void setWeaponHit(boolean hit)
    {
        weaponHit = hit;
    }
    
    //Based on:
    //https://www.tutorialspoint.com/design_pattern/visitor_pattern.htm
    /**
     * Accepts a WeaponVisitor to visit this weapon.
     * 
     * @param g The WeaponVisitor to visit this weapon.
     */
    @Override
    public void accept(WeaponVisitor weaponVisitor){
        weaponVisitor.visit(this);
    }
}
