package com.github.brunocs;

import javax.swing.*;

public class WindowGame {

    private static final int WIDTH= 445, HEIGHT = 629;

    private Board board;
    private final JFrame window;

    public WindowGame(){
        window = new JFrame("Tetris");
        window.setSize(WIDTH, HEIGHT);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(Boolean.FALSE);
        window.setLocationRelativeTo(null);

        board = new Board();
        window.add(board);

        window.setVisible(true);
    }

    public static void main(String[] args) {
        new WindowGame();
    }
}
