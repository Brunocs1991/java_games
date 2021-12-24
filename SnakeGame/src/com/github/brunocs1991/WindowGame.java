package com.github.brunocs1991;

import javax.swing.*;
import java.awt.*;

public class WindowGame extends JFrame {

    private static final int WINDOWN_WIDTH = 400;
    private static final int WINDOWN_HEIGHT = 400;
    private static final int MATRIX_SIZE = 20;
    private static final int PADDING = 50;
    private static final int[][] matrix = new int[MATRIX_SIZE][MATRIX_SIZE];
    private static final int sizeOfSnackBody = WINDOWN_WIDTH / MATRIX_SIZE;

    WindowGame() {
        setSize(WINDOWN_WIDTH + PADDING * 2, WINDOWN_HEIGHT + PADDING * 2);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new WindowGame();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[row].length; col++) {
                int x  = col * sizeOfSnackBody + PADDING;
                int y = row * sizeOfSnackBody + PADDING;
                g.drawRect(x, y, sizeOfSnackBody, sizeOfSnackBody);
            }
        }
    }
}
