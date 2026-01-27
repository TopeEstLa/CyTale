package io.squid.cytale.entities;

/**
 * @author TopeEstLa
 */
public interface Entity {

    void tick();

    void interact(Player player);

    char getSymbol();

    Location getLocation();

}
