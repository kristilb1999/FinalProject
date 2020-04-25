package TowerDefenseGame;

  
import java.io.InputStream;

/**
 * This class contains code for testing the functionality of the DatabaseDriver class
 *
 * @author Kristi Boardman, Cameron Costello, Will Skelly, Jake Burch
 * @version Spring 2020
 */
public class DatabaseDriverTester
{
    public static void main(String args[]){
        //based on
        //https://www.novixys.com/blog/read-file-resources-folder-java/
        InputStream databaseBuildStream = DatabaseDriverTester.class.getResourceAsStream("/dropCreateSampleDatabase.txt");
        
        //set the build stream in the database driver and reset the database
        DatabaseDriver.setBuildStream(databaseBuildStream);
        DatabaseDriver.resetDatabase();

        //points
        int count = 0;
        int maxCount = 0;
        //tests
        maxCount++;
        if(DatabaseDriver.fetchDatabase(databaseBuildStream) == 0){
            count++;
        }
        
        maxCount++;
        if(DatabaseDriver.checkDatabase() == 0){
            count++;
        }
        
        maxCount++;
        if(DatabaseDriver.checkDatabase(databaseBuildStream) == 0){
            count++;
        }
        
        maxCount++;
        if(DatabaseDriver.addPlayer("Will") == 0){
            count++;
        }

        maxCount++;
        if(DatabaseDriver.addPlayer("Will") != 0){
            count++;
        }

        maxCount++;
        if(DatabaseDriver.addPlayer("Not Will") == 0){
            count++;
        }

        maxCount++;
        if(DatabaseDriver.addFriendship("Will","Not Will") == 0){
            count++;
        }

        maxCount++;
        if(DatabaseDriver.addFriendship("Will","Not Will") != 0){
            count++;
        }

        maxCount++;
        if(DatabaseDriver.getScore("Will") == 0){
            count++;
        }

        maxCount++;
        if(DatabaseDriver.getScore("Not Will") == 0){
            count++;
        }

        maxCount++;
        if(DatabaseDriver.setScore("Will",5)){
            count++;
        }

        maxCount++;
        if(DatabaseDriver.setScore("Not Will",10)){
            count++;
        }

        maxCount++;
        if(DatabaseDriver.getScore("Will") == 5){
            count++;
        }

        maxCount++;
        if(DatabaseDriver.getScore("Not Will") == 10){
            count++;
        }

        maxCount++;
        if(DatabaseDriver.getFriends("Will").contains("Not Will")){
            count++;
        }

        maxCount++;
        if(DatabaseDriver.addPlayer("Not Not Will") == 0){
            count++;
        }

        maxCount++;
        if(DatabaseDriver.addFriendship("Will","Not Not Will") == 0){
            count++;
        }

        maxCount++;
        if(DatabaseDriver.getFriends("Will").contains("Not Not Will")){
            count++;
        }

        maxCount++;
        try{
            DatabaseDriver.getFriends("Not Will").get(0);
        }catch(IndexOutOfBoundsException e){
            count++;
        }

        maxCount++;
        if(DatabaseDriver.getAllPlayers().contains("Not Will")){
            count++;
        }

        maxCount++;
        if(DatabaseDriver.getAllPlayers().contains("Will")){
            count++;
        }

        maxCount++;
        if(DatabaseDriver.getAllPlayers().contains("Not Not Will")){
            count++;
        }

        maxCount++;
        if(!DatabaseDriver.getAllPlayers().contains("Very Much Not Will")){
            count++;
        }

        maxCount++;
        if(!DatabaseDriver.getAllPlayers().contains("Very Much Will")){
            count++;
        }
        
        maxCount++;
        if(DatabaseDriver.resetDatabase() == 0){
            count++;
        }
        
        //printing results
        System.out.println("Test Complete: " + count + "/" + maxCount + " points earned.");
    }
}
