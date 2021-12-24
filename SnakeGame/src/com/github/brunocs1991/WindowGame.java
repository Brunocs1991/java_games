package com.github.brunocs1991;

import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Timer;
import java.util.TimerTask;

public class WindowGame extends JFrame implements KeyListener {

    private static final int WINDOWN_WIDTH = 400;
    private static final int WINDOWN_HEIGHT = 400;
    private static final int MATRIX_SIZE = 20;
    private static final int PADDING = 50;
    private static final int[][] matrix = new int[MATRIX_SIZE][MATRIX_SIZE];
    private static final int sizeOfSnackBody = WINDOWN_WIDTH / MATRIX_SIZE;

    private int headX = 3, headY = 4;

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
        },0, 400);
    }

    private void moveSnake(){
        headX++;
        repaint();
    }
    public static void main(String[] args) {
        new WindowGame();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
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
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            headY--;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            headY++;
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            headX--;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            headX++;
        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
