/*
 * Copyright (C) 2020 William Skelly, Kristi Boardman, Cameron Costello, and Jacob Burch
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package TowerDefenseGame;

 
//imports
import java.awt.Graphics;
import java.awt.Image;
import java.awt.geom.Point2D;
import javax.swing.JComponent;

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
    
    //WHETHER THE WEAPON IS STILL BEING DRAGGED ON THE SCREEN OR NOT
    protected boolean released;

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
            //WHEN THE WEAPON HAS NOT BEEN RELEASED ONTO THE SCREEN
            while(!released) {
                container.repaint();
            }
            
            //SLEEP BETWEEN REDRAWING FRAMES
            try {
                sleep(DELAY_TIME);
            }
            catch (InterruptedException e) {
            }

            //EVERY ITERATION, UPDATE COORDINATES
            position.x += velocity.x;
            position.y += velocity.y;

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
     * Sets the released boolean of the weapon.
     * 
     * @param released Whether or not the weapon has been released.
     */
    public void setReleased(boolean released) {
        this.released = released;
    }
    
    /**
     * Sets the position of the weapon.
     * 
     * @param position The position of the weapon.
     */
    public void setWeaponPosition(Point2D.Double position) {
        this.position = new Point2D.Double(position.x - getSize()/2 , position.y - getSize()/2);
    }
    
    /**
     * Sets the velocity of the weapon based on inertia.
     * 
     * @param intertia The inertia of the weapon.
     */
    public void setWeaponInertia(Point2D.Double inertia){
        velocity = new Point2D.Double( inertia.x / getWeight() , inertia.y / getWeight() );
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
     * Returns the center of the Weapon.
     * 
     * @return the center of the Weapon.
     */
    public Point2D.Double getCenter(){
        return new Point2D.Double(
        position.x + getSize()/ 2.0 , 
        position.y + getSize() / 2.0);
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
