// going to be lazy about imports in this class
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.geom.*;

/**
 * This class keeps track of the current score, handles the
 * scoreboard dialog boxes, and accesses the database using
 * the DatabaseDriver class. 
 *
 * @author Kristi Boardman, Cameron Costello, Will Skelly, Jake Burch
 * @version Spring 2020
 */
public class Scoreboard extends Thread implements ActionListener
{
    //constants for window dimensions
    private static final int WINDOW_WIDTH = 500;
    private static final int WINDOW_HEIGHT = 250;
    //constants for text field dimensions
    private static final int TEXTFIELD_WIDTH = 100;
    private static final int TEXTFIELD_HEIGHT = 20;
    //constants for font sizes
    private static final int FONT_SIZE_LARGE = 25;
    private static final int FONT_SIZE_SMALL = 16;
    //constants for colors used
    private static final Color COLOR_TEXT = Color.WHITE;
    private static final Color COLOR_BUTTON = Color.BLUE;
    private static final Color COLOR_BACK = Color.BLACK;
    //constant for font name
    private static final String TEXT_FONT = "Rockwell";
    //database file name
    private static File databaseFile = new File("dropCreateSampleDatabase.txt");

    //instance variables
    //variables for storing information
    private int score;
    private String lastAddedPlayerName;
    //variables for user input objects
    private JDialog saveScoreDialog;
    private JDialog addFriendDialog;
    private JDialog addPlayerDialog;
    private JPanel saveScorePanel;
    private JPanel addPlayerPanel;
    private JPanel addFriendPanel;
    private JLabel yourScoreLabel;
    private JLabel friendScoreLabel;
    private JLabel selectFriendHighscoreLabel;
    private JTextField newPlayerNameField;
    private JComboBox<String> yourNameScoreCombo;
    private JComboBox<String> friendNameAddCombo;
    private JComboBox<String> yourNameAddCombo;
    private JComboBox<String> friendNameScoreCombo;
    private JButton saveScoreButton;
    private JButton addPlayerButton;
    private JButton saveNameButton;
    private JButton addFriendButton;
    private JButton saveFriendButton;
    private JButton exitButton;
    private JButton cancelAddPlayerButton;
    private JButton cancelAddFriendButton;
    private JComponent container;
    //lock for score for thread safety
    private Object scoreLock = new Object();

    /**
     * Constructs a new Scoreboard.
     * 
     * @param container The container used for centering the dialog boxes.
     */
    public Scoreboard(JComponent container){
        this.score = 0;
        this.lastAddedPlayerName = "";
        this.container = container;
    }

    /**
     * Constructs a new Scoreboard.
     * 
     * @param container The container used for centering the dialog boxes.
     */
    public Scoreboard(){
        this(new JPanel());
    }

