

import java.awt.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class Game extends JFrame implements Runnable {
    
    private JLabel statusBar;
    private JLabel highScoreBar;
   
    public JLabel getStatusBar() {
        return statusBar;
    }
    
    public JLabel getHighScoreBar() {
        return highScoreBar;
    }
 
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Game());
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        JFrame instructions = new JFrame();
        JOptionPane.showMessageDialog(instructions,"Welcome to my Tetris Game!\n"
                + "Here are the instructions: \n"
                + "left arrow key: move left\n"
                + "right arrow key: move right\n"
                + "up arrow key: rotate left\n"
                + "down arrow key: rotate right\n"
                + "d key: move down one line\n"
                + "ctrl key: hold a piece\n"
                + "p key: pause/unpause game",
                "Instructions",
                JOptionPane.INFORMATION_MESSAGE);  
        
        
        statusBar = new JLabel("score: 0");
        highScoreBar = new JLabel("highscore: " + Board.getHighScore());
        
        
        JPanel p = new JPanel(new GridLayout(0, 1)); 
        p.add(statusBar);
        p.add(highScoreBar);
        add(p, BorderLayout.SOUTH);
        
        Board board = new Board(this);
        add(board);
        board.start();
        setSize(600, 1000);
        setTitle("Tetris");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
   
}

