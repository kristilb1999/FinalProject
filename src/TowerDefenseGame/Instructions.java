package TowerDefenseGame;

 
//imports
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.UIManager;

/**
 * This class creates an instruction guide for the TowerDefense game.
 *
 * @author Kristi Boardman, Cameron Costello, Will Skelly, Jake Burch
 * @version Spring 2020
 */
public class Instructions extends Thread implements ActionListener
{
    //THE SIZE OF THE DIALOG BOX
    private static final int WINDOW_WIDTH = 1000;
    private static final int WINDOW_HEIGHT = 775;

    //THE SIZE OF THE PANEL HOLDING THE INSTRUCTIONS
    private static final int PANEL_WIDTH = 900;
    private static final int PANEL_HEIGHT = 600;

    //THE SIZE OF THE PANEL HOLDING THE BUTTONS
    private static final int BUTTON_PANEL_WIDTH = 900;
    private static final int BUTTON_PANEL_HEIGHT = 100;
    
    //WHERE TO FIND THE EXTRA INSTRUCTIONS
    //based on
    //https://www.novixys.com/blog/read-file-resources-folder-java/
    InputStream instructionsStream = Instructions.class.getResourceAsStream("/instructions.txt");
    
    //THE FONT TO USE
    private static final Font USE_THIS_FONT = new Font("Rockwell", Font.BOLD, 25);
    
    //THE COLOR OF THE BACKGROUND PANEL
    private static final Color BACKGROUND_COLOR = new Color(77, 51, 26);
    
    //THE COLOR OF THE INSIDE PANELS
    private static final Color BRICK_COLOR = new Color(252, 188, 176);

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
    private JPanel textPanel;
    private JPanel buttonPanel;

    //LABELS TO HOLD INFO OF EACH SOLDIER
    private JLabel aveZomLabel;
    private JLabel bigEyeLabel;
    private JLabel hunchbackLabel;
    private JLabel pirateLabel;

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
        closeButton = new JButton("     Close     ");
        closeButton.setFont(USE_THIS_FONT);
        closeButton.setForeground(Color.WHITE);
        closeButton.setBackground(Color.BLACK);

        //CREATES THE PANEL THAT GOES INTO THE DIALOG BOX
        //THIS ALLOWS THINGS TO BE DISPLAYED IN DIALOG BOX
        instructionPanel = new JPanel();
        instructionPanel.setBackground(BACKGROUND_COLOR);

        //CREATES PANEL TO HOLD PICTURES AND LABELS
        textPanel =  new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        textPanel.setBackground(BRICK_COLOR);

        //CREATES PANEL TO HOLD CLOSE BUTTON
        buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(BUTTON_PANEL_WIDTH, BUTTON_PANEL_HEIGHT));
        buttonPanel.setBackground(BRICK_COLOR);
        
        //INFO ABOUT AVERAGE ZOMBIE
        aveZomLabel = new JLabel("Average Zombie: Speed: 2 Strength: 1 Worth: 100 points");

        //INFO ABOUT HUNCHBACK 
        hunchbackLabel = new JLabel("Hunchback: Speed: 5 Strength: 2 Worth: 200 points");
        
        //INFO ABOUT BIG EYE 
        bigEyeLabel = new JLabel("Big Eye: Speed: 7 Strength: 3 Worth: 300 points");
        
        //INFO ABOUT PIRATE
        pirateLabel = new JLabel("Pirate: Speed: 10 Strength: 5 Worth: 500 points");
        
        //ADD SOLDIER LABELS TO TEXT PANEL
        textPanel.add(aveZomLabel);
        
        textPanel.add(hunchbackLabel);
        
        textPanel.add(bigEyeLabel);
       
        textPanel.add(pirateLabel);
        
        //INFO ABOUT GRENADE
        grenadeLabel = new JLabel("Bounces off of the ground and then explodes shortly after.");
        
        //INFO ABOUT MOLOTOV COCKTAIL
        molotovCocktailLabel = new JLabel("Explodes on impact with either ground or enemies into red explosion.");
        
        //INFO ABOUT TNT
        tntLabel = new JLabel("Explodes on impact with either ground or enemies into orange explosion.");
        
        //INFO ABOUT BOULDER
        boulderLabel = new JLabel("Bounces off of the ground.");
        
        //ADD WEAPON LABELS TO TEXT PANEL
        textPanel.add(grenadeLabel);
        
        textPanel.add(molotovCocktailLabel);
        
        textPanel.add(tntLabel);
        
        textPanel.add(boulderLabel);
        
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
        extraInformation.setBorder(UIManager.getBorder("Label.border"));

        //IF THE FILE IS VALID, ADD ALL INFO INTO THE TEXT AREA
        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(instructionsStream));

            String toAdd = "\n\n\n\n";

            String line;

            while((line = br.readLine()) != null) {
                toAdd += line + "\n";
            }

            extraInformation.setText(toAdd);
        } catch(IOException e) {
            System.err.println("IO Exception: " + e);
        }

        //ADD THE REST OF THE INSTRUCTIONS TO THE PANEL
        textPanel.add(extraInformation);
        
        //SETS THE FONT OF ALL TEXT ON SCREEN
        aveZomLabel.setFont(USE_THIS_FONT);
        bigEyeLabel.setFont(USE_THIS_FONT);
        hunchbackLabel.setFont(USE_THIS_FONT);
        pirateLabel.setFont(USE_THIS_FONT);
        
        grenadeLabel.setFont(USE_THIS_FONT);
        molotovCocktailLabel.setFont(USE_THIS_FONT);
        boulderLabel.setFont(USE_THIS_FONT);
        tntLabel.setFont(USE_THIS_FONT);
        
        extraInformation.setFont(USE_THIS_FONT);

        //SET THE ALIGNMENT OF LABELS TO CENTER
        textPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

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
        instructionPanel.add(textPanel);

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
