package menu;



public class Destination {
    String destName;
    int x, y;
    double dist;


    public Destination(String destName, int x, int y) {
        this.destName = destName;
        this.x = x;
        this.y = y;
        setDist();
    }


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

    public void setX(int x) {
        this.x = x;
        setDist();
    }

    public void setY(int y) {
        this.y = y;
        setDist();
    }

    private void setDist() {
        dist = Math.sqrt(x*x + y*y);
    }






}
