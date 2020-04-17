// going to be lazy about imports in this class
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.geom.*;

/**
 * Write a description of class TowerDefense here.
 *
 * @author Kristi Boardman, Cameron Costello, Will Skelly, Jake Burch
 * @version Spring 2020
 */
public class TowerDefense extends MouseAdapter implements Runnable, ActionListener
{
    private static final int PANEL_WIDTH = 1250;
    private static final int PANEL_HEIGHT = 700;

    private static final int FRAME_WIDTH = 1250;
    private static final int FRAME_HEIGHT = 800;

    private static final int TOWER_X_DISPLACEMENT = 325;
    private static final int TOWER_Y_DISPLACEMENT = 475;

    private static final int EASY = 0;
    private static final int MEDIUM = 1;
    private static final int HARD = 2;

    private int towerXPos;
    private int towerYPos;

    private int grassLine;

    private int towerHealth = 5;

    private static final Color DAY_GRASS = new Color(42, 153, 32);
    private static final Color NIGHT_GRASS = new Color(29, 112, 87);

    private static final Color DAY_SKY = new Color(73, 185, 230);
    private static final Color NIGHT_SKY = new Color(62, 53, 150);

    private static Image towerPic;

    private static final String towerPicFilename = "towerImage.png";

    private Vector<SoldierArmy> soldierArmyList;
    private Vector<Weapon> weaponList;

    private boolean nightTime;
    private boolean gameLost;

    private JPanel panel;
    private JPanel startPanel;

    private JButton easyRound;
    private JButton mediumRound;
    private JButton hardRound;

    public static final double SLING_FACTOR = 2.5;

    // press/drag points for launching, and if we are dragging
    private boolean dragging;
    private Point pressPoint;
    private Point dragPoint;

    // an object to serve as the lock for thread safety of our list access
    private Object weaponLock = new Object();
    private Object soldierLock = new Object();
    private Object healthLock = new Object();

    /**
    Method to redraw our basic winter scene in the graphics panel.

    @param g the Graphics object in which to paint
     */
    protected void redrawScene(Graphics g) {
        int width = panel.getWidth();
        int height = panel.getHeight();

        grassLine = height/4;

        towerXPos = width - TOWER_X_DISPLACEMENT;
        towerYPos = height - TOWER_Y_DISPLACEMENT;

        if(!nightTime) {
            g.setColor(DAY_SKY);
            g.fillRect(0, 0, width, height - grassLine);

            g.setColor(DAY_GRASS);
            g.fillRect(0, height - grassLine, width, height);
        } else {
            g.setColor(NIGHT_SKY);
            g.fillRect(0, 0, width, height - grassLine);

            g.setColor(NIGHT_GRASS);
            g.fillRect(0, height - grassLine, width, height);
        }

        g.drawImage(towerPic, towerXPos, towerYPos, null);

        // if we are currently dragging, draw a sling line
        if (dragging) {
            g.setColor(Color.RED);
            g.drawLine(pressPoint.x, pressPoint.y,
                dragPoint.x, dragPoint.y);
        }

        // redraw each ball at its current position,
        // remove the ones that are done along the way
        int i = 0;

        // since we will be modifying the list, we will
        // lock access in case a mouseReleased is going
        // to happen at the same time

        synchronized (soldierLock) {
            while (i < soldierArmyList.size()) {
                SoldierArmy s = soldierArmyList.get(i);

                if (s.done()) {
                    soldierArmyList.remove(i);
                }
                else {
                    s.paint(g);
                    s.setWeaponList(weaponList);
                    i++;
                }
            }
        }

        i = 0;
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

        if(gameLost) {
            int centerX = width/2;
            int centerY = height/2;

            Font currentFont = g.getFont();
            Font newFont = new Font("Rockwell", Font.BOLD, 50);
            g.setFont(newFont);

            FontMetrics fontInfo = g.getFontMetrics();
            centerX = centerX - (fontInfo.stringWidth("Click to create bubbles!!")/2);
            centerY = centerY - (fontInfo.getAscent()/2);

            g.setColor(Color.MAGENTA);
            g.drawString("You're a loser.", centerX, centerY);

        }
    }

