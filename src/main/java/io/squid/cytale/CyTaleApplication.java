package io.squid.cytale;


import io.squid.cytale.entities.Level;
import io.squid.cytale.entities.Player;
import io.squid.cytale.enums.Direction;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

/**
 * @author TopeEstLa
 */
public class CyTaleApplication {


    public void start(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.printf("Entrer votre pseudo : ");
        String playerName = scanner.nextLine();
        Player player = new Player(playerName);

        if (args.length == 0) {
            System.err.println("Please provide a level file path as an argument.");
            return;
        }

        boolean run = true;

        globalLoop:
        while (run) {
            player.setHealth(5);
            player.setScore(0);

            for (int i = 0; i < args.length; i++) { //preload level ig (?) if player die we literally recompute lmao
                String arg = args[i];
                Path path = Path.of(arg);
                if (!Files.exists(path) || Files.isDirectory(path)) {
                    System.err.printf("The file %s does not exist or is a directory.%n", arg);
                    return;
                }

                Level level = new Level(path, player);
                this.printBoard(level, player);
                boolean levelRunning = true;
                while (levelRunning) {

                    String input = scanner.nextLine();
                    switch (input) {
                        case "z":
                            level.moovePlayer(Direction.TOP);
                            break;
                        case "q":
                            level.moovePlayer(Direction.LEFT);
                            break;
                        case "s":
                            level.moovePlayer(Direction.BOT);
                            break;
                        case "d":
                            level.moovePlayer(Direction.RIGHT);
                            break;
                        case "exit":
                            break globalLoop;
                        default:
                            System.out.println("Invalid input");
                    }

                    this.printBoard(level, player);

                    if (level.isCompleted()) {
                        System.out.println("Level completed!");
                        levelRunning = false;
                    }

                    if (player.isDead()) {
                        System.out.println("You are dead! Game over.");
                        System.out.println("Final score: " + player.getScore());
                        System.out.println("Retry ? (y/n): ");
                        String retryInput = scanner.nextLine();
                        if (retryInput.equalsIgnoreCase("y")) {
                            i = 0;
                            player.setScore(0);
                            player.setHealth(5);
                            continue globalLoop;
                        } else {
                            break globalLoop;
                        }
                    }
                }
            }
            run = false;
        }

        System.out.printf("Merci d'avoir joué %s!%n", player.getName());
    }

    public void printBoard(Level level, Player player) {
        level.showLayout();
        System.out.printf("Score: %d | Health: %d%n", player.getScore(), player.getHealth());
    }
}
