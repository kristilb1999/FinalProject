// going to be lazy about imports in this class
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.geom.*;

/**
 * This class determines how many enemies will attack during each level.
 *
 * @author Cameron Costello, Kristi Boardman, Jacob Burch, Will Skelly
 * @version Spring 2020
 */
public class SoldierArmy extends Thread
{
    //DELAY TIME BETWEEN SPAWNING EACH ENEMY
    private static final int DELAY_TIME = 850;

    //EACH TYPE OF ZOMBIE GETS ITS OWN CONSTANT NUMBER FOR REFERENCE
    private static final int AVG_ZOMB = 0;
    private static final int HUNCHBACK = 1;
    private static final int BIG_EYE = 2;
    private static final int PIRATE = 3;

    //THE THREE DIFFICULTY LEVELS
    private static final int EASY = 0;
    private static final int MEDIUM = 1;
    private static final int HARD = 2;

    //STOP AND START COORDINATES FOR SPAWNING THE ENEMIES
    private static final int X_START = 0;
    private static final int Y_STOP = 52;

    //THE DIFFICULTY LEVEL
    private int difficultyLevel;

    //THE NUMBER OF ENEMIES TO SPAWN IN THIS ARMY WAVE
    private int numOfEnemies;

    //WHETHER OR NOT THE ARMY IS COMPLETELY FINISHED
    private boolean done;

    //WHETHER OR NOT ALL ENEMIES HAVE BEEN CREATED
    private boolean allStarted;

    //THE CONTAINER TO REPAINT
    private JComponent container;

    //THE LIST OF SOLDIERS TO POPULATE
    private Vector<Soldier> soldierList;

    //THE LIST OF WEAPONS CURRENTLY ON THE SCREEN
    private Vector<Weapon> wList;

    //THE TOWERDEFENSE GAME REFERENCE
    private TowerDefense tower;

    /**
     * Creates an army of enemy Soldiers.
     * 
     * @param difficultyLevel What level of enemies to produce.
     * @param container The container to put the enemies in.
     * @param tower The tower game reference.
     */
    public SoldierArmy(int difficultyLevel, JComponent container, TowerDefense tower)
    {
        //SETS THE DIFFICULTY LEVEL OF THIS PARTICULAR ARMY
        this.difficultyLevel = difficultyLevel;

        //THE NUMBER OF ENEMIES IS DECIDED BY WHICH DIFFICULTY IS CHOSEN
        this.numOfEnemies = (difficultyLevel + 1) * 10;

        //SET THE CONTAINER TO REPAINT IN
        this.container = container;

        //INITIALIZE THE SOLDIER ARMY LIST
        this.soldierList = new Vector<Soldier>();

        //NOT ALL OF THE ENEMIES ARE FINISHED YET
        this.done = false;

        //NOT ALL OF THE ENEMIEST HAVE STARTED YET
        this.allStarted = false;

        //SET THE TOWER GAME REFERENCE
        this.tower = tower;
    } 

    /**
     * Sets the weapon list to the current Weapons on screen.
     * 
     * @param wList The list of weapons on the screen.
     */
    public void setWeaponList(Vector<Weapon> wList){
        this.wList = wList;
    }

    /**
     * Paints all of the soldiers on the screen.
     * 
     * @param g The graphics object.
     */
    public void paint(Graphics g)
    {
        //IF THE ARMY HAS BEEN WIPED OUT, DO NOTHING
        if (!done){

            //IF THERE ARE SOLDIERS LEFT, PAINT EACH ONE AND SET THE LIST OF WEAPONS ON SCREEN
            //TO EACH ONE SO THEY WILL KNOW WHEN THEY HAVE BEEN HIT LATER
            for (Soldier soldier : soldierList) {
                soldier.paint(g);
                soldier.getWeapons(wList);
            }
        }
    }

