// going to be lazy about imports in this class
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.geom.*;

/**
 * This class creates an instruction guide for the TowerDefense game.
 *
 * @author Kristi Boardman, Cameron Costello, Will Skelly, Jake Burch
 * @version Spring 2020
 */
public class Instructions extends Thread implements ActionListener
{
    private static final int WINDOW_WIDTH = 300;
    private static final int WINDOW_HEIGHT = 300;
    
    private JDialog instructionDialog;
    
    private JPanel instructionPanel;
    
    private JLabel aveZomLabel;
    private JLabel bigEyeLabel;
    private JLabel hunchbackLabel;
    private JLabel pirateLabel;
    
    private JButton closeButton;
    
    private JComponent container;

    /**
     * Constructs an instruction object that sets the container.
     * 
     * @param container The container that holds the Instructions object.
     */
    public Instructions(JComponent container)
    {
        this.container = container;
    }
    
    /**
     * Creates the dialog panel with all the game information.
     */
    @Override
    public void run() {
        instructionDialog = new JDialog();
        instructionDialog.setTitle("Instructions");
        instructionDialog.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        
        ImageIcon averageZombie = new ImageIcon("soldierTypeOne.jpg");
        ImageIcon bigEye = new ImageIcon("soldierTypeTwo.jpg");
        ImageIcon hunchback = new ImageIcon("soldierTypeThree.jpg");
        ImageIcon pirate = new ImageIcon("soldierTypeFour.jpg");
        
        aveZomLabel = new JLabel("Average Zombie", averageZombie, JLabel.CENTER);
        bigEyeLabel = new JLabel("Big Eye", bigEye, JLabel.CENTER);
        hunchbackLabel = new JLabel("Hunchback", hunchback, JLabel.CENTER);
        pirateLabel = new JLabel("Pirate", pirate, JLabel.CENTER);
        
        closeButton = new JButton("Close");
        
        instructionPanel = new JPanel();
        
        instructionPanel.add(aveZomLabel);
        instructionPanel.add(bigEyeLabel);
        instructionPanel.add(hunchbackLabel);
        instructionPanel.add(pirateLabel);
        
        instructionPanel.add(closeButton);
        
        instructionDialog.add(instructionPanel);
        
    }
    
    /**
     * Closes the JDialog on button click.
     * 
     * @param e The action event found.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(closeButton)) {
            instructionDialog.dispose();
        }
    }
    
    /**
     * Makes the dialog panel visible.
     */
    public void show(){
        instructionDialog.setVisible(true);
    }
    
    /**
     * Sets the visibility of the dialog panel.
     * 
     * @param visible What to set the dialog's visibility to.
     */
    public void setVisible(boolean visible) {
        instructionDialog.setVisible(visible);
    }
    
    /**
     * Returns whether or not the dialog panel is visible.
     * 
     * @return Whether or not the dialog panel is visible.
     */
    public boolean isVisible() {
        return instructionDialog.isVisible();
    }
}
