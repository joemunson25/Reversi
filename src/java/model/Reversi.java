package model;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;

/**
 * Represents a Reversi game
 * @author josephmunson
 */
public class Reversi implements Serializable {

    public static int NUM_SPACES = 64;
    public static int PLAYER_1 = 1;
    public static int PLAYER_2 = 2;

    private Disc[] discs;
    private int currentPlayer;
    private int score_1;
    private int score_2;
    private boolean gameOver = false;
    private String winner;
    private ArrayList<Move> validMoves = new ArrayList<>();
    private boolean pass = false;

    /**
     * Constructor Initializes the Game
     */
    public Reversi() {
        initialize();
    }

    /**
     * Places a disc in a valid position on the reversi board
     * @param position position to place the disc, -1 if no valid positions
     */
    public void placeDisc(int position) {
        
        //1. verify position exists
        if (position >= -1 && position < 64) {
            //2. verify that position is one of the valid empty spaces
            if (position == -1 || isValidPosition(position)) {
                if (position != -1) {
                    //3. place disc
                    discs[position] = currentColorDisc();

                    //4. flip corresponding discs
                    flipDiscs(position);

                    //5. update score
                    updateScore();

                }
               

                //6. change current player
                switchPlayer();

                //7. scan board for valid moves for next player
                setValidMoves();

                //8. check to see if game over
                checkGameOver();

            }
        }

    }


    /**
     * Returns current player
     * @return current player
     */
    public int getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Returns true if there was a pass
     * @return pass
     */
    public boolean isPass() {
        return pass;
    }

    /**
     * Returns a count of discs on board for specified play
     *
     * @param player either 1 or 2
     * @return score for the specified player
     */
    public int getScore(int player) {
        if (player == PLAYER_1) {
            return score_1;
        } else if (player == PLAYER_2) {
            return score_2;
        }
        return 0;
    }

    /**
     * Returns the score for player 1 (dark)
     * @return score_1
     */
    public int getScoreOne() {
        return score_1;
    }

    /**
     * Returns the score for player 2 (light)
     * @return score_2
     */
    public int getScoreTwo() {
        return score_2;
    }

    /**
     * Returns all discs on the board
     * @return discs
     */
    public Disc[] getDiscs() {
        return discs.clone();
    }

    /**
     * Returns true if game is over
     * @return gameOver
     */
    public boolean isGameOver() {
        return gameOver;
    }

    /**
     * Returns winning message with player name
     * @return winner
     */
    public String getWinner() {
        return winner;
    }

    /*
    Checks if current game is over
    */
    private void checkGameOver() {
        boolean boardIsFull = true;

        //1. check if board is filled
        for (Disc disc : discs) {
            if (disc == null || disc == Disc.VALID) {
                boardIsFull = false;
                break;
            }
        }
        
        if (boardIsFull) {
            gameOver = true;
            
        } else {
            //if board is not full and there are no valid moves, flag a pass
            if (validMoves.isEmpty()) {
                if (pass == true) {
                    gameOver = true;
                    pass = false;
                } else {
                    pass = true;
                }
            } else {
                pass = false;
            }
        }
        
        //if game over, set winning message
        if(gameOver == true) {
            if (score_1 > score_2) {
                winner = "Player 1 is the winner!";
            } else if (score_2 > score_1) {
                winner = "Player 2 is the winner!";
            } else {
                winner = "Tie!";
            }
            
            currentPlayer = 0;
        }

    }

    /**
     * Resets the game
     */
    public void reset() {
        initialize();
    }

    /*
    Initializes a reversi game
    */
    private void initialize() {
        //set disc array 
        discs = new Disc[NUM_SPACES];

        //with 4 discs in the middle
        discs[27] = Disc.LIGHT;
        discs[28] = Disc.DARK;
        discs[35] = Disc.DARK;
        discs[36] = Disc.LIGHT;

        //set current player to 1
        currentPlayer = PLAYER_1;

        //set valid moves
        setValidMoves();

        //set scores to 2
        score_1 = 2;
        score_2 = 2;

        //set 'game over' to false
        gameOver = false;
        pass = false;

        //set 'winner' to null
        winner = null;


    }

