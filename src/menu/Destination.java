package menu;


import cli.Coordinate;

/**
 * Stores the name, location and distance from food source for a destination in a map
 */
public class Destination {
    private String destName; //Name of destination
    private int x, y; //x and y coordinates of the destination
    private double dist; //The distance from the food source

    //The distance the drone needs to travel to in order to get to the next destination
    private double distToTravelTo;

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
        distToTravelTo = -1;
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
        distToTravelTo = -1;
    }

    /**
     * Copy constructor
     * @param other
     */
    public Destination(Destination other) {
        this.destName = other.getDestName();
        this.x = other.getX();
        this.y = other.getY();
        this.dist = other.getDist();
        distToTravelTo = other.getDistToTravelTo();
    }

    /**
     * Get the name of the destination
     * @return
     */
    public String getDestName() {
        return destName;
    }

    /**
     * Get the x location for the destination
     * @return
     */
    public int getX() {
        return x;
    }

    /**
     * Get the y location for the destination
     * @return
     */
    public int getY() {
        return y;
    }

    /**
     * Get the distance from home for the destination
     * @return
     */
    public double getDist() {
        return dist;
    }

    /**
     * Get the coordinates of the destination
     * @return
     */
    public Coordinate getCoordinates() {
        return new Coordinate(x, y);
    }

    /**
     * Set the coordinates of the destination (Auto-calculates the new distance)
     * @param other
     */
    public void setCoordinates(Coordinate other) {
        x = (int)other.getX();
        y = (int)other.getY();
        setDist();
    }


    /**
     * Sets the x value of the destination (Auto-calculates the new distance)
     * @param x
     */
    public void setX(int x) {
        this.x = x;
        setDist();
    }

    /**
     * Sets the y value of the destination (Auto-calculates the new distance)
     * @param y
     */
    public void setY(int y) {
        this.y = y;
        setDist();
    }

    /**
     * Sets the distance from home for the destination
     */
    private void setDist() {
        dist = Math.sqrt(x*x + y*y);
    }

    /**
     * Find the distance between two destinations
     * @param d
     * @return
     */
    public double distanceBetween(Destination d) {
        return Math.sqrt((x-d.getX())*(x-d.getX()) + (y-d.getY())*(y-d.getY()));
    }

    /**
     * Set the distance it takes to travel to the next destination
     * @param dist
     */
    public void setDistToTravelTo(double dist) {
        distToTravelTo = dist;
    }

    /**
     * Get the distance it takes to travel to the next destination
     * @return
     */
    public double getDistToTravelTo() {
        return distToTravelTo;
    }


    @Override
    public String toString() {
        return destName + "\t" + x + "\t" + y + "\t" + dist;
    }

    /**
     * Finds if two destiantion are equal by comparing names
     * @param destination
     * @return
     */
    public boolean equals(Destination destination) {
        return this.destName == destination.getDestName();
    }
}
