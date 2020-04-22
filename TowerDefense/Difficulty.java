//Referenced:
//https://docs.oracle.com/javase/tutorial/java/javaOO/enum.html
//https://www.geeksforgeeks.org/enum-in-java/
/**
 * This enum is used to hold the different difficulty levels.
 *
 * @author Kristi Boardman, Cameron Costello, Will Skelly, Jake Burch
 * @version Spring 2020
 */
public enum Difficulty
{
    EASY(0),MEDIUM(1),HARD(2);
    
    private final int level;
    
    Difficulty(int level){
     this.level = level;   
    }
    
    public int getValue(){
        return this.level;
    }
}
