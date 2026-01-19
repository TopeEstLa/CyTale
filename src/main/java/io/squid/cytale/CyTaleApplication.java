package io.squid.cytale;


import io.squid.cytale.entities.Level;
import io.squid.cytale.entities.Player;
import io.squid.cytale.enums.Direction;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

/**
 * @author TopeEstLa
 */
public class CyTaleApplication {


    public void start(String[] args) {
        String path = args[0];
        Path filePath = Path.of(path);
        try {
            if (!Files.exists(filePath)) {
                System.err.println("File not found: " + path);
                return;
            }
        } catch (Exception e) {
            System.err.println("Error checking file: " + e.getMessage());
            return;
        }

        File file = filePath.toFile();

        char[][] layout = {
                {'#', ' ', '#', '#', '#', '#', '#', '#', '#', '#'},
                {' ', ' ', ' ', ' ', '#', ' ', ' ', ' ', ' ', '#'},
                {'#', ' ', '#', ' ', '#', ' ', '#', '#', ' ', '#'},
                {'#', ' ', '#', ' ', ' ', ' ', ' ', '#', ' ', '#'},
                {'#', ' ', '#', '#', '#', '#', ' ', '#', ' ', '#'},
                {'#', ' ', ' ', ' ', ' ', '#', ' ', '#', ' ', '#'},
                {'#', '#', '#', '#', ' ', '#', ' ', '#', ' ', '#'},
                {'#', ' ', ' ', '#', ' ', ' ', ' ', ' ', ' ', ' '},
                {'#', ' ', '#', '#', '#', '#', '#', '#', ' ', '#'},
                {'#', '#', '#', '#', '#', '#', '#', '#', ' ', '#'},
        };

        boolean run = true;
        Level level = new Level(layout, 10, 10, 1, 1);
        Scanner scanner = new Scanner(System.in);
        level.showLayout();
        while (run) {

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
                    run = false;
                    break;
                default:
                    System.out.println("Invalid input");
            }
            //wait for zqsd entry
            level.showLayout();
        }
    }

}
