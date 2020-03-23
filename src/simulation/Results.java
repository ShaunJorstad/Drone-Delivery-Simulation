package simulation;

public class Results {

	private float time;
	private int index;
	
	public Results(int index, float time) {
		this.time = time;
		this.index = index;
	}

	public float getTime() {
		return time;
	}

	public int getIndex() {
		return index;
	}
	
}
