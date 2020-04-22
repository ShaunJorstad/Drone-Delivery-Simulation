package cli;

import menu.Destination;

import java.util.ArrayList;

public class TSP {

    public TSP () {

    }

    /**
     * Calculates the least cost distance to complete the delivery cycle
     *
     * @param orders ArrayList of placed orders to travel and deliver food to
     * @return The least cost distance to complete the delivery cycle
     */
    public TSPResult runTSP(ArrayList<PlacedOrder> orders) {
        ArrayList<Destination> locations = new ArrayList<>(); //The destination of each order

        //The place where the drone leaves and returns to
        Destination home = new Destination("Home", 0, 0, 0);


        //Seed the location ArrayList with each destination from the order list
        for (int i = 0; i < orders.size(); i++) {
            locations.add(orders.get(i).getDest());
        }

        //Call the recursive function which does the brunt work of the algorithm
        return recursiveTSP(locations, home);
    }

    /**
     * Does the brunt work of the TSP. Should run in O(n^2 * 2^n) which is much better than O(n!)
     *
     * @param locations The locations yet to be visited
     * @param lastDest  The last destination the algorithm visited
     * @return The least cost distance to visit all the locations and return
     */
    private TSPResult recursiveTSP(ArrayList<Destination> locations, Destination lastDest) {
        TSPResult finalResult = new TSPResult();
        if (locations.size() == 0) { //base case
            return new TSPResult(lastDest.getDist()); //return to home
        } else {
            double min = Double.MAX_VALUE; //minimum travel distance for given depth in the recursion tree

            //For each possible set of locations
            for (int d = 0; d < locations.size(); d++) {
                Destination newDest = locations.remove(0); //remove the destination

                double distanceBetween = lastDest.distanceBetween(newDest);
                TSPResult newResult = recursiveTSP(locations, newDest);

                newResult.addDistance(distanceBetween);
                //recurse: it finds the fastest route in the subset and then adds the distance between the current
                //          point and the subset. It then takes the minimum at the level so to help in the recursion in
                //          the level above it
                if (newResult.getTotalDistance() < min) {
                    min = newResult.getTotalDistance();
                    Destination temp = new Destination(newDest);
                    temp.setDistToTravelTo(distanceBetween);
                    newResult.addStop(temp);
                    finalResult = newResult;


                }

                //Add back the location because each iteration of the loop on the same level should have the same
                //number of locations to check
                locations.add(newDest);
            }
            //return the shortest distance found in the given level
            return finalResult;
        }

    }
}
