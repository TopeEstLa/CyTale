package io.squid.cytale.entities;

import io.squid.cytale.enums.CellType;

import java.util.Objects;

/**
 * @author TopeEstLa
 */
public class Monster implements Entity {

    private static int monsterCount = 0;

    private final Level level;
    private final String name;
    private int health;

    private Location location;

    public Monster(Level level, int health, Location location) {
        this(level, "MONSTER" + monsterCount, health, location);
    }

    public Monster(Level level, String name, int health, Location location) {
        this.level = level;
        this.name = name;
        this.health = health;
        this.location = location;
        monsterCount++;
    }


    @Override
    public void tick() {
        int dx = 0;
        int dy = 0;

        int choice = (int) (Math.random() * 4);
        switch (choice) {
            case 0 -> dx = -1;
            case 1 -> dx = 1;
            case 2 -> dy = -1;
            case 3 -> dy = 1;
        }

        int nextX = (this.location.getX() + dx % this.level.getWidth() + this.level.getWidth()) % this.level.getWidth();
        int nextY = (this.location.getY() + dy % this.level.getLength() + this.level.getLength()) % this.level.getLength();

        LevelCell cell = this.level.getLayout()[nextY][nextX];
        if (isWalkable(cell.getType())) {
            this.location = new Location(nextX, nextY);
        }
    }

    @Override
    public void interact(Player player) {
        this.level.playerDamage();
        System.out.printf("%s attacked Player! Player health is now %d%n", this.name, player.getHealth());
    }

    public boolean isWalkable(CellType cellType) {
        return cellType.isWalkable() && !(cellType.equals(CellType.TRAP));
    }

    public void receiveDamage(int damage) {
        this.health -= damage;
    }

    public void setHealth(int health) {
        this.health = health;
    }


    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public char getSymbol() {
        return 'R';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Monster monster)) return false;
        return health == monster.health && Objects.equals(name, monster.name) && Objects.equals(location, monster.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, health, location);
    }
}
