package Snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SnakePanel extends JPanel implements ActionListener {
    private final int width = 600;
    private final int height = 600;
    private final int dotSize = 20;
    private final int allDots = (width / dotSize) * (height / dotSize);

    private final int[] x = new int[allDots];
    private final int[] y = new int[allDots];

    private int dots;
    private int foodPositionX;
    private int foodPositionY;

    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;
    private boolean inGame = true;

    private Timer timer;
    private Image bodyPart;
    private Image head;
    private Image food;

    SnakePanel() {
        initBoard();
    }

    private void initBoard() {
        addKeyListener(new MyKeyAdapter());
        setBackground(Color.DARK_GRAY);
        setFocusable(true);
        setPreferredSize(new Dimension(width, height));
        loadImages();
        initGame();
    }

    private void initGame() {

        dots = 3;
        for (int z = 0; z < dots; z++) {
            x[z] = 100 - z * 20;
            y[z] = 100;
        }
        findFood();

        int delay = 150;
        timer = new Timer(delay, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        doDrawing(graphics);
    }

    private void doDrawing(Graphics graphics) {
        if (inGame) {

            graphics.drawImage(food, foodPositionX, foodPositionY, this);

            for (int z = 0; z < dots; z++) {
                if (z == 0) {
                    graphics.drawImage(head, x[z], y[z], this);
                } else {
                    graphics.drawImage(bodyPart, x[z], y[z], this);
                }
            }

            Toolkit.getDefaultToolkit().sync();

        } else {

            gameOver(graphics);
        }

    }

    private void gameOver(Graphics graphics) {
        String message = "Game Over";
                graphics.drawString(message, width / 2, height / 2);

    }

    private void findFood() {
        int position = (width / dotSize) - 1;
        int r = (int) (Math.random() * position);
        foodPositionX = ((r * dotSize));

        r = (int) (Math.random() * position);
        foodPositionY = ((r * dotSize));
    }

    private void loadImages() {
        ImageIcon headImage = new ImageIcon("src/main/resources/Snake/head.png");
        ImageIcon bodyPartImage = new ImageIcon("src/main/resources/Snake/bodypart.png");
        ImageIcon foodImage = new ImageIcon("src/main/resources/Snake/food.png");

        head = headImage.getImage();
        bodyPart = bodyPartImage.getImage();
        food = foodImage.getImage();
    }

    private void checkFood() {
        if ((x[0] == foodPositionX) && (y[0] == foodPositionY)) {

            dots++;
            findFood();
        }
    }

    private void move() {
        for (int i = dots; i > 0; i--) {
            x[i] = x[(i - 1)];
            y[i] = y[(i - 1)];
        }
        if (left) {
            x[0] -= dotSize;
        }

        if (right) {
            x[0] += dotSize;
        }

        if (up) {
            y[0] -= dotSize;
        }

        if (down) {
            y[0] += dotSize;
        }
    }

    private void checkCollision() {

        for (int z = dots; z > 0; z--) {

            if ((z > 4) && (x[0] == x[z]) && (y[0] == y[z])) {
                inGame = false;
            }
        }

        if (y[0] >= height) {
            inGame = false;
        }

        if (y[0] < 0) {
            inGame = false;
        }

        if (x[0] >= width) {
            inGame = false;
        }

        if (x[0] < 0) {
            inGame = false;
        }

        if (!inGame) {
            timer.stop();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (inGame) {

            checkFood();
            checkCollision();
            move();
        }

        repaint();

    }

    private class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            if ((key == KeyEvent.VK_LEFT) && (!right)) {
                left = true;
                up = false;
                down = false;
            }

            if ((key == KeyEvent.VK_RIGHT) && (!left)) {
                right = true;
                up = false;
                down = false;
            }

            if ((key == KeyEvent.VK_UP) && (!down)) {
                up = true;
                right = false;
                left = false;
            }

            if ((key == KeyEvent.VK_DOWN) && (!up)) {
                down = true;
                right = false;
                left = false;
            }
        }
    }
}