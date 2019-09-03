package spaceInvanders;

import javax.swing.*;
import java.awt.event.KeyEvent;

class Player extends Sprite implements Commons{
    private final int startX = 540;
    private final int startY = 560;
    private final String playerImg = "src/images/player.png";
    private int width;

    Player(){ initPlayer();}

    private void initPlayer() {
        ImageIcon playerIcon = new ImageIcon(playerImg);
        width = playerIcon.getImage().getWidth(null);
        setImage(playerIcon.getImage());
        setX(startX);
        setY(startY);
    }
    void act(){
        x += dx;
        if (x <= 2){
            x = 2;
        }
        if (x >= boardWidth - 2*width){
            x = boardWidth - 2*width;
        }
    }

    void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT){
            dx = 0;
        }
        if (key == KeyEvent.VK_RIGHT){
            dx = 0;
        }
    }

    void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {

            dx = -2;
        }

        if (key == KeyEvent.VK_RIGHT) {

            dx = 2;
        }
    }
}
