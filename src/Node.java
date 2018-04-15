import java.util.LinkedList;
import java.util.List;

public class Node {

    public final State state;
    public final int depth;
    public final Node parent;
    public final double accumulatedCost;
    public final double estimatedCost;

    private boolean hashCalculated;
    private int hash;

    public Node(State state, int depth, Node parent, double accumulatedCost, double estimatedCost) {
        this.state = state;
        this.depth = depth;
        this.parent = parent;
        this.accumulatedCost = accumulatedCost;
        this.estimatedCost = estimatedCost;
        this.hashCalculated = false;
    }

    public List<Node> expand() {
        List<State.StateAndCost> statesList = state.nextStates();
        List<Node> result = new LinkedList<>();
        for (State.StateAndCost stateAndCost : statesList) {
            result.add(new Node(
                    stateAndCost.state,
                    depth + 1,
                    this,
                    accumulatedCost + stateAndCost.cost,
                    state.getEstimatedCost()
            ));
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        return (this == o
                ||
                (o instanceof Node
                        &&
                 depth == ((Node)o).depth
                        &&
                 Double.compare(((Node)o).accumulatedCost, accumulatedCost) == 0
                        &&
                 state.equals(((Node)o).state)
                ));
    }

    @Override
    public int hashCode() {
        if (!hashCalculated) {
            long temp;
            hash = state.hashCode();
            hash = 31 * hash + depth;
            temp = Double.doubleToLongBits(accumulatedCost);
            hash = 31 * hash + (int) (temp ^ (temp >>> 32));
            hashCalculated = true;
        }
        return hash;
    }
}