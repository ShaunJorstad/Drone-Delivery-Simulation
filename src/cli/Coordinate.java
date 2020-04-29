package cli;

public class Coordinate {
    private int x;
    private int y;
    private boolean isFirst;

    public Coordinate() {
        x = 0;
        y = 0;
        isFirst = false;
    }

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Coordinate(int x, int y, boolean isFirst) {
        this.x = x;
        this.y = y;
        this.isFirst = isFirst;
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

    public int getX() {
        return x;
    }

    public int getY() {
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
}
