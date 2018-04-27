package inf101.v18.sem2;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class HumanVSHuman implements iGUI {

    static Board board = new Board();
    static JFrame frameMainWindow;
    static JFrame frameGameOver;

    static String player1;
    static String player2;

    static JPanel panelBoardNumbers;
    static JLayeredPane layeredGameBoard;

    //	Player 1 letter -> X. He plays First
    //	Player 2 letter -> O. He goes second

    /*
    Constructor
     */
    public HumanVSHuman(String player1, String player2) {
        this.player1 = player1;
        this.player2 = player2;
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        createNewGame();
    }

    //Creates the visual representation of the main board.
    public JLayeredPane createLayeredBoard() {
        layeredGameBoard = new JLayeredPane();
        layeredGameBoard.setPreferredSize(new Dimension(570, 490));

        ImageIcon imageBoard = new ImageIcon(ResourceLoader.load("images/board.png"));
        JLabel imageBoardLabel = new JLabel(imageBoard);

        imageBoardLabel.setBounds(20, 20, imageBoard.getIconWidth(), imageBoard.getIconHeight());
        layeredGameBoard.add(imageBoardLabel, new Integer(0), 1);

        return layeredGameBoard;
    }

    // To be called when the game starts for the first time.
    public void createNewGame() {
        board = new Board();

        if (frameMainWindow != null) frameMainWindow.dispose();
        frameMainWindow = new JFrame(player1 + " vs " + player2);
        // Centers the window
        centerWindow(frameMainWindow, 570, 490);
        Component compMainWindowContents = createContentComponents();
        frameMainWindow.getContentPane().add(compMainWindowContents, BorderLayout.CENTER);

        frameMainWindow.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
/*
Makes it possible to pres buttons on the keyboard
 */
        frameMainWindow.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                //System.out.println("keyPressed="+KeyEvent.getKeyText(e.getKeyCode()));
                String button = KeyEvent.getKeyText(e.getKeyCode());

                if (button.equals("1")) {
                    makeMove(0);
                } else if (button.equals("2")) {
                    makeMove(1);
                } else if (button.equals("3")) {
                    makeMove(2);
                } else if (button.equals("4")) {
                    makeMove(3);
                } else if (button.equals("5")) {
                    makeMove(4);
                } else if (button.equals("6")) {
                    makeMove(5);
                } else if (button.equals("7")) {
                    makeMove(6);
                }

                if (button.equals("1") || button.equals("2") || button.equals("3") || button.equals("4")
                        || button.equals("5") || button.equals("6") || button.equals("7")) {
                    if (!board.isOccurredOverflow()) {
                        game();
                    } else {
                        board.setOccurredOverflow(false);
                    }
                }

            }

            @Override
            public void keyReleased(KeyEvent e) {
                //System.out.println("keyReleased = " + KeyEvent.getKeyText(e.getKeyCode()));
            }
        });

        frameMainWindow.setFocusable(true);

        // show window
        frameMainWindow.pack();
        frameMainWindow.setVisible(true);

    }

    /*
    Centers the window on the screen
     */
    public void centerWindow(Window frame, int width, int height) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) (((dimension.getWidth() - frame.getWidth()) / 2) - (width / 2));
        int y = (int) (((dimension.getHeight() - frame.getHeight()) / 2) - (height / 2));
        frame.setLocation(x, y);
    }

    // It finds which player plays next and makes a move on the board.
    public static void makeMove(int col) {
        board.setOccurredOverflow(false);

        int previousRow = board.getLastMove().getRow();
        int previousCol = board.getLastMove().getCol();
        int previousLetter = board.getLastSymbolPlayed();
        if (board.getLastSymbolPlayed() == Board.O) {
            board.makeMove(col, Board.X);
            layeredGameBoard.setBorder(BorderFactory.createTitledBorder(player2 + "s turn"));
        } else {
            board.makeMove(col, Board.O);
            layeredGameBoard.setBorder(BorderFactory.createTitledBorder(player1 + "s turn"));
        }

        if (board.isOccurredOverflow()) {
            board.getLastMove().setRow(previousRow);
            board.getLastMove().setCol(previousCol);
            board.setLastSymbolPlayed(previousLetter);
        }
    }

    // It places a checker on the board.
    public void placeChecker(String pic, int row, int col) {
        int xOffset = 75 * col;
        int yOffset = 75 * row;
        ImageIcon checkerIcon = new ImageIcon(ResourceLoader.load("images/" + pic));
        JLabel checkerLabel = new JLabel(checkerIcon);
        checkerLabel.setBounds(27 + xOffset, 27 + yOffset, checkerIcon.getIconWidth(), checkerIcon.getIconHeight());
        layeredGameBoard.add(checkerLabel, new Integer(0), 0);
        frameMainWindow.paint(frameMainWindow.getGraphics());
    }

    // Gets called after makeMove(int col) is called.
    public void game() {

        int row = board.getLastMove().getRow();
        int col = board.getLastMove().getCol();

        if (board.getLastSymbolPlayed() == Board.X) {
            // It places a checker in the corresponding [row][col] of the Gui.
            placeChecker("tile1.png", row, col);
        } else if (board.getLastSymbolPlayed() == Board.O) {
            // It places a checker in the corresponding [row][col] of the Gui.
            placeChecker("tile2.jpg", row, col);
        }
        if (board.checkGameOver()) {
            gameOver();
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
        Calls that decides what to when a column is chosen
         */
        JButton col1_button = new JButton("1");
        col1_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                makeMove(0);
                if (!board.isOccurredOverflow()) {
                    game();
                }
                frameMainWindow.requestFocusInWindow();
            }
        });

        JButton col2_button = new JButton("2");
        col2_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                makeMove(1);
                if (!board.isOccurredOverflow()) {
                    game();
                }
                frameMainWindow.requestFocusInWindow();
            }
        });

        JButton col3_button = new JButton("3");
        col3_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                makeMove(2);
                if (!board.isOccurredOverflow()) {
                    game();
                }
                frameMainWindow.requestFocusInWindow();
            }
        });

        JButton col4_button = new JButton("4");
        col4_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                makeMove(3);
                if (!board.isOccurredOverflow()) {
                    game();
                }
                frameMainWindow.requestFocusInWindow();
            }
        });

        JButton col5_button = new JButton("5");
        col5_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                makeMove(4);
                if (!board.isOccurredOverflow()) {
                    game();
                }
                frameMainWindow.requestFocusInWindow();
            }
        });

        JButton col6_button = new JButton("6");
        col6_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                makeMove(5);
                if (!board.isOccurredOverflow()) {
                    game();
                }
                frameMainWindow.requestFocusInWindow();
            }
        });

        JButton col7_button = new JButton("7");
        col7_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                makeMove(6);
                if (!board.isOccurredOverflow()) {
                    game();
                }
                frameMainWindow.requestFocusInWindow();
            }
        });

        /*
        adds all the buttons to the panel
         */
        panelBoardNumbers.add(col1_button);
        panelBoardNumbers.add(col2_button);
        panelBoardNumbers.add(col3_button);
        panelBoardNumbers.add(col4_button);
        panelBoardNumbers.add(col5_button);
        panelBoardNumbers.add(col6_button);
        panelBoardNumbers.add(col7_button);

        // Creates the main board
        layeredGameBoard = createLayeredBoard();

        // Stores all the elements
        JPanel panelMain = new JPanel();
        panelMain.setLayout(new BorderLayout());
        panelMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // Adds buttons and main board components to the board
        panelMain.add(panelBoardNumbers, BorderLayout.NORTH);
        panelMain.add(layeredGameBoard, BorderLayout.CENTER);

        frameMainWindow.setResizable(false);
        return panelMain;
    }

    public void gameOver() {
        board.setGameOver(true);

        /*
        Creates a window that gives the user option to start a new game or quit when it's game over
         */
        frameGameOver = new JFrame("Game over!");
        frameGameOver.setBounds(620, 400, 350, 128);
        centerWindow(frameGameOver, 0, 0);
        JPanel winPanel = new JPanel(new BorderLayout());
        winPanel.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));

        JLabel winLabel;
        board.checkWinState();
        if (board.getWinner() == Board.X) {
            winLabel = new JLabel(player1 + " wins! Start a new game?");
            winPanel.add(winLabel);
        } else if (board.getWinner() == Board.O) {
            winLabel = new JLabel(player2 + " wins! Start a new game?");
            winPanel.add(winLabel);
        } else {
            winLabel = new JLabel("It's a draw! Start a new game?");
            winPanel.add(winLabel);
        }
        winLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        winPanel.add(winLabel, BorderLayout.NORTH);

        JButton yesButton = new JButton("Yes");
        yesButton.setBorder(BorderFactory.createEmptyBorder(5, 30, 5, 30));
        yesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frameGameOver.setVisible(false);
                createNewGame();
            }
        });
/*
Exits game if user wants to quit
 */
        JButton quitButton = new JButton("Quit");
        quitButton.setBorder(BorderFactory.createEmptyBorder(5, 30, 5, 30));
        quitButton.addActionListener(new ActionListener() {
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
    public static void main(String[] args) {
        HumanVSHuman connect4 = new HumanVSHuman(player1, player2);
        connect4.createNewGame();
    }

}
