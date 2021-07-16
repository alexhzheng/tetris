

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;


public class ShapeTest {
    
    @Test
    public void setLineShape() {
        Shape piece = new Shape();
        piece.setShape(Tetrominoes.LineShape);
        int[][] expected = {{0,-1}, {0,0}, {0,1}, {0,2}};
        assertArrayEquals(expected, piece.getCoords());
    }

    @Test
    public void testRotateLeftSquareShape() {
        Shape piece = new Shape();
        piece.setShape(Tetrominoes.SquareShape);
        Shape rotated = piece.rotateLeft();
        assertArrayEquals(piece.getCoords(), rotated.getCoords());
    }
    
    @Test
    public void testRotateRightSquareShape() {
        Shape piece = new Shape();
        piece.setShape(Tetrominoes.SquareShape);
        Shape rotated = piece.rotateRight();
        assertArrayEquals(piece.getCoords(), rotated.getCoords());
    }
    
    @Test
    public void testRotateLeftTShape() {
        Shape piece = new Shape();
        piece.setShape(Tetrominoes.TShape);
        Shape rotated = piece.rotateLeft();
        int[][] expected = {{0,-1}, {0,0}, {0,1}, {-1,0}};
        assertArrayEquals(expected, rotated.getCoords());
    }
    
    @Test
    public void testRotateRightTShape() {
        Shape piece = new Shape();
        piece.setShape(Tetrominoes.TShape);
        Shape rotated = piece.rotateRight();
        int[][] expected = {{0,1}, {0,0}, {0,-1}, {1,0}};
        assertArrayEquals(expected, rotated.getCoords());
    }
    

    

}