    /**
     * Constructs a scoreboard dialog box for saving players and scores
     * as well as friendships between players in a database.
     * 
     */
    public void run(){
        //check that the database exists
        if(DatabaseDriver.checkDatabase(databaseFile) > 1){
           System.out.println("Scoreboard: Database check failed using build file: " + databaseFile);
        }

        //dialog box for adding a highscore
        saveScoreDialog = new JDialog();
        saveScoreDialog.setTitle("Add New Highscore");
        saveScoreDialog.setPreferredSize(new Dimension(WINDOW_WIDTH,WINDOW_HEIGHT));
        saveScorePanel = new JPanel();
        JLabel selectPlayerNameLabel = new JLabel("Select Your Player Name: ");
        saveScorePanel.add(selectPlayerNameLabel);
        yourNameScoreCombo = new JComboBox<String>(DatabaseDriver.getAllPlayers());
        saveScorePanel.add(yourNameScoreCombo);
        yourScoreLabel = new JLabel("Your Highscore: 0");
        saveScorePanel.add(yourScoreLabel);
        saveScoreButton = new JButton("Save Highscore");
        saveScorePanel.add(saveScoreButton);
        addPlayerButton = new JButton("Add New Player");
        saveScorePanel.add(addPlayerButton);
        addFriendButton = new JButton("Add Friend");
        saveScorePanel.add(addFriendButton);
        exitButton = new JButton("Exit");
        saveScorePanel.add(exitButton);

        selectFriendHighscoreLabel = 
        new JLabel("Select a friend's name to see their highscore: ");
        saveScorePanel.add(selectFriendHighscoreLabel);
        friendScoreLabel = new JLabel();

        String yourName = (String)yourNameScoreCombo.getSelectedItem();
        if(yourName != null){
            Vector<String> yourFriends = DatabaseDriver.getFriends(yourName);

            if(yourFriends.size() == 0){
                friendNameScoreCombo = new JComboBox<String>();
                friendScoreLabel.setVisible(false);
                friendNameScoreCombo.setVisible(false);
                selectFriendHighscoreLabel.setVisible(false);
            } else {
                friendNameScoreCombo = new JComboBox<String>(yourFriends);
            }

            try{
                friendNameScoreCombo.setSelectedIndex(0);
            }catch(IllegalArgumentException exc){
                friendNameScoreCombo.setSelectedItem(null);
            }
        } else {
            friendNameScoreCombo = new JComboBox<String>();
            friendScoreLabel.setVisible(false);
            friendNameScoreCombo.setVisible(false);
            selectFriendHighscoreLabel.setVisible(false);
        }
        saveScorePanel.add(friendNameScoreCombo);

        String friendName = (String)friendNameScoreCombo.getSelectedItem();
        friendScoreLabel.setText("Your Friend's Highscore: " + 
            (friendName == null ? "" : DatabaseDriver.getScore(friendName)));

        saveScorePanel.add(friendScoreLabel);
        saveScoreDialog.add(saveScorePanel);

        //dialog box for adding a new player
        addPlayerDialog = new JDialog();
        addPlayerDialog.setTitle("Add New Player");
        //Commented for potential addition
        //addPlayerDialog.setPreferredSize(new Dimension(WINDOW_WIDTH,WINDOW_HEIGHT));
        //fields for adding a player
        addPlayerPanel = new JPanel();
        JLabel enterYourNameLabel = new JLabel("Enter Your Name: ");
        addPlayerPanel.add(enterYourNameLabel);
        newPlayerNameField = new JTextField("");
        newPlayerNameField.setPreferredSize(new Dimension(TEXTFIELD_WIDTH,TEXTFIELD_HEIGHT));
        addPlayerPanel.add(newPlayerNameField);
        saveNameButton = new JButton("Save Player Name");
        addPlayerPanel.add(saveNameButton);
        cancelAddPlayerButton = new JButton("Cancel");
        addPlayerPanel.add(cancelAddPlayerButton);
        addPlayerDialog.add(addPlayerPanel);

        //dialog box for adding a friend
        addFriendDialog = new JDialog();
        addFriendDialog.setTitle("Add Friend");
        addFriendDialog.setPreferredSize(new Dimension(WINDOW_WIDTH,WINDOW_HEIGHT));
        //fields for adding a friend
        addFriendPanel = new JPanel();
        JLabel selectYourNameAddLabel = new JLabel("Select Your Player Name: ");
        addFriendPanel.add(selectYourNameAddLabel);
        yourNameAddCombo = new JComboBox<String>(DatabaseDriver.getAllPlayers());
        addFriendPanel.add(yourNameAddCombo);
        JLabel selectFriendAddLabel = new JLabel("Select Your Friend's Player Name: ");
        addFriendPanel.add(selectFriendAddLabel);
        friendNameAddCombo = new JComboBox<String>(DatabaseDriver.getAllPlayers());
        addFriendPanel.add(friendNameAddCombo);
        saveFriendButton = new JButton("Add Friend");
        addFriendPanel.add(saveFriendButton);
        cancelAddFriendButton = new JButton("Cancel");
        addFriendPanel.add(cancelAddFriendButton);
        addFriendDialog.add(addFriendPanel);

        //change background colors of panels
        saveScorePanel.setForeground(COLOR_TEXT);
        saveScorePanel.setBackground(COLOR_BACK);
        addPlayerPanel.setForeground(COLOR_TEXT);
        addPlayerPanel.setBackground(COLOR_BACK);
        addFriendPanel.setForeground(COLOR_TEXT);
        addFriendPanel.setBackground(COLOR_BACK);
        //change colors of labels
        yourScoreLabel.setForeground(COLOR_TEXT);
        friendScoreLabel.setForeground(COLOR_TEXT);
        selectFriendHighscoreLabel.setForeground(COLOR_TEXT);
        selectPlayerNameLabel.setForeground(COLOR_TEXT);
        selectPlayerNameLabel.setFont(new Font(TEXT_FONT, Font.BOLD, FONT_SIZE_LARGE));
        enterYourNameLabel.setForeground(COLOR_TEXT);
        selectYourNameAddLabel.setForeground(COLOR_TEXT);
        selectFriendAddLabel.setForeground(COLOR_TEXT);
        //change fonts of labels
        yourScoreLabel.setFont(new Font(TEXT_FONT, Font.BOLD, FONT_SIZE_SMALL));
        friendScoreLabel.setFont(new Font(TEXT_FONT, Font.BOLD, FONT_SIZE_SMALL));
        selectFriendHighscoreLabel.setFont(new Font(TEXT_FONT, Font.BOLD, FONT_SIZE_SMALL));
        selectPlayerNameLabel.setFont(new Font(TEXT_FONT, Font.BOLD, FONT_SIZE_SMALL));
        enterYourNameLabel.setFont(new Font(TEXT_FONT, Font.BOLD, FONT_SIZE_SMALL));
        selectYourNameAddLabel.setFont(new Font(TEXT_FONT, Font.BOLD, FONT_SIZE_SMALL));
        selectFriendAddLabel.setFont(new Font(TEXT_FONT, Font.BOLD, FONT_SIZE_SMALL));
        //make comobo boxes look nice
        yourNameScoreCombo.setForeground(COLOR_TEXT);
        friendNameAddCombo.setForeground(COLOR_TEXT);
        yourNameAddCombo.setForeground(COLOR_TEXT);
        friendNameScoreCombo.setForeground(COLOR_TEXT);
        yourNameScoreCombo.setBackground(COLOR_BACK);
        friendNameAddCombo.setBackground(COLOR_BACK);
        yourNameAddCombo.setBackground(COLOR_BACK);
        friendNameScoreCombo.setBackground(COLOR_BACK);
        yourNameScoreCombo.setFont(new Font(TEXT_FONT, Font.BOLD, FONT_SIZE_SMALL));
        friendNameAddCombo.setFont(new Font(TEXT_FONT, Font.BOLD, FONT_SIZE_SMALL));
        yourNameAddCombo.setFont(new Font(TEXT_FONT, Font.BOLD, FONT_SIZE_SMALL));
        friendNameScoreCombo.setFont(new Font(TEXT_FONT, Font.BOLD, FONT_SIZE_SMALL));
        //make the buttons look nice
        saveScoreButton.setFont(new Font(TEXT_FONT, Font.BOLD, FONT_SIZE_LARGE));
        addPlayerButton.setFont(new Font(TEXT_FONT, Font.BOLD, FONT_SIZE_LARGE));
        saveNameButton.setFont(new Font(TEXT_FONT, Font.BOLD, FONT_SIZE_LARGE));
        addFriendButton.setFont(new Font(TEXT_FONT, Font.BOLD, FONT_SIZE_LARGE));
        saveFriendButton.setFont(new Font(TEXT_FONT, Font.BOLD, FONT_SIZE_LARGE));
        exitButton.setFont(new Font(TEXT_FONT, Font.BOLD, FONT_SIZE_LARGE));
        cancelAddPlayerButton.setFont(new Font(TEXT_FONT, Font.BOLD, FONT_SIZE_LARGE));
        cancelAddFriendButton.setFont(new Font(TEXT_FONT, Font.BOLD, FONT_SIZE_LARGE));
        saveScoreButton.setForeground(COLOR_TEXT);
        addPlayerButton.setForeground(COLOR_TEXT);
        saveNameButton.setForeground(COLOR_TEXT);
        addFriendButton.setForeground(COLOR_TEXT);
        saveFriendButton.setForeground(COLOR_TEXT);
        exitButton.setForeground(COLOR_TEXT);
        cancelAddPlayerButton.setForeground(COLOR_TEXT);
        cancelAddFriendButton.setForeground(COLOR_TEXT);
        saveScoreButton.setBackground(COLOR_BUTTON);
        addPlayerButton.setBackground(COLOR_BUTTON);
        saveNameButton.setBackground(COLOR_BUTTON);
        addFriendButton.setBackground(COLOR_BUTTON);
        saveFriendButton.setBackground(COLOR_BUTTON);
        exitButton.setBackground(COLOR_BUTTON);
        cancelAddPlayerButton.setBackground(COLOR_BUTTON);
        cancelAddFriendButton.setBackground(COLOR_BUTTON);

        //based on:
        //https://stackoverflow.com/questions/10030947/center-jdialog-over-parent
        saveScoreDialog.pack();
        addPlayerDialog.pack();
        addFriendDialog.pack();
        saveScoreDialog.setLocationRelativeTo(this.container);
        addPlayerDialog.setLocationRelativeTo(this.container);
        addFriendDialog.setLocationRelativeTo(this.container);
        saveScoreDialog.setVisible(false);
        addPlayerDialog.setVisible(false);
        addFriendDialog.setVisible(false);

        yourNameScoreCombo.addActionListener(this);
        friendNameScoreCombo.addActionListener(this);

        saveScoreButton.addActionListener(this);
        addPlayerButton.addActionListener(this);
        saveNameButton.addActionListener(this);
        addFriendButton.addActionListener(this);
        saveFriendButton.addActionListener(this);
        exitButton.addActionListener(this);
        cancelAddPlayerButton.addActionListener(this);
        cancelAddFriendButton.addActionListener(this);
    }

