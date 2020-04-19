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

    //THE HEALTH FOR EACH SOLDIER
    protected int hitsUntilDeath;

    //THE SPEED AT WHICH EACH SOLDIER MOVES
    protected double speed;

    //INDICATES WHEN A SOLDIER HAS LEFT THE ARMY
    protected boolean done;

    //INDICATES WHETHER THE SOLDIER HAS BEEN HIT BY A WEAPON
    protected boolean hit;

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

    //THE REFERENCE TO THE TOWER IN THE GAME
    protected TowerDefense tower;

    /**
     * Creates a Soldier object.
     * 
     * @param position The position of the soldier.
     * @param container The container to repaint the soldier in.
     * @param tower The reference to the tower in the game.
     */
    public Soldier(Point2D.Double position, JComponent container, TowerDefense tower)
    {
        //SETTING ALL OF THE REFERENCES
        this.position = position;
        this.container = container;
        this.tower = tower;

        //THE SOLDIER IS NOT DEAD YET, HE HAS BARELY EVEN LIVED
        done = false;
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
            //container.repaint();

            //THE INITIAL NUMBER OF HITS THE ZOMBIE RECIEVES EACH TIME IT WALKS IS ZERO
            int numHits = 0;

            //THE INDEX, TO STEP THROUGH THE LIST OF WEAPONS
            int i = 0;

            //IF ANY NUMBER OF WEAPON HAS HIT THE ZOMBIE AS EVERYTHING MOVED, THE NUMBER
            //OF HITS TAKEN BY THAT ZOMBIE INCREASES BY THAT NUMBER OF WEAPONS
            while (i < projectiles.size()) {
                Weapon w = projectiles.get(i);
                contains(w);
                if (hit){
                    numHits++;
                    // if (w instanceof InelasticWeapon)
                    // {
                        // InelasticWeapon weapon = (InelasticWeapon) w;
                        // weapon.setWeaponHit(true);
                    // }
                    
                    //Visitor pattern structure based on:
                    //https://www.tutorialspoint.com/design_pattern/visitor_pattern.htm
                    w.accept(new WeaponCollisionVisitor());
                }
                i++;
            }

            //UPDATE THE HEALTH OF THAT ZOMBIE BASED ON HOW MUCH IT WAS HIT
            hitsUntilDeath -= numHits;

            //RESET HIT TO FALSE FOR NEXT MOVEMENT
            hit = false;

            //IF THE ZOMBIE HAS REACHED THE TOWER, STOP MOVING AND DIE
            //MAYBE WE CAN MAKE IT SO THAT THEY DO NOT DIE WHEN THEY REACH THE TOWER,
            //MAYBE THEY SHOULD JUST DO DAMAGE UNTIL THEY ARE KILLED?
            if (position.x > container.getWidth() - STOP_ZOMB)
            {
                tower.modifyTowerHealth(getStrength());
                done = true;
            }

            //IF THE ZOMBIE IS DEAD, THEN HE HAS OUTRUN HIS USEFULNESS AND
            //IS BANISHED FROM THE ARMY
            if( hitsUntilDeath <= 0) {
                done = true;
            }

            //REPAINT THE SCENE
            //container.repaint();
        }
        //REPAINT THE SCENE
        //container.repaint();
    }

    /**
     * Paints the Soldier on the container at it's position.
     * 
     * @param g The graphics object.
     */
    abstract public void paint(Graphics g);

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
    public void getWeapons(Vector<Weapon> wList){
        projectiles = wList;
    }

    /**
     * Each soldier takes damage if they are hit by a weapon.
     * 
     * @param weapon Check if the Soldier was hit by this weapon.
     */
    public void contains(Weapon weapon){
        //THE WEAPON CANNOT ARBITRARILY HIT THE SOLDIER
        hit = false;
        
        //THE POSITION OF THE CENTER OF THE WEAPON
        Point2D.Double damage = weapon.position;
        
        //THIS METHOD NEEDS TO BE FIXED, IT IS WORKING ON THE ASSUMPTION THAT THE WEAPON
        //POSITION IS THE UPPERLEFT CORNER, BUT THE WEAPON CLASS ASSIGNS THE CENTER
        //OF THE WEAPON TO THE POSITION INSTANCE VARIABLE
        
        //WE SHOULD ALSO ADD AN IF STATEMENT TO CHECK IF THE CENTER OF THE WEAPON HIT 
        //THE TARGET SO THAT WE GET THE FIVE MAIN POINTS OF THE WEAPON
        
        //CHECK IF ANY OF THE FOUR CORNERS OF THE WEAPON HIT THE ENEMY
        if(damage.x >= position.x && damage.x <= position.x + getSize()){
            if(damage.y >= position.y && damage.y <= position.y + (3 * getSize() / 2)){
                hit = true;
            } else if (damage.y + weapon.getSize() >= position.y && damage.y + weapon.getSize() <= position.y + (3 * getSize() / 2)){
                hit = true;
            }
        } else if (damage.x + weapon.getSize() >= position.x && damage.x + weapon.getSize() <= position.x + getSize()){
            if(damage.y >= position.y && damage.y <= position.y + (3 * getSize() / 2)){
                hit = true;
            } else if (damage.y + weapon.getSize() >= position.y && damage.y + weapon.getSize() <= position.y + (3 * getSize() / 2)){
                hit = true;
            }
        } 

    }

    /**
     * Returns whether or not the Soldier is done.
     * 
     * @return Whether the Soldier's work is done or not.
     */
    public boolean done() {
        return done;
    }

}
