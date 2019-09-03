package spaceInvanders;

import javax.swing.*;
import java.awt.*;

public class ArkanoidStart extends JFrame {

    private ArkanoidStart(){
        initUI();
    }

    private int boardHeight = 700;
    private int boardWidth = 716;

    private void initUI() {
        add(new ArkanoidPanel());
        setTitle("Space Invaders");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(boardWidth, boardHeight);
        setLocationRelativeTo(null);
        setResizable(false);
    }
    public static void main(String[] args) {
        EventQueue.invokeLater(()-> {
            ArkanoidStart game = new ArkanoidStart();
            game.setVisible(true);
        });
    }
}
