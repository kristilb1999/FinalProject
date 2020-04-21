// going to be lazy about imports in this class
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.geom.*;

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
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        typeFilePath = "weaponTypeOne.png";
        type = toolkit.getImage(typeFilePath).getScaledInstance(SIZE,SIZE,0);
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
