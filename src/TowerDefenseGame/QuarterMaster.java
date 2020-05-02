/*
 * Copyright (C) 2020 William Skelly, Kristi Boardman, Cameron Costello, and Jacob Burch
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
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
