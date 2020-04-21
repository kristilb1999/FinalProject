// going to be lazy about imports in this class
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.geom.*;

/**
 * Write a description of class HealthBar here.
 *
 * @author Kristi Boardman, Cameron Costello, Will Skelly, Jake Burch
 * @version Spring 2020
 */
public class HealthBar extends Thread
{
    //THE DELAY TIME FOR THE SLEEP METHOD
    private static final int DELAY_TIME = 33;

    //THE START HEALTH OF THE TOWER
    private static final int START_HEALTH = 30;

    //THE COLORS FOR THE TOWER HEALTH LABEL AND THE DIFFICULTY BUTTONS
    private final Color FULL_HEALTH = new Color(13, 201, 6);
    private final Color MED_HEALTH = new Color(245, 243, 110);
    private final Color LOW_HEALTH = new Color(176, 41, 32);
    private Color full_health;
    private Color med_health;
    private Color low_health;

    //THE AMOUNT OF HEALTH THE TOWER HAS
    private int towerHealth;

    //THE LABEL THAT DISPLAYS THE AMOUNT OF HEALTH THE TOWER HAS LEFT
    private JLabel healthBarLabel;

    private boolean done;

    private String message;

    public HealthBar(JLabel healthBarLabel){
        this.healthBarLabel = healthBarLabel;
        this.towerHealth = START_HEALTH;
        this.done = false;
        this.message = healthBarLabel.getText();
        full_health = FULL_HEALTH;
        med_health = MED_HEALTH;
        low_health = LOW_HEALTH;
    }

    public HealthBar(JLabel healthBarLabel,int startHealth){
        this(healthBarLabel);
        this.towerHealth = startHealth;
    }

    public void run(){
        while(!done){
            try{
                sleep(DELAY_TIME);
            } catch (InterruptedException e){
                System.out.print(e);
            }

            //COLOR THE TOWER HEALTH MESSAGE BASED ON AMOUNT OF HEALTH LEFT
            Color newHealthBarColor;
            if(towerHealth >= 2 * START_HEALTH / 3) {
                newHealthBarColor = FULL_HEALTH;
            } else if(towerHealth > START_HEALTH / 3) {
                newHealthBarColor = MED_HEALTH;
            } else {
                newHealthBarColor = LOW_HEALTH;
            }
            healthBarLabel.setForeground(newHealthBarColor);
            healthBarLabel.setText(this.message + towerHealth);
        }
    }

    public void setColors(Color c1, Color c2, Color c3){
        full_health = c1;
        med_health = c2;
        low_health = c3;
    }

    public void setHealth(int newHealth){
        this.towerHealth = newHealth;
    }

    public int getHealth(){
        return this.towerHealth;
    }

    public boolean done(){
        return this.done;   
    }

    public void setDone(boolean isDone){
        this.done = isDone;   
    }

    public void reduce(int damage){
        this.towerHealth = (this.towerHealth - damage < 0 ? 0 : this.towerHealth - damage);
    }

    public void setVisible(boolean isVisible){
        this.healthBarLabel.setVisible(isVisible);
    }
}
