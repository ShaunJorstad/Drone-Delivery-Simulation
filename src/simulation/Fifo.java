package simulation;

import java.util.ArrayList;
import java.util.LinkedList;

import cli.PlacedOrder;
import java.util.Queue;

public class Fifo {
	
	private Queue<PlacedOrder> orders;
	private float maxWeight;
	
	public Fifo(ArrayList<PlacedOrder> orders) {
		this.orders = new LinkedList<PlacedOrder>(orders);
		maxWeight = 12 * 16; //12 pounds, with 16 oz per pound
	}
	
	/**
	 * packDrone()
	 * @return an arrayList of PlacedOrders that represents the next
	 * shipment of orders.
	 */
	public ArrayList<PlacedOrder> packDrone() {
		
		ArrayList<PlacedOrder> drone = new ArrayList<PlacedOrder>();
		Boolean isFull = false;
		float currentWeight = 0;
		
		while (!orders.isEmpty() && !isFull) {
			
			if (currentWeight + orders.peek().getWeight() <= maxWeight) {
				
				currentWeight += orders.peek().getWeight();
				drone.add(orders.remove());
				isFull = false;
				
			} else {
				isFull = true;
			}//else
		}//while
		
		return drone;
	}
}
