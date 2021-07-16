

public enum Tetrominoes {

    NoShape(new int[][] {{0, 0}, {0, 0}, {0, 0}, {0, 0}}),
    ZShape(new int[][] {{0, -1}, {0, 0}, {-1, 0}, {-1, 1}}),
    SShape(new int[][] {{0, -1}, {0, 0}, {1, 0}, {1, 1}}),
    LineShape(new int[][] {{0, -1}, {0, 0}, {0, 1}, {0, 2}}),
    TShape(new int[][] {{-1, 0}, {0, 0}, {1, 0}, {0, 1}}),
    SquareShape(new int[][] {{0, 0}, {1, 0}, {0, 1}, {1, 1}}),
    LShape(new int[][] {{-1, -1}, {0, -1}, {0, 0}, {0, 1}}),
    MirroredLShape(new int[][] {{1, -1}, {0, -1}, {0, 0}, {0, 1}});

    private int[][] coords;

    private Tetrominoes(int[][] coords) {
        this.coords = coords;
    }
    
    public int getCoords(int x, int y) {
        return this.coords[x][y];   
    } 

}