package com.github.brunocs1991;


import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;
import java.util.Timer;
import java.util.*;

public class WindowGame extends JFrame implements KeyListener {

    private static final int UP = 0;
    private static final int DOWN = 1;
    private static final int LEFT = 2;
    private static final int RIGHT = 3;

    private static final int WINDOW_WIDTH = 400;
    private static final int WINDOW_HEIGHT = 400;
    private static final int MATRIX_SIZE = 20;
    private static final int PADDING = 50;
    private static final int DEFAULT_PERIOD = 400;


    private final int[][] matrix = new int[MATRIX_SIZE][MATRIX_SIZE];

    private final List<Integer> bodyX = new ArrayList<>();
    private final List<Integer> bodyY = new ArrayList<>();
    private final List<Integer> foodPositionX = new ArrayList<>();
    private final List<Integer> foodPositionY = new ArrayList<>();

    private final Random random = new Random();


    private int score = 0;
    private int period = DEFAULT_PERIOD;
    private int headX = 6, headY = 4;
    private int direction = RIGHT;

    private Timer timer;

    private boolean isDirectionChanged = false;
    private boolean isGameOver = false;

    WindowGame() {
        setSize(WINDOW_WIDTH + PADDING * 2, WINDOW_HEIGHT + PADDING * 2);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        addKeyListener(this);
        restartGame();
    }

    private void reschedule() {
        timer.cancel();
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                moveSnake();
            }
        }, period, period);
    }

    private void restartGame() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                moveSnake();
            }
        }, 0, period);

        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[0].length; col++) {
                matrix[row][col] = 0;
            }
        }

        period = DEFAULT_PERIOD;
        score = 0;
        bodyX.clear();
        bodyY.clear();
        direction = RIGHT;
        isGameOver = false;

        headX = 6;
        headY = 4;
        bodyX.add(5);
        bodyX.add(4);
        bodyX.add(3);
        bodyX.add(4);

        bodyY.add(4);
        bodyY.add(4);
        bodyY.add(4);
        bodyY.add(4);

        matrix[11][11] = 1;
    }

    private boolean isSnakeHeadCollisionBody() {
        for (int i = 0; i < bodyX.size(); i++) {
            if (bodyX.get(i) == headX && bodyY.get(i) == headY) {
                return true;
            }
        }
        return false;
    }

    private int[] getFoodPosition() {
        foodPositionX.clear();
        foodPositionY.clear();

        for (int x = 0; x < MATRIX_SIZE; x++) {
            for (int y = 0; y < MATRIX_SIZE; y++) {
                if (matrix[y][x] == 0) {
                    foodPositionX.add(x);
                    foodPositionY.add(y);
                }
            }
        }

        int randomIndex = random.nextInt(foodPositionX.size());
        int[] result = new int[2];
        result[0] = foodPositionX.get(randomIndex);
        result[1] = foodPositionY.get(randomIndex);

        return result;
    }

    private int[] getNextPositionHead() {
        int futureHeadX = 0, futureHeadY = 0;
        switch (direction) {
            case UP:
                futureHeadY = headY - 1;
                break;
            case DOWN:
                futureHeadY = headY + 1;
                break;
            case LEFT:
                futureHeadX = headX - 1;
                break;
            case RIGHT:
                futureHeadX = headX + 1;
                break;
        }
        int[] result = new int[2];
        result[0] = futureHeadX;
        result[1] = futureHeadY;
        return result;
    }

    private void moveSnake() {
        int[] futurePositionHead = getNextPositionHead();
        if (futurePositionHead[0] < 0 || futurePositionHead[0] >= MATRIX_SIZE
                || futurePositionHead[1] < 0 || futurePositionHead[1] >= MATRIX_SIZE
                || isSnakeHeadCollisionBody()) {
            isGameOver = true;
            repaint();
            timer.cancel();
        } else {
            isDirectionChanged = false;
            // Update snake body
            int oldTailX = bodyX.get(bodyX.size() - 1);
            int oldTailY = bodyY.get(bodyY.size() - 1);
            for (int i = bodyX.size() - 1; i > 0; i--) {
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
            // eat food
            if (matrix[headY][headX] == 1) {
                bodyX.add(oldTailX);
                bodyY.add(oldTailY);
                matrix[headY][headX] = 0;

                int[] newFoodPosition = getFoodPosition();
                matrix[newFoodPosition[1]][newFoodPosition[0]] = 1;
                increaseScore();
            }
            repaint();
        }
    }

    private void increaseScore() {
        score++;
        if (score % 5 == 0) {
            period = period / 2;
            reschedule();
        }
    }

    public static void main(String[] args) {
        new WindowGame();
    }

    public void paint(Graphics g) {
//		super.paint(g);
        g.setColor(Color.white);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.RED);
        int sizeOfSnakeBody = WINDOW_WIDTH / MATRIX_SIZE;
        g.fillRect(headX * sizeOfSnakeBody + PADDING, headY * sizeOfSnakeBody + PADDING, sizeOfSnakeBody, sizeOfSnakeBody);


        for (int i = 0; i < bodyX.size(); i++) {
            // Draw the snake body
            g.fillRect(bodyX.get(i) * sizeOfSnakeBody + PADDING, bodyY.get(i) * sizeOfSnakeBody + PADDING, sizeOfSnakeBody, sizeOfSnakeBody);
        }


        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[0].length; col++) {
                int x = col * sizeOfSnakeBody + PADDING;
                int y = row * sizeOfSnakeBody + PADDING;
                if (matrix[row][col] == 1) {
                    g.setColor(Color.blue);
                    g.fillRect(x, y, sizeOfSnakeBody, sizeOfSnakeBody);
                } else {
                    g.setColor(Color.black);
                    g.drawRect(x, y, sizeOfSnakeBody, sizeOfSnakeBody);
                }
            }
        }

        if (isGameOver) {
            g.setColor(Color.red);
            g.drawString("GAME OVER", 250, 250);
        }

        g.setColor(Color.blue);
        g.drawString("Score: " + score, 50, 470);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!isDirectionChanged) {
            if (e.getKeyCode() == KeyEvent.VK_UP && direction != DOWN) {
                direction = UP;
                isDirectionChanged = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_DOWN && direction != UP) {
                direction = DOWN;
                isDirectionChanged = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_LEFT && direction != RIGHT) {
                direction = LEFT;
                isDirectionChanged = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT && direction != LEFT) {
                direction = RIGHT;
                isDirectionChanged = true;
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_ENTER && isGameOver) {
            restartGame();
        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
