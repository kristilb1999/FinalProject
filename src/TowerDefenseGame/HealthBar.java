package TowerDefenseGame;

 
//imports
import java.awt.Color;
import javax.swing.JLabel;
/**
 * This class is a thread-safe health bar which keeps track of
 * an amount of health and a JLabel, which it updates the
 * color and text of as the healthbar gets calls to its
 * reduce method.
 *
 * @author Kristi Boardman, Cameron Costello, Will Skelly, Jake Burch
 * @version Spring 2020
 */
public class HealthBar extends Thread
{
    //THE DELAY TIME FOR THE SLEEP METHOD
    private static final int DELAY_TIME = 33;

    //THE START HEALTH
    private static final int START_HEALTH = 30;

    //THE COLORS FOR THE LABEL
    private static final Color FULL_HEALTH_COLOR = new Color(13, 201, 6);
    private static final Color MED_HEALTH_COLOR = new Color(245, 243, 110);
    private static final Color LOW_HEALTH_COLOR = new Color(176, 41, 32);
    private Color full_health_color;
    private Color med_health_color;
    private Color low_health_color;

    //THE AMOUNT OF HEALTH
    private int currentHealth;

    //THE AMOUNT OF HEALTH TO START WITH
    private int startHealth;

    //THE LABEL THAT DISPLAYS THE AMOUNT OF HEALTH REMAINING
    private JLabel healthBarLabel;

    //IF THE HEALTHBAR IS DONE
    private boolean done;

    //THE TEXT OF THE HEALTHBAR
    private String text;

    //OBJECTS THAT SERVE AS LOCKS FOR THREAD SAFETY
    private Object currentHealthLock = new Object();
    private Object startHealthLock = new Object();
    private Object colorsLock = new Object();
    private Object textLock = new Object();
    private Object doneLock = new Object();
    private Object visibleLock = new Object();

    public HealthBar(){
        this("");
    }

    public HealthBar(String text){
        this(new JLabel(text));
    }

    public HealthBar(String text,int startHealth){
        this(new JLabel(text),startHealth);
    }

    public HealthBar(JLabel healthBarLabel){
        this(healthBarLabel,START_HEALTH);
    }

    public HealthBar(JLabel healthBarLabel,int startHealth){
        this(healthBarLabel,startHealth,FULL_HEALTH_COLOR,MED_HEALTH_COLOR,LOW_HEALTH_COLOR);
    }

    public HealthBar(String text,int startHealth,Color full_health_color,Color med_health_color,Color low_health_color){
        this(new JLabel(text),startHealth,full_health_color,med_health_color,low_health_color);
    }

    public HealthBar(JLabel healthBarLabel,int startHealth,Color full_health_color,Color med_health_color,Color low_health_color){
        this.healthBarLabel = healthBarLabel;
        this.startHealth = startHealth;
        this.currentHealth = startHealth;
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

            synchronized (colorsLock) {
                //COLOR THE TEXT BASED ON AMOUNT OF HEALTH LEFT
                Color newHealthBarColor;
                if(currentHealth >= 2 * startHealth / 3) {
                    newHealthBarColor = this.full_health_color;
                } else if(currentHealth > startHealth / 3) {
                    newHealthBarColor = this.med_health_color;
                } else {
                    newHealthBarColor = this.low_health_color;
                }
                healthBarLabel.setForeground(newHealthBarColor);
            }

            synchronized (textLock) {
                healthBarLabel.setText(this.text + currentHealth);
            }
        }
    }

    public void setColors(Color full_health_color,Color med_health_color,Color low_health_color){
        synchronized (colorsLock) {
            this.full_health_color =  full_health_color;
            this.med_health_color = med_health_color;
            this.low_health_color = low_health_color;
        }
    }

    public void setHealth(int health){
        synchronized (currentHealthLock) {
            this.currentHealth = health;
        }
    }

    public int getHealth(){
        synchronized (currentHealthLock) {
            return this.currentHealth;
        }
    }

    public void setStartHealth(int health){
        synchronized (startHealthLock) {
            this.startHealth = health;
        }
    }

    public int getStartHealth(){
        synchronized (startHealthLock) {
            return this.startHealth;
        }
    }

    public void setText(String text){
        synchronized (textLock) {
            this.text = text;
            healthBarLabel.setText(this.text);
        }
    }

    public String getText(){
        synchronized (textLock) {
            return this.text;
        }
    }

    public void setLabel(JLabel label){
        synchronized (textLock) {
            this.healthBarLabel = label;
            this.text = this.healthBarLabel.getText();
        }
    }

    public JLabel getLabel(){
        synchronized (textLock) {
            return this.healthBarLabel;
        }
    }

    public boolean done(){
        synchronized (doneLock) {
            return this.done;   
        }
    }

    public void setDone(boolean isDone){
        synchronized (doneLock) {
            this.done = isDone;   
        }
    }

    public void reduce(int damage){
        synchronized (currentHealthLock) {
            //CURRENT HEALTH CAN HAVE A MINUMUM OF ZERO
            this.currentHealth = (this.currentHealth - damage < 0 ? 0 : this.currentHealth - damage);
        }
    }

    public void setVisible(boolean isVisible){
        synchronized (visibleLock) {
            this.healthBarLabel.setVisible(isVisible);
        }
    }

    public boolean isVisible(){
        synchronized (visibleLock) {
            return healthBarLabel.isVisible();
        }
    }

    public void resetHealthDefault(){
        synchronized (currentHealthLock) {
            this.currentHealth = START_HEALTH;
        }
    }

    public void resetHealth(){
        synchronized (currentHealthLock) {
            this.currentHealth = this.startHealth;
        }
    }
}
