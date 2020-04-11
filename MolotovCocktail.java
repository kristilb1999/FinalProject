// going to be lazy about imports in this class
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;

/**
 * Write a description of class MolotovCocktail here.
 *
 * @author Kristi Boardman, Cameron Costello, Will Skelly, Jake Burch
 * @version Spring 2020
 */
public class MolotovCocktail extends Weapon
{
    private static final int STRENGTH = 10;

    private static final int WEIGHT = 10;

    /**
     * Constructor for objects of class MolotovCocktail
     */
    public MolotovCocktail(JComponent container, Point position)
    {
        super(container, position);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        typeFilePath = "weaponTypeOne.png";
        type = toolkit.getImage(typeFilePath);
    }

    @Override
    public void paint(Graphics g) {
        if(!bounced) {
            g.drawImage(type, position.x - velocity.x, position.y + velocity.y, null);
        } else {

        }
    }

    @Override
    public int getStrength(){
        return STRENGTH;   
    }

    @Override
    public int getWeight(){
        return WEIGHT;
    }

    @Override 
    public void run() {

    }
}
