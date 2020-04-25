package TowerDefenseGame;

 
//imports
import java.awt.geom.Point2D;
import java.util.Random;
import javax.swing.JComponent;

/**
 * Procures weapons randomly, for the TowerDefense class.
 *
 * @author Kristi Boardman, Cameron Costello, Will Skelly, Jake Burch
 * @version Spring 2020
 */
public class QuarterMaster
{
    /**
     * Returns a random weapon from our four options.
     * 
     * @param container The container to paint it in.
     * @param position The weapon's current position.
     * @param inertia Determines object velocity.
     * 
     * @return The Weapon that the QuarterMaster has chosen for the user.
     */
    public static Weapon getRandomWeapon(JComponent container, Point2D.Double position, Point2D.Double inertia){
        //CREATE A RANDOM OBJECT
        Random rand = new Random();

        //STORE THE NEXT RANDOM NUMBER TO CHOOSE ONE OF THE WEAPONS
        int num = rand.nextInt(4);

        //CREATE A WEAPON TO RETURN
        Weapon toReturn;

        //BASED ON THE RANDOM NUMBER, RETURN ONE OF THE FOUR WEAPONS - BOULDER IS DEFAULT
        switch(num){
            case 0:
            toReturn = new MolotovCocktail(container,position,inertia);
            break;
            case 1:
            toReturn = new Grenade(container,position,inertia);
            break;
            case 2:
            toReturn = new TNT(container,position,inertia);
            break;
            case 3:
            toReturn = new Boulder(container,position,inertia);
            break;
            default:
            toReturn = new Boulder(container,position,inertia);
        }

        //RETURN THE NEW WEAPON
        return toReturn;
    }
}
