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
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.Point2D;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.JComponent;

/**
 * MolotovCocktail is an inelastic weapon, it cannot bounce.
 *
 * @author Kristi Boardman, Cameron Costello, Will Skelly, Jake Burch
 * @version Spring 2020
 */
public class MolotovCocktail extends InelasticWeapon
{

    //THE WEIGHT OF EVERY MOLOTOV COCKTAIL WEAPON
    private static final int WEIGHT = 10;

    //THE SIZE OF EVERY MOLOTOV COCKTAIL WEAPON
    private static final int SIZE = 50;
    
    //THE COLOR OF THE EXPLOSION
    private static final Color EXPLOSION_COLOR = new Color(201, 66, 28);

    /**
     * Creates a MolotovCocktail InelasticWeapon.
     * 
     * @param container The container to paint it in.
     * @param position The weapon's current position.
     * @param inertia Determines object velocity.
     */
    public MolotovCocktail(JComponent container, Point2D.Double position, Point2D.Double inertia)
    {
        //CALL SUPER CONSTRUCTOR FOR INELASTIC WEAPON
        super(container, position, inertia);
        
        //CREATES THE MOLOTOV COCKTAIL IMAGE
        try {
            typeFilePath = "/weaponTypeOne.png";
            InputStream imageStream = new BufferedInputStream(DatabaseDriver.class.getResourceAsStream(typeFilePath));
            Image image = ImageIO.read(imageStream);
            type = image.getScaledInstance(SIZE,SIZE,0);
        } catch (IOException e) {
            System.out.println("MolotovCocktail: Error loading image");
            e.printStackTrace();
        }
    }

    /**
     * Paints the MolotovCocktail weapon on the container at its position.
     * 
     * @param g The graphics object.
     */
    @Override
    public void paint(Graphics g) {
        //IF THE COCKTAIL HAS NOT HIT ANYTHING, DRAW IT.
        if (!weaponHit)
        {
            g.drawImage(type, (int)position.x, (int)position.y, null);
        }
        else
        {
            //DRAW THE EXPLOSION
            g.setColor(EXPLOSION_COLOR);
            g.fillOval((int)position.x - SIZE/4, (int)position.y - SIZE/2, SIZE * 3/2, SIZE * 3/2);
            g.setColor(Color.BLACK);
            g.drawOval((int)position.x - SIZE/4, (int)position.y - SIZE/2, SIZE * 3/2, SIZE * 3/2);

            g.setColor(EXPLOSION_COLOR);
            g.fillOval((int)position.x + SIZE/20, (int)position.y + SIZE/10, SIZE, SIZE);
            g.setColor(Color.BLACK);
            g.drawOval((int)position.x + SIZE/20, (int)position.y + SIZE/10, SIZE, SIZE);         

            g.setColor(EXPLOSION_COLOR);
            g.fillOval((int)position.x + SIZE, (int)position.y + SIZE/10, SIZE, SIZE);
            g.setColor(Color.BLACK);
            g.drawOval((int)position.x + SIZE, (int)position.y + SIZE/10, SIZE, SIZE);

            g.setColor(EXPLOSION_COLOR);
            g.fillOval((int)position.x - SIZE, (int)position.y + SIZE/10, SIZE, SIZE);
            g.setColor(Color.BLACK);
            g.drawOval((int)position.x - SIZE, (int)position.y + SIZE/10, SIZE, SIZE);

        }
    }

    /**
     * Returns the weight of the MolotovCocktail weapon.
     * 
     * @return The weight of the MolotovCocktail weapon object.
     */
    @Override
    public int getWeight(){
        return WEIGHT;
    }

    /**
     * Returns the size of the MolotovCocktail weapon.
     * 
     * @return The size of the MolotovCocktail weapon object.
     */
    @Override
    public int getSize(){
        return SIZE;
    }
}
