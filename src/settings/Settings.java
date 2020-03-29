package settings;

import cli.PlacedOrder;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import menu.Destination;
import menu.FoodItem;
import menu.Meal;

import java.io.*;
import java.util.*;

public class Settings {

    private static ArrayList<Integer> orderDistribution;
    private static ArrayList<Destination> map;
    private static Set<FoodItem> foods;
    private static List<Meal> meals;
    private File settingsFile;
    private final String defaultSettingsPath = "settings.txt";
    static final FileChooser fileChooser = new FileChooser();
    private static Settings instance = new Settings();

    private Settings() {
        map = new ArrayList<>();
        foods = new HashSet<>();
        meals = new ArrayList<>();
        orderDistribution = new ArrayList<>();

        // imports the default settings
        parseSettings(new File(defaultSettingsPath));
    }

    public static Settings getInstance() {
        return instance;
    }

    // Getters ------------------------------------------------------------------
    public static Set<FoodItem> getFoods() {
        return foods;
    }

    public static List<Meal> getMeals() {
        return meals;
    }

    public static ArrayList<Integer> getOrderDistribution() {
        return orderDistribution;
    }

    // Food Items----------------------------------------------------------------

    /**
     * Adds the provided food item
     *
     * @param fooditem new food item
     * @return true if settings are still valid
     */
    public static boolean addFoodItem(FoodItem fooditem) {
        foods.add(fooditem);
        return verifyFoodItems();
    }

    /**
     * Edits food item by removing the old object and adding the new object.
     *
     * @param old  FoodItem being removed
     * @param edit FoodItem being added
     * @return true if foodItems are valid. false otherwise
     */
    public static boolean editFoodItem(FoodItem old, FoodItem edit) {
        foods.remove(old);
        foods.add(edit);
        return verifyFoodItems();
    }

    /**
     * removes foodItem
     *
     * @param foodItem object being removed
     * @return true if settings are still valid. false otherwise
     */
    public static boolean removeFoodItem(FoodItem foodItem) {
        foods.remove(foodItem);
        return !foods.isEmpty();
    }

    /**
     * TODO: finish this method
     * checks foodItems for correctness
     *
     * @return true if settings are valid. false otherwise
     */
    private static boolean verifyFoodItems() {
        return true;
    }

    // Meal Items----------------------------------------------------------------
    /** TODO: write this method
     * adds provided meal
     * @param meal meal being added
     * @return true if meals are all valid
     */
    private boolean addMeal(Meal meal) {return true;}

    /** TODO: write this method
     * edits a meal by removing the old and adding the edited meal object
     * @param oldMeal old meal being removed
     * @param newMeal new meal being added
     * @return true if meals are all valid
     */
    private boolean editMeal(Meal oldMeal, Meal newMeal) {return true;}

    /** TODO: write this method
     * removes meal.
     * @param meal meal being removed
     * @return true if meal settings are valid
     */
    private boolean removeMeal(Meal meal) {return true;}

    /** TODO: write this method
     * checks meal settings for a valid simulation
     * @return true if meal settings are correct
     */
    private boolean verify() {return true;}

    // Order Distributions ------------------------------------------------------

    /** TODO: write this method
     * edits the distribution of meals
     * @param index index of distribution
     * @param distribution number of meals to be generated in that hour
     * @return true if valid settings
     */
    private boolean editDistribution(int index, int distribution) {return true;}

    /** TODO: write this method
     * verifies the order distribution
     * @return true if valid settings
     */
    private boolean verifyDistribution() {return true;}

    // Map ----------------------------------------------------------------------

    /** TODO: write this method
     * adds map point
     * @param x
     * @param y
     * @param dist
     */
    private void addMapPoint(int x, int y, float dist) {

    }

    /** TODO: write ths method
     * removes map point
     * @param x
     * @param y
     */
    private void removeMapPoint(int x, int y) {

    }

    /** TODO: write this method
     * edits map point
     * @param x
     * @param y
     * @param dist
     */
    private void editMapPoint(int x, int y, float dist) {

    }

    /** TODO: write this method
     * exports map
     */
    private void exportMap() {

    }

    /** TODO: write this method
     * imports map
     */
    private void importMap() {

    }

    /** TODO: write this method
     * verifies the map
     * @return
     */
    private boolean verifyMap() {

        return false;
    }

    // IO -----------------------------------------------------------------------
    /** TODO: write method
     * parses settings and returns string representation of the object to be printed to a file
     * @return string representation of object
     */
    private static String unparseSettings() {return "";}

    private static boolean parseSettings(File settingsFile) {
        try {
            //Seed the arraylist with names in the file
            Scanner s = new Scanner(settingsFile);
            while (s.hasNext()) {
                String next = s.nextLine();
                if (next.contains("<d>")) {
                    //next.substring(next.indexOf(" "),next.length());
                    //Destinations.add();
                    String name;
                    int x, y;
                    double dist;

                    //For each line in the file
                    while (s.hasNextLine()) {
                        Scanner line = new Scanner(s.nextLine()); //Scanner for the given line

                        //Get the attributes from the file
                        name = line.next();
                        x = line.nextInt();
                        y = line.nextInt();
                        dist = line.nextDouble();

                        //Create a new destination
                        map.add(new Destination(name, x, y, dist));
                        line.close();
                    }
                }
            }
            s.close();
            return true;

        } catch (Exception e) {
            System.out.println((e.getMessage()));
            return false;
        }
    }

    /**
     * opens the file browser and returns the file chosen by the user
     * @param stage current stage of the application
     * @return true if imported file. false otherwise
     */
    public static boolean importSettings(Stage stage) {
        fileChooser.setTitle("Import Settings");
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            // TODO: call method to parse the file and import into settings
            return parseSettings(file);
        }
        return false;
    }

    /**
     * exports the current settings to the chosen file by the user
     * @param stage current stage of the application
     * @return true if saved file. false otherwise
     */
    public static boolean exportSettings(Stage stage) {
        fileChooser.setTitle("Export Settings");
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            //TODO: pipe this string into the file
            unparseSettings();
            return true;
        }
        return false;
    }
}