    /**
     * Create the army of Soldiers based on the difficulty of the wave.
     */
    public void run(){
        //PERIODICALLY CHECK IF ALL OF THE SOLDIERS ARE DONE
        SoldierArmy thisSA = this;
        new Thread() {
            @Override
            public void run() {
                boolean allDone;
                while(!thisSA.done){
                    try{
                        sleep(DELAY_TIME);
                    } catch (InterruptedException e){
                        System.out.print(e);
                    }

                    //IF THEY HAVEN'T ALL STARTED, THEY CANNOT ALL BE DONE
                    if(allStarted){
                        allDone = true;

                        //THIS ARMY IS DONE WHEN ALL OF ITS SOLDIERS ARE DONE
                        for (Soldier soldier : soldierList) {
                            if (!soldier.done()) allDone = false;
                        }

                        if(allDone) thisSA.done = true;
                    }
                    
                }
            }
        }.start(); 

        //CREATE A RANDOM OBJET TO GIVE VARIETY
        Random r = new Random();

        //CREATE EVERY SOLDIER INDIVIDUALLY TO GET A VARIETY OF THEM
        for (int soldierCount= 0; soldierCount < numOfEnemies; soldierCount++) {
            //SLEEP BETWEEN CREATING EACH ENEMY TO GIVE THEM TIME ON THE SCREEN
            try {
                sleep(DELAY_TIME);
            }
            catch (InterruptedException e) {
            }

            //GIVE EACH ENEMY A RANDOM Y POSITION SO THE ENEMIES ARE STAGGERED
            int yCoord = (r.nextInt(container.getHeight() / 4 - Y_STOP) + 3 * container.getHeight() / 4 - Y_STOP / 2 );

            //THE STARTING POSITION OF THE ENEMY
            Point2D.Double startSpot = new Point2D.Double(X_START, yCoord);

            //THE TYPE OF SOLDIER TO BE CHOSEN
            int soldierType;
            /* DIFFERENT TYPES OF ENEMIES CAN SPAWN DEPENDING ON THE DIFFICULTY LEVEL OF
            EACH INDIVIDUAL WAVE:
            EASY IS ONLY WEAKEST TWO OPTIONS
            MEDIUM IS ALL BUT THE STRONGEST OPTION
            HARD IS EVERY TYPE OF SOLDIER CAN SPAWN
             */
            switch(difficultyLevel){
                case EASY:
                soldierType = r.nextInt(2);
                break;
                case MEDIUM:
                soldierType = r.nextInt(3);
                break;
                case HARD:
                soldierType = r.nextInt(4);
                break;
                default:
                soldierType = r.nextInt(2);
            }

            //THE TYPES OF SOLDIERS BEING CREATED BASED ON THEIR SPEEDS
            Soldier soldier;
            switch(soldierType){
                case 1:
                //A LITTLE FASTER THAN THE AVERAGE ZOMBIE
                soldier = new Hunchback(startSpot, container, tower);
                break;
                case 2:
                //THIS GUY ISN'T A SLOUCH WHEN IT COMES TO SPEED WALKING
                soldier = new BigEye(startSpot, container, tower);
                break;
                case 3:
                //ARR, HE'S COMING TO GET YOUR BOOTY
                soldier = new Pirate(startSpot, container, tower);
                break;
                default:
                //WEAKEST SOLDIER
                soldier = new AverageZombie(startSpot, container, tower);
            }
            soldierList.add(soldier);

            soldier.start();
        }

        //ALL OF THE ENEMIES HAVE BEEN CREATED
        allStarted = true;
    }
    
    /**
     * Kills all soldiers in the army.
     */
    public void killSoldiers() {
        //KILL EVERY SOLDIER IN THE ARMY
        for(Soldier s : soldierList) {
            s.kill();
        }
        
        //CLEAR THE LIST
        soldierList.clear();
        
        //SET DONE TO TRUE BECAUSE THE SOLDIER ARMY IS DEAD
        done = true;
    }

    /**
     * Returns true when all of the enemies in this army are done with their undead lives.
     */
    public boolean done()
    {
        //IF THEY ARE ALL DONE, THEN THEY ARE ALL DONE
        return this.done;
    }
}
