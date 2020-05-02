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
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.Point2D;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.JComponent;

/**
 * The Pirate Soldier.
 *
 * @author Cameron Costello, Kristi Boardman, Jacob Burch, Will Skelly
 * @version Spring 2020
 */
public class Pirate extends Soldier
{
    //THE SIZE OF THE PIRATE SOLDIER
    private static final int SIZE = 80;
    
    //THE STRENGTH OF THE PIRATE SOLDIER
    private static final int STRENGTH = 5;

    //THE SPEED OF THE PIRATE SOLDIER
    private static final int SPEED = 10;
    
    //THE AMOUNT OF POINTS THE PIRATE SOLDIER IS WORTH
    private static final int POINTS = 500;
    
    /**
     * Creates a Pirate Soldier object.
     * 
     * @param position The position of the soldier.
     * @param container The container to repaint the soldier in.
     * @param army The reference to the army that this soldier will belong to.
     */
    public Pirate(Point2D.Double position, JComponent container, SoldierArmy army)
    {
        //CALLS THE SUPER CONTRUCTOR FOR THE SOLDIER
        super(position, container, army);
        
        //CREATES THE IMAGE OF THE PIRATE SOLDIER AND GIVES IT A SIZE
        try {
            typeFilePath = "/soldierTypeFour.png";
            InputStream imageStream = new BufferedInputStream(DatabaseDriver.class.getResourceAsStream(typeFilePath));
            Image image = ImageIO.read(imageStream);
            type = image.getScaledInstance(SIZE,SIZE + SIZE/2,0);
        } catch (IOException e) {
            System.out.println("Pirate: Error loading image");
            e.printStackTrace();
        }
        
        //SETS THE HEALTH OF THE SOLDIER
        hitsUntilDeath = 4;
        
        //SETS THE SPEED OF THE SOLDIER
        speed = SPEED;
        
        //SETS THE LOCATION OF THE SOLDIER AT ITS POSITION
        position.setLocation(position.x, position.y - SIZE);
    }
    
    /**
     * Gets the amount of points each soldier is worth.
     * 
     * @return The amount of points each soldier is worth.
     */
    @Override
    public int getPoints() {
        return POINTS;
    }
    
    /**
     * Returns the strength of the Pirate Soldier.
     * 
     * @return The strength of this Pirate soldier.
     */
    @Override
    public int getStrength() 
    {
        return STRENGTH;
    }
    
    /**
     * Returns the size of the Pirate Soldier.
     * 
     * @return The size of this Pirate soldier.
     */
    @Override
    public int getSize() 
    {
        return SIZE;
    }
    
}

