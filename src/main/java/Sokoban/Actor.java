package Sokoban;

import java.awt.*;

public class Actor implements Commons{

    int x;
    int y;
    private Image image;

    Actor(int x, int y){
        this.x = x;
        this.y = y;
    }
    Image getImage(){
        return image;
    }

    public int x() {

        return x;
    }

    public int y() {

        return y;
    }

    public void setX(int x) {

        this.x = x;
    }

    public void setY(int y) {

        this.y = y;
    }

    boolean isLeftCollision(Actor actor){
        return x() - fieldSize == actor.x() && y() == actor.y();
    }
    boolean isRightCollision(Actor actor){
        return x() + fieldSize == actor.x() && y() == actor.y();
    }
    boolean isTopCollision(Actor actor){
        return y() - fieldSize == actor.y() && x() == actor.x();
    }
    boolean isBottomCollision(Actor actor){
        return y() + fieldSize == actor.y() && x() == actor.x();
    }


    public void setImage(Image image) {
        this.image = image;
    }
}
