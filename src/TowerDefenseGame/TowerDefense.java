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
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Vector;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * This game is loosely based off of a tower defense game. In this game, 
 * you must defend your tower against the invading zombie army. Your 
 * weapons of choice: explosives and boulders? You must shoot all of the
 * incoming zombies with your boulders, grenades, molotov cocktails, and 
 * your trusty tnt. The user may choose from three difficulty levels for
 * enemy waves and spawn as many enemies as you'd like. However, once 
 * your tower falls, you'll be exposed for the loser you are.
 *
 * @author Kristi Boardman, Cameron Costello, Will Skelly, Jake Burch
 * @version Spring 2020
 */
public class TowerDefense extends MouseAdapter implements Runnable, ActionListener
{
    //THE DELAY TIME FOR THE SLEEP METHOD
    private static final int DELAY_TIME = 33;

    //THE WIDTH AND HEIGHT OF THE PANEL
    private static final int PANEL_WIDTH = 1250;
    private static final int PANEL_HEIGHT = 700;

    //THE WIDTH AND HEIGHT OF THE FRAME
    private static final int FRAME_WIDTH = 1350;
    private static final int FRAME_HEIGHT = 800;

    //THE AMOUNT THE TOWER WILL BE DISPLACED FROM THE WIDTH AND HEIGHT OF THE PANEL
    private static final int TOWER_X_DISPLACEMENT = 325;
    private static final int TOWER_Y_DISPLACEMENT = 490;

    //THE AMOUNT THE CLICKABLE SQUARE WILL BE DISPLACED FROM THE HEIGHT OF THE PANEL
    private static final int CLICKABLE_X_DISPLACEMENT = 285;
    private static final int CLICKABLE_Y_DISPLACEMENT = 680;

    //THE SIZE OF THE CLICKABLE AREA
    private static final int CLICKABLE_SIZE = 250;

    //THE START HEALTH OF THE TOWER
    private static final int START_HEALTH = 30;

    //THE SLING FACTOR FOR THROWING THE WEAPONS
    public static final double SLING_FACTOR = 2.5;

    //THE FILENAME OF THE TOWER IMAGE
    private static final String TOWER_PIC_FILEPATH = "/towerImage.png";

    //THE COLORS FOR THE DIFFICULTY BUTTONS
    private static final Color EASY_COLOR = new Color(13, 201, 6);
    private static final Color MED_COLOR = new Color(245, 243, 110);
    private static final Color HARD_COLOR = new Color(176, 41, 32);

    //THE GRASS COLOR BASED ON WHETHER IT IS DAY OR NIGHT
    private static final Color DAY_GRASS = new Color(42, 153, 32);
    private static final Color NIGHT_GRASS = new Color(29, 112, 87);

    //THE SKY COLOR BASED ON WHETHER IT IS DAY OR NIGHT
    private static final Color DAY_SKY = new Color(73, 185, 230);
    private static final Color NIGHT_SKY = new Color(62, 53, 150);

    //THE COLOR OF THE BACKGROUND PANEL
    private static final Color BACKGROUND_COLOR = new Color(77, 51, 26);

    //THE FONTS TO USE THROUHGOUT THE PROGRAM FOR BUTTONS AND LABELS
    private static final Font FONT_USED = new Font("Rockwell", Font.BOLD, 20);
    private static final Font LARGER_FONT_USED = new Font("Rockwell", Font.BOLD, 50);

    //THE POSITION OF THE TOWER ON THE PANEL
    private int towerXPos;
    private int towerYPos;

    //THE Y VALUE OF THE GRASS LINE POSITION
    private int grassLine;

    //FALSE WHEN IT IS DAYTIME, TRUE WHEN IT IS NIGHTTIME ON SCREEN
    private boolean nightTime;

    //HAS THE GAME STARTED OR NOT?
    private boolean gameStarted;

    //IF THE WEAPON WAS MADE IN THE BOX
    private boolean weaponMade;

    //IS THE MOUSE BEING DRAGGED OR NOT?
    private boolean dragging;

    //THE IMAGE OF THE TOWER
    private static Image towerPic;

    //THE VECTORS THAT HOLD THE SOLDIER ARMY OR THE WEAPONS ON SCREEN
    private Vector<SoldierArmy> soldierArmyList;
    private Vector<Weapon> weaponList;

