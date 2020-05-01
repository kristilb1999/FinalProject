/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SupermarketShoppers;

/**
 *
 * @author Wil
 */
public class ShopperPanickingVisitor implements ShopperVisitor{
    
    @Override
    public boolean visit(Kristi shopper){
        
        
        return false;
    }
    
    @Override
    public boolean visit(Jacob shopper){
        
        
       return false;
    }
    
    @Override
    public boolean visit(Shopper shopper){
        
        
        return false;
    }

    @Override
    public boolean visit(Will shopper) {
        return shopper.isPanicking();
    }

    @Override
    public boolean visit(Cameron shopper) {
        return shopper.isPanicking();
    }
    
}
