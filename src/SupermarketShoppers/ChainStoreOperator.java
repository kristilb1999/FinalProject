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
package SupermarketShoppers;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Vector;

/**
 * This class controls the running of each supermarket. When calling this class,
 * input some positive integer for the number of stores to be created, the
 * default creation is one store. When the store has completed running, a string
 * of information about the stores will be output to the terminal window.
 *
 * @author Kristi Boardman, Will Skelly, Cameron Costello, Jake Burch
 * @version Spring 2020
 */
public class ChainStoreOperator extends Thread {

    //A LIST OF ALL STORES IN THE CHAIN
    private Vector<Supermarket> storesList;
    
    //THE NUMBER OF STORES IN THE CHAIN
    private int numStores;

/**
 * Constructs a chain store operator that takes in a number of stores.
 * @param numStores 
 */
    private ChainStoreOperator(int numStores) {
        this.numStores = numStores;
        this.storesList = new Vector<Supermarket>();
    }

    /**
     * Creates all of the supermarkets with their unique store numbers,
     * and waits until all of the supermarkets have finished. Finally, 
     * all supermarkets will print their own information and a collection
     * of other information will be printed before the program ends.
     */
    @Override
    public void run() {
        
        //CREATE A LIST OF SUPERMARKETS WITH UNIQUE STORE NUMBERS
        for (int i = 0; i < numStores; i++) {
            Supermarket nextStore = new Supermarket(i);
            storesList.add(nextStore);
            nextStore.start();
        }

        //WAITS UNTIL EVERY SUPERMARKET IS COMPLETE
        for (int i = 0; i < storesList.size(); i++) {
            try {
                storesList.get(i).join();
            } catch (InterruptedException e) {
                System.err.println(e);
            }
        }
        
        //PRINTS ALL SUPERMARKET INFORMATION IN ORDER BY UNIQUE NUMBER
        for (Supermarket store : storesList) {
            System.out.println(store.toString());
        }

        //PRINTS ALL IMPORTANT INFORMATION ABOUT STORES
        this.printInformation();

    }

    /**
     * Prints information about all of the stores. Will print the most popular
     * store (the one with the most visitors), the average amount of shoppers
     * that visited every supermarket, the average amount of shoppers that went
     * to jail for stealing, the amount of visitors that panic shopped, and the
     * amount of visitors that stole from the store.
     */
    public void printInformation() {

        System.out.println("Important supermarket information:");

        StringBuilder sb = new StringBuilder();
        
        sb.append("Most visited store of the day: Store number ").append(mostPopStore()).append("\n");
        sb.append("Average number of shoppers in one day: ").append(averageShoppers()).append("\n");
        sb.append("Average number of shoppers sent to jail: ").append(averageInJail()).append("\n");
        sb.append("Number of panic shoppers from all stores: ").append(panicShoppers()).append("\n");
        sb.append("Number of people who stole from all stores: ").append(stealingShoppers());

        System.out.println(sb);
    }

    /**
     * Returns the store number of the most visited store of the day.
     * @return The store number of the most popular store.
     */
    public int mostPopStore() {
        int mostPopular = -1;
        int mostCustomers = 0;
        for (Supermarket store : storesList) {
            if (store.getNumCustomers() > mostCustomers) {
                mostCustomers = store.getNumCustomers();
                mostPopular = store.getStoreNumber();
            }
        }
        return mostPopular;
    }

    /**
     * Returns the average number of shoppers across all of the stores.
     * @return The average number of shoppers across all of the stores.
     */
    public double averageShoppers() {
        double totalShoppers = 0;
        for (Supermarket store : storesList) {
            totalShoppers += store.getNumCustomers();
        }
        return totalShoppers / numStores;
    }

    /**
     * Returns the average number of people in jail across all stores.
     * @return The average number of people thrown in jail across all stores.
     */
    public double averageInJail() {
        double totalInJail = 0;
        for (Supermarket store : storesList) {
            totalInJail += store.numShoppersInJail();
        }
        return totalInJail / numStores;
    }

    /**
     * The total number of panic shoppers from all stores.
     * @return The number of panic shoppers from all stores.
     */
    public int panicShoppers() {
        int totalPanicking = 0;
        for (Supermarket store : storesList) {
            totalPanicking += store.getNumPanicShopping();
        }
        return totalPanicking;
    }

    /**
     * The total number of shoppers that stole from all stores.
     * @return The total number of shoppers that stole from all stores.
     */
    public int stealingShoppers() {
        int totalStealing = 0;
        for (Supermarket store : storesList) {
            totalStealing += store.getNumStealing();
        }
        return totalStealing;
    }

    /**
     * Runs the number of simulations that are input, or one by default.
     * All text output is redirected to the "simulation.txt" file.
     * @param args An integer amount of supermarkets to create.
     */
    public static void main(String args[]) {

        //OUTPUT WILL BE PRINTED TO THE SIMULATION.TXT FILE
        try{
        System.setOut(new PrintStream("simulation.txt"));
        }catch(FileNotFoundException e){
        System.err.println("File Not Found: " + e);
        }
        
        //ONE STORE IS CREATED BY DEFAULT
        int numOfStores = 1;

        //IF THERE IS NOT A VALID INPUT, ONE SUPERMARKET IS CREATED BY DEFAULT
        if (args.length != 1 || !(Integer.parseInt(args[0]) > 0)) {
            System.out.println("The amount of stores to run was not input. One store will be created by default.");
        } else if (Integer.parseInt(args[0]) > 0) {
            numOfStores = Integer.parseInt(args[0]);
        }

        //CREATE THE SUPERMARKETS AND RUN ALL THREADS
        java.awt.EventQueue.invokeLater(new ChainStoreOperator(numOfStores));

    }
}
