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
    //THE SIZE OF THE DIALOG BOX
    private static final int WINDOW_WIDTH = 750;
    private static final int WINDOW_HEIGHT = 500;

    //THE SIZE OF THE PANEL HOLDING THE INSTRUCTIONS
    private static final int PANEL_WIDTH = 700;
    private static final int PANEL_HEIGHT = 350;

    //THE SIZE OF THE PANEL HOLDING THE BUTTONS
    private static final int BUTTON_PANEL_WIDTH = 700;
    private static final int BUTTON_PANEL_HEIGHT = 150;

    //WHERE TO FIND THE EXTRA INSTRUCTIONS
    private static final String FILENAME = "instructions.txt";

    //THE FILE NAMES FOR THE SOLDIER PICTURES
    private static final String AVE_ZOM_FILE = "soldierTypeOne.png";
    private static final String HUNCHBACK = "soldierTypeThree.png";
    private static final String PIRATE = "soldierTypeFour.png";
    private static final String BIG_EYE = "soldierTypeTwo.png";
    
    //THE FILE NAMES FOR THE WEAPON PICTURES
    private static final String GRENADE = "weaponTypeThree.png";
    private static final String MOLOTOV_COCKTAIL = "weaponTypeTwo.png";
    private static final String TNT = "weaponTypeFour.png";
    private static final String BOULDER = "weaponTypeOne.png";

    //THE JDIALOG BOX TO HOLD THE INSTRUCTIONS
    private JDialog instructionDialog;

    //THE PANELS TO HOLD ALL INFORMATION TO DISPLAY
    private JPanel instructionPanel;
    private JPanel picturePanel;
    private JPanel buttonPanel;

    //IMAGES OF EACH TYPE OF SOLDIER
    private LoadImageApp aveZom;
    private LoadImageApp bigEye;
    private LoadImageApp hunchback;
    private LoadImageApp pirate;

    //LABELS TO HOLD INFO OF EACH SOLDIER
    private JLabel aveZomLabel;
    private JLabel bigEyeLabel;
    private JLabel hunchbackLabel;
    private JLabel pirateLabel;

    //IMAGES OF EACH TYPE OF WEAPON
    private LoadImageApp grenade;
    private LoadImageApp molotovCocktail;
    private LoadImageApp tnt;
    private LoadImageApp boulder;

    //LABELS FOR EACH TYPE OF WEAPON
    private JLabel grenadeLabel;
    private JLabel molotovCocktailLabel;
    private JLabel tntLabel;
    private JLabel boulderLabel;

    //TEXT AREA TO HOLD ALL READ IN INSTRUCTIONS
    private JTextArea extraInformation;

    //BUTTON TO CLOSE INSTRUCTION PANE
    private JButton closeButton;

    //CONTAINER TO PLACE DIALOG BOX OVER
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
    public void run(){
        //CREATES THE DIALOG BOX
        instructionDialog = new JDialog();
        instructionDialog.setTitle("Instructions");
        instructionDialog.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));

        //CREATES THE CLOSE BUTTON
        closeButton = new JButton("Close");
        closeButton.setFont(new Font("Rockwell", Font.BOLD, 25));
        closeButton.setForeground(Color.WHITE);
        closeButton.setBackground(Color.BLACK);

        //CREATES THE PANEL THAT GOES INTO THE DIALOG BOX
        //THIS ALLOWS THINGS TO BE DISPLAYED IN DIALOG BOX
        instructionPanel = new JPanel();

        //CREATES PANEL TO HOLD PICTURES AND LABELS
        picturePanel = new JPanel();
        picturePanel.setLayout(new BoxLayout(picturePanel, BoxLayout.Y_AXIS));
        picturePanel.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));

        //CREATES PANEL TO HOLD CLOSE BUTTON
        buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(BUTTON_PANEL_WIDTH, BUTTON_PANEL_HEIGHT));

        //INFO ABOUT AVERAGE ZOMBIE
        aveZomLabel = new JLabel("Average Zombie: Speed: 2 Strength: 1 Worth: 100 points");
        aveZom = new LoadImageApp(AVE_ZOM_FILE);
        aveZom.setSize(new Dimension(20, 40));

        //INFO ABOUT HUNCHBACK 
        hunchbackLabel = new JLabel("Hunchback: Speed: 5 Strength: 2 Worth: 200 points");
        hunchback = new LoadImageApp(HUNCHBACK);
        hunchback.setSize(new Dimension(20, 40));

        //INFO ABOUT BIG EYE 
        bigEyeLabel = new JLabel("Big Eye: Speed: 7 Strength: 3 Worth: 300 points");
        bigEye = new LoadImageApp(BIG_EYE);
        bigEye.setSize(new Dimension(20, 40));

        //INFO ABOUT PIRATE
        pirateLabel = new JLabel("Pirate: Speed: 10 Strength: 5 Worth: 500 points");
        pirate = new LoadImageApp(PIRATE);
        pirate.setSize(new Dimension(20, 40));

        //ADD SOLDIER LABELS AND IMAGES TO THE PICTURE PANEL
        picturePanel.add(aveZomLabel);
        //picturePanel.add(aveZom);

        picturePanel.add(hunchbackLabel);
        //picturePanel.add(hunchback);

        picturePanel.add(bigEyeLabel);
        //picturePanel.add(bigEye);

        picturePanel.add(pirateLabel);
        //picturePanel.add(pirate);

        //INFO ABOUT GRENADE
        grenadeLabel = new JLabel("Bounces off of the ground and then explodes shortly after.");
        grenade = new LoadImageApp(GRENADE);
        grenade.setSize(new Dimension(10, 10));

        //INFO ABOUT MOLOTOV COCKTAIL
        molotovCocktailLabel = new JLabel("Explodes on impact with either ground or enemies into red explosion.");
        molotovCocktail = new LoadImageApp(MOLOTOV_COCKTAIL);
        molotovCocktail.setSize(new Dimension(10, 10));

        //INFO ABOUT TNT
        tntLabel = new JLabel("Explodes on impact with either ground or enemies into orange explosion.");
        tnt = new LoadImageApp(TNT);
        tnt.setSize(new Dimension(10, 10));

        //INFO ABOUT BOULDER
        boulderLabel = new JLabel("Bounces off of the ground.");
        boulder = new LoadImageApp(BOULDER);
        boulder.setSize(new Dimension(10, 10));

        //ADD WEAPON LABELS AND IMAGES TO PICTURE PANEL
        picturePanel.add(grenadeLabel);
        //picturePanel.add(grenade);

        picturePanel.add(molotovCocktailLabel);
        //picturePanel.add(molotovCocktail);

        picturePanel.add(tntLabel);
        //picturePanel.add(tnt);

        picturePanel.add(boulderLabel);
        //picturePanel.add(boulder);
        
        
        
        //CODE BASED ON:
        // https://stackoverflow.com/questions/26420428/how-to-word-wrap-text-in-jlabel/26426585
        //CREATES A TEXT AREA THAT LOOKS AND ACTS LIKE A JLABEL
        //THAT HAS WRAP AROUND TEXT 
        extraInformation = new JTextArea();
        extraInformation.setWrapStyleWord(true);
        extraInformation.setLineWrap(true);
        extraInformation.setOpaque(false);
        extraInformation.setEditable(false);
        extraInformation.setFocusable(false);
        extraInformation.setBackground(UIManager.getColor("Label.background"));
        extraInformation.setFont(UIManager.getFont("Label.font"));
        extraInformation.setBorder(UIManager.getBorder("Label.border"));

        //IF THE FILE IS VALID, ADD ALL INFO INTO THE TEXT AREA
        try{
            BufferedReader br = new BufferedReader(new FileReader(FILENAME));

            String toAdd = "\n\n\n\n";

            String line = "";

            while((line = br.readLine()) != null) {
                toAdd += line + "\n";
            }

            extraInformation.setText(toAdd);
        }catch(FileNotFoundException e){
            System.err.println("File not found: " + FILENAME);
        } catch(IOException e) {
            System.err.println("IO Exception: " + e);
        }

        //ADD THE REST OF THE INSTRUCTIONS TO THE PANEL
        picturePanel.add(extraInformation);
        
        //SET THE ALIGNMENT OF LABELS TO CENTER
        picturePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        aveZomLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        hunchbackLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        bigEyeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        pirateLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        grenadeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        molotovCocktailLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        tntLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        boulderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        extraInformation.setAlignmentX(Component.CENTER_ALIGNMENT);

        //ADD PICTURE PANEL TO INSTRUCTION PANEL
        instructionPanel.add(picturePanel);

        //ADD CLOSE BUTTON TO BUTTON PANEL
        buttonPanel.add(closeButton);

        //ADD BUTTON PANEL TO INSTRUCTION PANEL
        instructionPanel.add(buttonPanel);

        //ADD EVERYTHING TO THE DIALOG
        instructionDialog.add(instructionPanel);

        //PACK AND SHOW DIALOG BOX
        instructionDialog.pack();
        instructionDialog.setLocationRelativeTo(this.container);
        instructionDialog.setVisible(false);

        //ADD ACTION LISTENER TO BUTTON
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

//CODE TAKEN FROM:
// https://docs.oracle.com/javase/tutorial/2d/images/examples/LoadImageApp.java

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
