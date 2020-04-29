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

    public Destination(Destination other) {
        this.destName = other.getDestName();
        this.x = other.getX();
        this.y = other.getY();
        this.dist = other.getDist();
        distToTravelTo = other.getDistToTravelTo();
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

    public Coordinate getCoordinates() {
        return new Coordinate(x, y);
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

    public void setDistToTravelTo(double dist) {
        distToTravelTo = dist;
    }

    public double getDistToTravelTo() {
        return distToTravelTo;
    }


    @Override
    public String toString() {
        return destName + "\t" + x + "\t" + y + "\t" + dist;
    }

    public boolean equals(Destination destination) {
        return this.destName == destination.getDestName();
    }
}
