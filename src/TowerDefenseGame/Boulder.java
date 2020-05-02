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
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.Point2D;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JComponent;

/**
 * Boulder is a Weapon, it can bounce off of the floor.
 *
 * @author Kristi Boardman, Cameron Costello, Will Skelly, Jake Burch
 * @version Spring 2020
 */
public class Boulder extends Weapon
{

    //THE WEIGHT OF EVERY BOULDER WEAPON
    private static final int WEIGHT = 8;

    //THE SIZE OF EVERY BOULDER WEAPON
    private static final int SIZE = 50;

    /**
     * Creates a Boulder Weapon.
     * 
     * @param container The container to paint it in.
     * @param position The weapon's current position.
     * @param inertia Determines object velocity.
     */
    public Boulder(JComponent container, Point2D.Double position, Point2D.Double inertia)
    {
        //CALL THE SUPER CONSTRUCTOR OF THE WEAPON CLASS
        super(container, position, inertia);
        
        //CREATES THE BOULDER IMAGE
        try {
            typeFilePath = "/weaponTypeFour.png";
            InputStream imageStream = new BufferedInputStream(DatabaseDriver.class.getResourceAsStream(typeFilePath));
            Image image = ImageIO.read(imageStream);
            type = image.getScaledInstance(SIZE,SIZE,0);
        } catch (IOException e) {
            System.out.println("Boulder: Error loading image");
            e.printStackTrace();
        }
        
    }

    /**
     * Paints the Boulder weapon on the container at its position.
     * 
     * @param g The graphics object.
     */
    @Override
    public void paint(Graphics g) {
        
            g.drawImage(type, (int)position.x, (int)position.y, null);
        
    }

    /**
     * Returns the weight of the Boulder weapon.
     * 
     * @return The weight of the Boulder weapon object.
     */
    @Override
    public int getWeight(){
        return WEIGHT;
    }

    /**
     * Returns the size of the Boulder weapon.
     * 
     * @return The size of the Boulder weapon object.
     */
    @Override
    public int getSize(){
        return SIZE;
    }
}
