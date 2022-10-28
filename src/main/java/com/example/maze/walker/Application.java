package com.example.maze.walker;

import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.IOException;
import java.io.Reader;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class Application {
    private static final int WALL_TILE = 8;
    private static final int EMPTY_TILE = 0;
    private static final int CHARACTER_TILE = 1;

    private static Map<Integer, Character> mapping = Map.of(
            CHARACTER_TILE, '@',
            WALL_TILE, '†',
            EMPTY_TILE, ' '
    );

    public static void main(String[] args) {
        Integer[][] core = new Integer[5][20];
        int playerPosX = 0;
        int playerPosY = 0;

        resetCore(core, playerPosY, playerPosX);
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
        System.out.println("GAME WON");
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

    public static void resetCore(Integer[][] array, int playerPosY, int playerPosX) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                array[i][j] = EMPTY_TILE;
                array[i][j] = identifyTile();
            }
        }
        array[playerPosY][playerPosX] = 1;
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
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static boolean conditionWin(Integer[][] array, int posY, int posX) {
        return posY == array.length - 1 && posX == array[0].length - 1;
    }

    public static int identifyTile() {
        Random cell = new Random();
        int result = cell.nextInt(0, 100);
        if (result < 90) {
            return EMPTY_TILE;
        } else {
            return WALL_TILE;
        }
    }
}
