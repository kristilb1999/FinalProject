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
public class Scoreboard extends Thread implements ActionListener
{
    private static final int WINDOW_WIDTH = 500;
    private static final int WINDOW_HEIGHT = 175;
    private static final int TEXTFIELD_WIDTH = 100;
    private static final int TEXTFIELD_HEIGHT = 20;

    private JDialog saveScoreDialog;
    private JDialog addFriendDialog;
    private JDialog addPlayerDialog;
    private JPanel savePanel;
    private JPanel addPlayerPanel;
    private JPanel addFriendPanel;
    private int score;
    private String lastAddedPlayerName;
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
    private Object scoreLock = new Object();

    public Scoreboard(JComponent container){
        this.score = 0;
        this.lastAddedPlayerName = "";
        this.container = container;
    }

    public void run(){
        //DatabaseDriver.fetchDatabase(new File("createSampleDatabase.txt"));

        //dialog box for adding a highscore
        saveScoreDialog = new JDialog();
        saveScoreDialog.setTitle("Add New Highscore");
        saveScoreDialog.setPreferredSize(new Dimension(WINDOW_WIDTH,WINDOW_HEIGHT));
        savePanel = new JPanel();
        savePanel.add(new JLabel("Select Your Player Name: "));
        yourNameScoreCombo = new JComboBox<String>(DatabaseDriver.getAllPlayers());
        savePanel.add(yourNameScoreCombo);
        yourScoreLabel = new JLabel("Your Highscore: 0");
        savePanel.add(yourScoreLabel);
        saveScoreButton = new JButton("Save Highscore");
        savePanel.add(saveScoreButton);
        addPlayerButton = new JButton("Add New Player");
        savePanel.add(addPlayerButton);
        addFriendButton = new JButton("Add Friend");
        savePanel.add(addFriendButton);
        exitButton = new JButton("Exit");
        savePanel.add(exitButton);

        selectFriendHighscoreLabel = 
        new JLabel("Select a friend's name to see their highscore: ");
        savePanel.add(selectFriendHighscoreLabel);
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
        savePanel.add(friendNameScoreCombo);

        String friendName = (String)friendNameScoreCombo.getSelectedItem();
        friendScoreLabel.setText("Your Friend's Highscore: " + 
            (friendName == null ? "" : DatabaseDriver.getScore(friendName)));

        savePanel.add(friendScoreLabel);
        saveScoreDialog.add(savePanel);

        //dialog box for adding a new player
        addPlayerDialog = new JDialog();
        addPlayerDialog.setTitle("Add New Player");
        addPlayerDialog.setSize(new Dimension(WINDOW_WIDTH,WINDOW_HEIGHT));
        //fields for adding a player
        addPlayerPanel = new JPanel();
        addPlayerPanel.add(new JLabel("Enter Your Name: "));
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
        addFriendDialog.setSize(new Dimension(WINDOW_WIDTH,WINDOW_HEIGHT));
        //fields for adding a friend
        addFriendPanel = new JPanel();
        addFriendPanel.add(new JLabel("Select Your Player Name: "));
        yourNameAddCombo = new JComboBox<String>(DatabaseDriver.getAllPlayers());
        addFriendPanel.add(yourNameAddCombo);
        addFriendPanel.add(new JLabel("Select Your Friend's Player Name: "));
        friendNameAddCombo = new JComboBox<String>(DatabaseDriver.getAllPlayers());
        addFriendPanel.add(friendNameAddCombo);
        saveFriendButton = new JButton("Add Friend");
        addFriendPanel.add(saveFriendButton);
        cancelAddFriendButton = new JButton("Cancel");
        addFriendPanel.add(cancelAddFriendButton);
        addFriendDialog.add(addFriendPanel);

        //based on:
        //https://stackoverflow.com/questions/10030947/center-jdialog-over-parent
        saveScoreDialog.pack();
        addPlayerDialog.pack();
        saveScoreDialog.pack();
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

    private void refreshFriendScore(){
        String friendName = (String)friendNameScoreCombo.getSelectedItem();

        friendScoreLabel.setText("Your Friend's Highscore: " + 
            (friendName == null ? "" : DatabaseDriver.getScore(friendName)));
    }

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

    private void addPlayer(){
        addPlayerDialog.setVisible(true);
    }

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

    private void exitScoreboard(){
        try{
            yourNameScoreCombo.setSelectedIndex(0);
        }catch(IllegalArgumentException exc){
            yourNameScoreCombo.setSelectedItem(null);
        }

        saveScoreDialog.setVisible(false);
    }

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

    private void cancelAddPlayer(){
        newPlayerNameField.setText("");
        addPlayerDialog.setVisible(false);
    }

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

    public void show(){
        saveScoreDialog.setVisible(true);
    }

    public void setScore(int score){
        this.score = score;
        yourScoreLabel.setText("Your Highscore: " + score);
    }

    public void updateScore(int score){
        synchronized(scoreLock){
            this.score += score;
        }
    }

    public int getScore(){
        return this.score;
    }
}
