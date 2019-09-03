package Sokoban;

import javax.swing.*;
import java.awt.*;

class Area extends Actor {

    Area(int x, int y) {
        super(x, y);
        initArea();
    }

    private void initArea() {
        ImageIcon iicon = new ImageIcon("src/main/resources/Sokoban/area.png");
        Image image = iicon.getImage();
        setImage(image);
    }
}