
import inf101.v18.sem2.Board;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

public class boardTest {

    /**
     * Creates a board and checks that a position is open to be moved to.
     */
    @Test
    public void canMoveTest() {
        Board b = new Board();
        if (!b.canMove(0, 0)) {
            fail("Does not Work");
        }
    }

    /**
     * Creates board and fills up a column.
     * Checks that the column is registered as full.
     */
    @Test
    public void fullColumnTest() {
        Board b = new Board();
        b.makeMove(0, Board.X);
        b.makeMove(0, Board.X);
        b.makeMove(0, Board.X);
        b.makeMove(0, Board.X);
        b.makeMove(0, Board.X);
        b.makeMove(0, Board.X);

        assertTrue(b.checkFullColumn(0));

    }


    /**
     * Creates board and places two checkers.
     * Checks that the first available position in that column is 3.
     */
    @Test
    public void rowTest() {
        Board b = new Board();
        b.makeMove(0, Board.X);
        b.makeMove(0, Board.X);
        assertTrue(b.getRowPosition(0) == 3);
    }

    /**
     * Creates board, places 4 checkers next to each other.
     * Checks if a winner is declared.
     */
    @Test
    public void winTest() {
        Board b = new Board();
        b.makeMove(0, Board.X);
        b.makeMove(1, Board.X);
        b.makeMove(2, Board.X);
        b.makeMove(3, Board.X);
        assertTrue(b.checkWinState());
    }

    /**
     * Creates board, places 4 checkers next to each other.
     * Checks if the game registers as game over..
     */
    @Test
    public void goTest() {
        Board b = new Board();
        b.makeMove(0, Board.X);
        b.makeMove(1, Board.X);
        b.makeMove(2, Board.X);
        b.makeMove(3, Board.X);
        assertTrue(b.checkGameOver());
    }
}
