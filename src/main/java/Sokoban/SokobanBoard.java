package Sokoban;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class SokobanBoard extends JPanel implements Commons{

    private ArrayList<Wall> walls;
    private ArrayList<Luggage> luggages;
    private ArrayList<Area> areas;

    private Player player;
    private int width = 0;
    private int height = 0;

    private boolean isCompleted = false;

    private String level =
            "    WWWWWW\n"
                    + "     W   W\n"
                    + "     WL  W\n"
                    + "   WWW  LWW\n"
                    + "   W  L L W\n"
                    + " WWW W WW W   WWWWWW\n"
                    + " W   W WW WWWWW  AAW\n"
                    + " W L  L          AAW\n"
                    + " WWWWW WWW WPWW  AAW\n"
                    + "    WW     WWWWWWWWW\n"
                    + "    WWWWWWWW\n";

    SokobanBoard() {
        initBoard();
    }

    private void initBoard() {
        addKeyListener(new MyAdapter());
        setFocusable(true);
        initNewGame();
    }

    int getBoardWidth() {
        return this.width;
    }

    int getBoardHeight() {
        return this.height;
    }

    private void initNewGame() {
        walls = new ArrayList<>();
        luggages = new ArrayList<>();
        areas = new ArrayList<>();

        int x = boundarySize;
        int y = boundarySize;

        Wall wall;
        Luggage luggage;
        Area area;

        for (int i = 0; i < level.length(); i++) {
            char item = level.charAt(i);
            switch (item) {
                case '\n':
                    y += fieldSize;
                    if (this.width < x) {
                        this.width = x;
                    }
                    x = boundarySize;
                    break;

                case 'W':
                    wall = new Wall(x, y);
                    walls.add(wall);
                    x += fieldSize;
                    break;

                case 'L':
                    luggage = new Luggage(x, y);
                    luggages.add(luggage);
                    x += fieldSize;
                    break;

                case 'A':
                    area = new Area(x, y);
                    areas.add(area);
                    x += fieldSize;
                    break;

                case 'P':
                    player = new Player(x, y);
                    x += fieldSize;
                    break;

                case ' ':
                    x += fieldSize;
                    break;

                default:
                    break;

            }

            height = y;
        }

    }

    private void createNewGame(Graphics graphics) {
        graphics.setColor(new Color(250, 170, 170));
        graphics.fillRect(0, 0, this.getBoardWidth(), this.getBoardHeight());

        ArrayList<Actor> game = new ArrayList<>();

        game.addAll(areas);
        game.addAll(luggages);
        game.addAll(walls);
        game.add(player);

        for (Actor item : game) {
            if (item instanceof Player || item instanceof Luggage) {
                graphics.drawImage(item.getImage(), item.x() + 2, item.y() + 2, this);
            } else {
                graphics.drawImage(item.getImage(), item.x, item.y, this);
            }

            if (isCompleted) {
                graphics.setColor(new Color(0, 0, 0));
                graphics.drawString("You won", 25, 20);
            }
        }
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        createNewGame(graphics);
    }

    private class MyAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            if (isCompleted) {
                return;
            }

            int key = e.getKeyCode();

            switch (key) {
                case KeyEvent.VK_LEFT:
                    if (checkWallCollision(player, leftCollision)) {
                        return;
                    }
                    if (checkLuggageCollision(leftCollision)) {
                        return;
                    }
                    player.move(-fieldSize, 0);
                    break;

                case KeyEvent.VK_RIGHT:
                    if (checkWallCollision(player, rightCollision)) {
                        return;
                    }
                    if (checkLuggageCollision(rightCollision)) {
                        return;
                    }
                    player.move(fieldSize, 0);
                    break;

                case KeyEvent.VK_UP:
                    if (checkWallCollision(player, topCollision)) {
                        return;
                    }
                    if (checkLuggageCollision(topCollision)) {
                        return;
                    }
                    player.move(0, -fieldSize);
                    break;

                case KeyEvent.VK_DOWN:
                    if (checkWallCollision(player, bottomCollision)) {
                        return;
                    }
                    if (checkLuggageCollision(bottomCollision)) {
                        return;
                    }
                    player.move(0, fieldSize);
                    break;

                case KeyEvent.VK_R:
                    restartLevel();
                    break;

                default:
                    break;
            }
            repaint();
        }
    }

    private boolean checkWallCollision(Actor actor, int type) {

        switch (type) {
            case leftCollision:
                for (Wall wall : walls) {
                    if (actor.isLeftCollision(wall)) {
                        return true;
                    }
                }
                return false;
            case rightCollision:
                for (Wall wall : walls) {
                    if (actor.isRightCollision(wall)) {
                        return true;
                    }
                }
                return false;
            case topCollision:
                for (Wall wall : walls) {
                    if (actor.isTopCollision(wall)) {
                        return true;
                    }
                }
                return false;
            case bottomCollision:
                for (Wall wall : walls) {
                    if (actor.isBottomCollision(wall)) {
                        return true;
                    }
                }
                return false;
            default:
                break;
        }
        return false;
    }

    private boolean checkLuggageCollision(int type) {

        switch (type) {

            case leftCollision:

                for (int i = 0; i < luggages.size(); i++) {

                    Luggage luggage = luggages.get(i);

                    if (player.isLeftCollision(luggage)) {

                        for (Luggage item : luggages) {

                            if (!luggage.equals(item)) {

                                if (luggage.isLeftCollision(item)) {
                                    return true;
                                }
                            }

                            if (checkWallCollision(luggage, leftCollision)) {
                                return true;
                            }
                        }

                        luggage.move(-fieldSize, 0);
                        isCompleted();
                    }
                }

                return false;

            case rightCollision:

                for (int i = 0; i < luggages.size(); i++) {

                    Luggage luggage = luggages.get(i);

                    if (player.isRightCollision(luggage)) {

                        for (Luggage item : luggages) {

                            if (!luggage.equals(item)) {

                                if (luggage.isRightCollision(item)) {
                                    return true;
                                }
                            }

                            if (checkWallCollision(luggage, rightCollision)) {
                                return true;
                            }
                        }

                        luggage.move(fieldSize, 0);
                        isCompleted();
                    }
                }
                return false;

            case topCollision:

                for (int i = 0; i < luggages.size(); i++) {

                    Luggage luggage = luggages.get(i);

                    if (player.isTopCollision(luggage)) {

                        for (Luggage item : luggages) {

                            if (!luggage.equals(item)) {

                                if (luggage.isTopCollision(item)) {
                                    return true;
                                }
                            }

                            if (checkWallCollision(luggage, topCollision)) {
                                return true;
                            }
                        }

                        luggage.move(0, -fieldSize);
                        isCompleted();
                    }
                }

                return false;

            case bottomCollision:

                for (int i = 0; i < luggages.size(); i++) {

                    Luggage luggage = luggages.get(i);

                    if (player.isBottomCollision(luggage)) {

                        for (Luggage item : luggages) {

                            if (!luggage.equals(item)) {

                                if (luggage.isBottomCollision(item)) {
                                    return true;
                                }
                            }

                            if (checkWallCollision(luggage, bottomCollision)) {

                                return true;
                            }
                        }

                        luggage.move(0, fieldSize);
                        isCompleted();
                    }
                }

                break;

            default:
                break;
        }

        return false;
    }

    private void isCompleted(){
        int numberOfLuggages = luggages.size();
        int providedLuggage = 0;

        for (Luggage luggage : luggages) {
            for (int j = 0; j < numberOfLuggages; j++) {
                Area area = areas.get(j);
                if (luggage.x() == area.x() && luggage.y() == area.y()) {
                    providedLuggage += 1;
                }
            }
        }
        if (providedLuggage == numberOfLuggages){
            isCompleted = true;
            repaint();
        }
    }
    private void restartLevel(){
        areas.clear();
        luggages.clear();
        walls.clear();

        initNewGame();
        if (isCompleted){isCompleted = false;}
    }
}