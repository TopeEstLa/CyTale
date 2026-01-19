package io.squid.cytale;


import io.squid.cytale.entities.Player;

/**
 * @author TopeEstLa
 */
public class CyTaleApplication {


    public void start(String[] args) {
        Player alice = new Player("Alice", 10);
        Player bob = new Player("Bob");
        bob.addScore(5);

        System.out.println(alice.getName() + " has score: " + alice.getScore());
        System.out.println(bob.getName() + " has score: " + bob.getScore());

        Player noName = new Player();
        System.out.println(noName.getName() + " has score: " + noName.getScore());

        System.out.printf("Total players created: %d%n", Player.getPlayerCounter());
    }

}
