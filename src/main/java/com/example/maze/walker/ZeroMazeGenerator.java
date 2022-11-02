package com.example.maze.walker;

import java.util.Arrays;

public class ZeroMazeGenerator implements MazeGenerator {
    @Override
    public Integer[][] generate(int height, int width) {
        Integer[][] maze = new Integer[height][width];
        for (Integer[] row : maze) {
            Arrays.fill(row, 0);
        }
        return maze;
    }
}
