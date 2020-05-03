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
 * @author Kristi Boardman
 */
public class ChainStoreOperator extends Thread {

    private int numStores;

    private Vector<Supermarket> storesList;

    private ChainStoreOperator(int numStores) {
        this.numStores = numStores;
        this.storesList = new Vector<Supermarket>();
    }

    @Override
    public void run() {
        for (int i = 0; i < numStores; i++) {
            Supermarket nextStore = new Supermarket(i);
            storesList.add(nextStore);
            nextStore.start();
        }

        for (int i = 0; i < storesList.size(); i++) {
            try {
                storesList.get(i).join();
            } catch (InterruptedException e) {
                System.err.println(e);
            }
        }
        
        for (Supermarket store : storesList) {
            System.out.println(store.toString());
        }

        this.printInformation();

    }

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

    public int mostPopStore() {
        int mostPopular = -1;
        int mostCustomers = 0;
        for (Supermarket store : storesList) {
            if (store.getNumCustomers() > mostCustomers) {
                mostPopular = store.getStoreNumber();
            }
        }
        return mostPopular;
    }

    public double averageShoppers() {
        double totalShoppers = 0;
        for (Supermarket store : storesList) {
            totalShoppers += store.getNumCustomers();
        }
        return totalShoppers / numStores;
    }

    public double averageInJail() {
        double totalInJail = 0;
        for (Supermarket store : storesList) {
            totalInJail += store.numShoppersInJail();
        }
        return totalInJail / numStores;
    }

    public int panicShoppers() {
        int totalPanicking = 0;
        for (Supermarket store : storesList) {
            totalPanicking += store.getNumPanicShopping();
        }
        return totalPanicking;
    }

    public int stealingShoppers() {
        int totalStealing = 0;
        for (Supermarket store : storesList) {
            totalStealing += store.getNumStealing();
        }
        return totalStealing;
    }

    public static void main(String args[]) {

        //print output to a file
//        try{
//        System.setOut(new PrintStream("simulation.txt"));
//        }catch(FileNotFoundException e){
//        System.err.println("File Not Found: " + e);
//        }
        int numOfStores = 1;

        if (args.length != 1 || !(Integer.parseInt(args[0]) > 0)) {
            System.out.println("The amount of stores to run was not input. One store will be created by default.");
        } else if (Integer.parseInt(args[0]) > 0) {
            numOfStores = Integer.parseInt(args[0]);
        }

        java.awt.EventQueue.invokeLater(new ChainStoreOperator(numOfStores));

    }
}
