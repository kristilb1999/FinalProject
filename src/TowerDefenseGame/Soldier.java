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
import java.util.Vector;
import javax.swing.JComponent;

/**
 * This abstract class outlines exactly what we expect our Soldiers to be
 * and what we expect them to do to the tower and when interacting with Weapons.
 *
 * @author Kristi Boardman, Cameron Costello, Will Skelly, Jake Burch
 * @version Spring 2020
 */
abstract public class Soldier extends Thread 
{
    //THE AMOUNT OF DISPLACEMENT TO STOP A ZOMBIE ENEMY AT
    protected static final int STOP_ZOMB = 300;

    //THE AMOUNT OF TIME BEFORE THE SOLDIER NEXT MOVES
    public static final int DELAY_TIME = 200;
    
    //THE AMOUNT OF TIME BEFORE THE SOLDIER NEXT MOVES
    public static final int DELAY_TIME_LONG = 1000;

    //THE HEALTH FOR EACH SOLDIER
    protected int hitsUntilDeath;

    //THE SPEED AT WHICH EACH SOLDIER MOVES
    protected double speed;

    //INDICATES WHEN A SOLDIER HAS LEFT THE ARMY
    protected boolean done;

    //THE IMAGE OF EACH TYPE OF SOLDIER
    protected Image type;

    //THE FILEPATH FOR EACH SOLDIER
    protected String typeFilePath;

    //THE POSITION OF EACH SOLDIER AS IT MOVES
    protected Point2D.Double position;

    //THE LIST OF WEAPONS THAT COULD HARM THE SOLDIER ON SCREEN
    protected Vector<Weapon> projectiles;

    //THE CONTAINER TO REPAINT
    protected JComponent container;

    //THE REFERENCE TO THE ARMY THIS SOLDIER IS IN
    protected SoldierArmy army;
    
    //THE WIDTH OF THE CONTAINER
    protected int containerWidth;

    /**
     * Creates a Soldier object.
     * 
     * @param position The position of the soldier.
     * @param container The container to repaint the soldier in.
     * @param tower The reference to the tower in the game.
     */
    public Soldier(Point2D.Double position, JComponent container, SoldierArmy army)
    {
        //SETTING ALL OF THE REFERENCES
        this.position = new Point2D.Double(position.x - getSize()/2 , position.y - (3*getSize())/2);
        this.container = container;
        this.army = army;
        this.containerWidth = container.getWidth();

        //THE SOLDIER IS NOT DEAD YET, HE HAS BARELY EVEN LIVED
        done = false;
        
        //GET REFERENCE TO THIS SOLDIER
        Soldier thisSoldier = this;        
        //CHECK THE CONTAINER WIDTH PERIODICALLY
        new Thread() {
            @Override
            public void run() {
                while(!thisSoldier.done){
                    try{
                        sleep(DELAY_TIME_LONG);
                    } catch (InterruptedException e){
                        System.out.print(e);
                    }

                    thisSoldier.containerWidth = container.getWidth();
                }
            }
        }.start(); 
    } 

    /**
     * The soldier moves across the screen, can be hit by weapons which cause damage
     * to their health, or reach the tower where they will sacrifice their lives to do
     * damage.
     */
    public void run(){
        while (!done) {
            //DELAY BETWEEN FRAMES OF MOVEMENT
            try {
                sleep(DELAY_TIME);
            }
            catch (InterruptedException e) {
            }

            //EVERY ITERATION, UPDATE THE COORDINATES
            position.x += speed;
            
            //THE INITIAL NUMBER OF HITS THE ZOMBIE RECIEVES EACH TIME IT WALKS IS ZERO
            int numHits = 0;

            //THE INDEX, TO STEP THROUGH THE LIST OF WEAPONS
            int i = 0;

            //IF ANY NUMBER OF WEAPON HAS HIT THE ZOMBIE AS EVERYTHING MOVED, THE NUMBER
            //OF HITS TAKEN BY THAT ZOMBIE INCREASES BY THAT NUMBER OF WEAPONS
            while (i < projectiles.size()) {
                Weapon w = projectiles.get(i);
                if (contains(w)){
                    numHits++;
                    w.accept(new WeaponCollisionVisitor());
                }
                i++;
            }

            //UPDATE THE HEALTH OF THAT ZOMBIE BASED ON HOW MUCH IT WAS HIT
            hitsUntilDeath -= numHits;
            
            //UPDATES THE SCORE
            army.updateScore(getPoints() * numHits);

            //IF THE ZOMBIE HAS REACHED THE TOWER, STOP MOVING AND DIE
            //MAYBE WE CAN MAKE IT SO THAT THEY DO NOT DIE WHEN THEY REACH THE TOWER,
            //MAYBE THEY SHOULD JUST DO DAMAGE UNTIL THEY ARE KILLED?
            if (position.x > containerWidth - STOP_ZOMB)
            {
                army.damageEnemy(getStrength());
                done = true;
            }

            //IF THE ZOMBIE IS DEAD, THEN HE HAS OUTRUN HIS USEFULNESS AND
            //IS BANISHED FROM THE ARMY
            if(hitsUntilDeath <= 0) {
                done = true;
            }
        }
    }

