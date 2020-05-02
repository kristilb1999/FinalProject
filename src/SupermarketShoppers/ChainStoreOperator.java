/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SupermarketShoppers;

import java.io.FileNotFoundException;
import java.io.PrintStream;
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
    
    private static final int DELAY_TIME = 50;
    
    private int numStores;
    
    private Vector<Supermarket> storesRunning;
    
    private boolean done;

    private ChainStoreOperator(int numStores) {
        this.numStores = numStores;
        this.storesRunning = new Vector<Supermarket>();
    }
   
    @Override
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
                this.done = true;
            }
            
            try{
                sleep(DELAY_TIME);
            } catch (InterruptedException e){
                System.err.println(e);
            } 
        }
        
        for(Supermarket store : storesRunning) {
                store.toString();
            }
        
        this.printInformation();
        
    }
    
    public void printInformation() {
        
        System.out.println("Important supermarket information:");
        
        String toPrint = 
                "Most visited store of the day: Store number " + mostPopStore() + "\n" +
                "Average number of shoppers in one day: " + averageShoppers() + "\n" +
                "Average number of shoppers sent to jail: " + averageInJail() + "\n" +
                "Number of panic shoppers from all stores: " + panicShoppers() + "\n" +
                "Number of people who stole from all stores: " + stealingShoppers();
                
        System.out.println(toPrint);
    }
    
    public int mostPopStore() {
        int mostPopular = -1;
        int mostCustomers = 0;
        for(Supermarket store : storesRunning) {
            if(store.getNumCustomers() > mostCustomers) {
                mostPopular = store.getStoreNumber();
            }
        }
        return mostPopular;
    }
    
    public double averageShoppers() {
        double totalShoppers = 0;
        for(Supermarket store : storesRunning) {
            totalShoppers += store.getNumCustomers();
            totalShoppers += store.numShoppersInJail();
        }
        return totalShoppers/numStores;
    }
    
    public double averageInJail() {
        double totalInJail = 0;
        for(Supermarket store : storesRunning) {
            totalInJail += store.numShoppersInJail();
        }
        return totalInJail/numStores;
    }
    
    public int panicShoppers() {
        int totalPanicking = 0;
        for(Supermarket store : storesRunning) {
            totalPanicking += store.getNumPanicShopping();
        }
        return totalPanicking;
    }
    
    public int stealingShoppers() {
        int totalStealing = 0;
        for(Supermarket store : storesRunning) {
            totalStealing += store.getNumStealing();
        }
        return totalStealing;
    }
    
    public static void main(String args[]){
        
        //print output to a file
//        try{
//        System.setOut(new PrintStream("simulation.txt"));
//        }catch(FileNotFoundException e){
//        System.err.println("File Not Found: " + e);
//        }
        
        int numOfStores = 1;
        
        if (args.length != 1 || !(Integer.parseInt(args[0]) > 0)) {
            System.out.println("The amount of stores to run was not input. One store will be created by default.");
        } else if(Integer.parseInt(args[0]) > 0) {
            numOfStores = Integer.parseInt(args[0]);
        }
        
        java.awt.EventQueue.invokeLater(new ChainStoreOperator(numOfStores));

    }
}



