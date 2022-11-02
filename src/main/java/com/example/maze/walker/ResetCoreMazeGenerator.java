package com.example.maze.walker;

import java.util.Random;

public class ResetCoreMazeGenerator implements MazeGenerator {

    private final Random random = new Random(10);

    @Override
    public Integer[][] generate(int height, int width) {
        Integer[][] array = new Integer[height][width];
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                array[i][j] = identifyTile();
            }
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
