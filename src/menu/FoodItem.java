package menu;

public class FoodItem {
	
	//Class variables
	private String name;
	private float weight;
	
	
	public FoodItem() {
		this.name = "";
		this.weight = 0;
	}
	
	/**
	 * FoodItem
	 * @param name the name of the food
	 * @param weight the weight in ounces
	 */
	public FoodItem(String name, float weight) {
		this.name = name;
		this.weight = weight;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}
	
	@Override
	public String toString() {
		return name + ": " + weight + "oz";
	}
}
