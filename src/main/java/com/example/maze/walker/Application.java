package com.example.maze.walker;

import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.IOException;
import java.io.Reader;
import java.util.Map;
import java.util.Scanner;

public class Application {
    public static final int WALL_TILE = 8;
    public static final int EMPTY_TILE = 0;
    public static final int CHARACTER_TILE = 1;

    private static Map<Integer, Character> mapping = Map.of(
            CHARACTER_TILE, '@',
            WALL_TILE, '†',
            EMPTY_TILE, ' '
    );

    public static void main(String[] args) {
        boolean correctChoice = false;
        MazeGenerator mazeGenerator = null;
        do {
            System.out.println("Choose maze implementation:");
            System.out.println("1. DFS");
            System.out.println("2. DFSv2");
            String choice = new Scanner(System.in).nextLine();
            switch (choice) {
                case "1" -> {
                    mazeGenerator = new DfsMazeGenerator();
                    correctChoice = true;
                }
                case "2" -> {
                    mazeGenerator = new DfsMazeGeneratorV2();
                    correctChoice = true;
                }
                default -> System.out.println("Incorrect choice. Repeat please");
            }
        } while (!correctChoice);
        int playerPosX = 0;
        int playerPosY = 0;

        clearScreen();
        Integer[][] core = mazeGenerator.generate(20, 80);
        core[0][0] = 1;
//        resetCore(core, playerPosY, playerPosX);
        showCore(core);

        while (conditionWin(core, playerPosY, playerPosX) == false) {
            char directionKey = nextDirectionKey();
            int[] currentDirection = direction(directionKey);
            int x = currentDirection[0];
            int y = currentDirection[1];

            if (!(playerPosY + y < 0 || playerPosX + x < 0 || playerPosY + y >= core.length || playerPosX + x >= core[0].length)) {
                if (core[playerPosY + y][playerPosX + x] < 1) {
                    core[playerPosY][playerPosX] = EMPTY_TILE;
                    core[playerPosY + y][playerPosX + x] = CHARACTER_TILE;

                    playerPosY += y;
                    playerPosX += x;
                }
            }
            clearScreen();
            showCore(core);
            System.out.println();
        }
        clearScreen();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("YOU WIN");
    }

    private static char nextDirectionKey() {
        try (Terminal terminal = TerminalBuilder.terminal()) {
            terminal.enterRawMode();
            try (Reader reader = terminal.reader()) {
                return switch (reader.read()) {
                    case 119 -> 'w';
                    case 100 -> 'd';
                    case 97 -> 'a';
                    case 115 -> 's';
                    default -> ' ';
                };
            } catch (IOException e) {
                return ' ';
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static int[] direction(char letter) {
        int x = 0;
        int y = 0;
        switch (letter) {
            case 'W':
            case 'w':
                y = -1;
                break;
            case 'D':
            case 'd':
                x = 1;
                break;
            case 'S':
            case 's':
                y = 1;
                break;
            case 'A':
            case 'a':
                x = -1;
                break;
        }
        int[] result = new int[2];
        result[0] = x;
        result[1] = y;
        return result;
    }

    public static void showCore(Integer[][] array) {
        String wallTile = "\u25A6";

        for (int i = 0; i < array[0].length + 2; i++) {
            System.out.print(wallTile);
        }
        System.out.println();
        for (int i = 0; i < array.length; i++) {
            System.out.print(wallTile);
            for (int j = 0; j < array[i].length; j++) {
                System.out.print(mapping.get(array[i][j]));
            }
            System.out.print(wallTile);
            System.out.println();
        }
        for (int i = 0; i < array[0].length + 2; i++) {
            System.out.print(wallTile);
        }
        System.out.println();
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static boolean conditionWin(Integer[][] array, int posY, int posX) {
        return posY == array.length - 1 && posX == array[0].length - 1;
    }
}
