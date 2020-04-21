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
    private static final Color FULL_HEALTH_COLOR = new Color(13, 201, 6);
    private static final Color MED_HEALTH_COLOR = new Color(245, 243, 110);
    private static final Color LOW_HEALTH_COLOR = new Color(176, 41, 32);
    private Color full_health_color;
    private Color med_health_color;
    private Color low_health_color;

    //THE AMOUNT OF HEALTH THE TOWER HAS
    private int towerHealth;
    
    //THE AMOUNT OF HEALTH THE TOWER HAS
    private int startHealth = START_HEALTH;

    //THE LABEL THAT DISPLAYS THE AMOUNT OF HEALTH THE TOWER HAS LEFT
    private JLabel healthBarLabel;

    private boolean done;

    private String text;

    public HealthBar(){
        this(new JLabel());
    }
    
    public HealthBar(String text){
        this(new JLabel());
        this.setText(text);
    }
    
    public HealthBar(JLabel healthBarLabel){
        this(healthBarLabel,START_HEALTH);
    }

    public HealthBar(JLabel healthBarLabel,int startHealth){
        this(healthBarLabel,startHealth,FULL_HEALTH_COLOR,MED_HEALTH_COLOR,LOW_HEALTH_COLOR);
    }
    
    public HealthBar(JLabel healthBarLabel,int startHealth,Color full_health_color,Color med_health_color,Color low_health_color){
        this.healthBarLabel = healthBarLabel;
        this.startHealth = startHealth;
        this.towerHealth = startHealth;
        this.done = false;
        this.text = healthBarLabel.getText();
        this.full_health_color = full_health_color;
        this.med_health_color = med_health_color;
        this.low_health_color = low_health_color;
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
            if(towerHealth >= 2 * startHealth / 3) {
                newHealthBarColor = this.full_health_color;
            } else if(towerHealth > startHealth / 3) {
                newHealthBarColor = this.med_health_color;
            } else {
                newHealthBarColor = this.low_health_color;
            }
            healthBarLabel.setForeground(newHealthBarColor);
            healthBarLabel.setText(this.text + towerHealth);
        }
    }

    public void setColors(Color full_health_color,Color med_health_color,Color low_health_color){
        this.full_health_color =  full_health_color;
        this.med_health_color = med_health_color;
        this.low_health_color = low_health_color;
    }

    public void setHealth(int newHealth){
        this.towerHealth = newHealth;
    }

    public int getHealth(){
        return this.towerHealth;
    }

    public void setText(String text){
        this.text = text;
        healthBarLabel.setText(this.text);
    }
    
    public String getText(){
        return this.text;
    }
    
    public void setLabel(JLabel label){
        this.healthBarLabel = label;
        this.text = this.healthBarLabel.getText();
    }
    
    public JLabel getLabel(){
        return this.healthBarLabel;
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
    
    public void resetDefaultHealth(){
        this.towerHealth = START_HEALTH;
    }
    
    public void resetHealth(){
        this.towerHealth = this.startHealth;
    }
    
    public void resetHealth(int startHealth){
        this.towerHealth = startHealth;
    }
}
