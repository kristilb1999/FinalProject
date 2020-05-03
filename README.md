# FinalProject: TD-ShopSim 
by Kristi Boardman, Cameron Costello, Will Skelly, Jake Burch
## Tower Defense Game & Shopping Simulation
This project contains two portions:  a Tower Defense Game and a Shopping Simulation.

## Windows Installer
* Download and run the [Installer](https://github.com/kristilb1999/FinalProject/releases/download/v0.2.0-alpha/TD-ShopSim_Installer.exe "TD-ShopSim_Installer.exe")
* Launch from Start Menu Programs
* Uninstall from Add or Remove Programs

## Running in BlueJ
* Clone this repository
* Run 'touch package.bluej' inside the FinalProject/src/ directory
* Run 'touch package.bluej' inside the FinalProject/src/TowerDefense directory
* Run 'touch package.bluej' inside the FinalProject/src/SupermarketShoppers directory
* Run 'touch package.bluej' inside the FinalProject/src/Launcher directory
* Run 'start package.bluej' inside the FinalProject/src/ directory
* Navigate to the Launcher package in BlueJ, run main method on LauncherWindow.java

## Building and Running in Netbeans 11
* Clone this repository, then open the Netbeans project 'FinalProject' from Netbeans
* The project can now be run by clicking 'Run Project' (keyboard shortcut F6) in Netbeans
* Unzip jre.zip to FinalProject/jre/
* Create a config.xml file from the config.xml.example file template in FinalProject/misc/, replacing the project path with your own.
* Install [Launch4J](http://launch4j.sourceforge.net/) (tested using version 3.12) to 'C:\Program Files (x86)\Launch4j'
* Install [NSIS](https://nsis.sourceforge.io/Main_Page) (tested using version 3.05) to 'C:\Program Files (x86)\NSIS'
* Navigate to Files (Netbeans Tab), locate build.xml, right click and select Run Target > Other Targets > package-for-installer (if this doesn't work, try running package-for-launch4j first)
* The Windows installer should now be located under FinalProject/installer/

## License
Copyright (C) 2020 William Skelly, Kristi Boardman, Cameron Costello, and Jacob Burch

The use of TD-ShopSim is governed by the terms of the GNU General 
Public License Version 2 with Classpath Exception, as found in [LICENSE.txt](https://github.com/kristilb1999/FinalProject/blob/master/LICENSE.txt).

TD-ShopSim makes use of 3rd party software which are under different 
licenses. These licenses can be found in [THIRDPARTYLICENSE.txt](https://github.com/kristilb1999/FinalProject/blob/master/THIRDPARTYLICENSE.txt).
