// going to be lazy about imports in this class
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.Random;

/**
 * Procures weapons
 *
 * @author Kristi Boardman, Cameron Costello, Will Skelly, Jake Burch
 * @version Spring 2020
 */
public class QuarterMaster
{
    public static Weapon getRandomWeapon(JComponent container, Point position, Point inertia){

        Random rand = new Random();

        int num = rand.nextInt(4);

        Weapon toReturn;

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

        return toReturn;
    }
}
