package Tetris;

import java.util.Random;

public class Shape{
    private Tetrominoe pieceShape;
    private int[][] cordinates;
    private int[][][] cordinatesTable;

    Shape(){
        initShape();
    }

    private void initShape() {
        cordinates = new int[4][2];
        setShape(Tetrominoe.NoShape);
    }

    void setShape(Tetrominoe shape) {
        cordinatesTable = new int[][][] {
                { { 0, 0 },   { 0, 0 },   { 0, 0 },   { 0, 0 } },
                { { 0, -1 },  { 0, 0 },   { -1, 0 },  { -1, 1 } },
                { { 0, -1 },  { 0, 0 },   { 1, 0 },   { 1, 1 } },
                { { 0, -1 },  { 0, 0 },   { 0, 1 },   { 0, 2 } },
                { { -1, 0 },  { 0, 0 },   { 1, 0 },   { 0, 1 } },
                { { 0, 0 },   { 1, 0 },   { 0, 1 },   { 1, 1 } },
                { { -1, -1 }, { 0, -1 },  { 0, 0 },   { 0, 1 } },
                { { 1, -1 },  { 0, -1 },  { 0, 0 },   { 0, 1 } }
        };
        for (int i = 0; i < 4 ; i++) {
            System.arraycopy(cordinatesTable[shape.ordinal()][i], 0, cordinates[i], 0, 2);
        }
        pieceShape = shape;
    }

    private void setX(int index, int x) {cordinates[index][0] = x;}

    private void setY(int index, int y) {cordinates[index][1] = y;}

    public int x(int index) {return cordinates[index][0];}

    public int y(int index) {return cordinates[index][1];}

    Tetrominoe getShape() {return pieceShape;}

    void setRandomPiece(){
        Random random = new Random();
        int x = Math.abs((random.nextInt()) & 7 +1);
        Tetrominoe[] values = Tetrominoe.values();
        setShape(values[x]);
    }

    public int minX(){
        int min = cordinates[0][0];
        for (int i = 0; i < 4; i++){
            min = Math.min(min, cordinates[i][0]);
        }
        return min;
    }

    int minY(){
        int min = cordinates[0][1];
        for (int i = 0; i < 4; i++){
            min = Math.min(min, cordinates[i][1]);
        }
        return min;
    }

    Shape turnLeft(){

        if (pieceShape == Tetrominoe.OShape) {return this;}
        Shape result = new Shape();
        result.pieceShape = pieceShape;

        for (int i = 0;  i < 4; i++){
            result.setX(i, y(i));
            result.setY(i, -x(i));
        }
        return result;
    }
    Shape turnRight(){

        if (pieceShape == Tetrominoe.OShape) {return this;}
        Shape result = new Shape();
        result.pieceShape = pieceShape;

        for (int i = 0; i < 4; i++){
            result.setX(i, -y(i));
            result.setY(i, x(i));
        }
        return result;
    }
}
