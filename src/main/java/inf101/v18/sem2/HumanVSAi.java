package inf101.v18.sem2;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;


public class HumanVSAi implements iGUI {

    static Board board = new Board();
    static JFrame frameMainWindow;
    static JFrame frameGameOver;

    static JPanel panelBoardNumbers;
    static JLayeredPane layeredGameBoard;


    private static int maxDepth ;

    //	static GamePlayer ai = new GamePlayer();
    static Minimax ai = new Minimax(maxDepth, Board.X);

    //	Human player letter -> X. He plays First
    //	Minimax AI letter -> O.

    public HumanVSAi(int difficulty) {
        try {
            UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() );
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.maxDepth = difficulty;
        createNewGame();
    }

    //Creates the main board
    public JLayeredPane createLayeredBoard() {
        layeredGameBoard = new JLayeredPane();
        layeredGameBoard.setPreferredSize(new Dimension(570, 490));
        layeredGameBoard.setBorder(BorderFactory.createTitledBorder("You go gurl"));

        ImageIcon imageBoard = new ImageIcon(ResourceLoader.load("images/board.png"));
        JLabel imageBoardLabel = new JLabel(imageBoard);

        imageBoardLabel.setBounds(20, 20, imageBoard.getIconWidth(), imageBoard.getIconHeight());
        layeredGameBoard.add(imageBoardLabel, new Integer (0), 1);

        return layeredGameBoard;
    }

    // To be called when the game starts for the first time
    // or a new game starts.
    //This method contains a lot of graphical interfaces.
    public void createNewGame() {
        board = new Board();

        // set the new difficulty setting
        ai.setMaxDepth(maxDepth);

        if (frameMainWindow != null) frameMainWindow.dispose();
        frameMainWindow = new JFrame(" Connect-4");
        // make the main window appear on the center
        centerWindow(frameMainWindow, 570, 490);
        Component compMainWindowContents = createContentComponents();
        frameMainWindow.getContentPane().add(compMainWindowContents, BorderLayout.CENTER);

        frameMainWindow.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        frameMainWindow.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                //System.out.println("keyPressed = " + KeyEvent.getKeyText(e.getKeyCode()));
                String button = KeyEvent.getKeyText(e.getKeyCode());

                board.setOccurredOverflow(false);
                int previousRow = board.getLastMove().getRow();
                int previousCol = board.getLastMove().getCol();
                int previousLetter = board.getLastSymbolPlayed();
/*
* Makes a human move based on what button has been pressed.
* */
                if (button.equals("1")) {
                    board.makeMove(0, Board.X);
                } else if (button.equals("2")) {
                    board.makeMove(1, Board.X);
                } else if (button.equals("3")) {
                    board.makeMove(2, Board.X);
                } else if (button.equals("4")) {
                    board.makeMove(3, Board.X);
                } else if (button.equals("5")) {
                    board.makeMove(4, Board.X);
                } else if (button.equals("6")) {
                    board.makeMove(5, Board.X);
                } else if (button.equals("7")) {
                    board.makeMove(6, Board.X);
                }

                if (button.equals("1") || button.equals("2") || button.equals("3") || button.equals("4")
                        || button.equals("5") || button.equals("6") || button.equals("7")) {
                    if (!board.isOccurredOverflow()) {
                        //Makes AI move
                        game();
                        aiMove();
                    } else {
                        board.getLastMove().setRow(previousRow);
                        board.getLastMove().setCol(previousCol);
                        board.setLastSymbolPlayed(previousLetter);
                    }
                }

            }

