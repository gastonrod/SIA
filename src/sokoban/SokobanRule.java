package sokoban;

import ar.com.itba.sia.Rule;

public class SokobanRule implements Rule<SokobanState> {

    private static final double DEFAULT_COST = 1.0;

    private double cost;
    private int x, y;
    private Direction direction;

    SokobanRule(int x, int y, Direction direction) {
        this(x, y, direction, DEFAULT_COST);
    }

    SokobanRule(int x, int y, Direction direction, double cost) {
        this.x = x;
        this.y = y;
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

    @Override
    public SokobanState applyToState(SokobanState sokobanState) {
        return null;
    }
}
