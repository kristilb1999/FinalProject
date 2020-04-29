/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SupermarketShoppers;

import java.util.Vector;
/**
 * This class controls the running of each supermarket. When calling this class, 
 * input some positive integer for the number of stores to be created, the default
 * creation is one store. When the store has completed running, a string of information
 * about the stores will be output to the terminal window.
 *
 * @author Kristi Boardman
 */
public class ChainStoreOperator extends Thread {
    
    private static final int DELAY_TIME = 33;
    
    private int numStores;
    
    private Vector<Supermarket> storesRunning;
    
    private boolean done;

    private ChainStoreOperator(int numStores) {
        this.numStores = numStores;
    }
   
    public void run(){
        for(int i = 0; i < numStores; i++) {
            Supermarket nextStore = new Supermarket(i);
            nextStore.start();
            storesRunning.add(nextStore);
        }
        
        while(!done){
            int index = 0;
            
            while(index < storesRunning.size()) {
                Supermarket s = storesRunning.get(index);
                if(s.done()) {
                    s.toString();
                    storesRunning.remove(index);
                } else {
                    index++;
                }
            }
            
            if(storesRunning.size() == 0) {
                done = true;
            }
        }
    }
    
    public static void main(String args[]){
        
        int numStores = 1;
        
        if (args.length != 1 || !(Integer.parseInt(args[0]) > 0)) {
            System.out.println("The amount of stores to run was not input. One store will be created by default.");
        } else if(Integer.parseInt(args[0]) > 0) {
            numStores = Integer.parseInt(args[0]);
        }
        
        java.awt.EventQueue.invokeLater(new ChainStoreOperator(numStores));

    }
}



