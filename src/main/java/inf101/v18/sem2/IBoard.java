package inf101.v18.sem2;

import java.util.ArrayList;

public interface IBoard {

 Move getLastMove();

 int getLastSymbolPlayed();

    /**
     *
     * @param lastLetterPlayed
     */
 void setLastSymbolPlayed(int lastLetterPlayed);

    /**
     *
     * @return int value of winner.
     */
 int getWinner();

 /**
  * Sets the winner int.
  * @param winner
  */
 void setWinner(int winner);

 /**
  *
  * @return True or false, depending if it's game over.
  */
 boolean isGameOver();

 /**
  * Sets the boolean gameOver.
  * @param isGameOver
  */
 void setGameOver(boolean isGameOver);

 /**
  *
  * @return If a column is overflowed
  */
 boolean isOccurredOverflow();

 /**
  * Sets the overflow boolean
  * @param occurredOverflow
  */
 void setOccurredOverflow(boolean occurredOverflow);

 /**
  * Makes a move on the board
  * @param col
  * @param letter
  */
 void makeMove(int col, int letter);

 /**
  *
  * @param row
  * @param col
  * @return canMove if the space is vacant
  */
 boolean canMove(int row, int col);

 /**
  *
  * @param col
  * @return fullColumn
  */
 boolean checkFullColumn(int col);

 /**
  *
  * @param col
  * @return Row
  */
 int getRowPosition(int col);

 /**
  *
  * @param letter
  * @return list of children
  */
 ArrayList<Board> getChildren(int letter);

 /**
  *Evaluates moves
  * @return evaluated value
  */
 int evaluate();

 /**
  * Checks if someone has won
  * @return Winner
  */
 boolean checkWinState();

 /**
  * Checks if it's game over
  * @return GameOver
  */
 boolean checkGameOver();

 /**
  *
  * @param playerSymbol
  * @return 3inARow
  */
 //int check3InARow(int playerSymbol);

 /**
  *
  * @param player
  * @return 2-inARow
  */
 //int check2InARow(int player);


}
