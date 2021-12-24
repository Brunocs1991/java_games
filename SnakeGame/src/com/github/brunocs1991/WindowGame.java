package com.github.brunocs1991;



import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
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


    private final List<Integer> bodyX = new ArrayList<>();
    private final List<Integer> bodyY = new ArrayList<>();
    private int headX = 6, headY = 4;
    private int direction = RIGHT;

    WindowGame() {
        setSize(WINDOWN_WIDTH + PADDING * 2, WINDOWN_HEIGHT + PADDING * 2);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setTitle("Snake Game");
        setResizable(false);
        addKeyListener(this);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                moveSnake();
            }
        }, 0, 400);
        bodyX.add(5);
        bodyX.add(4);

        bodyY.add(4);
        bodyY.add(4);

        matrix[11][11] = 1;
        matrix[11][12] = 1;
        matrix[11][13] = 1;
    }

    private void moveSnake() {
        //Update snack body
        int oldTailX = bodyX.get(bodyX.size() - 1);
        int oldTailY = bodyY.get(bodyY.size() - 1);
        for (int i  = bodyX.size() -1; i > 0; i --){
            bodyX.set(i, bodyX.get(i - 1));
            bodyY.set(i, bodyY.get(i - 1));
        }
        bodyX.set(0, headX);
        bodyY.set(0, headY);
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
        if(matrix[headY][headX] == 1){
            bodyX.add(oldTailX);
            bodyY.add(oldTailY);
            matrix[headY][headX] = 0;
        }
        repaint();
    }

    public static void main(String[] args) {
        new WindowGame();
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.GREEN);
        g.fillRect(headX * sizeOfSnackBody + PADDING, headY * sizeOfSnackBody + PADDING, sizeOfSnackBody, sizeOfSnackBody);

        for (int i = 0; i < bodyX.size(); i ++){
            //Draw the snake body
            g.setColor(Color.RED);
            g.fillRect(bodyX.get(i) * sizeOfSnackBody + PADDING,bodyY.get(i) * sizeOfSnackBody + PADDING, sizeOfSnackBody, sizeOfSnackBody);
        }
        g.setColor(Color.BLACK);
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[row].length; col++) {
                int x = col * sizeOfSnackBody + PADDING;
                int y = row * sizeOfSnackBody + PADDING;
                if(matrix[row][col] == 1){
                    g.setColor(Color.BLUE);
                    g.fillRect(x, y, sizeOfSnackBody, sizeOfSnackBody);
                }else{
                    g.setColor(Color.BLACK);
                    g.drawRect(x, y, sizeOfSnackBody, sizeOfSnackBody);
                }

            }
        }

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
