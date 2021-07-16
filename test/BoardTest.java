

import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;


import org.junit.jupiter.api.Test;


public class BoardTest {

    @Test
    public void updateNextPieceTest() {
        Board board = new Board(new Game());
        Board.fillNextPiecesList();
        Board.updateNextPiecesList();
        assertEquals(3, board.getQueue().size());
    }
    
    @Test
    public void fillNextPiecesListTest() {
        Board board = new Board(new Game());
        Board.fillNextPiecesList();
        assertEquals(3, board.getQueue().size());
    }
    
    
    @Test
    public void tryMoveLeftBoundaryTest() {
        Board board = new Board(new Game());
        Shape piece = new Shape();
        boolean possible = board.tryMove(piece, -1, 10);
        assertFalse(possible);
    }
    
    @Test
    public void tryMoveRightBoundaryTest() {
        Board board = new Board(new Game());
        Shape piece = new Shape();
        boolean possible = board.tryMove(piece, 1000, 10);
        assertFalse(possible);
    }
    
    @Test
    public void tryMoveTopBoundaryTest() {
        Board board = new Board(new Game());
        Shape piece = new Shape();
        boolean possible = board.tryMove(piece, 10, -1);
        assertFalse(possible);
    }

    @Test
    public void tryMoveBottomBoundaryTest() {
        Board board = new Board(new Game());
        Shape piece = new Shape();
        boolean possible = board.tryMove(piece, 10, 1000);
        assertFalse(possible);
    }

    @Test 
    public void newPieceFalseTest() {
        Board board = new Board(new Game());
        Board.fillNextPiecesList();
        Shape hold = new Shape();
        hold.setShape(Tetrominoes.TShape);
        board.setHoldPiece(hold);
        board.newPiece(false);
        Shape curr = board.getCurrPiece();
        assertEquals(hold.getShape(), curr.getShape());
    }
    
    @Test 
    public void newPieceTrueTest() {
        Board board = new Board(new Game());
        Board.fillNextPiecesList();
        LinkedList<Shape> pieces = board.getQueue();
        Shape piece = pieces.get(1);
        board.newPiece(true);
        Shape curr = pieces.getFirst();
        assertEquals(piece.getShape(), curr.getShape());
    }
    
    
    

    

}