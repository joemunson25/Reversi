package model;

/**
 * Represents a move in reversi, containing a start and end position, as well as a step
 * @author josephmunson
 */
public class Move {
    private int to;
    private int from;
    private int step; 
    
    /**
     * Constructor for setting a reversi move
     * @param from starting position
     * @param to ending position
     * @param step length of one step
     */
    public Move(int from, int to, int step) {
        this.to = to;
        this.from = from;
        this.step = step;
    }

    /**
     * Returns end position of a reversi move
     * @return to
     */
    public int getTo() {
        return to;
    }

    /**
     * Sets end position of a reversi move
     * @param to 
     */
    public void setTo(int to) {
        this.to = to;
    }

    /**
     * Returns start position of a reversi move
     * @return from
     */
    public int getFrom() {
        return from;
    }

    /**
     * Sets start position of a reversi move
     * @param from 
     */
    public void setFrom(int from) {
        this.from = from;
    }

    /**
     * Returns the step from one position to another
     * @return step
     */
    public int getStep() {
        return step;
    }

    /**
     * Sets the step from one position to another
     * @param step 
     */
    public void setStep(int step) {
        this.step = step;
    }
    
    
}
