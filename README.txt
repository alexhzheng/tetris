=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 120 Game Project README
PennKey: alexhz
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an appropriate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

  1. 2-D array - I used 2-D int arrays to represent the coordinates of each Tetrominoe. 
  				 This is appropriate because each piece has an X and Y coordinate on the board. 
  				 I can use these X and Y coordinates to check if the pieces are at the boundaries.
  				 I also rotate the tetrominoe by manipulating the 2-D array.
  				 I realized that I don't need to make the board a 2-D array and instead use an array of tetrominoes.

  2. I/O File - I used I/O File by reading and writing username/highscores to a dat file. 
  			    I used a dat file because it cannot be easily changed like a txt file.
  			    I used bufferedreader/bufferedwriter to implement this feature.
  			    I am able to store highscores from highest to lowest.
  			    I did not need to use I/O File to pause the game because I just stopped the timer and made sure repaint is not updating.
 
  3. Collections - I used collections to store upcoming pieces in a linkedlist. This way, I am able to display the upcoming pieces for users to see.
  				   I used a linkedlist because it is easy to add to the tail and remove from the head using the built in queue methods.	
  				   I also needed these pieces to be ordered by when they are added and Linkedlist successfully does this.

  4. Testable Component - I modeled my game based off MVC so I can test each part without testing the GUI first. 
  						  I was able to do this because I set a tetrominoe piece as a 2-d array and each function corresponds to the coordinates.

=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.
  
  I made three classes: Board, Shape, and Game.
  The Board class has all the main functions of the game. I draw the tetrominoe squares, the board and use keylisteners in this class.
  The Shape class creates the tetrominoes as 2-D arrays. I manipulate the tetrominoes by creating rotate functions in this class. This class also sets the shape that drops.
  The Game class creates the GUI in the form of JLabels and JFrames. I display the instruction window from this class and also run the whole game.

- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?
  
  I couldn't show these hold and upcoming pieces correctly, so instead of drawing them using my drawSquares method, I decided to find images online and display those instead. 
  I also had trouble showing the leaderboard. When I first created the methods, the file would be written by lowest score first. I was appending each new highscore to the file instead of prepending it.

- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?

  I feel like my design is well made. One part I would like to refactor is my highscore/leaderboard methods. I repeated a lot of code for each method because I didn't know how to combine them without having errors.
  I feel there is a good separation of functionality because each class has its own use.
  The private states are well encapsulated because I set all the fields to private. I didn't create any set methods so these methods cannot be changed.

========================
=: External Resources :=
========================

- Cite any external resources (libraries, images, tutorials, etc.) that you may
  have used while implementing your game.
  
  I found images of tetrimonoes off Google Images to be displayed to show hold piece and upcoming pieces.