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
public class ShopperSnitchingVisitor implements ShopperVisitor {

    @Override
    public boolean visit(Kristi shopper) {

        boolean isStealing = false;
        if (shopper.isStealing()) {
            isStealing = true;
            shopper.increaseJailProb();
        }

        return isStealing;
    }

    @Override
    public boolean visit(Jacob shopper) {

        boolean isStealing = false;
        if (shopper.isStealing()) {
            isStealing = true;
            shopper.increaseJailProb();
        }

        return isStealing;
    }

    @Override
    public boolean visit(Shopper shopper) {

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
