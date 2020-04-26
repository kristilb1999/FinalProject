# FinalProject
Kristi Boardman, Cameron Costello, Will Skelly, Jake Burch

# Windows Installer
* Download and run the installer
* Launch from Start Menu Programs
* Uninstall of Add or Remove Programs to uninstall

# Running in BlueJ
* Clone this repository
* Run 'touch package.bluej' inside the FinalProject/src/ directory
* Run 'touch package.bluej' inside the FinalProject/src/TowerDefense directory
* Run 'touch package.bluej' inside the FinalProject/src/SupermarketShoppers directory
* Run 'touch package.bluej' inside the FinalProject/src/Launcher directory
* Run 'start package.bluej' inside the FinalProject/src/ directory
* Navigate to the Launcher package in BlueJ, run main method on LauncherWindow.java

# Building and Running in Netbeans 11
* Create a new Netbeans project (select Java with Ant & Java Application)
* Clone this repository, replacing build.xml and src/ in project folder
* Right click FinalProject > Properties > Run and enter 'Launcher.LauncherWindow' next to 'Main Class:'
* Right click "Libraries" under your new project, click Add Jar/Folder.., and select lib/h2-1.4.200.jar 
* The project can now be run by clicking 'Run Project' (keyboard shortcut F6) in Netbeans
* Unzip jre.zip to FinalProject/jre/
* Create a config.xml file from the config.xml.example file template in FinalProject/misc/, replacing the project path with your own.
* Install Launch4J from here (tested using version )
* Install NSIS from here (tested using version )
* Navigate to Files (Netbeans Tab), locate build.xml, right click and select Run Target > Other Targets > package-for-installer (if this doesn't work, try running package-for-launch4j first)
* The Windows installer should now be located in FinalProject/installer/