    //THE GAME PANEL (WHICH HOLDS THE GAME PLAY) OR THE START PANEL (WHICH HOLDS THE BUTTONS)
    private JPanel panel;
    private JPanel startPanel;

    //THE BUTTONS INDICATION THE LEVEL OF DIFFICULTY FOR THE GAME
    private JButton easyRound;
    private JButton mediumRound;
    private JButton hardRound;

    //THE GAME INSTRUCTIONS
    private Instructions instructions;

    //THE BUTTON FOR DISPLAYING/HIDING THE INSTRUCTIONS
    private JButton instructionsButton;

    //THE BUTTON THAT STARTS THE GAME OR RESTARTS THE GAME SETTINGS
    private JButton startOrRestart;

    //THE BUTTON THAT SAVES THE SCORE
    private JButton scoreButton;

    //THE BUTTON THAT SETS THE TIME OF DAY
    private JButton timeOfDay;

    //THE BUTTON TO QUIT THE GAME
    private JButton quit;

    //THE HEALTHBAR THAT TRACKS THE AMOUNT OF HEALTH THE TOWER HAS LEFT
    private HealthBar healthBar;

    //THE LABEL THAT DISPLAYS THE PLAYER SCORE 
    private JLabel scoreLabel;

    //THE SCOREBOARD OBJECT THAT WILL STORE THE SCORE IN THE DATABASE
    private Scoreboard scoreboard;

    //THE POINTS THAT KEEP TRACK OF PRESSING AND DRAGGING TO LAUNCH THE WEAPONS
    private Point pressPoint;
    private Point dragPoint;

    //THE WEAPON TO BE LAUNCHED
    private Weapon newWeapon;

    // OBJECTS THAT SERVE AS LOCKS FOR THREAD SAFETY IN OUR LIST ACCESS
    private Object weaponLock = new Object();
    private Object soldierArmyListLock = new Object();

    /**
     * This method will repaint our background scene and the tower. It will also ensure that all enemies 
     * and weapons are repainted on the screen.
     * 
     * @param g The graphics object.
     */
    protected void redrawScene(Graphics g) {
        //SAVE THE WIDTH AND HEIGHT OF THE GAME PANEL FOR PROPER SPACING OF COMPONENETS
        int width = panel.getWidth();
        int height = panel.getHeight();

        //PLACE THE GRASSLINE 3/4 OF THE WAY DOWN THE SCREEN
        grassLine = height/4;

        //CREATE THE POSITION FOR THE TOWER BASED ON THE PANEL AND THE DISPLACEMENT
        towerXPos = width - TOWER_X_DISPLACEMENT;
        towerYPos = height - TOWER_Y_DISPLACEMENT;

        //PAINT THE BACKGROUND AS NIGHT OR AS DAY
        if(!nightTime) {
            //DAY TIME
            g.setColor(DAY_SKY);
            g.fillRect(0, 0, width, height - grassLine);

            g.setColor(DAY_GRASS);
            g.fillRect(0, height - grassLine, width, height);
        } else {
            //NIGHT TIME
            g.setColor(NIGHT_SKY);
            g.fillRect(0, 0, width, height - grassLine);

            g.setColor(NIGHT_GRASS);
            g.fillRect(0, height - grassLine, width, height);
        }

        //DRAW THE TOWER IN THE GRASS
        g.drawImage(towerPic, towerXPos, towerYPos, null);

        //BASED OFF OF:
        //https://stackoverflow.com/questions/16995308/can-you-increase-line-thickness-when-using-java-graphics-for-an-applet-i-dont
        //https://stackoverflow.com/questions/4219511/draw-rectangle-border-thickness

        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(3));

        //THE CLICKABLE SPACE TO LAUNCH A WEAPON FROM
        g2.setColor(Color.BLACK);
        g2.drawRect(width - CLICKABLE_X_DISPLACEMENT, height - CLICKABLE_Y_DISPLACEMENT, CLICKABLE_SIZE, CLICKABLE_SIZE);

        //IF WE ARE CURRENTLY DRAGGING, DRAW THE SLING LINE
        if (dragging) {
            g.setColor(Color.BLACK);
            g.drawLine(pressPoint.x, pressPoint.y,
                dragPoint.x, dragPoint.y);
        }

