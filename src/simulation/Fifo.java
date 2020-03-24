package simulation;

import java.util.ArrayList;
import cli.PlacedOrder;
import java.util.Queue;

public class Fifo {
	
	private int numSims;
	private float[] times;
	private Queue<PlacedOrder> orders;
	private float currWeight;
	private float maxWeight;
	private ArrayList<PlacedOrder> drone;
	
	public Fifo(int numSims, Queue<PlacedOrder> orders) {
		this.numSims = numSims;
		this.orders = orders;
		times = new float[numSims];
		currWeight = 0f;
		maxWeight = 12 * 16; //12 pounds, with 16 oz per pound
	}
	
	/* TODO: Finish runSim
	public ArrayList<Results> runSim() {
		Boolean isFull = false;
		float currentTime = 0;
		while (!orders.isEmpty()) {
			if (currWeight + orders.peek().weight <= maxWeight) {
				drone.add(orders.remove());
				isFull = false;
			} else {
				isFull = true;
			}
			if (isFull) {
			//	TSP(drone);
				
			}
		}
	}*/
}
