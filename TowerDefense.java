// going to be lazy about imports in this class
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;

/**
 * Write a description of class TowerDefense here.
 *
 * @author Kristi Boardman, Cameron Costello, Will Skelly, Jake Burch
 * @version Spring 2020
 */
public class TowerDefense extends MouseAdapter implements Runnable
{
    private static final int FRAME_WIDTH = 1250;
    private static final int FRAME_HEIGHT = 700;

    private static final int TOWER_X_DISPLACEMENT = 325;
    private static final int TOWER_Y_DISPLACEMENT = 475;

    private int towerXPos;
    private int towerYPos;

    private int grassLine;

    private static final Color DAY_GRASS = new Color(42, 153, 32);
    private static final Color NIGHT_GRASS = new Color(29, 112, 87);

    private static final Color DAY_SKY = new Color(73, 185, 230);
    private static final Color NIGHT_SKY = new Color(62, 53, 150);

    private static Image towerPic;

    private static final String towerPicFilename = "towerImage.png";

    private Vector<Soldier> soldierList;
    private Vector<Weapon> weaponList;

    private boolean nightTime;

    private JPanel panel;

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

        // JPanel with a paintComponent method
        panel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                // first, we should call the paintComponent method we are
                // overriding in JPanel
                super.paintComponent(g);

                // redraw our main scene
                redrawScene(g);
            }
        };

        frame.add(panel);
        panel.addMouseListener(this);

        // display the window we've created
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        towerPic = toolkit.getImage(towerPicFilename);

        // launch the main thread that will manage the GUI
        javax.swing.SwingUtilities.invokeLater(new TowerDefense());
    }

}