    /**
     * Checks what button or combo box generated the
     * actionEvent and calls the appropriate method.
     * 
     * @param e the ActionEvent from pressing a button or changing selection in a combo box
     */
    public void actionPerformed(ActionEvent e){
        if(e.getSource().equals(yourNameScoreCombo)){
            refreshFriendNameScoreCombo();
            refreshAddFriend();
        }else if(e.getSource().equals(friendNameScoreCombo)){
            refreshFriendScore();
        }else if(e.getSource().equals(saveScoreButton)){
            saveScore();
        } else if(e.getSource().equals(addPlayerButton)){
            addPlayer();
        } else if(e.getSource().equals(saveNameButton)){
            saveName();
        } else if(e.getSource().equals(addFriendButton)){
            addFriend();
        } else if(e.getSource().equals(saveFriendButton)){
            saveFriend();
        }else if(e.getSource().equals(exitButton)){
            exitScoreboard();
        }else if(e.getSource().equals(cancelAddPlayerButton)){
            cancelAddPlayer();
        }else if(e.getSource().equals(cancelAddFriendButton)){
            cancelAddFriend();
        }
    }

    /**
     * Resets the selection of the current player name 
     * selection for saving the score.
     * 
     */
    private void refreshYourNameScoreCombo(){
        yourNameScoreCombo.removeAllItems();
        Vector<String> allPlayers = DatabaseDriver.getAllPlayers();

        for(String name : allPlayers){
            yourNameScoreCombo.addItem(name);
        }

        try{
            yourNameScoreCombo.setSelectedIndex(0);
            yourNameScoreCombo.setSelectedItem(lastAddedPlayerName);
        }catch(IllegalArgumentException exc){
            yourNameScoreCombo.setSelectedItem(null);
        }

        refreshFriendNameScoreCombo();
    }

