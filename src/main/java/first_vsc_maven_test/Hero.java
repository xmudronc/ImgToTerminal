package first_vsc_maven_test;

public final class Hero {
    private int x;
    private int y;

    public Hero(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Hero() {
        this.x = 1;
        this.y = 1;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void setX(int val) {
        this.x = val;
    }

    public void setY(int val) {
        this.y = val;
    }
}