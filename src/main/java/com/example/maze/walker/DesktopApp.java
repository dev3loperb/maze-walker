package com.example.maze.walker;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class DesktopApp extends JPanel {
    private final Integer[][] array;
    private int playerPosY = 0;
    private int playerPosX = 0;
    private final BufferedImage floor;
    private BufferedImage player;
    private final BufferedImage playerLeft;
    private final BufferedImage playerRight;
    private final BufferedImage wall;

    public DesktopApp(int height, int width) {
        array = new DfsMazeGeneratorV2().generate(height, width);
        array[playerPosY][playerPosX] = Application.CHARACTER_TILE;

        try {
            floor = ImageIO.read(getClass().getResource("/img/Stone_Floor_texture.png"));
            player = ImageIO.read(getClass().getResource("/img/Player.png"));
            playerLeft = rotateClockwise90(ImageIO.read(getClass().getResource("/img/Player.png")));
            playerRight = ImageIO.read(getClass().getResource("/img/Player.png"));
            wall = ImageIO.read(getClass().getResource("/img/Wall.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        addKeyListener(new Keyboard(this));
        setFocusable(true);
    }

    public static BufferedImage rotateClockwise90(BufferedImage src) {
        int width = src.getWidth();
        int height = src.getHeight();

        BufferedImage dest = new BufferedImage(height, width, src.getType());

        Graphics2D graphics2D = dest.createGraphics();
        graphics2D.rotate(Math.PI / 2, height / 2, width / 2);
        graphics2D.drawRenderedImage(src, null);

        return dest;
    }

    public static void main(String[] args) {
        DesktopApp m = new DesktopApp(11, 11);
        JFrame f = new JFrame();
        f.add(m);
        f.setSize(1000, 1000);
        f.setVisible(true);
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    @Override
    public void paint(Graphics g) {
        try {
            int size = 64;
            for (int i = 0; i < array[0].length + 2; i++) {
                g.drawImage(wall, -size / 2 + i * size, -size / 2, size, size, this);
                g.drawImage(wall, -size / 2 + i * size, size / 2 + array.length * size, size, size / 2, this);
            }
            for (int i = 0; i < array.length; i++) {
                g.drawImage(wall, -size / 2, size / 2 + i * size, size, size, this);
                g.drawImage(wall, size / 2 + array[0].length * size, size / 2 + i * size, size / 2, size, this);
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

    private class Keyboard implements KeyListener {

        private final JPanel jPanel;

        public Keyboard(JPanel jPanel) {
            this.jPanel = jPanel;
        }

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
            Direction direction = switch (e.getKeyCode()) {
                case KeyEvent.VK_RIGHT, KeyEvent.VK_D -> Direction.RIGHT;
                case KeyEvent.VK_LEFT, KeyEvent.VK_A -> Direction.LEFT;
                case KeyEvent.VK_UP, KeyEvent.VK_W -> Direction.UP;
                case KeyEvent.VK_DOWN, KeyEvent.VK_S -> Direction.DOWN;
                default -> null;
            };
            if (direction == null) {
                return;
            }
            if (direction == Direction.LEFT) {
                player = playerLeft;
            }
            if (direction == Direction.RIGHT) {
                player = playerRight;
            }

            int x = direction.getX();
            int y = direction.getY();

            if (!(playerPosY + y < 0 || playerPosX + x < 0 || playerPosY + y >= array.length || playerPosX + x >= array[0].length)) {
                if (array[playerPosY + y][playerPosX + x] < 1) {
                    array[playerPosY][playerPosX] = Application.EMPTY_TILE;
                    array[playerPosY + y][playerPosX + x] = Application.CHARACTER_TILE;

                    playerPosY += y;
                    playerPosX += x;
                }
            }
            jPanel.repaint();
        }

        @Override
        public void keyReleased(KeyEvent e) {
        }
    }
}

