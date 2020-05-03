package cli;

/**
 * Stores x, y coordinates
 */
public class Coordinate {
    private double x; //x location
    private double y; //y location
    private boolean isFirst; //If the coordinate is the first coordinate (aka the home coordinate)

    /**
     * Default constructor
     */
    public Coordinate() {
        x = 0;
        y = 0;
        isFirst = false;
    }

    /**
     * Constructor
     * @param x
     * @param y
     */
    public Coordinate(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Copy constructor
     * @param other
     */
    public Coordinate(Coordinate other) {
        this.x = other.x;
        this.y = other.y;
        this.isFirst = other.isFirst;
    }


    /**
     * Set the x coordinate
     * @param x
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Set the y coordinate
     * @param y
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Set whether this coordinate is the first coordinate (aka the home coordinate)
     * @param first
     */
    public void setFirst(boolean first) {
        isFirst = first;
    }

    /**
     * Get the x coordinate
     * @return
     */
    public double getX() {
        return x;
    }

    /**
     * Get the y coordinate
     * @return
     */
    public double getY() {
        return y;
    }

    /**
     * Get whether this coordinate is the first coordinate (aka the home coordinate)
     * @return
     */
    public boolean isFirst() {
        return isFirst;
    }

    /**
     * Finds the distance between two coordinates
     * @param coordinate
     * @return
     */
    public double distanceBetween(Coordinate coordinate) {
        return Math.sqrt((x - coordinate.getX())* (x - coordinate.getX()) + (y - coordinate.getY()) * (y - coordinate.getY()));
    }

    @Override
    public String toString() {
        return "Coordinate{" +
                "x=" + x +
                ", y=" + y +
                ", isFirst=" + isFirst +
                '}';
    }

    /**
     * Add the other coordinate to this coordinate
     * @param other
     */
    public void add(Coordinate other) {
        this.x += other.x;
        this.y += other.y;
        return;
    }

    /**
     * Subtract the other coordinate and return a new coordinate
     * @param other
     * @return Coordinate which is the result of the subtraction
     */
    public Coordinate subtract(Coordinate other) {
        Coordinate newCoordinate = new Coordinate(this.x-other.x, this.y - other.y);
        return newCoordinate;
    }

}
