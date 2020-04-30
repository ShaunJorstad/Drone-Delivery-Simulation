package simulation;

import java.util.ArrayList;
import java.util.LinkedList;

import cli.PlacedOrder;
import menu.Drone;

import java.util.Queue;

public class Fifo {
	
	private Queue<PlacedOrder> orders;
	private double maxWeight;
	private double timePassed;
	private int maxNumFlights;
	
	public Fifo(ArrayList<PlacedOrder> orders, int maxNumFlights) {
		this.orders = new LinkedList<PlacedOrder>(orders);
		Drone drone = Settings.getDrone();
		maxWeight = drone.getWeight(); //12 pounds, with 16 oz per pound
		timePassed = 0;
		this.maxNumFlights = maxNumFlights;
	}
	
	/**
	 * packDrone()
	 * @return an arrayList of PlacedOrders that represents the next
	 * shipment of orders.
	 */
	public ArrayList<PlacedOrder> packDrone(double elapsedTime) {
		
		ArrayList<PlacedOrder> drone = new ArrayList<PlacedOrder>();
		Boolean isFull = false;
		double currentWeight = 0;
		
		while (!orders.isEmpty() && !isFull && drone.size() < maxNumFlights) {
			if (currentWeight + orders.peek().getWeight() <= maxWeight) {
				if (orders.peek().getOrderedTime() < (int)elapsedTime) {
					currentWeight += orders.peek().getWeight();
					drone.add(orders.remove());
					isFull = false;
				} else if (drone.size() == 0){
					timePassed = Math.abs(elapsedTime - (double)orders.peek().getOrderedTime());
					currentWeight += orders.peek().getWeight();
					drone.add(orders.remove());
					isFull = true;
				} else isFull = true;
				
			} else  isFull = true;
		}//while
		if (orders.size() == 0) return null;
		return drone;
	}
	
	public double getTimeSkipped() {
		double out = timePassed;
		timePassed = 0;
		return out;
	}
}
