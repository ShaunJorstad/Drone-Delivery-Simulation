# Drone-Delivery-Simulation
This application creates a drone delivery simulation that analyzes different packing algorithms for optimal delivery performance. The application provides a gui to change the following simulation settings:

- food items: name and weight
- meal items: name food items, order frequency 
- delivery map: campus map, delivery destinations
- drones: number of drones, carrying capcaity, speed of drone, etc...

Settings update in real time and are verified by the application before running. Configurations can be imported and exported to disk for later analysis, as can the results. The results shows the average and worst times for the FIFO and knapsack algorithms, and displays a graph of the two packing algorithms. 

# Install
- first create a javafx project in your IDE of choice
- clone this repo into the root directory, replacing the src folder 
- download the appropriate JavaFX library according to your JDK
- add the javafx library to your path OR include it in your IDE's project libraries
- Running gui.Main.java should display the gui. 