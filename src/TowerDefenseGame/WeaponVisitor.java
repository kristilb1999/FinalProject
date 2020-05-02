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
