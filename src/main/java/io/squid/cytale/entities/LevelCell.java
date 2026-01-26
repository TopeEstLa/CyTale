package io.squid.cytale.entities;

import io.squid.cytale.enums.CellType;

/**
 * Represents a cell in the level
 * with a type and location
 * coin indicates if there is a coin in the cell
 * @author TopeEstLa
 */
public class LevelCell {

    private final CellType type;
    private final Location location;

    private boolean coin;

    /**
     * Constructor for LevelCell
     * @param type Cell type
     * @param location Cell location
     */
    public LevelCell(CellType type, Location location) {
        this(type, location, false);
    }

    /**
     * Constructor for LevelCell
     * @param type Cell type
     * @param location Cell location
     * @param coin Indicates if there is a coin in the cell
     */
    public LevelCell(CellType type, Location location, boolean coin) {
        this.type = type;
        this.location = location;
        this.coin = coin;
    }

    /**
     * Gets the symbol representing the cell
     * @return Character symbol
     */
    public char getSymbol() {
        return coin ? 'C' : type.getSymbol();
    }

    /**
     * Gets the cell type
     * @return CellType
     */
    public CellType getType() {
        return type;
    }

    /**
     * Gets the cell location
     * @return Location
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Checks if the cell has a coin
     * @return True if there is a coin, false otherwise
     */
    public boolean hasCoin() {
        return coin;
    }

    /**
     * Sets the coin status of the cell
     * @param coin New coin status
     */
    public void setCoin(boolean coin) {
        this.coin = coin;
    }
}
