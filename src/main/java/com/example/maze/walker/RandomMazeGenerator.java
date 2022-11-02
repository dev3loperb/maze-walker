package com.example.maze.walker;

import java.util.Random;

public class RandomMazeGenerator implements MazeGenerator {

    private final Random random = new Random(10);
    private final int positionPlayerX = 0;
    private final int positionPlayerY = 0;

    @Override
    public Integer[][] generate(int height, int width) {
//        int positionPlayerX = random.nextInt(width);
//        int positionPlayerY = random.nextInt(height);

        Integer[][] array = new Integer[height][width];
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                array[i][j] = Application.WALL_TILE;
//                array[i][j] = identifyTile();
            }
        }
        int posCurTileY = 0;
        int posCurTileX = 0;
        if (random.nextInt(1) == 0) {
            posCurTileX = 1;
            array[posCurTileY][posCurTileX] = Application.EMPTY_TILE;
        } else {
            posCurTileY = 1;
            array[posCurTileY][posCurTileX] = Application.EMPTY_TILE;
        }
        int weightedY = 0;
        int weightedX = 0;
//        while (!(posCurTileY == height - 1 || posCurTileX == width - 1)) {
        while (!(posCurTileY == height - 1 || posCurTileX == width - 1)) {
            int deltaY = (int) (Math.pow(-1, random.nextInt(2, 5))) *
                    Math.min(10, random.nextInt(1, 16) + weightedY) / 10;
            if (posCurTileY < height - 1 && posCurTileY >= 0) {
                if (deltaY > 0) {
                    weightedY = 0;
                } else {
                    weightedY++;
                }
            }
            int deltaX = Math.min(10, random.nextInt(1, 21) + weightedX) / 10;
            if (posCurTileX < width - 1) {
                if (deltaX > 0) {
                    weightedX = 0;
                } else {
                    weightedX++;
                }
            }

            if (posCurTileX + deltaX > width - 1) {
                deltaX = 0;
            }
            if (posCurTileY + deltaY < 0 || posCurTileY + deltaY > height - 1) {
                deltaY = 0;
            }

            if (random.nextBoolean()) {
                array[posCurTileY + deltaY][posCurTileX] = Application.EMPTY_TILE;
            } else {
                array[posCurTileY][posCurTileX + deltaX] = Application.EMPTY_TILE;
            }
            array[posCurTileY + deltaY][posCurTileX + deltaX] = Application.EMPTY_TILE;
            posCurTileY += deltaY;
            posCurTileX += deltaX;
        }
        while (!(posCurTileY == height - 1 && posCurTileX == width - 1)) {
            posCurTileY = Math.min(height - 1, posCurTileY + 1);
            posCurTileX = Math.min(width - 1, posCurTileX + 1);
            array[posCurTileY][posCurTileX] = Application.EMPTY_TILE;
        }
        return array;
    }

    private int identifyTile() {
        int result = random.nextInt(0, 100);

        if (result < 90) {
            return Application.EMPTY_TILE;
        } else {
            return Application.WALL_TILE;
        }
    }
}

