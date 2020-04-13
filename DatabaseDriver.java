import java.io.*;
import java.sql.*;
import java.util.Scanner;

/**
 * Write a description of class DatabaseDriver here.
 *
 * @author Kristi Boardman, Cameron Costello, Will Skelly, Jake Burch
 * @version Spring 2020
 */
public class DatabaseDriver
{

    public void fetchDatabase(){
        try(Connection conn = DriverManager.getConnection("jdbc:h2:./Player-Friends","sa","");){

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
        int exitCode = 0;

        try(Connection conn = DriverManager.getConnection("jdbc:h2:./Player-Friends","sa","");){
            String query = "INSERT INTO Player values ('" + playerName + "',0)";
            PreparedStatement stmt = conn.prepareStatement(query);
            //System.err.println(stmt.toString());
            exitCode = 0;
        }catch(SQLIntegrityConstraintViolationException e){
            exitCode = 1;
        }catch(SQLException e){
            System.err.println("SQL Error: " + e); 
            e.printStackTrace();
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
        dbd.addPlayer("Will");

    }
}
