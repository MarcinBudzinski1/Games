package spaceInvanders;

import javax.swing.*;

class Alien extends Sprite {
    private Bomb bomb;

    Alien(int x, int y) {

        initAlien(x, y);
    }

    private void initAlien(int x, int y) {
        this.x = x;
        this.y = y;

        bomb = new Bomb(x, y);
        String alienImg = "src/images/alien.png";
        ImageIcon alienIcon = new ImageIcon(alienImg);
        setImage(alienIcon.getImage());
    }

    class Bomb extends Sprite{
        private boolean destroyed;

        Bomb(int x, int y) {
            initBomb(x, y);
        }

        private void initBomb(int x, int y) {

            setDestroyed(true);
            this.x = x;
            this.y = y;
            String bombImg = "src/images/bomb.png";
            ImageIcon bomb = new ImageIcon(bombImg);
            setImage(bomb.getImage());
        }

        void setDestroyed(boolean destroyed) {
            this.destroyed = destroyed;
        }

        boolean isDestroyed() {
            return destroyed;
        }
    }

    void act(int direction){
        this.x += direction;
    }

    Bomb getBomb(){
        return bomb;
    }
}
