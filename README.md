# FinalProject: TD-ShopSim 
by Kristi Boardman, Cameron Costello, Will Skelly, Jake Burch
## Tower Defense Game & Shopping Simulation
This project contains two portions:  a Tower Defense Game and a Shopping Simulation.

Tower Defense Game Description: 
The objective of this game is to defend the tower against the incoming zombie army. The zombies enter the scene from the left most side of the screen to attack the tower that resides on the right most end of the screen. The zombies will walk in a straight path in this 2D version of Tower Defense in order to reach the tower. There are four types of zombies in this army to watch out for the Average Zombie, the Big-Eye Zombie, the Hunchback Zombie, and the Pirate Zombie. All zombies in the army will do the same amount of damage as their strength to the tower health. The Average Zombie is the weakest of the enemies, it is the slowest (2) and has the least amount of health (1). The Hunchback Zombie is the next weakest of the enemies, it moves a little bit faster (5) than the Average Zombie and has slightly more health (2). The Big-Eye Zombie is slightly stronger than the other two zombies, with a speed of 7 and will take 3 hits before it dies. The strongest of the enemies is the Pirate Zombie which is the fastest with a speed of 10 and the strongest with a max health of 5. These enemies can be destroyed by launching weapons from the clickable launch area above the tower. Click and drag your mouse in order to launch a weapon towards the incoming enemies before they reduce your tower health to zero and you lose the game. The weapons that are launched will randomly be chosen from four different options: a boulder, a grenade, a molotov cocktail, and tnt.

Shopping Simulation Description: 
This program takes an integer as input to specify the number of supermarkets that will be simulated concurrently, if no input is given then only one supermarket will run. Each supermarket is in charge of creating between one and twenty shoppers that will search the supermarket's inventory for the items that they wish to purchase. Each shopper can only buy the things that they can afford, unless they are a shopper that steals from the store. There are four types of shoppers that can enter the store: a shopper that will never steal any items, but will snitch on any shopper they find stealing; a shopper that will never steal and will never snitch; a shopper that will steal from the store, but will not snitch on other people that are stealing; and a shopper that will steal items and will snitch on any other shopper that is stealing. Shoppers that do not steal also have a tendency to participate in panic shopping when they cannot buy all of the items that are on their shopping list because the store does not have any in it's inventory. At the end of the simulation, information about the stores and the shopper types will output into a text file.

## Windows Installer
* Download and run the [Installer](https://github.com/kristilb1999/FinalProject/releases/download/v0.3.0-alpha/TD-ShopSim_Installer.exe "TD-ShopSim_Installer.exe")
* Launch from Start Menu Programs
* Uninstall from Add or Remove Programs

## Running in BlueJ
* Clone this repository
* Run `touch package.bluej` inside the `FinalProject/src/` directory
* Run `touch package.bluej` inside the `FinalProject/src/TowerDefense` directory
* Run `touch package.bluej` inside the `FinalProject/src/SupermarketShoppers` directory
* Run `touch package.bluej` inside the `FinalProject/src/Launcher` directory
* Run `start package.bluej` inside the `FinalProject/src/` directory
* Navigate to the Launcher package in BlueJ, run main method on LauncherWindow.java

## Building and Running in Netbeans 11
* Clone this repository, then open the Netbeans project 'FinalProject' from Netbeans
* The project can now be run by clicking 'Run Project' (keyboard shortcut F6) in Netbeans
* Unzip jre.zip to `FinalProject/jre/`
* Create a config.xml file from the config.xml.example file template in `FinalProject/misc/`, replacing the project path with your own.
* Install [Launch4J](http://launch4j.sourceforge.net/) (tested using version 3.12) to `C:\Program Files (x86)\Launch4j`
* Install [NSIS](https://nsis.sourceforge.io/Main_Page) (tested using version 3.05) to `C:\Program Files (x86)\NSIS`
* Navigate to Files (Netbeans Tab), locate build.xml, right click and select Run Target > Other Targets > package-for-installer (if this doesn't work, try running package-for-launch4j first)
* The Windows installer should now be located under `FinalProject/installer/`

## License
Copyright (C) 2020 William Skelly, Kristi Boardman, Cameron Costello, and Jacob Burch

The use of TD-ShopSim is governed by the terms of the GNU General 
Public License Version 2 with Classpath Exception, as found in [LICENSE.txt](https://github.com/kristilb1999/FinalProject/blob/master/LICENSE.txt).

TD-ShopSim makes use of 3rd party software which are under different 
licenses. These licenses can be found in [THIRDPARTYLICENSE.txt](https://github.com/kristilb1999/FinalProject/blob/master/THIRDPARTYLICENSE.txt).
