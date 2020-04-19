# Dromedary Drones 
This application allows the user to run an ordering simulation that analyzes packing algorithms of a delivery drone, supplying food to a college campus. 

The application currently allows the user to modify the settings of the simulation, import and export settings to and from disk, and run the simulation. The results shows the average and worst times for the FIFO and knapsack algorithms, and displays a graph of the two packing algorithms. 

The result data can be exported as an xml document to disk, which can then be imported to Excel for analysis. 

# Install
- first create a javafx project in your IDE of choice
- clone this repo into the root directory, replacing the src folder 
- download the appropriate JavaFX library according to your JDK
- add the javafx library to your path OR include it in your IDE's project libraries
- Running gui.Main.java should display the gui. 