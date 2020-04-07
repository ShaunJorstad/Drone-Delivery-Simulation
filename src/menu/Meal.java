package menu;

import simulation.Settings;

import java.util.*;
import java.util.Map.Entry;

public class Meal {

    //Class variables
    private Map<FoodItem, Integer> foodItems;    //to store items in meal with
    private String name;                        //name of meal for user
    private int id;                                //identifier
    private float weight;                        //total weight
    private float distribution;                    //meal distribution, [0,1]


    /**
     * Meal - create a new meal
     *
     * @param id specify id
     */
    public Meal(int id) {
        foodItems = new HashMap<FoodItem, Integer>();
        name = "";
        weight = 0;
        this.id = id;
        distribution = 0;
    }

    /**
     * Meal - create a new meal
     *
     * @param name specify meal name
     * @param id   specify id
     */
    public Meal(String name, int id) {
        foodItems = new HashMap<FoodItem, Integer>();
        this.name = name;
        this.id = id;
        weight = 0;
        distribution = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getDistribution() {
        return distribution;
    }

    public float getWeight() {
        return weight;
    }

    public Map<FoodItem, Integer> getFoodItems() {
        return foodItems;
    }

    /**
     * Sets the distribution of the meal
     *
     * @param distribution a float percentage
     * @throws IllegalArgumentException if distribution is not [0,1]
     */
    public void setDistribution(float distribution) throws IllegalArgumentException {
        if (distribution < 0)
            throw new IllegalArgumentException("Distribution must be non-negative!");
        if (distribution > 1)
            throw new IllegalArgumentException("Distribution must be no greater than 1!");
        this.distribution = distribution;
    }

    /**
     * Adds the
     *
     * @param foodItem food to the meal
     */
    public void incrementFoodItem(FoodItem foodItem) {
        foodItems.put(foodItem, foodItems.getOrDefault(foodItem, 0) + 1);
        weight += foodItem.getWeight();
    }

    /**
     * Adds num foodItems to the meal
     *
     * @param foodItem
     * @param num
     */
    public void incrementFoodItem(FoodItem foodItem, int num) {
        foodItems.put(foodItem, foodItems.getOrDefault(foodItem, 0) + num);
        weight += (foodItem.getWeight() * num);
    }

    /**
     * Removes num foodItems from meal
     *
     * @param foodItem
     * @param num
     * @throws IllegalArgumentException if num > number in meal
     */
    public void decrementFoodItem(FoodItem foodItem, int num) {
        if (foodItems.getOrDefault(foodItem, 0) < num)
            return;
        foodItems.put(foodItem, foodItems.get(foodItem) - num);
        weight -= (foodItem.getWeight() * num);
    }

    public List<FoodItem> getOutstandingFoodItems() {
        List<FoodItem> outstandingFoods = new ArrayList<>();
        for (FoodItem item : Settings.getFoods()) {
            if (!foodItems.containsKey(item)) {
                outstandingFoods.add(item);
            }
        }
        return outstandingFoods;
    }

    public void removeFoodItem(FoodItem food) {
        if (!foodItems.containsKey(food)) {
            return;
        }
        weight -= food.getWeight() * foodItems.get(food);
        foodItems.remove(food);
    }

    public void replaceFoodItem(FoodItem old, FoodItem change) {
        int num = foodItems.get(old);
        weight -= old.getWeight() * num;
        foodItems.remove(old);
        foodItems.put(change, num);
        weight += change.getWeight() * num;
    }

    public int getNumberOfFood(FoodItem food) {
        if (!foodItems.containsKey(food)) {
            return 0;
        }
        return foodItems.get(food);
    }

    public void calcWeight() {
        weight = 0;
        foodItems.forEach((key, value) -> {
            weight += key.getWeight() * value;
        });
    }

    @Override
    public String toString() {
        String out = name + ":\n";
        Iterator<Entry<FoodItem, Integer>> iter = foodItems.entrySet().iterator();
        while (iter.hasNext()) {
            Entry<FoodItem, Integer> entry = iter.next();
            out += "\t" + entry.getKey().getName() + ": " + entry.getValue() + "\n";
        }
        return out;
    }


}
