package spaceInvanders;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class ArkanoidPanel extends JPanel implements Runnable, Commons {

    private Dimension dimension;
    private ArrayList<Alien> aliens;
    private Player player;
    private Shot shot;

    private final int alienInitX = 150;
    private final int alienInitY = 5;
    private int direction = -1;
    private int deaths = 0;

    private boolean inGame = true;
    private final String explosionImg = "src/images/explosion.png";
    private String message = "Game Over";
    private Thread animator;

    ArkanoidPanel() {
        initPanel();
    }

    private void initPanel() {
        addKeyListener(new TAdapter());
        setFocusable(true);
        dimension = new Dimension(boardWidth, boardHeight);
        setBackground(Color.black);

        gameInit();
        setDoubleBuffered(true);
    }

    @Override
    public void addNotify(){
        super.addNotify();
        gameInit();
    }

    private void gameInit() {
        aliens = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 6; j++) {

                Alien alien = new Alien(alienInitX + 18 * j, alienInitY + 18 * i);
                aliens.add(alien);
            }
        }
        player = new Player();
        shot = new Shot();
        if (animator == null || !inGame) {

            animator = new Thread(this);
            animator.start();
        }
    }

    @Override
    public void run() {
        long beforeTime, timeDiff, sleep;

        beforeTime = System.currentTimeMillis();

        while (inGame) {

            repaint();
            animationCycle();

            timeDiff = System.currentTimeMillis() - beforeTime;
            sleep = delay - timeDiff;

            if (sleep < 0) {
                sleep = 2;
            }

            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                System.out.println("interrupted");
            }

            beforeTime = System.currentTimeMillis();
        }

        gameOver();
    }

    private void drawAliens(Graphics graphics) {
        Iterator iterator = aliens.iterator();
        for (Alien alien : aliens) {
            if (alien.isVisible()) {
                graphics.drawImage(alien.getImage(), alien.getX(), alien.getY(), this);
            }
            if (alien.isDying()) {
                alien.die();
            }
        }

    }

    private void drawPlayer(Graphics graphics) {
        if (player.isVisible()) {
            graphics.drawImage(player.getImage(), player.getX(), player.getY(), this);
        }
        if (player.isDying()) {
            player.die();
            inGame = false;
        }
    }

    private void drawShot(Graphics graphics) {
        if (shot.isVisible()) {
            graphics.drawImage(shot.getImage(), shot.getX(), shot.getY(), this);
        }
    }

    private void drawBombing(Graphics graphics) {
        for (Alien a : aliens) {
            Alien.Bomb bomb = a.getBomb();
            if (!bomb.isDestroyed()) {
                graphics.drawImage(bomb.getImage(), bomb.getX(), bomb.getY(), this);
            }
        }
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, dimension.width, dimension.height);
        graphics.setColor(Color.GREEN);

        if (inGame) {
            graphics.drawLine(0, ground, boardWidth, ground);
            drawAliens(graphics);
            drawPlayer(graphics);
            drawShot(graphics);
            drawBombing(graphics);
        }
        Toolkit.getDefaultToolkit().sync();
        graphics.dispose();
    }

    private void gameOver() {
        Graphics graphics = this.getGraphics();

        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, boardWidth, boardHeight);
        graphics.setColor(new Color(0, 32, 48));
        graphics.fillRect(50, boardWidth / 2 - 30, boardWidth - 100, 50);
        graphics.setColor(Color.white);
        graphics.fillRect(50, boardWidth / 2 - 30, boardWidth - 100, 50);

        Font font = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metrics = this.getFontMetrics(font);
        graphics.setColor(Color.white);
        graphics.setFont(font);
        graphics.drawString(message, (boardWidth - metrics.stringWidth(message)) / 2, boardWidth / 2);
    }


    private void animationCycle() {
        if (deaths == numberOfALiens) {
            inGame = false;
            message = "Game Won!";
        }
        player.act();

        if (shot.isVisible()) {
            int shotX = shot.getX();
            int shotY = shot.getY();

            for (Alien alien : aliens) {
                int alienX = alien.getX();
                int alienY = alien.getY();

                if (alien.isVisible() && shot.isVisible()) {
                    if (shotX >= (alienX)
                            && shotX <= (alienX + alienWidth)
                            && shotY >= (alienY)
                            && shotY <= (alienY - alienHeight)
                    ) {
                        ImageIcon explosionIcon = new ImageIcon(explosionImg);
                        alien.setImage(explosionIcon.getImage());
                        alien.setDying(true);
                        deaths++;
                        shot.die();
                    }
                }
            }
            int y = shot.getY();
            y -= 4;
            if (y < 0) {
                shot.die();
            } else {
                shot.setY(y);
            }
        }

        for (Alien alien : aliens) {
            int x = alien.getX();
            if (x >= boardWidth - borderRight && direction != -1) {
                direction = -1;

                for (Alien a2 : aliens) {

                    a2.setY(a2.getY() + goDown);
                }
            }
            if (x <= borderLeft && direction != 1) {

                direction = 1;

                for (Alien a : aliens) {

                    a.setY(a.getY() + goDown);
                }
            }
        }
        for (Alien alien : aliens) {
            if (alien.isVisible()) {
                int y = alien.getY();
                if (y > ground - alienHeight) {
                    inGame = false;
                    message = "Invasion!";
                }
                alien.act(direction);
            }
        }
        Random random = new Random();
        for (Alien alien : aliens) {
            int shot = random.nextInt(15);
            Alien.Bomb b = alien.getBomb();

            if (shot == chance && alien.isVisible() && b.isDestroyed()) {

                b.setDestroyed(false);
                b.setX(alien.getX());
                b.setY(alien.getY());
            }

            int bombX = b.getX();
            int bombY = b.getY();
            int playerX = player.getX();
            int playerY = player.getY();

            if (player.isVisible() && !b.isDestroyed()) {

                if (bombX >= (playerX)
                        && bombX <= (playerX + playerWidth)
                        && bombY >= (playerY)
                        && bombY <= (playerY + playerHeight)) {
                    ImageIcon explosionIcon = new ImageIcon(explosionImg);
                    player.setImage(explosionIcon.getImage());
                    player.setDying(true);
                    b.setDestroyed(true);
                }
            }
            if (!b.isDestroyed()) {

                b.setY(b.getY() + 1);

                if (b.getY() >= ground - boardHeight) {
                    b.setDestroyed(true);
                }
            }
        }
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {

            player.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {

            player.keyPressed(e);

            int x = player.getX();
            int y = player.getY();

            int key = e.getKeyCode();

            if (key == KeyEvent.VK_SPACE) {

                if (inGame) {
                    if (!shot.isVisible()) {
                        shot = new Shot(x, y);
                    }
                }
            }
        }
    }
}