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
    private static final int FRAME_WIDTH = 1000;
    private static final int FRAME_HEIGHT = 500;

    private Vector<Soldier> soldierList;
    private Vector<Weapon> weaponList;

    private JPanel panel;

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
            }
        };
        
        frame.add(panel);
	panel.addMouseListener(this);
	
	// display the window we've created
	frame.pack();
	frame.setVisible(true);
    }

    public static void main(String[] args) {
        // launch the main thread that will manage the GUI
        javax.swing.SwingUtilities.invokeLater(new TowerDefense());
    }

}
