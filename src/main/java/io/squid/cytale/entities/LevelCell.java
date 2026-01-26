package io.squid.cytale.entities;

import io.squid.cytale.enums.CellType;

/**
 * @author TopeEstLa
 */
public class LevelCell {

    private final CellType type;
    private final Location location;

    private boolean coin;

    public LevelCell(CellType type, Location location) {
        this(type, location, false);
    }

    public LevelCell(CellType type, Location location, boolean coin) {
        this.type = type;
        this.location = location;
        this.coin = coin;
    }


    public char getSymbol() {
        return coin ? 'C' : type.getSymbol();
    }

    public CellType getType() {
        return type;
    }

    public Location getLocation() {
        return location;
    }

    public boolean hasCoin() {
        return coin;
    }

    public void setCoin(boolean coin) {
        this.coin = coin;
    }
}
