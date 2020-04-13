import java.io.*;
import java.sql.*;
import java.util.Scanner;

/**
 * Write a description of class DatabaseDriver here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class DatabaseDriver
{

    public void fetchDatabase(){
        try{
            Connection conn = DriverManager.getConnection("jdbc:h2:./test","sa","");

            Scanner sc = new Scanner(new File("createSampleDatabase.txt"));

            String query = "";
            PreparedStatement stmt;
            while(sc.hasNextLine()){

                query = sc.next();
                stmt = conn.prepareStatement(query);
                System.out.println("SQL INPUT: " + stmt.toString() + "\n");
                stmt.execute();
            }

            conn.close();
        }catch(FileNotFoundException e){
            System.err.println("Database File Missing: " + e);
        }catch(SQLException e){
            System.err.println("SQL Error: " + e);
        }
    }

    public int addPlayer(String playerName){
        int exitCode = 0;

        try{
            Connection conn = DriverManager.getConnection("jdbc:h2:./test","sa","");

            String query = "SELECT count(*) FROM Player P WHERE P.Name = playerName";
            PreparedStatement stmt = conn.prepareStatement(query);
            //System.out.println("SQL INPUT: " + stmt.toString() + "\n");//REMOVEME
            int count = stmt.executeQuery().getInt(1);

            if(count == 0){
                query = "INSERT INTO Player P values (playerName,0)";
                stmt = conn.prepareStatement(query);
                stmt.executeUpdate();
                exitCode = 0;
            } else {

                exitCode = 1;
            }

            conn.close();
        }catch(SQLException e){
            System.err.println("SQL Error: " + e);   
            exitCode = 2;
        }

        return exitCode;
    }

    public static void main(String args[]){

        // try{
            // System.setErr(new PrintStream("error_log.txt"));
        // }catch(FileNotFoundException e){
            // System.err.println("Error Log Missing: " + e);
        // }

        
        DatabaseDriver dbd = new DatabaseDriver();
        
        dbd.fetchDatabase();
        dbd.addPlayer("Will");

    }
}
