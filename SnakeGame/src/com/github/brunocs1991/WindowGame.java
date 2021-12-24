package com.github.brunocs1991;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Timer;
import java.util.TimerTask;

public class WindowGame extends JFrame implements KeyListener {


    private static final int UP = 0;
    private static final int DOWN = 1;
    private static final int LEFT = 2;
    private static final int RIGHT = 3;
    private static final int WINDOWN_WIDTH = 400;
    private static final int WINDOWN_HEIGHT = 400;
    private static final int MATRIX_SIZE = 20;
    private static final int PADDING = 50;
    private static final int[][] matrix = new int[MATRIX_SIZE][MATRIX_SIZE];
    private static final int sizeOfSnackBody = WINDOWN_WIDTH / MATRIX_SIZE;

    private int headX = 3, headY = 4;
    private int direction = RIGHT;

    WindowGame() {
        setSize(WINDOWN_WIDTH + PADDING * 2, WINDOWN_HEIGHT + PADDING * 2);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        addKeyListener(this);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                moveSnake();
            }
        }, 0, 400);
    }

    private void moveSnake() {
        switch (direction) {
            case UP:
                headY--;
                break;
            case DOWN:
                headY++;
                break;
            case LEFT:
                headX--;
                break;
            case RIGHT:
                headX++;
                break;
        }
        repaint();
    }

    public static void main(String[] args) {
        new WindowGame();
    }

    @Override
    public void paint(Graphics g) {
        //super.paint(g);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.BLACK);
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[row].length; col++) {
                int x = col * sizeOfSnackBody + PADDING;
                int y = row * sizeOfSnackBody + PADDING;
                g.drawRect(x, y, sizeOfSnackBody, sizeOfSnackBody);
            }
        }
        g.setColor(Color.RED);
        g.fillRect(headX * sizeOfSnackBody + PADDING, headY * sizeOfSnackBody + PADDING, sizeOfSnackBody, sizeOfSnackBody);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP && direction != DOWN) {
            direction = UP;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN && direction != UP) {
            direction = DOWN;
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT && direction != RIGHT) {
            direction = LEFT;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT && direction != LEFT) {
            direction = RIGHT;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
