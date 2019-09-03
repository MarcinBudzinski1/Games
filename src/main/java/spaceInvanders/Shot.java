package spaceInvanders;

import javax.swing.*;

class Shot extends Sprite {

    private final String shotImg = "src/images/shot.png";
    private final int hSpace = 6;
    private final int vSpace = 1;

    Shot(){}

    Shot(int x, int y) {
        initShot(x, y);
    }

    private void initShot(int x, int y) {
        ImageIcon ii = new ImageIcon(shotImg);
        setImage(ii.getImage());

        setX(x + hSpace);
        setY(y - vSpace);
    }
}
