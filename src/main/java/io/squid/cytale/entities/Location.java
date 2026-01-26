package io.squid.cytale.entities;

/**
 * @author TopeEstLa
 */
public class Location {

    private int x;
    private int y;

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void translate(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }

    public int getX() { return x; }
    public int getY() { return y; }

    public Location copy() {
        return new Location(x, y);
    }
}
