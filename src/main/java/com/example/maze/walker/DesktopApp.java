package com.example.maze.walker;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Random;

public class DesktopApp extends Canvas {
    private final Integer[][] array;

    public DesktopApp(int height, int width) {
        array = new DfsMazeGeneratorV2().generate(height, width);
        array[0][0] = Application.CHARACTER_TILE;
    }

    public static void main(String[] args) {
        DesktopApp m = new DesktopApp(21, 35);
        JFrame f = new JFrame();
        f.add(m);
        f.setSize(1000, 1000);
        f.setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        try {
            Image floor = ImageIO.read(getClass().getResource("/img/Stone_Floor_texture.png"));
            Image player = ImageIO.read(getClass().getResource("/img/Player.png"));
            Image wall = ImageIO.read(getClass().getResource("/img/Wall.png"));
            int size = 32;
            for (int i = 0; i < array[0].length + 2; i++) {
                g.drawImage(wall, -size / 2 + i * size, -size / 2, size, size, this);
                g.drawImage(wall, -size / 2 + i * size, size / 2 + array.length * size, size, size/2, this);
            }
            for (int i = 0; i < array.length; i++) {
                g.drawImage(wall, -size / 2, size / 2 + i * size, size, size, this);
                g.drawImage(wall, size / 2 + array[0].length*size, size / 2 + i * size, size/2, size, this);
            }

            for (int i = 0; i < array.length; i++) {
                for (int j = 0; j < array[0].length; j++) {
                    if (array[i][j] == Application.EMPTY_TILE) {
                        draw(g, floor, size, i, j);
                    } else if (array[i][j] == Application.CHARACTER_TILE) {
                        draw(g, floor, size, i, j);
                        draw(g, player, size, i, j);
                    } else {
                        draw(g, wall, size, i, j);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void draw(Graphics g, Image floor, int size, int i, int j) {
        g.drawImage(floor, size - size / 2 + j * size, size - size / 2 + i * size, size, size, this);
    }
}
