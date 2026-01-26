package io.squid.cytale.entities;

/**
 * Represents a player in the game
 * with a name and score
 * @author TopeEstLa
 */
public class Player {

    private static int playerCounter = 0;

    private final String name;
    private int score;
    private int health;

    private Location location;

    /**
     * Default constructor for Player
     * Name is "Player" + playerCounter
     * Initializes score to 0
     */
    public Player() {
        this("Player" + playerCounter + 1);
    }

    /**
     * Constructor for Player
     * @param name Name of the player
     * Initializes score to 0
     */
    public Player(String name) {
        this(name, 0);
    }

    /**
     * Constructor for Player
     * @param name Name of the player
     * @param score Initial score of the player
     */
    public Player(String name, int score) {
        this(name, score, 5);
    }

    /**
     * Constructor for Player
     * @param name Name of the player
     * @param score Initial score of the player
     * @param health Initial health of the player
     */
    public Player(String name, int score, int health) {
        this.name = name;
        this.score = score;
        this.health = health;
        this.location = new Location(0, 0);
        playerCounter++;
    }

    /**
     * Adds points to the player's score
     * @param points Points to add
     */
    public void addScore(int points) {
        this.score += points;
    }

    /**
     * Removes points from the player's score
     * 0 if the score would go below 0
     * @param points Points to remove
     */
    public void removeScore(int points) {
        if (this.score - points < 0) {
            this.score = 0;
            return;
        }

        this.score -= points;
    }

    /**
     * Sets the player's score to a specific value
     * @param score New score value
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Removes health from the player
     * 0 if health would go below 0
     * @param damage Amount of health to remove
     */
    public void removeHealth(int damage) {
        this.health -= damage;
        if (this.health < 0) {
            this.health = 0;
        }
    }

    /**
     * Sets the player's health to a specific value
     * @param health New health value
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * Sets the player's location
     * @param location New location of the player
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * Gets the name of the player
     * @return Name of the player
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the score of the player
     * @return Score of the player
     */
    public int getScore() {
        return score;
    }

    /**
     * Gets the health of the player
     * @return Health of the player
     */
    public int getHealth() {
        return health;
    }

    /**
     * Checks if the player is dead (health <= 0)
     * @return true if the player is dead, false otherwise
     */
    public boolean isDead() {
        return this.health <= 0;
    }

    /**
     * Gets the location of the player
     * @return Location of the player
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Gets the total number of players created
     * @return Total number of players created
     */
    public static int getPlayerCounter() {
        return playerCounter;
    }

    /**
     * Checks equality based on player name (case insensitive)
     * @param obj the reference object with which to compare  .
     * @return true if this object is the same as the obj argument; false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Player target)) return false;

        return target.getName().equalsIgnoreCase(this.name);
    }

    /**
     * String representation of the player
     * format : "Player#name : Plater#score pt(s)"
     * @return String representation of the player
     */
    @Override
    public String toString() {
        return this.name + " : " + this.score + " pt" + (this.score > 1 ? "s" : "");
    }
}
