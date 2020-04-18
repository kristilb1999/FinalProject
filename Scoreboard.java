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
    JDialog saveScoreDialog;
    JDialog addFriendDialog;
    JDialog addPlayerDialog;
    int score;
    JTextField newPlayerName;
    JComboBox<String> yourNameSelect;
    JComboBox<String> friendNameSelect;
    JButton saveScoreButton;
    JButton addPlayerButton;
    JButton saveNameButton;
    JButton addFriendButton;
    JButton exitButton;
    JButton cancelAddPlayerButton;
    JButton cancelAddFriendButton;
    JComponent container;

    public Scoreboard(JComponent container){
        this.container = container;
    }

    public void run(){
        //dialog box for adding a highscore
        saveScoreDialog = new JDialog();
        saveScoreDialog.setTitle("Add New Highscore");
        saveScoreDialog.setSize(new Dimension(500,500));
        //fields for adding a highscore
        //saveScoreDialog.add(new JLabel("Select Your Player Name: "));
        //saveScoreDialog.add(yourNameSelect);
        saveScoreButton = new JButton("Save Highscore");
        //saveScoreDialog.add(saveScoreButton);
        //addPlayerButton = new JButton("Add New Player");
        
        JPanel savePanel = new JPanel();
        savePanel.add(new JLabel("Select Your Player Name: "));
        yourNameSelect = new JComboBox<String>();
        savePanel.add(yourNameSelect);
        savePanel.add(saveScoreButton);
        saveScoreDialog.add(savePanel);

        //dialog box for adding a new player
        addPlayerDialog = new JDialog();
        addPlayerDialog.setTitle("Add New Player");
        addPlayerDialog.setSize(new Dimension(500,500));
        //fields for adding a player
        addPlayerDialog.add(new JLabel("Enter Your Name: "));
        newPlayerName = new JTextField();
        addPlayerDialog.add(newPlayerName);
        saveNameButton = new JButton("Save Player Name");
        addPlayerDialog.add(saveNameButton);
        cancelAddPlayerButton = new JButton("Cancel");
        addPlayerDialog.add(cancelAddPlayerButton);

        //dialog box for adding a friend
        addPlayerDialog = new JDialog();
        addPlayerDialog.setTitle("Add Friend");
        addPlayerDialog.setSize(new Dimension(500,500));
        //fields for adding a friend
        addFriendDialog.add(new JLabel("Select Your Player Name: "));
        addFriendDialog.add(yourNameSelect);
        addFriendDialog.add(new JLabel("Select Your Friend's Player Name: "));
        addFriendDialog.add(friendNameSelect);
        addFriendButton = new JButton("Add Friend");
        addFriendDialog.add(addFriendButton);
        cancelAddFriendButton = new JButton("Cancel");
        addFriendDialog.add(cancelAddFriendButton);

        container.add(saveScoreDialog);
        container.add(addPlayerDialog);
        container.add(addFriendDialog);
        saveScoreDialog.setVisible(false);
        addPlayerDialog.setVisible(false);
        addFriendDialog.setVisible(false);

        saveScoreButton.addActionListener(this);
        addPlayerButton.addActionListener(this);
        addFriendButton.addActionListener(this);
        exitButton.addActionListener(this);
        cancelAddPlayerButton.addActionListener(this);
        cancelAddFriendButton.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource().equals(saveScoreButton)){
            DatabaseDriver.setScore((String)yourNameSelect.getSelectedItem(),this.score);
        } else if(e.getSource().equals(addPlayerButton)){
            addPlayerDialog.setVisible(true);
        } else if(e.getSource().equals(saveNameButton)){
            DatabaseDriver.addPlayer(newPlayerName.getText());
            newPlayerName.setText("");
            addPlayerDialog.setVisible(false);
        } else if(e.getSource().equals(addFriendButton)){
            DatabaseDriver.addFriendship((String)yourNameSelect.getSelectedItem(),(String)friendNameSelect.getSelectedItem());
            friendNameSelect.setSelectedItem(null);
            addFriendDialog.setVisible(false);
        }else if(e.getSource().equals(exitButton)){
            friendNameSelect.setSelectedItem(null);
            yourNameSelect.setSelectedItem(null);
            saveScoreDialog.setVisible(false);
        }else if(e.getSource().equals(cancelAddPlayerButton)){
            newPlayerName.setText("");
            addPlayerDialog.setVisible(false);
        }else if(e.getSource().equals(cancelAddFriendButton)){
            friendNameSelect.setSelectedItem(null);
            addFriendDialog.setVisible(false);
        }

    }

    public void show(){
        saveScoreDialog.setVisible(true);
    }

    public void setScore(int score){
        this.score = score;
    }
}
