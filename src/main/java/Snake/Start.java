package Snake;

import javax.swing.*;
import java.awt.*;

public class Start extends JFrame {

    private Start(){
        initUI();
    }

    private void initUI() {
        add(new SnakePanel());
        setResizable(false);
        pack();

        setTitle("Snake");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(()->{
            JFrame game = new Start();
            game.setVisible(true);
        });
    }
}
