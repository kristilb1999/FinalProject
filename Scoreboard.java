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
    //THE DELAY TIME FOR THE SLEEP METHOD
    private static final int DELAY_TIME = 33;

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
    private JTextField newPlayerName;
    private JComboBox<String> yourNameSelect;
    private JComboBox<String> friendNameSelect;
    private JComboBox<String> yourNameWithFriendSelect;
    private JComboBox<String> selectAFriendSelect;
    private JButton saveScoreButton;
    private JButton addPlayerButton;
    private JButton saveNameButton;
    private JButton addFriendButton;
    private JButton saveFriendButton;
    private JButton exitButton;
    private JButton cancelAddPlayerButton;
    private JButton cancelAddFriendButton;

    public Scoreboard(){
        this.score = 0;
        this.lastAddedPlayerName = "";
    }

    public void run(){
        DatabaseDriver.fetchDatabase(new File("createSampleDatabase.txt"));

        //dialog box for adding a highscore
        saveScoreDialog = new JDialog();
        saveScoreDialog.setTitle("Add New Highscore");
        saveScoreDialog.setSize(new Dimension(500,500));
        savePanel = new JPanel();
        savePanel.add(new JLabel("Select Your Player Name: "));
        yourNameSelect = new JComboBox<String>(DatabaseDriver.getAllPlayers());
        savePanel.add(yourNameSelect);
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

        String yourName = (String)yourNameSelect.getSelectedItem();
        if(yourName != null){
            selectAFriendSelect = new JComboBox<String>(DatabaseDriver.getFriends(yourName));

            try{
                selectAFriendSelect.setSelectedIndex(0);
            }catch(IllegalArgumentException exc){
                selectAFriendSelect.setSelectedItem(null);
            }
        } else {
            selectAFriendSelect = new JComboBox<String>();
        }
        savePanel.add(selectAFriendSelect);
        
        friendScoreLabel = new JLabel();
        String friendName = (String)selectAFriendSelect.getSelectedItem();
        friendScoreLabel.setText("Your Friend's Highscore: " + 
        (friendName == null ? "" : DatabaseDriver.getScore(friendName)));
        
        savePanel.add(friendScoreLabel);
        saveScoreDialog.add(savePanel);

        //dialog box for adding a new player
        addPlayerDialog = new JDialog();
        addPlayerDialog.setTitle("Add New Player");
        addPlayerDialog.setSize(new Dimension(500,500));
        //fields for adding a player
        addPlayerPanel = new JPanel();
        addPlayerPanel.add(new JLabel("Enter Your Name: "));
        newPlayerName = new JTextField("");
        newPlayerName.setPreferredSize(new Dimension(100,20));
        addPlayerPanel.add(newPlayerName);
        saveNameButton = new JButton("Save Player Name");
        addPlayerPanel.add(saveNameButton);
        cancelAddPlayerButton = new JButton("Cancel");
        addPlayerPanel.add(cancelAddPlayerButton);
        addPlayerDialog.add(addPlayerPanel);

        //dialog box for adding a friend
        addFriendDialog = new JDialog();
        addFriendDialog.setTitle("Add Friend");
        addFriendDialog.setSize(new Dimension(500,500));
        //fields for adding a friend
        addFriendPanel = new JPanel();
        addFriendPanel.add(new JLabel("Select Your Player Name: "));
        yourNameWithFriendSelect = new JComboBox<String>(DatabaseDriver.getAllPlayers());
        addFriendPanel.add(yourNameWithFriendSelect);
        addFriendPanel.add(new JLabel("Select Your Friend's Player Name: "));
        friendNameSelect = new JComboBox<String>(DatabaseDriver.getAllPlayers());
        addFriendPanel.add(friendNameSelect);
        saveFriendButton = new JButton("Add Friend");
        addFriendPanel.add(saveFriendButton);
        cancelAddFriendButton = new JButton("Cancel");
        addFriendPanel.add(cancelAddFriendButton);
        addFriendDialog.add(addFriendPanel);

        saveScoreDialog.setVisible(false);
        addPlayerDialog.setVisible(false);
        addFriendDialog.setVisible(false);

        yourNameSelect.addActionListener(this);
        selectAFriendSelect.addActionListener(this);

        saveScoreButton.addActionListener(this);
        addPlayerButton.addActionListener(this);
        saveNameButton.addActionListener(this);
        addFriendButton.addActionListener(this);
        saveFriendButton.addActionListener(this);
        exitButton.addActionListener(this);
        cancelAddPlayerButton.addActionListener(this);
        cancelAddFriendButton.addActionListener(this);

        Scoreboard thisScoreBoard = this;
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

                    thisScoreBoard.paint();
                }
            }
        }.start(); 
    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource().equals(yourNameSelect)){
            refreshSelectAFriendSelect();
        }else if(e.getSource().equals(selectAFriendSelect)){
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

    private void refreshYourNameSelect(){
        yourNameSelect.removeAllItems();
        Vector<String> allPlayers = DatabaseDriver.getAllPlayers();

        for(String name : allPlayers){
            yourNameSelect.addItem(name);
        }

        try{
            yourNameSelect.setSelectedIndex(0);
            yourNameSelect.setSelectedItem(lastAddedPlayerName);
        }catch(IllegalArgumentException exc){
            yourNameSelect.setSelectedItem(null);
        }

        refreshSelectAFriendSelect();
    }

    private void refreshSelectAFriendSelect(){
        selectAFriendSelect.removeAllItems();
        String yourName = (String)yourNameSelect.getSelectedItem();
        if(yourName != null){
            Vector<String> yourFriends = DatabaseDriver.getFriends(yourName);

            for(String name : yourFriends){
                selectAFriendSelect.addItem(name);
            }

            try{
                selectAFriendSelect.setSelectedIndex(0);
            }catch(IllegalArgumentException exc){
                yourNameWithFriendSelect.setSelectedItem(null);
            }

        }

        refreshFriendScore();
    }

    private void refreshFriendScore(){
        String friendName = (String)selectAFriendSelect.getSelectedItem();
        
        friendScoreLabel.setText("Your Friend's Highscore: " + 
        (friendName == null ? "" : DatabaseDriver.getScore(friendName)));
    }

    private void saveScore(){
        String yourName = (String)yourNameSelect.getSelectedItem();
        if(yourName != null){
            DatabaseDriver.setScore(yourName,this.score);
        }
    }

    private void addPlayer(){
        addPlayerDialog.setVisible(true);
    }

    private void saveName(){
        String yourName = newPlayerName.getText();
        
        if(yourName.length() != 0 ){
            DatabaseDriver.addPlayer(newPlayerName.getText());
        lastAddedPlayerName = newPlayerName.getText();
        newPlayerName.setText("");
        addPlayerDialog.setVisible(false);
        refreshYourNameSelect();
        refreshAddFriend();
        } else {
            JOptionPane.showMessageDialog(null,"A man has a name!","What is your name? What is your quest...",JOptionPane.ERROR_MESSAGE);
        }
    }

    private void refreshAddFriend(){
        yourNameWithFriendSelect.removeAllItems();
        friendNameSelect.removeAllItems();
        Vector<String> allPlayers = DatabaseDriver.getAllPlayers();

        for(String name : allPlayers){
            yourNameWithFriendSelect.addItem(name);
        }

        for(String name : allPlayers){
            friendNameSelect.addItem(name);
        }
    }
    
    private void addFriend(){
        addFriendDialog.setVisible(true);

        try{
            yourNameWithFriendSelect.setSelectedItem(yourNameSelect.getSelectedItem());
        }catch(IllegalArgumentException exc){
            yourNameWithFriendSelect.setSelectedItem(null);
        }
        
        try{
                friendNameSelect.setSelectedIndex(0);
            }catch(IllegalArgumentException exc){
                friendNameSelect.setSelectedItem(null);
            }
    }

    private void exitScoreboard(){
        try{
            yourNameSelect.setSelectedIndex(0);
        }catch(IllegalArgumentException exc){
            yourNameSelect.setSelectedItem(null);
        }

        saveScoreDialog.setVisible(false);
    }

    private void saveFriend(){
        String yourName = (String)yourNameWithFriendSelect.getSelectedItem();

        String friendName = (String)friendNameSelect.getSelectedItem();

        if(yourName != null && friendName != null){
            DatabaseDriver.addFriendship(yourName,friendName);
        }

        try{
            friendNameSelect.setSelectedIndex(0);
        }catch(IllegalArgumentException exc){
            friendNameSelect.setSelectedItem(null);
        }

        addFriendDialog.setVisible(false);

        refreshSelectAFriendSelect();
    }

    private void cancelAddPlayer(){
        newPlayerName.setText("");
        addPlayerDialog.setVisible(false);
    }

    private void cancelAddFriend(){
        try{
            friendNameSelect.setSelectedIndex(0);
        }catch(IllegalArgumentException exc){
            friendNameSelect.setSelectedItem(null);
        }

        try{
            yourNameWithFriendSelect.setSelectedIndex(0);
        }catch(IllegalArgumentException exc){
            yourNameWithFriendSelect.setSelectedItem(null);
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

    public void paint(){
        savePanel.repaint();
        addFriendPanel.repaint();
        addPlayerPanel.repaint();
    }
}
