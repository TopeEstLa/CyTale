package io.squid.cytale.enums;

/**
 * Represents different types of cells in the game level
 * @author TopeEstLa
 */
public enum CellType {

    WALL('#'),
    FLOOR(' ', true),
    DOOR('D'),
    TRAP('*', true),
    ;

    private final char symbol;
    private final boolean walkable;

    CellType(char symbol) {
        this(symbol, false);
    }

    CellType(char symbol, boolean walkable) {
        this.symbol = symbol;
        this.walkable = walkable;
    }

    public char getSymbol() {
        return symbol;
    }

    public boolean isWalkable() {
        return walkable;
    }

    public static CellType fromSymbol(char symbol) {
        for (CellType type : values()) {
            if (type.getSymbol() == symbol) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown cell type symbol: " + symbol);
    }

}
