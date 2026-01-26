package io.squid.cytale.entities;

import io.squid.cytale.enums.CellType;
import io.squid.cytale.enums.Direction;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Represents a level in the game
 * with a char layout " " for blank and "#" for wall
 * X = right to left
 * Y = top to bottom
 * 0 0 is top left corner
 *
 * @author TopeEstLa
 */
public class Level {

    private LevelCell[][] layout;
    private int length;
    private int width;

    private Player player;
    private Location defaultPlayerLocation;

    /**
     * Parsing a level from a file
     * literally reading the file line by line and char by char
     * and storing it in a 2D char array
     * Player position is marked by '1' in the file
     *
     * @param file Path to the level file
     */
    public Level(Path file, Player player) {
        List<String> lines;
        try {
            lines = Files.readAllLines(file);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error reading file: " + e.getMessage());
        }

        this.length = lines.size();
        this.width = lines.get(0).length();

        this.layout = new LevelCell[this.length][this.width];

        boolean playerFound = false;

        for (int i = 0; i < this.length; i++) {
            String line = lines.get(i);
            for (int j = 0; j < this.width; j++) {
                char charStr = line.charAt(j);
                CellType cellType;
                boolean hasCoin = false;
                if (charStr == '1') {
                    this.defaultPlayerLocation = new Location(j, i);
                    cellType = CellType.FLOOR;
                } else if (charStr == '.') {
                    cellType = CellType.FLOOR;
                    hasCoin = true;
                } else {
                    cellType = CellType.fromSymbol(charStr);
                }

                this.layout[i][j] = new LevelCell(cellType, new Location(j, i), hasCoin);
            }
        }

        if (this.defaultPlayerLocation == null) {
            throw new IllegalArgumentException("Player position not found in level file");
        }

        if (player == null) {
            throw new IllegalArgumentException("Player cannot be null");
        }

        this.player = player;
        this.player.setLocation(this.defaultPlayerLocation.copy());
    }

    /**
     * Constructor for Level
     * Always throw IllegalArgumentException cause player position must be specified
     *
     * @param layout 2D char array representing the layout
     * @param length length of the layout
     * @param width  width of the layout
     */
    public Level(LevelCell[][] layout, int length, int width, Player player) {
        throw new IllegalArgumentException("Player position must be specified");
    }

    /**
     * Constructor for Level
     * Throw IllegalArgumentException if player position is out of bounds or on a wall
     *
     * @param layout  2D char array representing the layout
     * @param length  length of the layout
     * @param width   width of the layout
     * @param playerX player X position
     * @param playerY player Y position
     */
    public Level(LevelCell[][] layout, int length, int width, int playerX, int playerY, Player player) {
        this.layout = layout;
        this.length = length;
        this.width = width;

        if (playerX < 0 || playerY < 0) {
            throw new IllegalArgumentException("Player position must be non-negative");
        }

        if (playerY >= this.length || playerX >= this.width) {
            throw new IllegalArgumentException("Player position is out of bounds");
        }

        this.defaultPlayerLocation = new Location(playerX, playerY);

        if (!layout[playerY][playerX].getType().isWalkable()) {
            throw new IllegalArgumentException("Player position must be walkable");
        }

        if (player == null) {
            throw new IllegalArgumentException("Player cannot be null");
        }

        this.player = player;
        this.player.setLocation(this.defaultPlayerLocation.copy());
    }

    /**
     * Moves the player in the specified direction
     *
     * @param direction Direction to move the player
     */
    public void moovePlayer(Direction direction) {
        int dx = 0;
        int dy = 0;

        switch (direction) {
            case TOP:
                dy = -1;
                break;
            case BOT:
                dy = 1;
                break;
            case LEFT:
                dx = -1;
                break;
            case RIGHT:
                dx = 1;
                break;
            default:
                throw new IllegalArgumentException("Invalid direction");
        }

        int nextX = (this.player.getLocation().getX() + dx % this.width + this.width) % this.width; //dark calculus who some how work
        int nextY = (this.player.getLocation().getY() + dy % this.length + this.length) % this.length;

        LevelCell targetCell = this.layout[nextY][nextX];
        if (!targetCell.getType().isWalkable()) {
            return;
        }

        player.setLocation(new Location(nextX, nextY));

        if (targetCell.hasCoin()) {
            player.addScore(10);
            targetCell.setCoin(false);
        }

        if (targetCell.getType().equals(CellType.TRAP)) {
            player.removeHealth(1);
            player.setLocation(this.defaultPlayerLocation.copy());
        }
    }

    /**
     * Displays the layout with the player position marked as '1'
     */
    public void showLayout() {
        for (int i = 0; i < this.length; i++) {
            for (int j = 0; j < this.width; j++) {
                if (i == this.player.getLocation().getY() && j == this.player.getLocation().getX()) {
                    System.out.print("1 ");
                } else {
                    System.out.print(this.layout[i][j].getSymbol() + " ");
                }
            }
            System.out.println();
        }
    }

    public boolean isCompleted() {
        for (int i = 0; i < this.length; i++) {
            for (int j = 0; j < this.width; j++) {
                if (this.layout[i][j].hasCoin()) {
                    return false;
                }
            }
        }
        return true;
    }
}