    /**
     * Paints the Soldier on the container at it's position.
     * 
     * @param g The graphics object.
     */
    public void paint(Graphics g){
        if(!done) {
            g.drawImage(type, (int)position.x, (int)position.y, null);
        }
    }

    /**
     * Returns the strength of the Soldier.
     * 
     * @return The strength of this soldier.
     */
    abstract public int getStrength();

    /**
     * Returns the size of the Soldier.
     * 
     * @return The size of this soldier.
     */
    abstract public int getSize();

    /**
     * Sets the projectile list to the list of weapons on screen.
     * 
     * @param wList The list of weapons on screen.
     */
    public void setWeapons(Vector<Weapon> wList){
        projectiles = wList;
    }

    /**
     * Each soldier takes damage if they are hit by a weapon.
     * 
     * @param weapon Check if the Soldier was hit by this weapon.
     */
    public boolean contains(Weapon weapon){
        //THE WEAPON CANNOT ARBITRARILY HIT THE SOLDIER
        boolean hit = false;

        //THE POSITION OF THE CENTER OF THE WEAPON
        Point2D.Double wPos = weapon.getPosition();
        Point2D.Double sPos = position;
        int sSize = getSize();
        int wSize = weapon.getSize();
        //THIS METHOD NEEDS TO BE FIXED, IT IS WORKING ON THE ASSUMPTION THAT THE WEAPON
        //POSITION IS THE UPPERLEFT CORNER, BUT THE WEAPON CLASS ASSIGNS THE CENTER
        //OF THE WEAPON TO THE POSITION INSTANCE VARIABLE

        //untested
        //need to check if upperleft issue exists for Soldier class
        double upperDiff = wPos.y - sPos.y;
        double bottomDiff = (wPos.y + wSize) - sPos.y;
        double leftDiff = wPos.x - sPos.x;
        double rightDiff = (wPos.x + wSize) - sPos.x;
        double vertSSize = (3 * sSize / 2);
        //in case the Weapon has greater dimensions than the Soldier
        double greaterVertSize = (vertSSize > wSize ? vertSSize : wSize);
        double greaterHorizSize = (sSize > wSize ? sSize : wSize);
        return ((
        //checking if Weapon trailing y edge is in the Soldier's y bounds
        (vertSSize > upperDiff && upperDiff >= 0) || 
        //checking if Weapon leading y edge is in the Soldier's y bounds
        //checking if Soldier trailing y edge is in the Weapon's y bounds
        (greaterVertSize >= bottomDiff && bottomDiff >= 0)
        ) && (
        //checking if Weapon trailing x edge is in the Soldier's x bounds
        (sSize > leftDiff && leftDiff >= 0) ||
        //checking if Weapon leading x edge is in the Soldier's x bounds
        //checking if Soldier trailing x edge is in the Weapon's x bounds
        (greaterHorizSize >= rightDiff && rightDiff >= 0)
        ));
    }
    
    /**
     * Kills each soldier thread.
     */
    public void kill() {
        //KILLS EVERY SOLDIER THREAD
        done = true;
    }
    
    /**
     * Gets the amount of points each soldier is worth.
     * 
     * @return The amount of points each soldier is worth.
     */
    abstract public int getPoints();
    
    /**
     * Returns whether or not the Soldier is done.
     * 
     * @return Whether the Soldier's work is done or not.
     */
    public boolean done() {
        return done;
    }
    
    /**
     * Calls reduce on towerHealthBar to reduce the health stored in the
     * HealthBar of the enemy.
     * 
     * @param damage the amount to reduce the HealthBar of the enemy by
     */
    protected void damageEnemy(int damage){
        army.damageEnemy(damage);
    }

}
