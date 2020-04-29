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
        storesRunning = new Vector<Supermarket>();
    }
   
    public void run(){
        for(int i = 0; i < numStores; i++) {
            Supermarket nextStore = new Supermarket(i);
            nextStore.start();
            storesRunning.add(nextStore);
        }
        
        while(!done){
            int numDone = 0;
            
            for(Supermarket store : storesRunning) {
                if(store.done()){
                    numDone++;
                }
            }
            
            if(numDone == storesRunning.size()) {
                done = true;
            }
            
            try{
                sleep(DELAY_TIME);
            } catch (InterruptedException e){
                System.err.println(e);
            } 
        }
        
        printInformation();
        
    }
    
    public void printInformation() {
        
        System.out.println("All supermarket information:");
        
        for(Supermarket store : storesRunning) {
            store.toString();
        }
        
        System.out.println("Important supermarket information:");
        
        String toPrint = 
                "Most visited store of the day: Store number " + mostPopStore() + "\n" +
                "Average number of shoppers in one day: " + averageShoppers() + "\n" +
                "Average number of shoppers sent to jail: " + averageInJail() + "\n" +
                "Number of panic shoppers from all stores: " + panicShoppers() + "\n" +
                "Number of people who stole from all stores: " + stealingShoppers();
    }
    
    public int mostPopStore() {
        return -1;
    }
    
    public int averageShoppers() {
        return -1;
    }
    
    public int averageInJail() {
        return -1;
    }
    
    public int panicShoppers() {
        return -1;
    }
    
    public int stealingShoppers() {
        return -1;
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



