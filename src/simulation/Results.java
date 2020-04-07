package simulation;

import cli.PlacedOrder;

import java.util.ArrayList;

/**
 * Save the results of run of the simulation
 */
public class Results {

	private ArrayList<Double> times; //Saves the delivery time of all the orders of one run of the simulation
	private final double MAXTIME = 120; //Maximum time for a item to be delivered in minutes
	private double worstTime;
	private double avgTime;
	int orderCounter;
	
	public Results() {
		orderCounter = 0;
		times = new ArrayList<>();
	}

	/**
	 * Process one delivery of the drone
	 * @param time The time the drone finished delivering one drone run
	 * @param onDrone The orders on the drone delivery
	 * @throws Exception
	 */
	public void processDelivery(double time, ArrayList<PlacedOrder> onDrone) throws Exception {
		//The difference in minutes between when the order was placed and the drone finished up its delivery
		double deliveryTime = 0;

		//For each order on the drone, find the delivery time, check to make sure that the delivery time is
		//not too long, and then store the result
		for (int i = 0; i < onDrone.size(); i++) {
			deliveryTime = time - onDrone.get(i).getOrderedTime();
			if (deliveryTime > MAXTIME) {
				worstTime = 120;
				avgTime = 120;
				throw new Exception("Order #" + (orderCounter + 1) + " was over the max time allowed.");
			}
			times.add(deliveryTime);
			orderCounter++;
		}
	}

	

	/**
	 * Find the longest delivery time
	 * @return
	 */

	private double calcWorstTime() {

		double highest = times.get(0);
		if (times.size() > 1) {
			for (int i = 1; i < times.size(); i++) {
				if (times.get(i) > highest)
					highest = times.get(i);
			}//for
		}//if
		worstTime = highest;
		return worstTime;
	}

	/**
	 * Find the average delivery time
	 * @return
	 */

	private double calcAvgTime() {

		double total = 0f;
		for (double curr : times) {
			total += curr;
		}
		avgTime = total/ times.size();
		return avgTime;
	}

	/**
	 * Gets the final results for the algorithm.
	 * It will eventually write the results to a file
	 * @param algorithm The name of the algorithm that the results are for
	 */
	public void getFinalResults(String algorithm) {
		System.out.println("The worst time for " + algorithm + " is: " + calcWorstTime());
		System.out.println("The average time for " + algorithm + " is: " + calcAvgTime());
	}

	public double getWorstTime() {
		return worstTime;
	}

	public double getAvgTime() {
		return avgTime;
	}









	
}
