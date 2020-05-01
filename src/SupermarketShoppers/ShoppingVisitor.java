/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SupermarketShoppers;

/**
 * Write a description here.
 *
 * @author Cameron Costello, Kristi Boardman, Will Skelly, Jacob Burch
 * @version Spring 2020
 */
public interface ShoppingVisitor {
    
    
    public boolean visit(Kristi shopper);
    public boolean visit(Will shopper);
    public boolean visit(Cameron shopper);
    public boolean visit(Jacob shopper);
    public boolean visit(Shopper shopper);
}
