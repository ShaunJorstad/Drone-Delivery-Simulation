package cli;

public class Coordinate {
    private double x;
    private double y;
    private boolean isFirst;

    public Coordinate() {
        x = 0;
        y = 0;
        isFirst = false;
    }

    public Coordinate(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Coordinate(int x, int y, boolean isFirst) {
        this.x = x;
        this.y = y;
        this.isFirst = isFirst;
    }

    public Coordinate(Coordinate other) {
        this.x = other.x;
        this.y = other.y;
        this.isFirst = other.isFirst;
    }


    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setFirst(boolean first) {
        isFirst = first;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public boolean isFirst() {
        return isFirst;
    }

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
/*
    public boolean equals(Coordinate other) {
        if (x == other.getX() && y == other.getY()) {
            return true;
        } else {
            return false;
        }
    }*/

    public void add(Coordinate other) {
        this.x += other.x;
        this.y += other.y;
        return;
    }

    public Coordinate subtract(Coordinate other) {
        Coordinate newCoordinate = new Coordinate(this.x-other.x, this.y - other.y);
        return newCoordinate;
    }

}
