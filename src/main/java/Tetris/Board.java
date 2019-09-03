package Tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.TimerTask;
import java.util.Timer;

public class Board extends JPanel {
    private final int boardWidth = 10;
    private final int boardHeight = 22;
    private final int delay = 100;
    private final int periodDelay = 300;

    private Timer timer = new Timer();
    private boolean isFallingFinished = false;
    private boolean isStarted = false;
    private boolean isPaused = false;

    private int numberLinesDestroyed = 0;
    private int currentX = 0;
    private int currentY = 0;
    private JLabel statusBar;
    private Shape currentShape;
    private Tetrominoe[] board;

    Board(Tetris parent){
        initBoard(parent);

    }

    private void initBoard(Tetris parent) {
        setFocusable(true);
        timer.scheduleAtFixedRate(new ScheduleTask(), delay, periodDelay);
        currentShape = new Shape();
        statusBar = parent.getStatusBar();
        board = new Tetrominoe[boardWidth * boardHeight];
        addKeyListener(new TAdapter());
        clearBoard();
    }
    private int squareWidth(){
        return (int) getSize().getWidth() / boardWidth;
    }
    private int squareHeight(){
        return (int) getSize().getHeight() / boardHeight;
    }
    private Tetrominoe shapeAt(int x, int y) {
        return board[(y * boardWidth) + x];
    }
    public void start(){
        isStarted = true;
        clearBoard();
        newPiece();
    }

    private void clearBoard() {

        for (int i = 0; i < boardHeight * boardWidth; ++i) {
            board[i] = Tetrominoe.NoShape;
        }
    }

    private void pause() {

        if (!isStarted) {
            return;
        }

        isPaused = !isPaused;

        if (isPaused) {

            statusBar.setText("paused");
        } else {

            statusBar.setText(String.valueOf(numberLinesDestroyed));
        }
    }
    private void doDrawing(Graphics g) {

        Dimension size = getSize();
        int boardTop = (int) size.getHeight() - boardHeight * squareHeight();

        for (int i = 0; i < boardHeight; ++i) {

            for (int j = 0; j < boardWidth; ++j) {

                Tetrominoe shape = shapeAt(j, boardHeight - i - 1);

                if (shape != Tetrominoe.NoShape) {

                    drawSquare(g, j * squareWidth(),
                            boardTop + i * squareHeight(), shape);
                }
            }
        }

        if (currentShape.getShape() != Tetrominoe.NoShape) {

            for (int i = 0; i < 4; ++i) {

                int x = currentX + currentShape.x(i);
                int y = currentY - currentShape.y(i);
                drawSquare(g, x * squareWidth(),
                        boardTop + (boardHeight - y - 1) * squareHeight(),
                        currentShape.getShape());
            }
        }
    }



    private void drawSquare(Graphics g, int x, int y, Tetrominoe shape) {
        Color[] colors = {
                new Color(0, 0, 0), new Color(204, 102, 102),
                new Color(102, 204, 102), new Color(102, 102, 204),
                new Color(204, 204, 102), new Color(204, 102, 204),
                new Color(102, 204, 204), new Color(218, 170, 0)
        };

        Color color = colors[shape.ordinal()];

        g.setColor(color);
        g.fillRect(x + 1, y + 1, squareWidth() - 2, squareHeight() - 2);

        g.setColor(color.brighter());
        g.drawLine(x, y + squareHeight() - 1, x, y);
        g.drawLine(x, y, x + squareWidth() - 1, y);

        g.setColor(color.darker());
        g.drawLine(x + 1, y + squareHeight() - 1,
                x + squareWidth() - 1, y + squareHeight() - 1);
        g.drawLine(x + squareWidth() - 1, y + squareHeight() - 1,
                x + squareWidth() - 1, y + 1);

    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        doDrawing(g);
    }

    private void dropDown() {

        int newY = currentY;

        while (newY > 0) {

            if (!tryMove(currentShape, currentX, newY - 1)) {

                break;
            }

            --newY;
        }

        pieceDropped();
    }

    private void pieceDropped() {

        for (int i = 0; i < 4; ++i) {

            int x = currentX + currentShape.x(i);
            int y = currentY - currentShape.y(i);
            board[(y * boardWidth) + x] = currentShape  .getShape();
        }

        removeFullLines();

        if (!isFallingFinished) {
            newPiece();
        }
    }

    private void removeFullLines() {
        int numFullLines = 0;

        for (int i = boardHeight - 1; i >= 0; --i) {
            boolean lineIsFull = true;

            for (int j = 0; j < boardWidth; ++j) {

                if (shapeAt(j, i) == Tetrominoe.NoShape) {

                    lineIsFull = false;
                    break;
                }
            }

            if (lineIsFull) {

                ++numFullLines;

                for (int k = i; k < boardHeight - 1; ++k) {
                    for (int j = 0; j < boardWidth; ++j) {

                        board[(k * boardWidth) + j] = shapeAt(j, k + 1);
                    }
                }
            }
        }

        if (numFullLines > 0) {

            numberLinesDestroyed += numFullLines;
            statusBar.setText(String.valueOf(numberLinesDestroyed));
            isFallingFinished = true;
            currentShape.setShape(Tetrominoe.NoShape);
            repaint();
        }
    }

    private void newPiece() {
        currentShape.setRandomPiece();
        currentX = boardWidth / 2 + 1;
        currentY = boardHeight - 1 + currentShape.minY();

        if (!tryMove(currentShape, currentX, currentY)) {

            currentShape.setShape(Tetrominoe.NoShape);
            timer.cancel();
            isStarted = false;
            statusBar.setText("Game over");
        }
    }

    private void oneLineDown() {

        if (!tryMove(currentShape, currentX, currentY - 1)) {

            pieceDropped();
        }
    }

    private boolean tryMove(Shape newShape, int newX, int newY) {
        for (int i = 0; i < 4; ++i) {

            int x = newX + newShape.x(i);
            int y = newY - newShape.y(i);

            if (x < 0 || x >= boardWidth || y < 0 || y >= boardHeight) {
                return false;
            }

            if (shapeAt(x, y) != Tetrominoe.NoShape) {
                return false;
            }
        }

        currentShape = newShape;
        currentX = newX;
        currentY = newY;

        repaint();

        return true;
    }
    private void doGameCycle(){
        update();
        repaint();
    }
    private void update(){
        if (isPaused) {
            return;
        }

        if (isFallingFinished) {

            isFallingFinished = false;
            newPiece();
        } else {

            oneLineDown();
        }
    }

    private class ScheduleTask extends TimerTask {
        @Override
        public void run() {

            doGameCycle();
        }
    }

    private class TAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {

            System.out.println("key pressed");

            if (!isStarted || currentShape.getShape() == Tetrominoe.NoShape) {
                return;
            }

            int keycode = e.getKeyCode();

            if (keycode == KeyEvent.VK_P) {
                pause();
                return;
            }

            if (isPaused) {
                return;
            }

            switch (keycode) {

                case KeyEvent.VK_LEFT:
                    tryMove(currentShape, currentX - 1, currentY);
                    break;

                case KeyEvent.VK_RIGHT:
                    tryMove(currentShape, currentX + 1, currentY);
                    break;

                case KeyEvent.VK_DOWN:
                    tryMove(currentShape.turnRight(), currentX, currentY);
                    break;

                case KeyEvent.VK_UP:
                    tryMove(currentShape.turnLeft(), currentX, currentY);
                    break;

                case KeyEvent.VK_SPACE:
                    dropDown();
                    break;

                case KeyEvent.VK_D:
                    oneLineDown();
                    break;
            }
        }
    }
}
