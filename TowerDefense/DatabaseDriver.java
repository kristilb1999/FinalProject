import java.io.*;
import java.sql.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Write a description of class DatabaseDriver here.
 *
 * @author Kristi Boardman, Cameron Costello, Will Skelly, Jake Burch
 * @version Spring 2020
 */
public class DatabaseDriver
{
    private static String databaseName = "./Player-Friends";
    
    private static File buildFile = new File("dropCreateSampleDatabase.txt");
    
    public static void setBuildFile(File file){
        buildFile = file;
    }

    public static void checkDatabase(){
        String queryPlayer = "SELECT * FROM Player";
        String queryFriendship = "SELECT * FROM Friendship";
        try(Connection conn = DriverManager.getConnection("jdbc:h2:" + databaseName,"sa","");){
            PreparedStatement stmt = conn.prepareStatement(queryPlayer);
            stmt.executeQuery();
            stmt = conn.prepareStatement(queryFriendship);
            stmt.executeQuery();
        }catch(SQLException e){
            DatabaseDriver.fetchDatabase(buildFile);
        }
    }
    
    //resets the database to the build file
    public void resetDatabase(){
        String queryFriendship = "DROP TABLE IF EXISTS Friendship";
        String queryPlayer = "DROP TABLE IF EXISTS Player";
        try(Connection conn = DriverManager.getConnection("jdbc:h2:" + databaseName,"sa","");){
            PreparedStatement stmt = conn.prepareStatement(queryFriendship);
            stmt.executeQuery();
            stmt = conn.prepareStatement(queryPlayer);
            stmt.executeQuery();
        }catch(SQLException e){
            System.err.println("Error dropping database: " + e);
            e.printStackTrace();
        }
        DatabaseDriver.fetchDatabase(buildFile);
    }

    public static void fetchDatabase(File file){
        try(Connection conn = DriverManager.getConnection("jdbc:h2:" + databaseName,"sa","");){

            Scanner sc = new Scanner(file);

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
            e.printStackTrace();
        }
    }

    public static int addPlayer(String playerName){
        if(playerName == null || playerName == "") return 3;
        return insert("INSERT INTO Player values ('" + playerName + "',0)");
    }

    public static int addFriendship(String name1,String name2){
        if(name1 == null || name1 == "" || name2 == null|| name2 == "") return 3;
        return insert("INSERT INTO Friendship values ('" + name1 + "','" + name2 + "')");
    }

    private static int insert(String queryIn){
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

    public static boolean setScore(String playerName,int score){
        boolean successful = true;
        if(playerName == null || playerName == "") successful = false;

        if(successful){
            String query = "UPDATE Player SET Player.Score = " + score + " WHERE Player.Name = '" + playerName + "'";
            try(Connection conn = DriverManager.getConnection("jdbc:h2:" + databaseName,"sa","");){
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.executeUpdate();
            }catch(SQLException e){
                System.err.println("SQL Error: " + e); 
                e.printStackTrace();
                successful = false;
            }
        }

        return successful;
    }

    public static int getScore(String playerName){
        int score = 0;

        if(playerName != null && playerName != ""){
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
        }

        return score;
    }

    public static Vector<String> getFriends(String playerName){
        Vector friends = new Vector<String>();

        if(playerName != null && playerName != ""){
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
        }

        return friends;
    }

    public static Vector<String> getAllPlayers(){
        Vector players= new Vector<String>();
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
        DatabaseDriver.fetchDatabase(new File("createSampleDatabase.txt"));

        int count = 0;
        int maxCount = 0;
        maxCount++;
        if(DatabaseDriver.addPlayer("Will") == 0){
            count++;
        };

        maxCount++;
        if(DatabaseDriver.addPlayer("Will") != 0){
            count++;
        };

        maxCount++;
        if(DatabaseDriver.addPlayer("Not Will") == 0){
            count++;
        };

        maxCount++;
        if(DatabaseDriver.addFriendship("Will","Not Will") == 0){
            count++;
        };

        maxCount++;
        if(DatabaseDriver.addFriendship("Will","Not Will") != 0){
            count++;
        };

        maxCount++;
        if(DatabaseDriver.getScore("Will") == 0){
            count++;
        };

        maxCount++;
        if(DatabaseDriver.getScore("Not Will") == 0){
            count++;
        };

        maxCount++;
        if(DatabaseDriver.setScore("Will",5)){
            count++;
        };

        maxCount++;
        if(DatabaseDriver.setScore("Not Will",10)){
            count++;
        };

        maxCount++;
        if(DatabaseDriver.getScore("Will") == 5){
            count++;
        };

        maxCount++;
        if(DatabaseDriver.getScore("Not Will") == 10){
            count++;
        };

        maxCount++;
        if(DatabaseDriver.getFriends("Will").contains("Not Will")){
            count++;
        };

        maxCount++;
        if(DatabaseDriver.addPlayer("Not Not Will") == 0){
            count++;
        };

        maxCount++;
        if(DatabaseDriver.addFriendship("Will","Not Not Will") == 0){
            count++;
        };

        maxCount++;
        if(DatabaseDriver.getFriends("Will").contains("Not Not Will")){
            count++;
        };

        maxCount++;
        try{
            DatabaseDriver.getFriends("Not Will").get(0);
        }catch(IndexOutOfBoundsException e){
            count++;
        }

        maxCount++;
        if(DatabaseDriver.getAllPlayers().contains("Not Will")){
            count++;
        };

        maxCount++;
        if(DatabaseDriver.getAllPlayers().contains("Will")){
            count++;
        };

        maxCount++;
        if(DatabaseDriver.getAllPlayers().contains("Not Not Will")){
            count++;
        };

        maxCount++;
        if(!DatabaseDriver.getAllPlayers().contains("Very Much Not Will")){
            count++;
        };

        maxCount++;
        if(!DatabaseDriver.getAllPlayers().contains("Very Much Will")){
            count++;
        };

        System.out.println("Test Complete: " + count + "/" + maxCount + " points earned.");

    }
}