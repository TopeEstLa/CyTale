package io.squid.cytale.entities;

import io.squid.cytale.enums.CellType;
import io.squid.cytale.enums.Direction;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    private final LevelCell[][] layout;
    private final int length;
    private final int width;

    private final List<Entity> entities;
    private final Set<Location> entitiesLocation;

    private final Player player;
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

        this.entities = new ArrayList<>();
        this.entitiesLocation = new HashSet<>();

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
                } else if (charStr == 'R') {
                    Location location = new Location(j, i);
                    Monster monster = new Monster(this, 5, location);
                    this.entities.add(monster);
                    this.entitiesLocation.add(location.copy());
                    cellType = CellType.FLOOR;
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

        this.entities = new ArrayList<>();
        this.entitiesLocation = new HashSet<>();

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

        // nextX = (((x + dx) mod(width)) + width) mod(width)
        int nextX = (this.player.getLocation().getX() + dx % this.width + this.width) % this.width; //dark calculus who some how work
        int nextY = (this.player.getLocation().getY() + dy % this.length + this.length) % this.length;

        LevelCell targetCell = this.layout[nextY][nextX];
        Location loca = new Location(nextX, nextY);
        if (targetCell.getType().isWalkable()) {
            player.setLocation(loca.copy());

            if (targetCell.hasCoin()) {
                player.addScore(10);
                targetCell.setCoin(false);
            }

            if (targetCell.getType().equals(CellType.TRAP)) {
                this.playerDamage();
            }

            if (this.entitiesLocation.contains(loca)) {
                List<Entity> targetEntities = this.getEntitiesAt(loca);
                for (Entity entity : targetEntities) {
                    entity.interact(this.player);
                }
            }
        }

        this.tickLevel();
    }

    public void playerDamage() {
        player.removeHealth(1);
        player.setLocation(this.defaultPlayerLocation.copy());
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
                    Location loc = new Location(j, i);
                    if (this.entitiesLocation.contains(loc)) {
                        List<Entity> locationEntity = this.getEntitiesAt(loc);
                        if (!locationEntity.isEmpty()) {
                            System.out.print(locationEntity.get(0).getSymbol() + " ");
                            continue;
                        }
                    }
                    System.out.print(this.layout[i][j].getSymbol() + " ");
                }
            }
            System.out.println();
        }
    }

    public void tickLevel() {
        this.entitiesLocation.clear();
        for (Entity entity : this.entities) {
            entity.tick();
            this.entitiesLocation.add(entity.getLocation().copy());
        }
    }

    /**
     * Checks if the level is completed (no coins left)
     *
     * @return true if completed, false otherwise
     */
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

    public List<Entity> getEntitiesAt(Location location) {
        List<Entity> foundEntities = new ArrayList<>();
        for (Entity entity : this.entities) {
            if (entity.getLocation().equals(location)) {
                foundEntities.add(entity);
            }
        }
        return foundEntities;
    }

    public LevelCell[][] getLayout() {
        return layout;
    }

    public int getLength() {
        return length;
    }

    public int getWidth() {
        return width;
    }

    public Player getPlayer() {
        return player;
    }

    public Location getDefaultPlayerLocation() {
        return defaultPlayerLocation;
    }
}
