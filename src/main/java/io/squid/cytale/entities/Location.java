package io.squid.cytale.entities;

/**
 * Represents a location in 2D space
 * X = right to left
 * Y = top to bottom
 * 0 0 is top left corner
 * @author TopeEstLa
 */
public class Location {

    private int x;
    private int y;

    /**
     * Constructor for Location
     * @param x
     * @param y
     */
    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Translates the location by dx and dy
     * @param dx
     * @param dy
     */
    public void translate(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }

    /**
     * Gets the X coordinate
     * @return X coordinate
     */
    public int getX() { return x; }

    /**
     * Gets the Y coordinate
     * @return Y coordinate
     */
    public int getY() { return y; }

    /**
     * Creates a copy of the location
     * @return New Location object with same coordinates
     */
    public Location copy() {
        return new Location(x, y);
    }
}
