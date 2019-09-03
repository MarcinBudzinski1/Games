package Tetris;

import javax.swing.*;
import java.awt.*;

public class Tetris extends JFrame {
    private JLabel statusBar;

    private Tetris(){
        initUI();
    }

    private void initUI() {
        statusBar = new JLabel(" 0");
        add(statusBar, BorderLayout.SOUTH);
        Board board = new Board(this);
        add(board);
        board.start();
        setTitle("Tetris");
        setSize(200, 400);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    JLabel getStatusBar() {
        return statusBar;
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            Tetris game = new Tetris();
            game.setVisible(true);
        });
    }
}
