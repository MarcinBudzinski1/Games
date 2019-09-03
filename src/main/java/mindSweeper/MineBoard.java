package mindSweeper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class MineBoard extends JPanel {

    private final int cellSize = 30;

    private final int coveredCell = 10;
    private final int markedCell = 10;
    private final int emptyCell = 0;
    private final int minedCell = 9;
    private final int coveredMinedCell = coveredCell + minedCell;
    private final int markedMinedCell = coveredMinedCell + markedCell;

    private final int numberOfRows = 16;
    private final int numberOfColumns = 16;

    private int[] field;
    private boolean inGame;
    private int minesLeft;
    private Image[] img;

    private int allCells;
    private final JLabel gameBar;

    public MineBoard(JLabel gameBar){

        this.gameBar = gameBar;
        initBoard();
    }

    private void initBoard() {

        int boardWidth = numberOfRows * cellSize;
        int boardHeight = numberOfColumns * cellSize;
        setPreferredSize(new Dimension(boardWidth, boardHeight));

        int numberOfImages = 13;
        img = new Image[numberOfImages];

        for (int i = 0; i < numberOfImages; i++){
            String path = "src/main/resources/mineSweeper/" + i + ".png";
            img[i] = (new ImageIcon(path)).getImage();
        }

        addMouseListener(new MinesAdapter());
        newGame();
    }

    private void newGame(){

        int cell;

        Random random = new Random();
        inGame = true;
        int numberOfMines = 40;
        minesLeft = numberOfMines;

        allCells = numberOfRows * numberOfColumns;
        field = new int[allCells];

        for (int i = 0; i < allCells; i++){
            field[i] = coveredCell;
        }

        gameBar.setText(Integer.toString(minesLeft));

        int i = 0;

        while (i < numberOfMines){

            int position = (int) (allCells * random.nextDouble());

            if ((position < allCells)
                    && (field[position] != coveredMinedCell )){

                int currentColumn = position % numberOfColumns;
                field[position] =coveredMinedCell;
                i++;

                if (currentColumn > 0) {
                    cell = position - 1 - numberOfColumns;
                    if (cell >= 0) {
                        if (field[cell] != coveredMinedCell)
                            {field[cell] += 1;
                        }
                    }
                    cell = position - 1;
                    if (cell >= 0) {
                        if (field[cell] != coveredMinedCell) {
                            field[cell] += 1;
                        }
                    }

                    cell = position + numberOfColumns -1;
                    if (cell < allCells) {
                        if (field[cell] != coveredMinedCell)
                        {field[cell] +=1;
                        }
                    }
                }
                cell = position - numberOfColumns;
                if (cell >= 0) {
                    if (field[cell] != coveredMinedCell) {
                        field[cell] += 1;
                    }
                }

                cell = position + numberOfColumns;
                if (cell < allCells) {
                    if (field[cell] != coveredMinedCell) {
                        field[cell] += 1;
                    }
                }
                if (currentColumn < (numberOfColumns - 1)) {
                    cell = position - numberOfColumns + 1;
                    if (cell >= 0) {
                        if (field[cell] != coveredMinedCell) {
                            field[cell] += 1;
                        }
                    }
                    cell = position + numberOfColumns + 1;
                    if (cell < allCells) {
                        if (field[cell] != coveredMinedCell) {
                            field[cell] += 1;
                        }
                    }
                    cell = position + 1;
                    if (cell < allCells) {
                        if (field[cell] != coveredMinedCell) {
                            field[cell] += 1;
                        }
                    }
                }

                    }
                }
            }
            private void findEmptyCells(int j){
        int currentColumn = j % numberOfColumns;
        int cell;
                if (currentColumn > 0) {
                    cell = j - numberOfColumns - 1;
                    if (cell >= 0) {
                        if (field[cell] > minedCell) {
                            field[cell] -= coveredCell;
                            if (field[cell] == emptyCell) {
                                findEmptyCells(cell);
                            }
                        }
                    }

                    cell = j - 1;
                    if (cell >= 0) {
                        if (field[cell] > minedCell) {
                            field[cell] -= coveredCell;
                            if (field[cell] == emptyCell) {
                                findEmptyCells(cell);
                            }
                        }
                    }

                    cell = j + numberOfColumns - 1;
                    if (cell < allCells) {
                        if (field[cell] > minedCell) {
                            field[cell] -= coveredCell;
                            if (field[cell] == emptyCell) {
                                findEmptyCells(cell);
                            }
                        }
                    }
                }

                cell = j - numberOfColumns;
                if (cell >= 0) {
                    if (field[cell] > minedCell) {
                        field[cell] -= coveredCell;
                        if (field[cell] == emptyCell) {
                            findEmptyCells(cell);
                        }
                    }
                }

                cell = j + numberOfColumns;
                if (cell < allCells) {
                    if (field[cell] > minedCell) {
                        field[cell] -= coveredCell;
                        if (field[cell] == emptyCell) {
                            findEmptyCells(cell);
                        }
                    }
                }

                if (currentColumn < (numberOfColumns - 1)) {
                    cell = j - numberOfColumns + 1;
                    if (cell >= 0) {
                        if (field[cell] > minedCell) {
                            field[cell] -= coveredCell;
                            if (field[cell] == emptyCell) {
                                findEmptyCells(cell);
                            }
                        }
                    }

                    cell = j + numberOfColumns + 1;
                    if (cell < allCells) {
                        if (field[cell] > minedCell) {
                            field[cell] -= coveredCell;
                            if (field[cell] == emptyCell) {
                                findEmptyCells(cell);
                            }
                        }
                    }

                    cell = j + 1;
                    if (cell < allCells) {
                        if (field[cell] > minedCell) {
                            field[cell] -= coveredCell;
                            if (field[cell] == emptyCell) {
                                findEmptyCells(cell);
                            }
                        }
                    }
                }

            }
        @Override
        public void paintComponent(Graphics graphics){
        int uncover = 0;
        for(int i = 0; i < numberOfRows; i++) {
            for (int j = 0; j < numberOfColumns; j++) {
                int cell = field[(i * numberOfColumns) + j];
                if (inGame && cell == minedCell) {
                    inGame = false;
                }
                int drawCover = 10;
                int drawMark = 11;
                if (!inGame) {
                    if (cell == coveredMinedCell){
                        cell = 9;
                    } else if (cell == markedMinedCell){
                        cell = drawMark;
                    } else if (cell > coveredMinedCell) {
                        cell = 12;
                    } else if (cell > minedCell) {
                        cell = drawCover;
                    }
                } else {
                    if (cell > coveredMinedCell) {
                        cell = drawMark;
                    } else if (cell > minedCell) {
                        cell = drawCover;
                        uncover++;
                    }
                }
                graphics.drawImage(img[cell], (j * cellSize), (i * cellSize), this);
            }
        }
        if (uncover == 0 && inGame) {
            inGame = false;
            gameBar.setText("Gratulacje! Wygrana!");
        } else if (!inGame) {gameBar.setText("Porażka");}
        }
        private class  MinesAdapter extends MouseAdapter{
        @Override
        public void mousePressed(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();

            int cCol = x / cellSize;
            int cRow = y / cellSize;

            boolean doRepaint = false;

            if (!inGame) {
                newGame();
                repaint();
            }
            if ((x < numberOfColumns * cellSize) && (y < numberOfRows * cellSize)) {
                if ((e.getButton() == MouseEvent.BUTTON3)) {
                    if (field[(cRow * numberOfColumns) + cCol] > minedCell) {
                        doRepaint = true;
                        if (field[(cRow * numberOfColumns) + cCol] <= coveredMinedCell) {
                            if (minesLeft > 0) {
                                field[(cRow * numberOfColumns) + cCol] += markedCell;
                                minesLeft--;
                                String message = Integer.toString(minesLeft);
                                gameBar.setText(message);
                            } else {
                                gameBar.setText("Wszystkie użyte");
                            }
                        } else {
                            field[(cRow * numberOfColumns) + cCol] -= markedCell;
                            minesLeft++;
                            String message = Integer.toString(minesLeft);
                            gameBar.setText(message);
                        }
                    }
                } else {
                        if (field[(cRow * numberOfColumns) + cCol] > coveredMinedCell) {
                            return;
                        }
                        if ((field[(cRow * numberOfColumns) + cCol] > minedCell)
                                && (field[(cRow * numberOfColumns) + cCol] < markedMinedCell)) {
                            field[(cRow * numberOfColumns) + cCol] -= coveredCell;
                            doRepaint = true;

                            if (field[(cRow * numberOfColumns) + cCol] == minedCell) {
                                inGame = false;
                            }

                            if (field[(cRow * numberOfColumns) + cCol] == emptyCell) {
                                findEmptyCells((cRow * numberOfColumns) + cCol);
                        }
                        }
                    }
                    if (doRepaint) {
                        repaint();
                    }
                }
            }
        }
    }
