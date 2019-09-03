package Sokoban;

import javax.swing.*;
import java.awt.*;

class Luggage extends Actor {

    Luggage(int x, int y) {
        super(x, y);

        initBaggage();
    }

    private void initBaggage() {

        ImageIcon iicon = new ImageIcon("src/main/resources/Sokoban/luggage.png");
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