            @Override
            public void keyReleased(KeyEvent e) {
                //System.out.println("keyReleased = " + KeyEvent.getKeyText(e.getKeyCode()));
            }
        });

        frameMainWindow.setFocusable(true);

        //Shows window
        frameMainWindow.pack();
        frameMainWindow.setVisible(true);

        //Makes the AI move if the human player played last
        if (board.getLastSymbolPlayed() == Board.X) {
            Move aiMove = ai.MiniMax(board);
            board.makeMove(aiMove.getCol(), Board.O);
            game();
        }

    }


    // Makes the window centered on screen.
    public void centerWindow(Window frame, int width, int height) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) (((dimension.getWidth() - frame.getWidth()) / 2) - (width/2));
        int y = (int) (((dimension.getHeight() - frame.getHeight()) / 2) - (height/2));
        frame.setLocation(x, y);
    }

    //Checker gets placed on the board
    public void placeChecker(String pic, int row, int col) {
        int xOffset = 75 * col;
        int yOffset = 75 * row;
        //The visual representation of the checker
        ImageIcon checkerIcon = new ImageIcon(ResourceLoader.load("images/" + pic));
        JLabel checkerLabel = new JLabel(checkerIcon);
        checkerLabel.setBounds(27 + xOffset, 27 + yOffset, checkerIcon.getIconWidth(),checkerIcon.getIconHeight());
        layeredGameBoard.add(checkerLabel, new Integer(0), 0);
        frameMainWindow.paint(frameMainWindow.getGraphics());
    }

    // Gets called after makeMove(int col) is called.
    public void game() {

        int row = board.getLastMove().getRow();
        int col = board.getLastMove().getCol();

        int currentPlayer = board.getLastSymbolPlayed();

        if (currentPlayer == Board.X) {
            // Places a checker in the corresponding [row][col] of the Gui.
            // This is the human player.
            placeChecker("tile1.png", row, col);
        }

        if (currentPlayer == Board.O) {
            // Places a checker in the corresponding [row][col] of the Gui.
            // This is the AI player
            placeChecker("tile2.jpg", row, col);
        }

        if (board.checkGameOver()) {
            board.setGameOver(true);
            gameOver();
        }


    }

    // Gets called after the human player makes a move. It makes an minimax AI move.
    public void aiMove(){

        if (!board.isGameOver()) {
            // Check if human player played last
            if (board.getLastSymbolPlayed() == Board.X) {
                Move aiMove = ai.MiniMax(board);
                board.makeMove(aiMove.getCol(), Board.O);
                game();
            }
        }

    }

    /**
     * Returns a component to be drawn by main window.
     * This function creates the main window components.
     * Kalei ton actionListener otan ginetai click me to pontiki panw se button.
     */
    public Component createContentComponents() {

        // Create a panel to set up the board buttons.
        panelBoardNumbers = new JPanel();
        panelBoardNumbers.setLayout(new GridLayout(1, 7, 6, 4));
        panelBoardNumbers.setBorder(BorderFactory.createEmptyBorder(2, 22, 2, 22));
/*
This method handles events that happen when a corresponding button is pressed. It saves the last move that was made by the opposition.
The code is repetitive for each number 1-7.
 */
        JButton col1_button = new JButton("1");
        col1_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.setOccurredOverflow(false);
                int previousRow = board.getLastMove().getRow();
                int previousCol = board.getLastMove().getCol();
                int previousLetter = board.getLastSymbolPlayed();
                board.makeMove(0, Board.X);
                if (!board.isOccurredOverflow()) {
                    game();
                    aiMove();
                } else {
                    board.getLastMove().setRow(previousRow);
                    board.getLastMove().setCol(previousCol);
                    board.setLastSymbolPlayed(previousLetter);
                }
                frameMainWindow.requestFocusInWindow();
            }
        });

        JButton col2_button = new JButton("2");
        col2_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.setOccurredOverflow(false);
                int previousRow = board.getLastMove().getRow();
                int previousCol = board.getLastMove().getCol();
                int previousLetter = board.getLastSymbolPlayed();
                board.makeMove(1, Board.X);
                if (!board.isOccurredOverflow()) {
                    game();
                    aiMove();
                } else {
                    board.getLastMove().setRow(previousRow);
                    board.getLastMove().setCol(previousCol);
                    board.setLastSymbolPlayed(previousLetter);
                }
                frameMainWindow.requestFocusInWindow();
            }
        });

        JButton col3_button = new JButton("3");
        col3_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.setOccurredOverflow(false);
                int previousRow = board.getLastMove().getRow();
                int previousCol = board.getLastMove().getCol();
                int previousLetter = board.getLastSymbolPlayed();
                board.makeMove(2, Board.X);
                if (!board.isOccurredOverflow()) {
                    game();
                    aiMove();
                } else {
                    board.getLastMove().setRow(previousRow);
                    board.getLastMove().setCol(previousCol);
                    board.setLastSymbolPlayed(previousLetter);
                }
                frameMainWindow.requestFocusInWindow();
            }
        });

        JButton col4_button = new JButton("4");
        col4_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.setOccurredOverflow(false);
                int previousRow = board.getLastMove().getRow();
                int previousCol = board.getLastMove().getCol();
                int previousLetter = board.getLastSymbolPlayed();
                board.makeMove(3, Board.X);
                if (!board.isOccurredOverflow()) {
                    game();
                    aiMove();
                } else {
                    board.getLastMove().setRow(previousRow);
                    board.getLastMove().setCol(previousCol);
                    board.setLastSymbolPlayed(previousLetter);
                }
                frameMainWindow.requestFocusInWindow();
            }
        });

        JButton col5_button = new JButton("5");
        col5_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.setOccurredOverflow(false);
                int previousRow = board.getLastMove().getRow();
                int previousCol = board.getLastMove().getCol();
                int previousLetter = board.getLastSymbolPlayed();
                board.makeMove(4, Board.X);
                if (!board.isOccurredOverflow()) {
                    game();
                    aiMove();
                } else {
                    board.getLastMove().setRow(previousRow);
                    board.getLastMove().setCol(previousCol);
                    board.setLastSymbolPlayed(previousLetter);
                }
                frameMainWindow.requestFocusInWindow();
            }
        });

        JButton col6_button = new JButton("6");
        col6_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.setOccurredOverflow(false);
                int previousRow = board.getLastMove().getRow();
                int previousCol = board.getLastMove().getCol();
                int previousLetter = board.getLastSymbolPlayed();
                board.makeMove(5, Board.X);
                if (!board.isOccurredOverflow()) {
                    game();
                    aiMove();
                } else {
                    board.getLastMove().setRow(previousRow);
                    board.getLastMove().setCol(previousCol);
                    board.setLastSymbolPlayed(previousLetter);
                }
                frameMainWindow.requestFocusInWindow();
            }
        });

        JButton col7_button = new JButton("7");
        col7_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.setOccurredOverflow(false);
                int previousRow = board.getLastMove().getRow();
                int previousCol = board.getLastMove().getCol();
                int previousLetter = board.getLastSymbolPlayed();
                board.makeMove(6, Board.X);
                if (!board.isOccurredOverflow()) {
                    game();
                    aiMove();
                } else {
                    board.getLastMove().setRow(previousRow);
                    board.getLastMove().setCol(previousCol);
                    board.setLastSymbolPlayed(previousLetter);
                }
                frameMainWindow.requestFocusInWindow();
            }
        });

        /*
        Adds all the buttons to the graphic interface
         */
        panelBoardNumbers.add(col1_button);
        panelBoardNumbers.add(col2_button);
        panelBoardNumbers.add(col3_button);
        panelBoardNumbers.add(col4_button);
        panelBoardNumbers.add(col5_button);
        panelBoardNumbers.add(col6_button);
        panelBoardNumbers.add(col7_button);

        // Main Connect-4 board creation
        layeredGameBoard = createLayeredBoard();

        //A panel is created to store all the elements of the board.
        JPanel panelMain = new JPanel();
        panelMain.setLayout(new BorderLayout());
        panelMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        //Adds button and main board components to the "panelMain" JPanel
        panelMain.add(panelBoardNumbers, BorderLayout.NORTH);
        panelMain.add(layeredGameBoard, BorderLayout.CENTER);

        /*
        To much work to make all components be able to resize with the window.
         */
        frameMainWindow.setResizable(false);
        return panelMain;
    }

    public void gameOver() {
/*
Creates a window that the user can interact with when it is game over.
 */
        frameGameOver = new JFrame("Game over!");
        frameGameOver.setBounds(620, 400, 350, 128);
        centerWindow(frameGameOver, 0, 0);
        JPanel winPanel = new JPanel(new BorderLayout());
        winPanel.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));

        JLabel winLabel;
        board.checkWinState();
        if (board.getWinner() == Board.X) {
            winLabel = new JLabel("You win! Start a new game?");
            winPanel.add(winLabel);
        } else if (board.getWinner() == Board.O) {
            winLabel = new JLabel("Computer AI wins! Start a new game?");
            winPanel.add(winLabel);
        } else {
            winLabel = new JLabel("It's a draw! Start a new game?");
            winPanel.add(winLabel);
        }
        winLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        winPanel.add(winLabel, BorderLayout.NORTH);
/*
Creates a new board if the user says "yes"
 */
        JButton yesButton = new JButton("Yes");
        yesButton.setBorder(BorderFactory.createEmptyBorder(5, 30, 5, 30));
        yesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameGameOver.setVisible(false);
                createNewGame();
            }
        });
/*
Exits if the user wants to quit.
 */
        JButton quitButton = new JButton("Quit");
        quitButton.setBorder(BorderFactory.createEmptyBorder(5, 30, 5, 30));
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameGameOver.setVisible(false);
                System.exit(0);
            }
        });

        JPanel subPanel = new JPanel();
        subPanel.add(yesButton);
        subPanel.add(quitButton);

        winPanel.add(subPanel, BorderLayout.CENTER);
        frameGameOver.getContentPane().add(winPanel, BorderLayout.CENTER);
        frameGameOver.setResizable(false);
        frameGameOver.setVisible(true);
    }

    @SuppressWarnings("static-access")
    public static void main(String[] args){
        HumanVSAi connect4 = new HumanVSAi(maxDepth);
        connect4.createNewGame();
    }

}