    /**
     * Resets the selection of the friend name selection
     * for displaying another player's score.  
     * 
     */
    private void refreshFriendNameScoreCombo(){
        friendNameScoreCombo.removeAllItems();
        String yourName = (String)yourNameScoreCombo.getSelectedItem();
        if(yourName != null){
            Vector<String> yourFriends = DatabaseDriver.getFriends(yourName);

            if(yourFriends.size() == 0){
                friendScoreLabel.setVisible(false);
                friendNameScoreCombo.setVisible(false);
                selectFriendHighscoreLabel.setVisible(false);
            }else {
                friendScoreLabel.setVisible(true);
                friendNameScoreCombo.setVisible(true);  
                selectFriendHighscoreLabel.setVisible(true);
                for(String name : yourFriends){
                    friendNameScoreCombo.addItem(name);
                }

                try{
                    friendNameScoreCombo.setSelectedIndex(0);
                }catch(IllegalArgumentException exc){
                    yourNameAddCombo.setSelectedItem(null);
                }
            }
        }

        refreshFriendScore();
    }

    /**
     * Resets the display of the selected friend's score.  
     * 
     */
    private void refreshFriendScore(){
        String friendName = (String)friendNameScoreCombo.getSelectedItem();

        friendScoreLabel.setText("Your Friend's Highscore: " + 
            (friendName == null ? "" : DatabaseDriver.getScore(friendName)));
    }

