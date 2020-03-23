package simulation;

import java.util.ArrayList;
import java.util.Queue;

public class Fifo {
	
	private int numSims;
	private float avgTime;
	private float worstTime;
	private Queue<Integer> orders;
	private float currWeight;
	private float maxWeight;
	private ArrayList<Integer> drone;
	
	public Fifo(int numSims, Queue<Integer> orders) {
		this.numSims = numSims;
		this.orders = orders;
		currWeight = 0f;
		maxWeight = 12 * 16; //12 pounds, with 16 oz per pound
	}
	
	
	public ArrayList<Results> runSim() {
		Boolean isFull = false;
		while (!orders.isEmpty()) {
			if (currWeight + orders.peek().weight <= maxWeight) {
				drone.add(orders.remove())
				isFull = false;
			} else {
				isFull = true;
			}
			if (isFull) {
				TSP(drone);
			}
		}
	}
}
