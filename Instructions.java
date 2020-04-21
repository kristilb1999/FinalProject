// going to be lazy about imports in this class
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.imageio.*;
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
    private static final int WINDOW_WIDTH = 750;
    private static final int WINDOW_HEIGHT = 500;
    
    private static final int PANEL_WIDTH = 750;
    private static final int PANEL_HEIGHT = 350;
    
    private static final int BUTTON_PANEL_WIDTH = 750;
    private static final int BUTTON_PANEL_HEIGHT = 150;

    private JDialog instructionDialog;

    private JPanel instructionPanel;
    private JPanel picturePanel;
    private JPanel buttonPanel;

    private LoadImageApp aveZom;
    private LoadImageApp bigEye;
    private LoadImageApp hunchback;
    private LoadImageApp pirate;
    
    private JLabel aveZomLabel;
    private JLabel bigEyeLabel;
    private JLabel hunchbackLabel;
    private JLabel pirateLabel;
    
    private LoadImageApp grenade;
    private LoadImageApp molotovCocktail;
    private LoadImageApp tnt;
    private LoadImageApp boulder;
    
    private JLabel grenadeLabel;
    private JLabel molotovCocktailLabel;
    private JLabel tntLabel;
    private JLabel boulderLabel;

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

        closeButton = new JButton("Close");
        closeButton.setFont(new Font("Rockwell", Font.BOLD, 25));
        closeButton.setForeground(Color.WHITE);
        closeButton.setBackground(Color.BLACK);

        instructionPanel = new JPanel();
        
        picturePanel = new JPanel();
        picturePanel.setLayout(new BoxLayout(picturePanel, BoxLayout.Y_AXIS));
        picturePanel.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        
        buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(BUTTON_PANEL_WIDTH, BUTTON_PANEL_HEIGHT));
        
        aveZomLabel = new JLabel("Average Zombie: Speed: 2 Strength: 1 Worth: 100 points");
        
        aveZom = new LoadImageApp("soldierTypeOne.png");
        aveZom.setSize(new Dimension(20, 40));
        
        hunchbackLabel = new JLabel("Hunchback: Speed: 5 Strength: 2 Worth: 200 points");
        
        hunchback = new LoadImageApp("soldierTypeThree.png");
        hunchback.setSize(new Dimension(20, 40));
        
        bigEyeLabel = new JLabel("Big Eye: Speed: 7 Strength: 3 Worth: 300 points");
        
        bigEye = new LoadImageApp("soldierTypeTwo.png");
        bigEye.setSize(new Dimension(20, 40));
        
        pirateLabel = new JLabel("Pirate: Speed: 10 Strength: 5 Worth: 500 points");
        
        pirate = new LoadImageApp("soldierTypeFour.png");
        pirate.setSize(new Dimension(20, 40));
        
        picturePanel.add(aveZomLabel);
        //picturePanel.add(aveZom);
        
        picturePanel.add(hunchbackLabel);
        //picturePanel.add(hunchback);
        
        picturePanel.add(bigEyeLabel);
        //picturePanel.add(bigEye);
        
        picturePanel.add(pirateLabel);
        //picturePanel.add(pirate);
        
        grenadeLabel = new JLabel("Bounces off of the ground and then explodes shortly after.");
        
        grenade = new LoadImageApp("weaponTypeThree.png");
        grenade.setSize(new Dimension(10, 10));
        
        molotovCocktailLabel = new JLabel("Explodes on impact with either ground or enemies into red explosion.");
        
        molotovCocktail = new LoadImageApp("weaponTypeTwo.png");
        molotovCocktail.setSize(new Dimension(10, 10));
        
        tntLabel = new JLabel("Explodes on impact with either ground or enemies into orange explosion.");
        
        tnt = new LoadImageApp("weaponTypeFour.png");
        tnt.setSize(new Dimension(10, 10));
        
        boulderLabel = new JLabel("Bounces off of the ground.");
        
        boulder = new LoadImageApp("weaponTypeOne.png");
        boulder.setSize(new Dimension(10, 10));
        
        picturePanel.add(grenadeLabel);
        //picturePanel.add(grenade);
        
        picturePanel.add(molotovCocktailLabel);
        //picturePanel.add(molotovCocktail);
        
        picturePanel.add(tntLabel);
        //picturePanel.add(tnt);
        
        picturePanel.add(boulderLabel);
        //picturePanel.add(boulder);
        
        picturePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        aveZomLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        hunchbackLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        bigEyeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        pirateLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        grenadeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        molotovCocktailLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        tntLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        boulderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        instructionPanel.add(picturePanel);

        buttonPanel.add(closeButton);
        
        instructionPanel.add(buttonPanel);
        
        instructionDialog.add(instructionPanel);

        instructionDialog.pack();
        instructionDialog.setLocationRelativeTo(this.container);
        instructionDialog.setVisible(false);

        closeButton.addActionListener(this);

    }

    /**
     * Closes the JDialog on button click.
     * 
     * @param e The action event found.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(closeButton)) {
            instructionDialog.setVisible(false);
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

/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */ 

/**
 * This class demonstrates how to load an Image from an external file
 */
class LoadImageApp extends Component {
          
    BufferedImage img;

    public void paint(Graphics g) {
        g.drawImage(img, 0, 0, null);
    }

    public LoadImageApp(String filename) {
       try {
           img = ImageIO.read(new File(filename));
       } catch (IOException e) {
       }
    }

    public Dimension getPreferredSize() {
        if (img == null) {
             return new Dimension(100,100);
        } else {
           return new Dimension(img.getWidth(null), img.getHeight(null));
       }
    }
}
