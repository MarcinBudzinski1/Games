package spaceInvanders;

import java.awt.*;

public class Sprite {

    private boolean visible;
    private Image image;
    protected int x, y;
    private boolean dying;
    int dx;

    public Sprite(){
        visible = true;
    }

    public void die(){
        visible =  false;
    }

    boolean isVisible(){
        return visible;
    }

    protected void setVisible(boolean visible){
        this.visible = visible;
    }

    public void setImage(Image image){
        this.image = image;
    }
    public Image getImage(){
        return image;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    boolean isDying() {
        return dying;
    }

    void setDying(boolean dying) {
        this.dying = dying;
    }
}
