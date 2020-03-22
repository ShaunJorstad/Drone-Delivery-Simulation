package menu;


/**
 * Stores the name, location and distance from food source for a destination in a map
 */
public class Destination {
    private String destName; //Name of destination
    private int x, y; //x and y coordinates of the destination
    private double dist; //The distance from the food source

    /**
     * Constructor for destination. Auto-calculates the distance
     * @param destName Name of destination
     * @param x x coordinate of the destination
     * @param y y coordinate of the destination
     */
    public Destination(String destName, int x, int y) {
        this.destName = destName;
        this.x = x;
        this.y = y;
        setDist();
    }

    /**
     * Constructor for destination
     * @param destName Name of destination
     * @param x x coordinate of the destination
     * @param y y coordinate of the destination
     * @param dist The distance from the food source
     */
    public Destination(String destName, int x, int y, double dist) {
        this.destName = destName;
        this.x = x;
        this.y = y;
        this.dist = dist;
    }

    public String getDestName() {
        return destName;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public double getDist() {
        return dist;
    }

    public void setDestName(String destName) {
        this.destName = destName;
    }

    //Auto-calculates the new distance
    public void setX(int x) {
        this.x = x;
        setDist();
    }

    //Auto-calculates the new distance
    public void setY(int y) {
        this.y = y;
        setDist();
    }

    //finds the new distance
    private void setDist() {
        dist = Math.sqrt(x*x + y*y);
    }

    public double distanceBetween(Destination d) {
        return Math.sqrt((x-d.getX())*(x-d.getX()) + (y-d.getY())*(y-d.getY()));
    }

    @Override
    public String toString() {
        return destName + "\t" + x + "\t" + y + "\t" + dist;
    }
}
