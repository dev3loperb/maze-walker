package com.example.maze.walker;

import java.util.Arrays;
import java.util.Random;

import static com.example.maze.walker.Application.EMPTY_TILE;
import static com.example.maze.walker.Application.WALL_TILE;
import static com.example.maze.walker.Application.showCore;

public class DfsMazeGenerator implements MazeGenerator {

    private static final Random RANDOM = new Random();
    private static final int INITIAL_MAZE_FIELD = 9;

    @Override
    public Integer[][] generate(int height, int width) {
        Integer[][] result = new Integer[height][width];
        for (Integer[] integers : result) {
            Arrays.fill(integers, INITIAL_MAZE_FIELD);
        }

        dfs(result, 0, 0);
        for (Integer[] row : result) {
            for (int i = 0; i < row.length; i++) {
                if (row[i] == INITIAL_MAZE_FIELD) {
                    row[i] = WALL_TILE;
                }
            }
        }

        return result;
    }

    private void dfs(Integer[][] array, int x, int y) {
        array[y][x] = EMPTY_TILE;

        while (hasNextStep(array, x, y)) {

            Direction direction;
            do {
                direction = Direction.randomDirection();
            } while (!canStepOn(array, x, y, x + direction.getX(), y + direction.getY()));

            Arrays.asList(direction.left(), direction.right()).forEach(dir -> {
                int lx = x + dir.getX();
                int ly = y + dir.getY();
                if (lx >= 0 && lx < array[0].length && ly >= 0 && ly < array.length) {
                    if (array[ly][lx] == INITIAL_MAZE_FIELD) {
                        array[ly][lx] = WALL_TILE;
                    }
                }
            });
            dfs(array, x + direction.getX(), y + direction.getY());
        }
    }

    private boolean hasNextStep(Integer[][] array, int x, int y) {
        boolean isBlocked = Arrays.stream(Direction.values()).allMatch(direction -> {
            if (!inScope(array, x + direction.getX(), y + direction.getY())) {
                return true;
            }
            if ((array[y][x + direction.getX()] == EMPTY_TILE || array[y][x + direction.getX()] == WALL_TILE) &&
                    (array[y + direction.getY()][x] == EMPTY_TILE || array[y + direction.getY()][x] == WALL_TILE)) {
                return true;
            }
            return false;
        });
        if (isBlocked) {
            return false;
        }
        if (!canStepOn(array, x, y, x - 1, y) &&
                !canStepOn(array, x, y, x, y - 1) &&
                !canStepOn(array, x, y, x + 1, y) &&
                !canStepOn(array, x, y, x, y + 1)
        ) {
            return false;
        }
        return true;
    }

    private boolean canStepOn(Integer[][] array, int x, int y, int toX, int toY) {
        if (!inScope(array, toX, toY)) {
            return false;
        }
        boolean anyEmptyNeighbour = Arrays.stream(Direction.values()).anyMatch(direction -> {
            if (!inScope(array, toX + direction.getX(), toY + direction.getY())) {
                return false;
            }
            if (x == toX + direction.getX() && y == toY + direction.getY()) {
                return false;
            }
            return array[toY + direction.getY()][toX + direction.getX()] == EMPTY_TILE;
        });
        return !anyEmptyNeighbour;
    }

    private boolean inScope(Integer[][] array, int x, int y) {
        return !(x < 0 || x >= array[0].length || y < 0 || y >= array.length);
    }
}
