
/**
 * This class is used when a weapon has a collision and
 * determines what methods, if any, should be called on
 * that weapon.
 * Structure based on:
 * https://www.tutorialspoint.com/design_pattern/visitor_pattern.htm
 *
 * @author Cameron Costello, Kristi Boardman, Jacob Burch, Will Skelly
 * @version Spring 2020
 */
public class WeaponCollisionVisitor implements WeaponVisitor
{
    /**
     * Calls setWeaponHit with parameter true on InelasticWeapon objects.
     * 
     * @param inelasticWeapon The InelasticWeapon to call setWeaponHit on.
     */ 
    @Override
    public void visit(InelasticWeapon inelasticWeapon) {
        inelasticWeapon.setWeaponHit(true);
    }

    /**
     * Does nothing on Weapon objects.
     * 
     * @param weapon The Weapon to do nothing on.
     */ 
    @Override
    public void visit(Weapon weapon) {
        //do nothing
    }
}
