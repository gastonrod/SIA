import java.util.*;

public class Search {

    private PriorityQueue<Node> frontier;
    private Comparator<? super Node> comparator;
    private Set<Node> explored;
    private int expanded;
    private int generated;

    public Search(Comparator<? super Node> comparator) {
        this.comparator = comparator;
        reset();
    }

    public void reset() {
        reset(frontier.comparator());
    }

    public void reset(Comparator<? super Node> comparator) {
        frontier = new PriorityQueue<>(comparator);
        explored = new HashSet<>();
        expanded = 0;
        generated = 0;
    }

    public Node solve(State initialState) {
        return solve(initialState, -1);
    }

    public Node solve(State initialState, int depthLimit) {

        Node initialNode = new Node(
                initialState,
                0,
                null,
                0,
                initialState.getEstimatedCost()
        );
        generated++;
        if (initialNode.state.isGoal()) {
            return initialNode;
        }
        frontier.add(initialNode);
        while(!frontier.isEmpty()) {
            Node currentNode = frontier.poll();
            explored.add(currentNode);
            List<Node> nextNodes = currentNode.expand();
            expanded++;
            generated += nextNodes.size();
            for (Node next: nextNodes) {
                if (!explored.contains(next)) {
                    if (next.state.isGoal()) {
                        return next;
                    }
                    if (depthLimit >= 0 && next.depth < depthLimit) {
                        frontier.add(next);
                    }
                }
            }
        }
        return null;
    }

    public int frontierSize() {
        return frontier.size();
    }

    public int expandedSize() {
        return expanded;
    }

    public int generatedSize() {
        return generated;
    }
}