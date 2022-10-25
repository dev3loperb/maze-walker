package com.example.maze.walker;

import java.util.Scanner;

public class Application {
    public static void main(String[] args) throws Exception {
        int labLength = 5;
        int labWidth = 20;
        Integer[][] core = new Integer[labLength][labWidth];
        resetCore(core);
        int playerPosX = 0;
        int playerPosY = 0;
        showCore(core);
        Scanner input = new Scanner(System.in);
        String scan = input.nextLine();

        for (int i = 0; i < scan.length(); ++i) {

            int[] currentDirection = direction(scan.charAt(i));
            int x = currentDirection[0];
            int y = currentDirection[1];

            core[playerPosY][playerPosX] = 0;
            core[playerPosY + y][playerPosX + x] = 1;
            playerPosY += y;
            playerPosX += x;

            showCore(core);
            System.out.println();
        }
    }

    private static int[] direction(char letter) {
        int x = 0;
        int y = 0;
        switch (letter) {
            case 'W':
            case 'w':
                y = -1;
                x = 0;
                break;
            case 'D':
            case 'd':
                y = 0;
                x = 1;
                break;
            case 'S':
            case 's':
                y = 1;
                x = 0;
                break;
            case 'A':
            case 'a':
                y = 0;
                x = -1;
        }
        int[] result = new int[2];
        result[0] = x;
        result[1] = y;
        return result;
    }

    public static void resetCore(Integer[][] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                array[i][j] = 0;
            }
        }
        array[0][0] = 1;
    }

    public static void showCore(Integer[][] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                System.out.print(array[i][j]);
            }
            System.out.println();
        }
    }

}
