package Sokoban;

import javax.swing.*;
import java.awt.*;

public class SokobanStart extends JFrame {

    private SokobanStart(){
        initUI();
    }

    private void initUI() {
        SokobanBoard sokobanBoard = new SokobanBoard();
        add(sokobanBoard);

        setTitle("Sokoban start");

        int fieldSize = 40;
        setSize(sokobanBoard.getBoardWidth() + fieldSize,
                sokobanBoard.getBoardHeight() + 2 * fieldSize);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(()->{
                SokobanStart game = new SokobanStart();
                game.setVisible(true);
        });
    }
}