    /*
    Sets all valid moves for the current player
    */
    private void setValidMoves() {
        Disc currentColor = currentColorDisc();
        Disc oppositeColor = oppositeColorDisc();
        validMoves.clear();

        //clear previous valid moves
        for (int i = 0; i < 64; i++) {

            if (discs[i] == Disc.VALID) {
                discs[i] = null;
            }

        }

        //check for discs of current color
        for (int i = 0; i < 64; i++) {
            if (discs[i] == currentColor) {
                //Scan around disc
                int row = (int) Math.floor(i / 8);
                int column = i % 8;

                //RIGHT
                scan(i, (8 * row) + 6, 1);

                //LEFT
                scan(i, (8 * row) + 1, -1);

                //UP
                scan(i, 8 + column, -8);

                //DOWN
                scan(i, 48 + column, 8);

                //UP LEFT
                scan(i, getEnd(row, column, -9), -9);

                //UP RIGHT
                scan(i, getEnd(row, column, -7), -7);

                //DOWN LEFT
                scan(i, getEnd(row, column, 7), 7);

                //DOWN RIGHT
                scan(i, getEnd(row, column, 9), 9);

            }
        }
    }

    /*
    Gets the last possible board position for any diagonal move
    */
    private int getEnd(int row, int column, int step) {
        if (step == -9) {
            if (column > 1 && row > 1) {
                int difference = Math.min(row, column) - 1;
                row = row - difference;
                column = column - difference;
            }
        } else if (step == 9) {
            if (column < 6 && row < 6) {
                int difference = 6 - Math.max(row, column);
                row = row + difference;
                column = column + difference;
            }

        } else if (step == -7) {
            if (column < 6 && row > 1) {
                int difference = Math.min(row - 1, 6 - column);
                row = row - difference;
                column = column + difference;
            }

        } else if (step == 7) {
            if (column > 1 && row < 6) {
                int difference = Math.min(6 - row, column - 1);
                row = row + difference;
                column = column - difference;
            }
        }

        return row * 8 + column;
    }

    /*
    Flips discs adjacent to a given position
    */
    private void flipDiscs(int position) {
        Disc currentColor = currentColorDisc();

        for (Move move : validMoves) {
            if (move.getTo() == position) {
                boolean greaterThan = false;

                if (move.getStep() < 0) {
                    greaterThan = true;
                }

                for (int i = move.getFrom(); compare(i, move.getTo(), greaterThan); i = i + move.getStep()) {
                    discs[i + move.getStep()] = currentColor;
                }

            }
        }
    }

    /*
    Scans for valid moves
    */
    private void scan(int start, int end, int step) {
        boolean greaterThan = false;
        boolean proceed = false;
        Disc currentColor = currentColorDisc();
        Disc oppositeColor = oppositeColorDisc();

        if (step < 0) {
            greaterThan = true;
        }

        if (compare(start, end, greaterThan)) {
            if (discs[start + step] == oppositeColor) {
                proceed = true;
            }
        }

        if (proceed) {
            for (int i = start + step; compare(i, end + step, greaterThan); i = i + step) {
                if (discs[i + step] == null || discs[i + step] == Disc.VALID) {
                    discs[i + step] = Disc.VALID;
                    validMoves.add(new Move(start, i + step, step));
                    break;
                } else if (discs[i + step] == oppositeColor) {
                    continue;
                } else if (discs[i + step] == currentColor) {
                    break;
                } 
            }
        }
    }

    /*
    Compares two positions based on specified 'greater than' or 'less than'
    */
    private boolean compare(int a, int b, boolean greaterThan) {
        if (greaterThan) {
            return a > b;
        } else {
            return a < b;
        }
    }

    /*
    Returns Disc color of current player
    */
    private Disc currentColorDisc() {
        return currentPlayer == 1 ? Disc.DARK : Disc.LIGHT;
    }

    /*
    Returns opposite Disc color of current player
    */
    private Disc oppositeColorDisc() {
        return currentPlayer == 1 ? Disc.LIGHT : Disc.DARK;
    }

    /*
    Switches the current player to other player
    */
    private void switchPlayer() {
        if (currentPlayer == 1) {
            currentPlayer = 2;
        } else if (currentPlayer == 2) {
            currentPlayer = 1;
        }
    }

    /*
    Counts the score for each player
    */
    private void updateScore() {
        score_1 = 0;
        score_2 = 0;

        for (int i = 0; i < 64; i++) {
            if (discs[i] == Disc.DARK) {
                score_1++;
            } else if (discs[i] == Disc.LIGHT) {
                score_2++;
            }
        }
    }

    /*
    Checks if a position is valid
    */
    private boolean isValidPosition(int position) {
        return discs[position] == Disc.VALID;
    }

}
