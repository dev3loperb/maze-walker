package com.example.maze.walker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static com.example.maze.walker.Application.EMPTY_TILE;
import static com.example.maze.walker.Application.WALL_TILE;

public enum Direction {
    LEFT(-1, 0),
    UP(0, -1),
    RIGHT(1, 0),
    DOWN(0, 1);

    private static final Random RANDOM = new Random();

    private final int x;
    private final int y;

    Direction(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Direction left() {
        return switch (this) {
            case LEFT -> DOWN;
            case UP -> LEFT;
            case RIGHT -> UP;
            case DOWN -> RIGHT;
        };
    }

    public Direction right() {
        return switch (this) {
            case LEFT -> UP;
            case UP -> RIGHT;
            case RIGHT -> DOWN;
            case DOWN -> LEFT;
        };
    }

    public static Direction randomDirection() {
        int rand = RANDOM.nextInt(0, 4);
        return switch (rand) {
            case 1 -> UP;
            case 2 -> RIGHT;
            case 3 -> DOWN;
            default -> LEFT;
        };
    }
}
