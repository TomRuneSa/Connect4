package inf101.v18.sem2;

import java.util.ArrayList;

public class Board implements IBoard {

    public static final int X = 1; // player 1
    public static final int O = -1; // player 2
    public static final int EMPTY = 0;

    private Move lastMove;

    //Saves the last symbol, X/O, to know who played last.
    private int lastSymbolPlayed;

    private int winner;
    private int[][] board;

    private boolean occurredOverflow = false;

    private boolean isGameOver;

    //Creates a new empty board
    public Board() {
        lastMove = new Move();
        lastSymbolPlayed = O;
        winner = 0;
        board = new int[6][7];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                board[i][j] = EMPTY;
            }
        }
    }

    //Copy of new board
    public Board(Board board) {
        lastMove = board.lastMove;
        lastSymbolPlayed = board.lastSymbolPlayed;
        winner = board.winner;
        this.board = new int[6][7];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                this.board[i][j] = board.board[i][j];
            }
        }
    }

    public Move getLastMove() {
        return lastMove;
    }


    public int getLastSymbolPlayed() {
        return lastSymbolPlayed;
    }


    public void setLastSymbolPlayed(int lastLetterPlayed) {
        this.lastSymbolPlayed = lastLetterPlayed;
    }


    public int getWinner() {
        return winner;
    }


    public void setWinner(int winner) {
        this.winner = winner;
    }


    public boolean isGameOver() {
        return isGameOver;
    }


    public void setGameOver(boolean isGameOver) {
        this.isGameOver = isGameOver;
    }


    public boolean isOccurredOverflow() {
        return occurredOverflow;
    }


    public void setOccurredOverflow(boolean occurredOverflow) {
        this.occurredOverflow = occurredOverflow;
    }


    //Makes move based on column. The row to be placed is found automaticly.
    public void makeMove(int col, int letter) {
        try {
            //"lastMove" must be changed before "board[][]" because of the function "getRowPosition(col)"
            this.lastMove = new Move(getRowPosition(col), col);
            this.lastSymbolPlayed = letter;
            this.board[getRowPosition(col)][col] = letter;
        } catch (ArrayIndexOutOfBoundsException e) {
            //If a column is full
            System.err.println("Column " + (col + 1) + " is full!");
            setOccurredOverflow(true);
        }
    }

    //A method to search the whole board inside the borders.
    public boolean canMove(int row, int col) {
        if ((row <= -1) || (col <= -1) || (row > 5) || (col > 6)) {
            return false;
        }
        return true;
    }

    //Method to check if a column is full
    public boolean checkFullColumn(int col) {
        if (board[0][col] == EMPTY)
            return false;
        return true;
    }


    // A method that returns the position of the last empty row in a column.
    public int getRowPosition(int col) {
        int rowPosition = -1;
        for (int row = 0; row < 6; row++) {
            if (board[row][col] == EMPTY) {
                rowPosition = row;
            }
        }
        return rowPosition;
    }


    //A method that returns a list of the "children" in a column.
    //There can never be more than 7 children(We only have 7 columns)
    public ArrayList<Board> getChildren(int letter) {
        ArrayList<Board> children = new ArrayList<Board>();
        for (int col = 0; col < 7; col++) {
            if (!checkFullColumn(col)) {
                Board child = new Board(this);
                child.makeMove(col, letter);
                children.add(child);
            }
        }
        return children;
    }

    public int evaluate() {
        // If the state is +100, "X" wins, -100 and "O" wins.
        int Xlines = 0;
        int Olines = 0;

        if (checkWinState()) {
            if (getWinner() == X) {
                Xlines = Xlines + 100;
            } else if (getWinner() == O) {
                Olines = Olines + 100;
            }
        }


        // if the result is 0, then it'a a draw 
        return Xlines - Olines;
    }


    //A method that checks if somebody has won the game.
    public boolean checkWinState() {

        // Check for 4 consecutive checkers in a row, horizontally.
        for (int i = 5; i >= 0; i--) {
            for (int j = 0; j < 4; j++) {
                if (board[i][j] == board[i][j + 1]
                        && board[i][j] == board[i][j + 2]
                        && board[i][j] == board[i][j + 3]
                        && board[i][j] != EMPTY) {
                    setWinner(board[i][j]);
                    return true;
                }
            }
        }

        // Check for 4 consecutive checkers in a row, vertically.
        for (int i = 5; i >= 3; i--) {
            for (int j = 0; j < 7; j++) {
                if (board[i][j] == board[i - 1][j]
                        && board[i][j] == board[i - 2][j]
                        && board[i][j] == board[i - 3][j]
                        && board[i][j] != EMPTY) {
                    setWinner(board[i][j]);
                    return true;
                }
            }
        }

        // Check for 4 consecutive checkers in a row, in descending diagonals.
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                if (board[i][j] == board[i + 1][j + 1]
                        && board[i][j] == board[i + 2][j + 2]
                        && board[i][j] == board[i + 3][j + 3]
                        && board[i][j] != EMPTY) {
                    setWinner(board[i][j]);
                    return true;
                }
            }
        }

        // Check for 4 consecutive checkers in a row, in ascending diagonals.
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                if (canMove(i - 3, j + 3)) {
                    if (board[i][j] == board[i - 1][j + 1]
                            && board[i][j] == board[i - 2][j + 2]
                            && board[i][j] == board[i - 3][j + 3]
                            && board[i][j] != EMPTY) {
                        setWinner(board[i][j]);
                        return true;
                    }
                }
            }
        }

        setWinner(0); // set as winner nobody
        return false;

    }

    public boolean checkGameOver() {
        // Check if there is a winner.
        if (checkWinState()) {
            return true;
        }

        // Looks for an empty cell in the board. IF there is no empty cells, the board is full and it's a draw.
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                if (board[row][col] == EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }
}