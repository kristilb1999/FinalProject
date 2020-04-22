
/**
 * 
 * This interface defines what methods a WeaponVisitor must have,
 * so only two headers are included, as this is the only
 * distinction required at present.
 * Structure based on:
 * https://www.tutorialspoint.com/design_pattern/visitor_pattern.htm
 *
* @author Cameron Costello, Kristi Boardman, Jacob Burch, Will Skelly
 * @version Spring 2020
 */
public interface WeaponVisitor
{
    /**
     * Visits an InelasticWeapon
     * 
     * @param inelasticWeapon The InelasticWeapon to visit.
     */
    public void visit(InelasticWeapon inelasticWeapon);
    
    /**
     * Visits a Weapon
     * 
     * @param weapon The Weapon to visit.
     */
    public void visit(Weapon weapon);
}
