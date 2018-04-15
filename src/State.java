import java.util.List;

public abstract class State {

    public abstract boolean isGoal();
    public abstract List<StateAndCost> nextStates();
    public abstract double getEstimatedCost();

    public static class StateAndCost {
        public State state;
        public double cost;
    }
}