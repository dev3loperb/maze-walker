package com.example.maze.walker;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Random;

public class DesktopApp extends Canvas {
    public static void main(String[] args) {
        DesktopApp m = new DesktopApp();
        JFrame f = new JFrame();
        f.add(m);
        f.setSize(10 * 64 + 2 * 64, 10 * 64 + 3 * 64);
        f.setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        try {
            Image floor = ImageIO.read(getClass().getResource("/Stone_Floor_texture.png"));
            Image player = ImageIO.read(getClass().getResource("/Player.png"));
            int size = 64;
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    Image image;
                    if (new Random().nextInt(100) == 10) {
                        image = player;
                    } else {
                        image = floor;
                    }
                    g.drawImage(image, size + i * size, size + j * size, size, size, this);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
