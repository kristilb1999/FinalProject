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

    //STOP AND START COORDINATES FOR SPAWNING THE ENEMIES
    private static final int X_START = 0;
    private static final int Y_STOP = 52;

    //THE DIFFICULTY LEVEL
    private Difficulty difficultyLevel;

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

    //THE REFERENCE TO THE ENEMY
    private TowerDefense enemy;

    //THE REFERENCE TO THE SCOREBOARD
    private Scoreboard scoreboard;

    //LOCK FOR SOLDIER LIST
    private Object soldierLock = new Object();

    /**
     * Creates an army of enemy Soldiers.
     * 
     * @param difficultyLevel What level of enemies to produce.
     * @param container The container to put the enemies in.
     * @param tower The tower game reference.
     */
    public SoldierArmy(Difficulty difficultyLevel, JComponent container, TowerDefense enemy)
    {
        //SETS THE DIFFICULTY LEVEL OF THIS PARTICULAR ARMY
        this.difficultyLevel = difficultyLevel;

        //THE NUMBER OF ENEMIES IS DECIDED BY WHICH DIFFICULTY IS CHOSEN
        this.numOfEnemies = (difficultyLevel.getValue() + 1) * 10;

        //SET THE CONTAINER TO REPAINT IN
        this.container = container;

        //INITIALIZE THE SOLDIER ARMY LIST
        this.soldierList = new Vector<Soldier>();

        //NOT ALL OF THE ENEMIES ARE FINISHED YET
        this.done = false;

        //NOT ALL OF THE ENEMIEST HAVE STARTED YET
        this.allStarted = false;

        //SET THE TOWER GAME REFERENCE
        this.enemy = enemy; 

        //SET THE SCOREBOARD
        this.scoreboard = enemy.getScoreboard();
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
            synchronized(soldierLock){
                for (Soldier soldier : soldierList) {
                    soldier.paint(g);
                    soldier.setWeapons(wList);
                }
            }
        }
    }

    /**
     * Create the army of Soldiers based on the difficulty of the wave.
     */
    @Override
    public void run(){
        //PERIODICALLY CHECK IF ALL OF THE SOLDIERS ARE DONE
        SoldierArmy thisSA = this;
        new Thread() {
            @Override
            public void run() {
                //WAIT UNTIL ALL SOLDIERS HAVE STARTED BEFORE CHECKING IF ANY ARE DONE
                while(!thisSA.allStarted){
                    try{
                        sleep(DELAY_TIME);
                    } catch (InterruptedException e){
                        System.out.print(e);
                    }
                }

                boolean allDone;
                while(!thisSA.done){
                    try{
                        sleep(DELAY_TIME);
                    } catch (InterruptedException e){
                        System.out.print(e);
                    }

                    allDone = true;
                    //THIS ARMY IS DONE WHEN ALL OF ITS SOLDIERS ARE DONE
                    synchronized(soldierLock){
                        for (Soldier soldier : soldierList) {
                            if (!soldier.done()) allDone = false;
                        }
                    }

                    if(allDone) thisSA.done = true;

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

            Soldier soldier = SoldierProvider.getRandom(container, this, X_START, Y_STOP, difficultyLevel);
            synchronized(soldierLock){
                soldierList.add(soldier);
                soldier.start();
            }
        }

        //ALL OF THE ENEMIES HAVE BEEN CREATED
        allStarted = true;
    }

    /**
     * Kills all soldiers in the army.
     */
    public void killSoldiers() {
        //KILL EVERY SOLDIER IN THE ARMY
        synchronized(soldierLock){
            for(Soldier s : soldierList) {
                s.kill();
            }
        }

        //CLEAR THE LIST
        synchronized(soldierLock){
            soldierList.clear();
        }

        //SET DONE TO TRUE BECAUSE THE SOLDIER ARMY IS DEAD
        done = true;
    }

    /**
     * Calls damage on the enemy to damage the enemy
     * 
     * @param damage the amount to damage the enemy by
     */
    public void damageEnemy(int damage){
        enemy.damage(damage);
    }

    public void updateScore(int score){
        this.scoreboard.updateScore(score);
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