    /**
     * Updates the selected player's score in the database.
     * Displays an error message if saving the score fails.
     * 
     */
    private void saveScore(){
        String yourName = (String)yourNameScoreCombo.getSelectedItem();
        if(yourName != null){
            if(!DatabaseDriver.setScore(yourName,this.score)){
                JOptionPane.showMessageDialog(null,
                    "Error: Highscore not saved.",
                    "Highscore Not Saved",
                    JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null,
                    "Highscore Saved!",
                    "Highscore Saved",
                    JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    /**
     * Opensthe Add Player dialog.
     * 
     */
    private void addPlayer(){
        addPlayerDialog.setVisible(true);
    }

    /**
     * Saves a new player with the name specified in the text field,
     * if a name is specified.  Dislays appropriate error dialogs 
     * if saving the player fails.  
     * 
     */
    private void saveName(){
        String yourName = newPlayerNameField.getText();

        if(yourName.length() != 0 ){

            int exitCode = DatabaseDriver.addPlayer(newPlayerNameField.getText());
            if(exitCode == 0){
                lastAddedPlayerName = newPlayerNameField.getText();
                newPlayerNameField.setText("");
                addPlayerDialog.setVisible(false);
                refreshYourNameScoreCombo();
                refreshAddFriend();
            } else if(exitCode == 1) {
                JOptionPane.showMessageDialog(null,
                    "A player with that name already exists, please pick another player name.",
                    "Player Already Exists",
                    JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null,
                    "Error: Player name not saved.\nExit Code: " + exitCode,
                    "Player Name Not Saved",
                    JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null,
                "A man has a name!",
                "What is your name? What is your quest...",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Resets the selections for adding a friend at the
     * Add Friend dialog.
     * 
     */
    private void refreshAddFriend(){
        yourNameAddCombo.removeAllItems();
        friendNameAddCombo.removeAllItems();
        Vector<String> allPlayers = DatabaseDriver.getAllPlayers();
        //reset
        for(String name : allPlayers){
            yourNameAddCombo.addItem(name);
        }

        for(String name : allPlayers){
            friendNameAddCombo.addItem(name);
        }

        //reset selected names
        try{
            yourNameAddCombo.setSelectedIndex(0);
        }catch(IllegalArgumentException exc){
            yourNameAddCombo.setSelectedItem(null);
        }
        yourNameAddCombo.setSelectedItem(yourNameScoreCombo.getSelectedItem());

        Vector<String> yourFriends = DatabaseDriver.getFriends((String)yourNameScoreCombo.getSelectedItem());

        for(String name : yourFriends){
            friendNameAddCombo.removeItem(name);
        }

        try{
            friendNameAddCombo.setSelectedIndex(0);
        }catch(IllegalArgumentException exc){
            friendNameAddCombo.setSelectedItem(null);
        }
    }

    /**
     * Opens the dialog for adding a friend and resets the associated fields.
     * 
     */
    private void addFriend(){
        Vector<String> allPlayers = DatabaseDriver.getAllPlayers();
        Vector<String> yourFriends = DatabaseDriver.getFriends((String)yourNameScoreCombo.getSelectedItem());

        for(String name : yourFriends){
            allPlayers.remove(name);
        }

        if(allPlayers.size() == 0){
            JOptionPane.showMessageDialog(null,
                "You are already friends with everyone.",
                "All Friendships Already Exist",
                JOptionPane.ERROR_MESSAGE);
        } else {
            addFriendDialog.setVisible(true);
        }
    }

    /**
     * Hides the scoreboard and resets the player name selection.
     * 
     */
    private void exitScoreboard(){
        try{
            yourNameScoreCombo.setSelectedIndex(0);
        }catch(IllegalArgumentException exc){
            yourNameScoreCombo.setSelectedItem(null);
        }

        saveScoreDialog.setVisible(false);
    }

    /**
     * Saves a friendship in the database between the 
     * current player and the selected player.
     * Also resets the fields in the Add Friend as
     * well as the combo fields in the Save Score
     * dialog.
     * 
     */
    private void saveFriend(){
        String yourName = (String)yourNameAddCombo.getSelectedItem();

        String friendName = (String)friendNameAddCombo.getSelectedItem();

        if(yourName != null && friendName != null){
            int exitCode = DatabaseDriver.addFriendship(yourName,friendName);
            if(exitCode == 0){

                try{
                    friendNameAddCombo.setSelectedIndex(0);
                }catch(IllegalArgumentException exc){
                    friendNameAddCombo.setSelectedItem(null);
                }

                addFriendDialog.setVisible(false);
                refreshFriendNameScoreCombo();
                yourNameScoreCombo.setSelectedItem(yourName);
                friendNameScoreCombo.setSelectedItem(friendName);

            } else if(exitCode == 1) {
                JOptionPane.showMessageDialog(null,
                    "You are already friends with " + friendName + ".",
                    "Friendship Already Exists",
                    JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null,
                    "Error: Friend not added.\nExit Code: " + exitCode,
                    "Friend Not Added",
                    JOptionPane.ERROR_MESSAGE);
            }
        }

    }

    /**
     * Hides the Add Friend dialog box and resets the text field.
     * 
     */
    private void cancelAddPlayer(){
        newPlayerNameField.setText("");
        addPlayerDialog.setVisible(false);
    }

    /**
     * Hides the Add Friend dialog box and resets its the fields.
     * 
     */
    private void cancelAddFriend(){
        try{
            friendNameAddCombo.setSelectedIndex(0);
        }catch(IllegalArgumentException exc){
            friendNameAddCombo.setSelectedItem(null);
        }

        try{
            yourNameAddCombo.setSelectedIndex(0);
        }catch(IllegalArgumentException exc){
            yourNameAddCombo.setSelectedItem(null);
        }

        addFriendDialog.setVisible(false);
    }

    /**
     * Shows the scoreboard.
     * 
     */
    public void show(){
        saveScoreDialog.setVisible(true);
    }

    /**
     * Sets the score to the amount specified.
     * 
     * @param scoreIn the amount to set the score to
     */
    public void setScore(int scoreIn){
        synchronized(scoreLock){
            score = scoreIn;
            yourScoreLabel.setText("Your Highscore: " + score);
        }
    }

    /**
     * Updates the current score by adding the amount specified.
     * 
     * @param scoreIn the amount to add to the current score
     */
    public void updateScore(int scoreIn){
        synchronized(scoreLock){
            score += scoreIn;
            yourScoreLabel.setText("Your Highscore: " + score);
        }
    }

    /**
     * Returns the current score.
     * 
     * @return the score
     */
    public int getScore(){
        return this.score;
    }
}
