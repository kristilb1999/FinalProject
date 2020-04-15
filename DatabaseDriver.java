import java.io.*;
import java.sql.*;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * Write a description of class DatabaseDriver here.
 *
 * @author Kristi Boardman, Cameron Costello, Will Skelly, Jake Burch
 * @version Spring 2020
 */
public class DatabaseDriver
{
    private static String databaseName = "./Player-Friends";

    public void fetchDatabase(){
        try(Connection conn = DriverManager.getConnection("jdbc:h2:" + databaseName,"sa","");){

            Scanner sc = new Scanner(new File("createSampleDatabase.txt"));

            String query = "";
            PreparedStatement stmt;
            while(sc.hasNextLine()){

                query = sc.nextLine();
                stmt = conn.prepareStatement(query);
                stmt.execute();
            }

        }catch(FileNotFoundException e){
            System.err.println("Database File Missing: " + e);
        }catch(SQLException e){
            System.err.println("SQL Error: " + e);
        }
    }

    public int addPlayer(String playerName){
        return insert("INSERT INTO Player values ('" + playerName + "',0)");
    }

    public int addFriendship(String name1,String name2){
        return insert("INSERT INTO Friendship values ('" + name1 + "','" + name2 + "')");
    }

    private int insert(String queryIn){
        int exitCode = 0;

        try(Connection conn = DriverManager.getConnection("jdbc:h2:" + databaseName,"sa","");){
            PreparedStatement stmt = conn.prepareStatement(queryIn);
            stmt.execute();
        }catch(SQLIntegrityConstraintViolationException e){
            exitCode = 1;
        }catch(SQLException e){
            System.err.println("SQL Error: " + e); 
            e.printStackTrace();
            exitCode = 2;
        }

        return exitCode;
    }

    public boolean setScore(String playerName,int score){
        boolean successful = true;
        String query = "UPDATE Player SET Player.Score = " + score + " WHERE Player.Name = '" + playerName + "'";
        try(Connection conn = DriverManager.getConnection("jdbc:h2:" + databaseName,"sa","");){
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.executeUpdate();
        }catch(SQLException e){
            System.err.println("SQL Error: " + e); 
            e.printStackTrace();
            successful = false;
        }

        return successful;
    }

    public int getScore(String playerName){
        int score = 0;
        String query = "SELECT Score FROM Player WHERE Player.Name = '" + playerName + "'";
        try(Connection conn = DriverManager.getConnection("jdbc:h2:" + databaseName,"sa","");){
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rset = stmt.executeQuery();

            rset.next();
            score = rset.getInt("Score");
        }catch(SQLException e){
            System.err.println("SQL Error: " + e); 
            e.printStackTrace();
            score = -1;
        }

        return score;
    }

    public ArrayList<String> getFriends(String playerName){
        ArrayList friends = new ArrayList<String>();
        String query = "SELECT Name2 FROM Friendship WHERE Friendship.Name1 = '" + playerName + "'";
        try(Connection conn = DriverManager.getConnection("jdbc:h2:" + databaseName,"sa","");){
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rset = stmt.executeQuery();

            while(rset.next()){

                friends.add(rset.getString("Name2"));
            }
        }catch(SQLException e){
            System.err.println("SQL Error: " + e); 
            e.printStackTrace();
        }

        return friends;
    }
    
    public ArrayList<String> getAllPlayers(){
        ArrayList players= new ArrayList<String>();
        String query = "SELECT Name FROM Player";
        try(Connection conn = DriverManager.getConnection("jdbc:h2:" + databaseName,"sa","");){
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rset = stmt.executeQuery();

            while(rset.next()){

                players.add(rset.getString("Name"));
            }
        }catch(SQLException e){
            System.err.println("SQL Error: " + e); 
            e.printStackTrace();
        }

        return players;
    }

    public static void main(String args[]){

        DatabaseDriver dbd = new DatabaseDriver();
        dbd.fetchDatabase();

        int count = 0;
        int maxCount = 0;
        maxCount++;
        if(dbd.addPlayer("Will") == 0){
            count++;
        };

        maxCount++;
        if(dbd.addPlayer("Will") != 0){
            count++;
        };

        maxCount++;
        if(dbd.addPlayer("Not Will") == 0){
            count++;
        };

        maxCount++;
        if(dbd.addFriendship("Will","Not Will") == 0){
            count++;
        };

        maxCount++;
        if(dbd.addFriendship("Will","Not Will") != 0){
            count++;
        };

        maxCount++;
        if(dbd.getScore("Will") == 0){
            count++;
        };

        maxCount++;
        if(dbd.getScore("Not Will") == 0){
            count++;
        };

        maxCount++;
        if(dbd.setScore("Will",5)){
            count++;
        };

        maxCount++;
        if(dbd.setScore("Not Will",10)){
            count++;
        };

        maxCount++;
        if(dbd.getScore("Will") == 5){
            count++;
        };

        maxCount++;
        if(dbd.getScore("Not Will") == 10){
            count++;
        };

        maxCount++;
        if(dbd.getFriends("Will").contains("Not Will")){
            count++;
        };

        maxCount++;
        if(dbd.addPlayer("Not Not Will") == 0){
            count++;
        };

        maxCount++;
        if(dbd.addFriendship("Will","Not Not Will") == 0){
            count++;
        };

        maxCount++;
        if(dbd.getFriends("Will").contains("Not Not Will")){
            count++;
        };

        maxCount++;
        try{
            dbd.getFriends("Not Will").get(0);
        }catch(IndexOutOfBoundsException e){
            count++;
        }
        
        maxCount++;
        if(dbd.getAllPlayers().contains("Not Will")){
            count++;
        };
        
        maxCount++;
        if(dbd.getAllPlayers().contains("Will")){
            count++;
        };
        
        maxCount++;
        if(dbd.getAllPlayers().contains("Not Not Will")){
            count++;
        };
        
        maxCount++;
        if(!dbd.getAllPlayers().contains("Very Much Not Will")){
            count++;
        };
        
        maxCount++;
        if(!dbd.getAllPlayers().contains("Very Much Will")){
            count++;
        };

        System.out.println("Test Complete: " + count + "/" + maxCount + " points earned.");

    }
}
