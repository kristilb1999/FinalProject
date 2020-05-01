/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SupermarketShoppers;

/**
 *
 * @author Will Skelly
 * @version Spring 2020
 */
public class ShopperStealingVisitor implements ShopperVisitor{
    
    @Override
    public boolean visit(Kristi shopper){
        
        
        return shopper.isStealing();
    }
    
    @Override
    public boolean visit(Jacob shopper){
        
        
       return shopper.isStealing();
    }
    
    @Override
    public boolean visit(Shopper shopper){
        
        
        return false;
    }

    @Override
    public boolean visit(Will shopper) {
        return false;
    }

    @Override
    public boolean visit(Cameron shopper) {
        return false;
    }
    
    
    
}
