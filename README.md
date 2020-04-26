# FinalProject
Kristi Boardman, Cameron Costello, Will Skelly, Jake Burch

# Windows Installer
* Download and run the [Installer](https://github.com/kristilb1999/FinalProject/releases/download/v0.1.0-alpha/FinalProject-Installer.exe "FinalProject-Installer.exe")
* Launch from Start Menu Programs
* Uninstall from Add or Remove Programs to uninstall

# Running in BlueJ
* Clone this repository
* Run 'touch package.bluej' inside the FinalProject/src/ directory
* Run 'touch package.bluej' inside the FinalProject/src/TowerDefense directory
* Run 'touch package.bluej' inside the FinalProject/src/SupermarketShoppers directory
* Run 'touch package.bluej' inside the FinalProject/src/Launcher directory
* Run 'start package.bluej' inside the FinalProject/src/ directory
* Navigate to the Launcher package in BlueJ, run main method on LauncherWindow.java

# Building and Running in Netbeans 11
* Clone this repository, then open the Netbeans project 'FinalProject' from Netbeans
* The project can now be run by clicking 'Run Project' (keyboard shortcut F6) in Netbeans
* Unzip jre.zip to FinalProject/jre/
* Create a config.xml file from the config.xml.example file template in FinalProject/misc/, replacing the project path with your own.
* Install [Launch4J](http://launch4j.sourceforge.net/) (tested using version 3.12) to 'C:\Program Files (x86)\Launch4j'
* Install [NSIS](https://nsis.sourceforge.io/Main_Page) (tested using version 3.05) to 'C:\Program Files (x86)\NSIS'
* Navigate to Files (Netbeans Tab), locate build.xml, right click and select Run Target > Other Targets > package-for-installer (if this doesn't work, try running package-for-launch4j first)
* The Windows installer should now be located under FinalProject/installer/