        //USE THIS LOCAL VARIABLE FOR INDEXING
        int i = 0;

        //REDRAW EACH SOLDIER AT ITS CURRENT POSITION AND REMOVE THE ONES THAT ARE DONE ALONG THE WAY
        //SINCE WE WILL BE MODIFYING THE LIST, WE WILL LOCK ACCESS SO THAT NO CONCURRENT EXCEPTION WILL BE THROWN
        synchronized (soldierArmyListLock) {
            while (i < soldierArmyList.size()) {
                SoldierArmy s = soldierArmyList.get(i);

                if (s.done()) {
                    soldierArmyList.remove(i);
                }
                else {
                    s.paint(g);
                    synchronized (weaponLock) {
                        s.setWeaponList((Vector<Weapon>)weaponList.clone());
                    }
                    i++;
                }
            }
        }

        //RESET INDEX TO ZERO
        i = 0;

        //REDRAW EACH WEAPON AT ITS CURRENT POSITION AND REMOVE THE ONES THAT ARE DONE ALONG THE WAY
        //SINCE WE WILL BE MODIFYING THE LIST, WE WILL LOCK ACCESS SO THAT NO CONCURRENT EXCEPTION WILL OCCUR
        synchronized (weaponLock) {
            while (i < weaponList.size()) {
                Weapon w = weaponList.get(i);
                if (w.done()) {
                    weaponList.remove(i);
                }
                else {
                    w.paint(g);
                    i++;
                }
            }
        }

