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
        this.name = name;
        this.score = score;
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
