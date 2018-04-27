package sokoban;

import ar.com.itba.sia.Rule;

public class SokobanRule implements Rule<SokobanState> {

    private static final double DEFAULT_COST = 1.0;

    private double cost;
    private int boxX, boxY;
    private Direction direction;

    SokobanRule(int x, int y, Direction direction) {
        this(x, y, direction, DEFAULT_COST);
    }

    SokobanRule(int boxX, int boxY, Direction direction, double cost) {
        this.boxX = boxX;
        this.boxY = boxY;
        this.direction = direction;
        this.cost = cost;
    }

    @Override
    public double getCost() {
        return cost;
    }

    @Override
    public void setCost(double cost) {
        this.cost = cost;
    }

    /*  For efficiency, this function does not check if the rule is applicable to the given state.
        The responsibility of applying proper rules to proper states is left to the user of these rules.
        Rules misuse yields undefined results.
     */
    @Override
    public SokobanState applyToState(SokobanState sokobanState) {
        Element[][] board = sokobanState.getBoardCopy();
        int placedBoxes = sokobanState.getPlacedBoxes();
        Element currentElement = board[boxX][boxY];
        if (currentElement == Element.BOX) {
            board[boxX][boxY] = Element.EMPTY;
        } else if (currentElement == Element.BOX_AND_GOAL) {
            board[boxX][boxY] = Element.GOAL;
            placedBoxes--;
        } else {
            throw new InvalidRuleException();
        }
        Element nextElement = board[boxX+direction.x][boxY+direction.y];
        if (nextElement == Element.EMPTY) {
            board[boxX+direction.x][boxY+direction.y] = Element.BOX;
        } else if (nextElement == Element.GOAL) {
            board[boxX + direction.x][boxY + direction.y] = Element.BOX_AND_GOAL;
            placedBoxes++;
        } else {
            throw new InvalidRuleException();
        }
        return new SokobanState(board, boxX, boxY, sokobanState.getBoxes(), placedBoxes);
    }

    private class InvalidRuleException extends RuntimeException {

        InvalidRuleException() {
            super("Applying invalid rule");
        }
    }
}
