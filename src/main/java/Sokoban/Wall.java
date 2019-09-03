package Sokoban;

import javax.swing.*;
import java.awt.*;

class Wall extends Actor {

    private Image image;

    Wall(int x, int y){
        super(x,y);
        
        createWall();
    }

    private void createWall() {
        ImageIcon icon = new ImageIcon("src/main/resources/Sokoban/wall.png");
        image = icon.getImage();
        setImage(image);
    }

}
