
import java.util.Arrays;
import java.util.Random;

public class Shape implements Comparable<Shape> {
    
   
    private Tetrominoes pieceShape;
    private int[][] coords;
   
    public Shape() {
        coords = new int[4][2];
        setShape(Tetrominoes.NoShape);
    }
   
    public void setShape(Tetrominoes shape) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 2; j++) {
                coords[i][j] = shape.getCoords(i, j);
            }
        }
   
        pieceShape = shape;
    }
    
    public int[][] getCoords() {
        return this.coords;   
    }  
    
    //helper functions for rotation
    private void setX(int index, int x) {
        coords[index][0] = x;
    }
   
    private void setY(int index, int y) {
        coords[index][1] = y;
    }
   
    public int getX(int index) {
        return coords[index][0];
    }
   
    public int getY(int index) {
        return coords[index][1];
    }
   
    public Tetrominoes getShape() {
        return pieceShape;
    }
   
    public void setRandomShape() {
        Random r = new Random();
        int x = Math.abs(r.nextInt()) % 7 + 1;
        Tetrominoes[] values = Tetrominoes.values();
        setShape(values[x]);
    }
   
    public int minX() {
        int m = coords[0][0];
   
        for (int i = 0; i < 4; i++) {
            m = Math.min(m, coords[i][0]);
        }
   
        return m;
    }
   
    public int minY() {
        int m = coords[0][1];
   
        for (int i = 0; i < 4; i++) {
            m = Math.min(m, coords[i][1]);
        }
   
        return m;
    }
   
    public Shape rotateRight() {
        if (pieceShape == Tetrominoes.SquareShape) {
            return this;
        }
   
        Shape result = new Shape();
        result.pieceShape = pieceShape;
   
        for (int i = 0; i < 4; i++) {
            result.setX(i, getY(i));
            result.setY(i, -getX(i));
        }
   
        return result;
    }
   
    public Shape rotateLeft() {
        if (pieceShape == Tetrominoes.SquareShape) {
            return this;
        }
   
        Shape result = new Shape();
        result.pieceShape = pieceShape;
   
        for (int i = 0; i < 4; i++) {
            result.setX(i, -getY(i));
            result.setY(i, getX(i));
        }
   
        return result;
    }

    

    @Override
    public int compareTo(Shape o) {
        // TODO Auto-generated method stub
        if (Arrays.deepEquals(this.coords, o.getCoords())) {
            return 0;
        } else {
            return -1;
        }
    }
    
    
   
}
