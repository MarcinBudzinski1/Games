package Sokoban;

import javax.swing.*;
import java.awt.*;

class Player extends Actor {

    Player(int x, int y) {
        super(x, y);

        initPlayer();
    }

    private void initPlayer() {

        ImageIcon iicon = new ImageIcon("src/main/resources/Sokoban/sokoban.png");
        Image image = iicon.getImage();
        setImage(image);
    }

    void move(int x, int y) {

        int dx = x() + x;
        int dy = y() + y;

        setX(dx);
        setY(dy);
    }
}