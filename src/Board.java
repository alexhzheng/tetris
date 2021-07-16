



import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import javax.imageio.ImageIO;
import javax.swing.*;





@SuppressWarnings("serial")
public class Board extends JPanel implements ActionListener {
    
    private static final int BOARD_WIDTH = 10;
    private static final int BOARD_HEIGHT = 22;
    private Timer timer;
    private boolean isFallingFinished = false;
    private boolean isStarted = false;
    private boolean isPaused = false;
    private int numLinesRemoved = 0;
    private int currX = 0;
    private int currY = 0;
    private JLabel statusBar;
    private JLabel highScoreBar;
    private Shape currPiece;
    private Shape holdPiece;
    private static Queue<Shape> nextPieces; 
    private Tetrominoes[] board;
    private int holdCounter = 0;
    private String highScore = "";
    private boolean isHoldEmpty = true;
    
    
    static BufferedImage l;
    static BufferedImage li;
    static BufferedImage ml;
    static BufferedImage s;
    static BufferedImage sq;
    static BufferedImage t;
    static BufferedImage z;
   
    public Board(Game parent) {
        setFocusable(true);
        currPiece = new Shape();
        holdPiece = new Shape();
        nextPieces = new LinkedList<Shape>();
        timer = new Timer(300, this); // timer for lines down
        statusBar = parent.getStatusBar();
        highScoreBar = parent.getHighScoreBar();
        board = new Tetrominoes[BOARD_WIDTH * BOARD_HEIGHT];
        nextPieces = new LinkedList<Shape>();
        clearBoard();
        addKeyListener(new MyTetrisAdapter());
        
        //store images to be used later
        try {
            l = ImageIO.read(new File("images/l.png"));
            li = ImageIO.read(new File("images/li.png"));
            ml = ImageIO.read(new File("images/ml.png"));
            s = ImageIO.read(new File("images/s.png"));
            sq = ImageIO.read(new File("images/sq.png"));
            t = ImageIO.read(new File("images/t.png"));
            z = ImageIO.read(new File("images/z.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
   
    //gets width and height of one tetrominoe square
    public int getSquareWidth() {
        return (int) getSize().getWidth() / (BOARD_WIDTH + 3);
    }
   
    public int getSquareHeight() {
        return (int) getSize().getHeight() / BOARD_HEIGHT;
    }
   
    //determine the shape at the given coordinates
    public Tetrominoes shapeAt(int x, int y) {
        return board[y * BOARD_WIDTH + x];
    }
   
    //clears board by putting NoShapes in them
    public void clearBoard() {
        for (int i = 0; i < BOARD_HEIGHT * BOARD_WIDTH; i++) {
            board[i] = Tetrominoes.NoShape;
        }
    }
    
    public void pieceDropped() {
        for (int i = 0; i < 4; i++) {
            int x = currX + currPiece.getX(i);
            int y = currY - currPiece.getY(i);
            board[y * BOARD_WIDTH + x] = currPiece.getShape();
        }
   
        removeFullLines();
   
        if (!isFallingFinished) {
            newPiece(true);
        }
    }
   
    public void newPiece(boolean notHolding) {
        if (notHolding) {
            currPiece.setShape(((LinkedList<Shape>) nextPieces).getFirst().getShape());
            updateNextPiecesList();
            holdCounter = 0;
        } else {
            currPiece.setShape(holdPiece.getShape());
        }
        
        currX = BOARD_WIDTH / 2 + 1;
        currY = BOARD_HEIGHT - 1 + currPiece.minY();
   
        if (!tryMove(currPiece, currX, currY - 1)) {
            currPiece.setShape(Tetrominoes.NoShape);
            timer.stop();
            isStarted = false;
            statusBar.setText("Game Over");
            checkScore();   
            showHighScores();
        }
    }
   
    private void oneLineDown() {
        if (!tryMove(currPiece, currX, currY - 1)) {
            pieceDropped();
        }
    }
   
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (isFallingFinished) {
            isFallingFinished = false;
            newPiece(true);
        } else {
            oneLineDown();
        }
    } 
   
    private void drawSquare(Graphics g, int x, int y, Tetrominoes shape) {

        Color[] colors = {new Color(0, 0, 0), new Color(204, 102, 102),
            new Color(102, 204, 102), new Color(102, 102, 204),
                          new Color(204, 204, 102), new Color(204, 102, 204),
                             new Color(102, 204, 204), new Color(218, 170, 0)};

        Color color = colors[shape.ordinal()];

        g.setColor(color);
        g.fillRect(x + 1, y + 1, getSquareWidth() - 2, getSquareHeight() - 2);

        g.setColor(color.brighter());
        g.drawLine(x, y + getSquareHeight() - 1, x, y);
        g.drawLine(x, y, x + getSquareWidth() - 1, y);

        g.setColor(color.darker());
        g.drawLine(x + 1, y + getSquareHeight() - 1,
                x + getSquareWidth() - 1, y + getSquareHeight() - 1);
        g.drawLine(x + getSquareWidth() - 1, y + getSquareHeight() - 1,
                x + getSquareWidth() - 1, y + 1);
    }
    

    
    private void drawSquareNoGrid(Graphics g, int x, int y, Color color) {
        g.setColor(color);
        g.fillRect(x, y, getSquareWidth(), getSquareHeight());
        g.setColor(color.brighter());
    }

   
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Dimension size = getSize();
        int boardTop = (int) size.getHeight() - BOARD_HEIGHT * getSquareHeight();
   
        for (int i = 0; i < BOARD_HEIGHT; i++) {
            for (int j = 0; j < BOARD_WIDTH; j++) {
                Tetrominoes shape = shapeAt(j, BOARD_HEIGHT - i - 1);
   
                if (shape != Tetrominoes.NoShape) {
                    drawSquare(g, j * getSquareWidth(), boardTop + i * getSquareHeight(), shape);
                }
                
            }
            for (int j = 0; j < 3; j++) {
                drawSquareNoGrid(g, (j + BOARD_WIDTH) * getSquareWidth(),
                        boardTop + i * getSquareHeight(), Color.WHITE);
            }
        }
        if (currPiece.getShape() != Tetrominoes.NoShape) {
            for (int i = 0; i < 4; i++) {
                int x = currX + currPiece.getX(i);
                int y = currY - currPiece.getY(i);
                drawSquare(g, x * getSquareWidth(), 
                        boardTop + (BOARD_HEIGHT - y - 1) * getSquareHeight(),
                        currPiece.getShape());
            }
        }
        
        
        drawExtra(g);
        
        if (highScore.equals("")) {
            highScore = getHighScore();
        }
        
    }
    
    //read highest score from file
    public static String getHighScore() {
        FileReader fr = null;
        BufferedReader br = null;
        try {
            fr = new FileReader("Highscore.dat");
            br = new BufferedReader(fr);
            return br.readLine();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            return "nobody:0";
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    //show highscores at the end of game if there are highscores
    public void showHighScores() {
        FileReader fr = null;
        BufferedReader br = null;
        String scores = "";
        String result = "";
        try {
            fr = new FileReader("Highscore.dat");
            br = new BufferedReader(fr);
            while ((scores = br.readLine()) != null) {
                result += scores + "\n";
            }
            JOptionPane.showMessageDialog(null, result, 
                    "LeaderBoards", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            return;
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    
    public void checkScore() {
        //format alex:1
        if (numLinesRemoved > Integer.parseInt(highScore.split(":")[1])) {
            String name = JOptionPane.showInputDialog("You set a new highscore. Enter name: ");
            this.highScore = name + ":" + numLinesRemoved + "\n";
            
            File scoreFile = new File("Highscore.dat");
            if (!scoreFile.exists()) {
                try {
                    scoreFile.createNewFile();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            
            FileWriter fw = null;
            BufferedWriter bw = null;
            FileReader fr = null;
            BufferedReader br = null;
            String scores = "";
            String result = "";
            
            //create new file to prepend highest score at top
            try {
                fr = new FileReader("Highscore.dat");
                br = new BufferedReader(fr);
                while ((scores = br.readLine()) != null) {
                    result += scores + "\n";
                }
            } catch (Exception e) {
                return;
            } finally {
                try {
                    if (br != null) {
                        br.close();
                    }
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            
            try {
                fw = new FileWriter(scoreFile);
                bw = new BufferedWriter(fw);
                bw.write(this.highScore);
                bw.write(result);
            } catch (Exception e) {
                return;
            } finally {
                if (bw != null) {
                    try {
                        bw.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }

        highScoreBar.setText("highscore: " + Board.getHighScore());
    }
    
    public void start() {
        if (isPaused) {
            return;
        }
   
        isStarted = true;
        isFallingFinished = false;
        numLinesRemoved = 0;
        clearBoard();
        fillNextPiecesList();
        newPiece(true);
        timer.start();
    }
   
    public void pause() {
        if (!isStarted) {
            return;
        }
   
        isPaused = !isPaused;
   
        if (isPaused) {
            timer.stop();
            statusBar.setText("Paused");
        } else {
            timer.start();
            statusBar.setText("score: " + String.valueOf(numLinesRemoved));
        }
   
        repaint();
    }
   
    //test to see if move is possible
    public boolean tryMove(Shape newPiece, int newX, int newY) {
        for (int i = 0; i < 4; i++) {
            int x = newX + newPiece.getX(i);
            int y = newY - newPiece.getY(i);
   
            if (x < 0 || x >= BOARD_WIDTH || y < 0 || y >= BOARD_HEIGHT) {
                return false;
            }
   
            if (shapeAt(x, y) != Tetrominoes.NoShape) {
                return false;
            }
          
        }
   
        currPiece = newPiece;
        currX = newX;
        currY = newY;
        repaint();
   
        return true;
    }
   
    //remove full lines
    private void removeFullLines() {
        int numFullLines = 0;
   
        for (int i = BOARD_HEIGHT - 1; i >= 0; i--) {
            boolean lineIsFull = true;
   
            for (int j = 0; j < BOARD_WIDTH; ++j) {
                if (shapeAt(j, i) == Tetrominoes.NoShape) {
                    lineIsFull = false;
                    break;
                }
            }
   
            if (lineIsFull) {
                numFullLines++;
   
                for (int k = i; k < BOARD_HEIGHT - 1; k++) {
                    for (int j = 0; j < BOARD_WIDTH; j++) {
                        board[k * BOARD_WIDTH + j] = shapeAt(j, k + 1);
                    }
                }
            }
   
            if (numFullLines > 0) {
                numLinesRemoved += numFullLines;
                statusBar.setText("score: " + String.valueOf(numLinesRemoved));
                isFallingFinished = true;
                currPiece.setShape(Tetrominoes.NoShape);
                repaint();
            }
        }
    }
   
    //function to dropDown to bottom
    private void dropDown() {
        int newY = currY;
   
        while (newY > 0) {
            if (!tryMove(currPiece, currX, newY - 1)) {
                break;
            }
   
            newY--;
        }
   
        pieceDropped();
    }
    
    //function to hold pieces
    public void hold() {
        if (holdCounter == 1)  {
            return;
        }

        Shape tempShape = new Shape();

        tempShape.setShape(currPiece.getShape());
        if (holdPiece.getShape() == Tetrominoes.NoShape) {
            newPiece(isHoldEmpty);
            holdPiece.setShape(tempShape.getShape());
            isHoldEmpty = !isHoldEmpty;
        } else {
            newPiece(false);
            holdPiece.setShape(tempShape.getShape());
        }
        holdCounter = 1;
    }
    
    public Shape getHoldPiece() {
        return holdPiece;
    }
    
    public Shape getCurrPiece() {
        return currPiece;
    }
    
    public void setHoldPiece(Shape piece) {
        holdPiece = piece;
    }
    
    
    
    
    //can see 3 upcoming pieces
    public static void fillNextPiecesList() {
        for (int i = 0; i < 3; i++) {
            Shape random = new Shape();
            random.setRandomShape();
            nextPieces.add(random);
        }
    }
    
    
    public static void updateNextPiecesList() {
        Shape tempShape = nextPieces.remove(); //remove from head
        tempShape.setRandomShape();
        nextPieces.add(tempShape);//add to tail
    }
    
    public LinkedList<Shape> getQueue() {
        return (LinkedList<Shape>) nextPieces;
    }
    
    //draws the hold pieces and upcoming pieces
    //used images online for these
    private void drawExtra(Graphics g) {
        Dimension size = getSize();
        int boardTop = (int) size.getHeight() - BOARD_HEIGHT * getSquareHeight();
        int b = (BOARD_WIDTH) * getSquareWidth();

        g.drawString("Hold Piece", b, boardTop + getSquareHeight());

        Image tempImage = getShapeImage(holdPiece.getShape());
        g.drawImage(tempImage, b,boardTop + (getSquareHeight()), getSquareWidth() * 3, 
                getSquareHeight() * 3, null);

        g.drawString("Upcoming Piece", b , boardTop + 6 * getSquareHeight());


        for (Shape s : nextPieces) {
            tempImage = getShapeImage(s.getShape());
            g.drawImage(tempImage, b,boardTop + 
                    (((LinkedList<Shape>) nextPieces).indexOf(s) * 3 + 6) 
                    * getSquareHeight(), 
                    getSquareWidth() * 3 , getSquareHeight() * 3, null);
        }
    }

    public static Image getShapeImage(Tetrominoes tetrominoes) {
        switch (tetrominoes) {
            case LShape:
                return l;
            case LineShape:
                return li;
            case MirroredLShape:
                return ml;
            case SShape:
                return s;
            case SquareShape:
                return sq;
            case TShape:
                return t;
            case ZShape:
                return z;
            default:
                break;
        }
        return null;
    }

    

    class MyTetrisAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent ke) {
            if (!isStarted || currPiece.getShape() == Tetrominoes.NoShape) {
                return;
            }
   
            int keyCode = ke.getKeyCode();
   
            if (keyCode == 'p' || keyCode == 'P') {
                pause();
            }
   
            if (isPaused) {
                return;
            }
   
            switch (keyCode) {
                case KeyEvent.VK_LEFT:
                    tryMove(currPiece, currX - 1, currY);
                    break;
                case KeyEvent.VK_RIGHT:
                    tryMove(currPiece, currX + 1, currY);
                    break;
                case KeyEvent.VK_DOWN:
                    tryMove(currPiece.rotateRight(), currX, currY);
                    break;
                case KeyEvent.VK_UP:
                    tryMove(currPiece.rotateLeft(), currX, currY);
                    break;
                case KeyEvent.VK_SPACE:
                    dropDown();
                    break;
                case KeyEvent.VK_CONTROL:
                    hold();
                    break;
                case 'd':
                case 'D':
                    oneLineDown();
                    break;
                default:
                    break;
            }
   
        }
    }
   
}
