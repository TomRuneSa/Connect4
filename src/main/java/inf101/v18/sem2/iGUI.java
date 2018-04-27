package inf101.v18.sem2;

import javax.swing.*;
import java.awt.*;

public interface iGUI {
    /**
     * Creates the connect-4 board with images.
     * @return LayeredBoard
     */
     JLayeredPane createLayeredBoard();

    /**
     * Creates a new game.
     */
    void createNewGame();

    /**
     *
     * The window gets centered on the screen for maximjun user experience.
     *
     * @param frame
     * @param width
     * @param height
     *
     */
    void centerWindow(Window frame, int width, int height);

    /**
     *
     * Places a checker on the board.
     * @param pic
     * @param row
     * @param col
     */
    void placeChecker(String pic, int row, int col);

    /**
     *Updates the board by checking whos turn it is, and if it's game over.
     */
    void game();

    /**
     * Creates all the buttons and components that one can use on the board.
     *
     * @return JPanel
     */
    Component createContentComponents();

    /**
     * Checks if it's game over.
     */
    void gameOver();


}
