package simulation;

public class Results {

	private float[] times;
	private final float MAXTIME = 15 * 60; //15 minutes * 60 seconds
	
	public Results(float[] times) throws Exception {
		for (int i = 0; i < times.length; i++) {
			if (times[i] > MAXTIME) throw new Exception("Order #" + (i+1) + " was over the max time allowed.");
		}
		this.times = times;
	}

	
	public void export(String url) throws Exception {
		/* TODO: generate csv */
	}
	
	private float getWorstTime() {
		float highest = times[0];
		if (times.length > 1) {
			for (int i = 1; i < times.length; i++) {
				if (times[i] > highest)
					highest = times[i];
			}//for
		}//if
		return highest;
	}
	
	private float getAvgTime() {
		float total = 0f;
		for (float curr : times) {
			total += curr;
		}
		return total / times.length;
	}

	
}
