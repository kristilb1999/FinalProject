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
    // instance variables - replace the example below with your own
    private int x;

    private Vector<Soldier> soldierList;

    private boolean done;

    private int difficultyLevel;

    private int numOfEnemies;

    private JComponent container;

    private static final int DELAY_TIME = 33;

    private static final int AVG_ZOMB = 0;

    private static final int PIRATE = 0;

    private static final int HUNCHBACK = 0;

    private static final int BIG_EYE = 0;

    private boolean allStarted;

    private static final int X_START = 0;
    /**
     * Constructor for objects of class Soldier
     */
    public SoldierArmy(int difficultyLevel, JComponent container)
    {
        this.difficultyLevel = difficultyLevel;

        numOfEnemies = (difficultyLevel + 1) * 7;
        this.container = container;
        soldierList = new Vector<Soldier>();
        done = false;
        allStarted = false;
    } 

    public void paint(Graphics g)
    {
        if (done) return;

        for (Soldier soldier : soldierList) {
            soldier.paint(g);
        }
    }

    public void run(){

        Random r = new Random();

        for (int soldierCount= 0; soldierCount < numOfEnemies; soldierCount++) {
            try {
                sleep(DELAY_TIME);
            }
            catch (InterruptedException e) {
            }

            // random y coordinate
            int yCoord = r.nextInt(container.getHeight() / 4) + 3 * container.getHeight() / 4 ;
            Point2D.Double startSpot = new Point2D.Double(X_START, yCoord);
            int soldierType = r.nextInt(4);
            if(soldierType == AVG_ZOMB)
            {
                Soldier soldier = new AverageZombie(startSpot, container);
                soldierList.add(soldier);
                soldier.start();
            }
            else if  (soldierType == BIG_EYE)
            {
                Soldier soldier = new BigEye(startSpot, container);
                soldierList.add(soldier);
                soldier.start();
            }
            else if  (soldierType == HUNCHBACK)
            {
                Soldier soldier = new Hunchback(startSpot, container);
                soldierList.add(soldier);
                soldier.start();
            }
            else if  (soldierType == BIG_EYE)
            {
                Soldier soldier = new Pirate(startSpot, container);
                soldierList.add(soldier);
                soldier.start();
            }

        }
        allStarted = true;
    }

    public boolean done()
    {

        if (!allStarted) return false;

        if (done) return true;

        // this Cloud is done if all of its flakes have melted
        for (Soldier soldier : soldierList) {
            if (!soldier.done()) return false;
        }
        done = true;
        return true;
    }
}
