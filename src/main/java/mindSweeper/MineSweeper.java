package mindSweeper;

import javax.swing.*;
import java.awt.*;

public class MineSweeper extends JFrame {

    private MineSweeper() {
        initUI();
    }

    private void initUI() {
        JLabel gameStatus = new JLabel("");
        add(gameStatus, BorderLayout.SOUTH);
        add(new MineBoard(gameStatus));
        setResizable(false);
        pack();
        setTitle("Minesweeper");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            MineSweeper ex = new MineSweeper();
            ex.setVisible(true);
        });
    }

}