        //IF THE START BUTTON WAS PRESSED AND THE CURRENT HEALTH IS <=0
        if( gameStarted && healthBar.getHealth() <= 0) {
            int centerX = width/2;
            int centerY = height/2;

            g.setFont(LARGER_FONT_USED);

            FontMetrics fontInfo = g.getFontMetrics();
            centerX = centerX - (fontInfo.stringWidth("You're a loser.")/2);
            centerY = centerY - (fontInfo.getAscent()/2);

            g.setColor(Color.MAGENTA);
            g.drawString("You're a loser.", centerX, centerY);

        }
    }

    /**
     * This method creates the frame, the panels, the labels, and the buttons. The scene gets
     * painted and repainted based on a delay time. All action listeners are added to the
     * buttons and all buttons and labels except start are invisible upon startup of the game.
     * 
     */
    @Override
    public void run() {
        //SET UP THE GUI "LOOK AND FEEL" WHICH SHOULD MATCH THE OS ON WHICH IT IS RUN
        JFrame.setDefaultLookAndFeelDecorated(true);

        //CREATE A JFRAME IN WHICH WE WILL BUILD OUR GUI AND GIVE THE WINDOW A NAME
        JFrame frame = new JFrame("TowerDefenseGame");
        frame.setPreferredSize(new Dimension(FRAME_WIDTH,FRAME_HEIGHT));

        //TELL THE JFRAME THAT WHEN SOMEONE CLOSES THE WINDOW, THE APPLICATION SHOULD TERMINATE
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //CREATE THE INSTRUCTIONS BUTTON
        instructionsButton = new JButton("Instructions");

        //CREATE THE START BUTTON
        startOrRestart = new JButton("Start");

        //CREATE THE SCORE BUTTON
        scoreButton = new JButton("Save Score");

        //CREATE THE DIFFICULTY LEVEL BUTTONS
        easyRound = new JButton("Easy");      
        mediumRound = new JButton("Medium");
        hardRound = new JButton("Hard");

        //CREATES THE DAY TIME BUTTON
        timeOfDay = new JButton("Night");

        //CREATES THE QUIT BUTTON
        quit = new JButton("Quit Game");

        //CREATE THE HEALTHBAR TO TRACK THE HEALTH
        //THE TOWER STARTS WITH MAXIMUM HEALTH
        healthBar = new HealthBar("Tower Health: ",START_HEALTH,EASY_COLOR,MED_COLOR,HARD_COLOR);
        healthBar.start();

        //CREATE THE LABEL THAT DISPLAYS THE SCORE
        scoreLabel = new JLabel(" Player score: ");

        //CREATE A PANEL WITH A FLOWLAYOUT TO HOLD THE GAME AND THE BUTTONS PANEL AND ADD IT TO THE FRAME
        JPanel panelHolder = new JPanel(new FlowLayout());
        panelHolder.setBackground(BACKGROUND_COLOR);
        frame.add(panelHolder);

        // JPANEL WITH A PAINTCOMPONENT METHOD
        panel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                //CALL THE PAINTCOMPONENT METHOD WE ARE OVERRIDING IN JPANEL
                super.paintComponent(g);

                //REDRAW THE MAIN SCENE
                redrawScene(g);
            }

        };

        //CREATE THE GAME INSTRUCTIONS
        instructions = new Instructions(panelHolder);
        instructions.start();

        //CREATE THE SCOREBOARD OBJECT AND START THE THREAD
        scoreboard = new Scoreboard(panelHolder);
        scoreboard.start();

        //CREATE A PANEL FOR THE GAME
        panel.setPreferredSize(new Dimension(PANEL_WIDTH,PANEL_HEIGHT));

        //CREATE A PANEL FOR THE BUTTONS AND SET THE COLOR TO BLACK
        startPanel = new JPanel();
        startPanel.setBackground(Color.BLACK);

        //SET THE FONT AND COLORS OF THE INSTRUCTIONS BUTTON
        startOrRestart.setFont(FONT_USED);
        startOrRestart.setForeground(Color.CYAN);
        startOrRestart.setBackground(Color.BLACK);

        //SET THE FONT AND COLORS OF THE START BUTTON
        startOrRestart.setFont(FONT_USED);
        startOrRestart.setForeground(Color.CYAN);
        startOrRestart.setBackground(Color.BLACK);

        //SET THE FONT AND COLORS OF THE EASY BUTTON
        easyRound.setFont(FONT_USED);
        easyRound.setForeground(EASY_COLOR);
        easyRound.setBackground(Color.BLACK);

        //SET THE FONT AND COLORS OF THE MEDIUM BUTTON
        mediumRound.setFont(FONT_USED);
        mediumRound.setForeground(MED_COLOR);
        mediumRound.setBackground(Color.BLACK);

        //SET THE FONT AND COLORS OF THE HARD BUTTON
        hardRound.setFont(FONT_USED);
        hardRound.setForeground(HARD_COLOR);
        hardRound.setBackground(Color.BLACK);

        //SET THE FONT OF THE HEALTH LABEL
        healthBar.getLabel().setFont(FONT_USED);

        //SET FONT AND COLORS OF THE SCORE LABEL
        scoreLabel.setFont(FONT_USED);
        scoreLabel.setForeground(Color.BLUE);
        scoreLabel.setBackground(Color.BLACK);

        //SET THE FONT AND COLORS OF THE SCORE BUTTON
        scoreButton.setFont(FONT_USED);
        scoreButton.setForeground(Color.MAGENTA);
        scoreButton.setBackground(Color.BLACK);

        //SET THE FONT AND COLORS OF THE DAY TIME BUTTON
        timeOfDay.setFont(FONT_USED);
        timeOfDay.setForeground(Color.YELLOW);
        timeOfDay.setBackground(Color.BLACK);

        //SET THE FONT AND COLORS OF THE QUIT BUTTON
        quit.setFont(FONT_USED);
        quit.setForeground(Color.PINK);
        quit.setBackground(Color.BLACK);

        //SET THE FONT AND COLORS OF THE INSTRUCTION BUTTON
        instructionsButton.setFont(FONT_USED);
        instructionsButton.setForeground(Color.WHITE);
        instructionsButton.setBackground(Color.BLACK);

        //ADD THE INSTRUCTIONS BUTTON TO THE BUTTON PANEL
        startPanel.add(instructionsButton);

        //ADD THE START BUTTON TO THE BUTTON PANEL
        startPanel.add(startOrRestart);

        //ADD THE DIFFICULTY BUTTONS TO THE BUTTON PANEL
        startPanel.add(easyRound);
        startPanel.add(mediumRound);
        startPanel.add(hardRound);

        //ADD THE HEALTH LABEL TO THE BUTTON PANEL
        startPanel.add(healthBar.getLabel());

        //ADD THE SCORE LABEL TO THE BUTTON PANEL
        startPanel.add(scoreLabel);

        //ADD THE SCORE BUTTON TO THE BUTTON PANEL
        startPanel.add(scoreButton);

        //ADD THE TIME OF DAY BUTTON TO THE BUTTON PANEL
        startPanel.add(timeOfDay);

        //ADD THE QUIT BUTTON TO THE BUTTON PANEL
        startPanel.add(quit);

        //SET DIFFICULTY BUTTONS INVISIBLE
        easyRound.setVisible(false);
        mediumRound.setVisible(false);
        hardRound.setVisible(false);

        //SET SCORE BUTTON INVISIBLE
        scoreButton.setVisible(false);

        //SET TIME OF DAY BUTTON INVISIBLE
        timeOfDay.setVisible(false);

        //SET QUIT BUTTON INVISIBLE
        //quit.setVisible(false);

        //SET HEALTH LABEL INVISIBLE
        healthBar.setVisible(false);

        //SET SCORE LABEL INVISBLE
        scoreLabel.setVisible(false);

        //ADD THE GAME PANEL AND THE BUTTONS PANEL TO THE PANEL HOLDER ON THE JFRAME
        panelHolder.add(panel);
        panelHolder.add(startPanel);

        //ADD THE MOUSELISTENER TO THE GAME PANEL
        panel.addMouseListener(this);
        panel.addMouseMotionListener(this);

        //ADD THE ACTION LISTENER TO THE START BUTTON
        startOrRestart.addActionListener(this);

        //ADD THE ACTION LISTENER TO THE DIFFICULTY BUTTONS
        easyRound.addActionListener(this);
        mediumRound.addActionListener(this);
        hardRound.addActionListener(this);

        //ADD THE ACTION LISTENER TO THE SCORE BUTTON
        scoreButton.addActionListener(this);

        //ADD THE ACTION LISTENER TO THE TIME OF DAY BUTTON
        timeOfDay.addActionListener(this);

        //ADD THE ACTION LISTENER TO THE QUIT BUTTON
        quit.addActionListener(this);

        //ADD ACTION LISTENER TO THE INSTRUCTION BUTTON
        instructionsButton.addActionListener(this);

        //INITIALIZE THE WEAPONS AND ENEMY LISTS
        weaponList = new Vector<Weapon>();
        soldierArmyList = new Vector<SoldierArmy>();

        //DISPLAY THE WINDOW THAT WE CREATED
        frame.pack();
        frame.setVisible(true);

        //REPAINT THE SCENE AFTER A SET AMOUNT OF TIME
        new Thread() {
            @Override
            public void run() {
                while(true){
                    try{
                        sleep(DELAY_TIME);
                    } catch (InterruptedException e){
                        System.out.print(e);
                    }

                    panel.repaint();
                }
            }
        }.start(); 

        //UPDATE THE SCORE LABEL PERIODICALLY
        new Thread() {
            @Override
            public void run() {
                while(true){
                    try{
                        sleep(DELAY_TIME);
                    } catch (InterruptedException e){
                        System.out.print(e);
                    }

                    scoreLabel.setText(" Player score: " + scoreboard.getScore());
                }
            }
        }.start(); 
    }

    /**
     * The action listener that handles events for each button.
     * Start button will start the game. Reset button will
     * reset all variables and reset screen so that game can be
     * started again. Difficulty buttons will begin enemy spawning.
     * Score button allows score to be saved.
     * 
     * @param e The action event to handle.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        //WHEN THE BUTTON PRESSED IS THE START OR RESTART BUTTONS
        if(e.getSource().equals(instructionsButton)){
            //SHOW HIDE GAME INSTRUCTIONS
            if(!instructions.isVisible()){
                instructions.setVisible(true);
            }else{
                instructions.setVisible(false);
            }

        }else if(e.getSource().equals(startOrRestart)) {
            //IF THE BUTTON CURRENTLY SAYS START (WHEN THE GAME BEGINS)
            if(startOrRestart.getText().equals("Start")) { 
                startGame();
            } else {
                endGame();
            }
        }else if (e.getSource().equals(easyRound))
        {
            //IF THE PLAYER CHOOSES AN EASY ROUND AN EASY LEVEL WILL BE STARTED
            startRound(Difficulty.EASY);
        }else if (e.getSource().equals(mediumRound))
        {
            //IF THE PLAYER CHOOSES A MEDIUM ROUND A MEDIUM LEVEL WILL BE STARTED
            startRound(Difficulty.MEDIUM);
        }else if (e.getSource().equals(hardRound))
        {
            //IF THE PLAYER CHOOSES A HARD ROUND A HARD LEVEL WILL BE STARTED
            startRound(Difficulty.HARD);
        }else if(e.getSource().equals(scoreButton)) {
            //THE DIALOG BOX WILL POP UP TO SAVE THE SCORE
            scoreboard.show();
        } else if(e.getSource().equals(timeOfDay)) {
            //THE TIME OF DAY WILL CHANGE AND THE BUTTON WILL BE SET TO THE OPPOSITE TIME
            setTime();
        } else if(e.getSource().equals(quit)) {
            System.exit(0);
        }
    }

    /**
     * Starts the game by setting boolean making addition
     * buttons and labels visible.
     */
    private void startGame(){
        //THE GAME HAS STARTED
        gameStarted = true;

        //THE DIFFICULTY BUTTONS BECOME VISIBLE
        easyRound.setVisible(true);
        mediumRound.setVisible(true);
        hardRound.setVisible(true);

        //THE AMOUNT OF HEALTH LEFT BECOMES VISIBLE
        healthBar.setVisible(true);
        healthBar.resetHealth();

        //SET SCORE LABEL VISIBLE
        scoreLabel.setVisible(true);

        //THE SCORE BUTTON BECOMES VISIBLE
        scoreButton.setVisible(true);

        //THE TIME OF DAY BUTTON BECOMES VISIBLE
        timeOfDay.setVisible(true);

        //THE START BUTTON BECOMES THE RESTART BUTTON
        startOrRestart.setText("Restart");
    }

    /**
     * Ends the current game and allows a new one to begin.
     */
    private void endGame(){
        //END THE GAME
        gameStarted = false;

        //SET SCORE BACK TO ZERO
        scoreboard.setScore(0);

        //RESET THE BUTTON TO SAY START
        startOrRestart.setText("Start");

        //THE DIFFICULTY BUTTONS ARE HIDDEN
        easyRound.setVisible(false);
        mediumRound.setVisible(false);
        hardRound.setVisible(false);

        //THE AMOUNT OF HEALTH LEFT IS HIDDED
        healthBar.setVisible(false);

        //SET SCORE LABEL INVISBLE
        scoreLabel.setVisible(false);

        //THE SCORE BUTTON IS HIDDEN
        scoreButton.setVisible(false);

        //THE TIME OF DAY BUTTON BECOMES INVISIBLE
        timeOfDay.setVisible(false);

        //CLEAR THE SCREEN OF ANY WEAPONS OR ENEMIES
        synchronized(soldierArmyListLock){
            for(SoldierArmy sa : soldierArmyList) {
                sa.killSoldiers();
            }
            soldierArmyList.clear();
        }

        synchronized(weaponLock){
            weaponList.clear();
        }
    }

    /**
     * Sets the difficulty of the next army wave.
     */
    private void startRound(Difficulty difficulty){
        //AN ARMY WITH THE SPECIFIED DIFFICULTY WILL BE CREATED, ADDED TO THE LIST, AND STARTED
        SoldierArmy army = new SoldierArmy(difficulty, panel, this);

        synchronized (weaponLock) {
            army.setWeaponList((Vector<Weapon>)weaponList.clone());
        }

        synchronized(soldierArmyListLock){
            soldierArmyList.add(army);
        }

        army.start();
    }

    /**
     * Sets the time of day. Can be changed by user at any time
     * after the game has been started.
     */
    private void setTime() {
        //THE TIME ALWAYS STARTS AS DAY
        if(nightTime) {
            nightTime = false;
            timeOfDay.setText("Night");
        }else {
            //IF THE DAY IS NIGHT TIME
            nightTime = true;
            timeOfDay.setText(" Day ");
        }
    }

    /**
     * Returns the scoreboard object.
     * 
     * @return The scoreboard.
     */
    public Scoreboard getScoreboard() {
        return scoreboard;
    }

    /**
     * Calculates whether or not the click is within the launching
     * box above the tower.
     * 
     * @param clickedPoint The point that has been clicked on the screen.
     */
    public boolean clickedInBox(Point clickedPoint) {
        return (clickedPoint.x > panel.getWidth() - CLICKABLE_X_DISPLACEMENT && clickedPoint.x < panel.getWidth() - CLICKABLE_X_DISPLACEMENT + CLICKABLE_SIZE
            && clickedPoint.y > panel.getHeight() - CLICKABLE_Y_DISPLACEMENT && clickedPoint.y < panel.getHeight() - CLICKABLE_Y_DISPLACEMENT + CLICKABLE_SIZE);
    }

    /**
     * The mouse press event that will set up a weapon
     * to be launched upon release, if the game has begun.
     * 
     * @param e The mouse event to handle.
     */
    @Override
    public void mousePressed(MouseEvent e) {
        //IF THE START BUTTON HAS BEEN PRESSED
        if(gameStarted) {
            //THE WEAPON HAS NOT YET BEEN MADE
            weaponMade = false;

            //SAVE THE ORIGINAL PRESS POINT
            pressPoint = e.getPoint();
            if(clickedInBox(pressPoint)) {

                //CREATE A NEW WEAPON TO ADD TO THE WEAPON LIST
                newWeapon = QuarterMaster.getRandomWeapon(panel,
                    new Point2D.Double(e.getPoint().x, e.getPoint().y),
                    new Point2D.Double( 
                        SLING_FACTOR * (pressPoint.x - e.getPoint().x) , 
                        SLING_FACTOR * (pressPoint.y - e.getPoint().y) 
                    ) );

                //LOCK ACCESS TO THE LIST IN CASE paintComponent IS USING IT CONCURRENTLY
                synchronized (weaponLock) {
                    //ADD THE NEW WEAPON TO THE LIST
                    weaponList.add(newWeapon);
                }

                //START THE NEW WEAPON NOW THAT IT HAS BEEN ADDED
                newWeapon.start();

                //THE WEAPON HAS BEEN MADE
                weaponMade = true;
            }
        }
    }

    /**
     * The mouse drag event that will set up a weapon
     * to be launched upon release, if the game has begun.
     * Will draw a sling line.
     * 
     * @param e The mouse event to handle.
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        //IS THE START BUTTON HAS BEEN PRESSED
        if(weaponMade) {
            //SAVE THE DRAG POINT FOR PAINT METHOD
            dragPoint = e.getPoint();

            //RESET THE POSITION AND INERTIA OF THE WEAPON
            newWeapon.setWeaponPosition(new Point2D.Double(e.getPoint().x, e.getPoint().y));
            newWeapon.setWeaponInertia(new Point2D.Double( 
                    SLING_FACTOR * (pressPoint.x - e.getPoint().x) , 
                    SLING_FACTOR * (pressPoint.y - e.getPoint().y) 
                ));

            //SET DRAG TO FALSE FOR PAINT METHOD
            dragging = true;
        }
    }

    /**
     * The mouse release event that will create a new weapon
     * and add it to the list if the game has begun.
     * 
     * @param e The mouse event to handle.
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        //AS LONG AS THE START BUTTON HAS BEEN PRESSED
        if(weaponMade) {
            //RESET DRAGGING TO FALSE FOR PAINT METHOD
            dragging = false;
            newWeapon.setReleased(true);
        } 
    }

    /**
     * Returns the HealthBar for the tower.
     * 
     * @return the HealthBar for the tower.
     */
    public HealthBar getHealthBar() {
        return this.healthBar;
    }

    /**
     * Updates the tower healthBar with the damage done to the tower.
     * 
     * @param damage the damage done to the tower
     */
    public void damage(int damage){
        healthBar.reduce(damage);
    }

    /**
     * The main method for executing the GUI.
     * 
     * @param args Not used.
     */
    public static void main(String[] args) {
        //THIS WILL PRINT OUT ANY ERRORS IN THE ERROR LOG TEXT FILE
        try{
        System.setErr(new PrintStream("error_log.txt"));
        }catch(FileNotFoundException e){
        System.err.println("File Not Found: " + e);
        }

        //CREATES THE TOWER IMAGE
        try {
            InputStream imageStream = new BufferedInputStream(DatabaseDriver.class.getResourceAsStream(TOWER_PIC_FILEPATH));
            towerPic = ImageIO.read(imageStream);
        } catch (IOException e) {
            System.out.println("TowerDefense: Error loading image");
            e.printStackTrace();
        }

        //LAUNCH THE MAIN THREAD THAT WILL MANAGAE THE GUI
        javax.swing.SwingUtilities.invokeLater(new TowerDefense());
    }

}