    @Override
    public void run() {
        // set up the GUI "look and feel" which should match
        // the OS on which we are running
        JFrame.setDefaultLookAndFeelDecorated(true);

        // create a JFrame in which we will build our very
        // tiny GUI, and give the window a name
        JFrame frame = new JFrame("TowerDefenseGame");
        frame.setPreferredSize(new Dimension(FRAME_WIDTH,FRAME_HEIGHT));

        // tell the JFrame that when someone closes the
        // window, the application should terminate
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        easyRound = new JButton("Easy");      
        mediumRound = new JButton("Medium");
        hardRound = new JButton("Hard");

        JPanel panelHolder = new JPanel(new FlowLayout());
        frame.add(panelHolder);

        // JPanel with a paintComponent method
        panel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                // first, we should call the paintComponent method we are
                // overriding in JPanel
                super.paintComponent(g);

                // redraw our main scene
                redrawScene(g);

                if(towerHealth <= 0) {
                    gameLost = true;
                }
            }

        };

        panel.setPreferredSize(new Dimension(PANEL_WIDTH,PANEL_HEIGHT));
        startPanel = new JPanel();
        startPanel.setBackground(Color.RED);

        startPanel.add(easyRound);
        startPanel.add(mediumRound);
        startPanel.add(hardRound);

        panelHolder.add(panel);
        panelHolder.add(startPanel);
        panel.addMouseListener(this);
        easyRound.addActionListener(this);
        mediumRound.addActionListener(this);
        hardRound.addActionListener(this);

        weaponList = new Vector<Weapon>();

        soldierArmyList = new Vector<SoldierArmy>();

        // display the window we've created
        frame.pack();
        frame.setVisible(true);

        new Thread() {
            @Override
            public void run() {
                while(true){
                    try{
                        sleep(33);
                    } catch (InterruptedException e){
                        System.out.print(e);
                    }

                    panel.repaint();
                }
            }
        }.start(); 
    }

    /** 
    Mouse press event handler to set up to create a new
    BouncingGravityBall on subsequent release.
    @param e mouse event info
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource().equals(easyRound))
        {
            SoldierArmy easy = new SoldierArmy(EASY, panel, this);
            soldierArmyList.add(easy);
            easy.start();
        }
        if (e.getSource().equals(mediumRound))
        {
            SoldierArmy medium = new SoldierArmy(MEDIUM, panel, this);
            soldierArmyList.add(medium);
            medium.start();
        }
        if (e.getSource().equals(hardRound))
        {
            SoldierArmy hard = new SoldierArmy(HARD, panel, this);
            soldierArmyList.add(hard);
            hard.start();
        }
    }

    /**
    Mouse press event handler to set up to create a new
    BouncingGravityBall on subsequent release.
    @param e mouse event info
     */
    @Override
    public void mousePressed(MouseEvent e) {

        pressPoint = e.getPoint();
    }

    /**
    Mouse drag event handler to create remember the current point
    for sling line drawing.
    @param e mouse event info
     */
    @Override
    public void mouseDragged(MouseEvent e) {

        dragPoint = e.getPoint();
        dragging = true;
    }

    /**
    Mouse release event handler to create a new BouncingGravityBall
    centered at the release point, initial velocities depending on 
    distance from press point.
    @param e mouse event info
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        Weapon newWeapon = QuarterMaster.getRandomWeapon(panel,
                new Point2D.Double(e.getPoint().x , e.getPoint().y),
                new Point2D.Double( 
                    SLING_FACTOR * (pressPoint.x - e.getPoint().x) , 
                    SLING_FACTOR * (pressPoint.y - e.getPoint().y) 
                ) );

        // lock access to the list in case paintComponent is using it
        // concurrently
        synchronized (weaponLock) {
            weaponList.add(newWeapon);
        }

        newWeapon.start();
        dragging = false;
    }

    public void modifyTowerHealth(int numDamage) {
        synchronized (healthLock) {
            towerHealth = towerHealth - numDamage;
        }
    }

    public static void main(String[] args) {
        try{
            System.setErr(new PrintStream("error_log.txt"));
        }catch(FileNotFoundException e){
            System.err.println("File Not Found: " + e);
        }

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        towerPic = toolkit.getImage(towerPicFilename);

        // launch the main thread that will manage the GUI
        javax.swing.SwingUtilities.invokeLater(new TowerDefense());
    }

}
