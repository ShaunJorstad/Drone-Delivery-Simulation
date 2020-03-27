package cli;

import menu.Destination;
import menu.FoodItem;
import menu.Meal;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Settings {
    private ArrayList<Integer> orderDistribution;
    private Set<FoodItem> foods;
    private List<Meal> meals;
    private ArrayList<Destination> map;

    public Settings() {
        orderDistribution = new ArrayList<>();
        foods = new HashSet<>();
        meals = new ArrayList<>();
        map = new ArrayList<>();
        globalImport(null);
    }

//    Order Distribution -----------------------------------------------------------------
    public boolean editOrderDistribution(int index, int numOrders) {
        orderDistribution.set(index, numOrders);
        return verifyOrderDistributions();
    }

    public ArrayList<Integer> getOrderDistribution() {
        return orderDistribution;
    }

//    Food Items ------------------------------------------------------------------------
    public boolean addFoodItem(FoodItem fooditem) {
        foods.add(fooditem);
        return verifyFoodItems();
    }

    public boolean editFoodItem(FoodItem old, FoodItem edit) {
        foods.remove(old);
        foods.add(edit);
        return verifyFoodItems();
    }

    public boolean removeFoodItem(FoodItem foodItem) {
        foods.remove(foodItem);
        return !foods.isEmpty();
    }

    public Set<FoodItem> getFoods() {
        return foods;
    }

    public List<Meal> getMeals() {
        return meals;
    }

    public ArrayList<Destination> getMap() {
        return map;
    }

    //    Settings Verification ---------------------------------------------------------
    public boolean verifySettings() {
        boolean verified = true;
        verified = verified && verifyFoodItems();
        verified = verified && verifyMeals();
        verified = verified && verifyOrderDistributions();
        verified = verified && verifyMap();
        verified = verified && verifyDrone();
        return verified;
    }

    public boolean verifyFoodItems() {
        return true;
    }

    public boolean verifyMeals() {
        return true;
    }

    public boolean verifyOrderDistributions() {
        return true;
    }

    public boolean verifyMap() {
        return true;
    }

    public boolean verifyDrone() {
        return true;
    }

//    Settings import / export-------------------------------------------------------
//    TODO: this is probably very wrong

    public void globalImport(String path) {
        if (path == null) {
            foods.add(new FoodItem("Hamburger", 6));
            foods.add(new FoodItem("Fries", 4));
            foods.add(new FoodItem("12 oz. drink", 14));

            orderDistribution.clear();
            orderDistribution.add(15);
            orderDistribution.add(40);
            orderDistribution.add(107);
            orderDistribution.add(13);
        }
    }

    public void globalExport() {
    }

    public void importFoods() {
    }

    public void exportFoods() {
    }

    public void importMeals() {
    }

    public void exportMeals() {
    }

    public void importDistribution() {
    }

    public void exportDistribution() {
    }

    public void importMap() {
    }

    public void exportMap() {
    }

    public void importDrone() {
    }

    public void exportDrone() {
    }
}
