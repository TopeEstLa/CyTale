package io.squid.cytale.entities;

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

    private char[][] layout;
    private int length;
    private int width;

    private int playerX;
    private int playerY;

    /**
     * Parsing a level from a file
     * literally reading the file line by line and char by char
     * and storing it in a 2D char array
     * Player position is marked by '1' in the file
     * @param file Path to the level file
     */
    public Level(Path file) {
        List<String> lines;
        try {
            lines = Files.readAllLines(file);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error reading file: " + e.getMessage());
        }

        this.length = lines.size();
        this.width = lines.get(0).length();

        this.layout = new char[this.length][this.width];

        boolean playerFound = false;

        for (int i = 0; i < this.length; i++) {
            String line = lines.get(i);
            for (int j = 0; j < this.width; j++) {
                String charStr = String.valueOf(line.charAt(j));
                if (charStr.equalsIgnoreCase("1")) {
                    this.playerX = j;
                    this.playerY = i;
                    this.layout[i][j] = ' ';
                    playerFound = true;
                } else {
                    this.layout[i][j] = line.charAt(j);
                }
            }
        }

        if (!playerFound) {
            throw new IllegalArgumentException("Player position must be specified in the level file");
        }
    }

    /**
     * Constructor for Level
     * Always throw IllegalArgumentException cause player position must be specified
     *
     * @param layout 2D char array representing the layout
     * @param length length of the layout
     * @param width  width of the layout
     */
    public Level(char[][] layout, int length, int width) {
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
    public Level(char[][] layout, int length, int width, int playerX, int playerY) {
        this.layout = layout;
        this.length = length;
        this.width = width;

        this.playerX = playerX;
        this.playerY = playerY;

        if (playerX < 0 || playerY < 0) {
            throw new IllegalArgumentException("Player position must be non-negative");
        }

        if (playerY >= this.length || playerX >= this.width) {
            throw new IllegalArgumentException("Player position is out of bounds");
        }

        if (layout[playerY][playerX] != ' ') {
            throw new IllegalArgumentException("Player position must be on a blank space");
        }
    }

    /**
     * Moves the player in the specified direction
     *
     * @param direction Direction to move the player
     */
    public void moovePlayer(Direction direction) {
        switch (direction) {
            case TOP:
                if (playerY > 0 && layout[playerY - 1][playerX] == ' ') {
                    playerY--;
                }
                break;
            case BOT:
                if (playerY < length - 1 && layout[playerY + 1][playerX] == ' ') {
                    playerY++;
                }
                break;
            case RIGHT:
                if (playerX < width - 1 && layout[playerY][playerX + 1] == ' ') {
                    playerX++;
                }
                break;
            case LEFT:
                if (playerX > 0 && layout[playerY][playerX - 1] == ' ') {
                    playerX--;
                }
                break;
            default:
                throw new IllegalArgumentException("Invalid direction");
        }
        this.showLayout();
    }

    /**
     * Displays the layout with the player position marked as '1'
     */
    public void showLayout() {
        for (int i = 0; i < this.length; i++) {
            for (int j = 0; j < this.width; j++) {
                if (i == playerY && j == playerX) {
                    System.out.print("1 ");
                } else {
                    System.out.print(this.layout[i][j] + " ");
                }
            }
            System.out.println();
        }
    }
}
