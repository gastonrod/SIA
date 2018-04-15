import java.util.Comparator;

public class Solver {

    private static Comparator<Node> bfsComparator = Comparator.comparingInt(n -> n.depth);
    private static Comparator<Node> dfsComparator = bfsComparator.reversed();
    private static Comparator<Node> idsComparator = dfsComparator;
    private static Comparator<Node> greedyComparator = Comparator.comparingDouble(n -> n.estimatedCost);
    private static Comparator<Node> aStarComparator = Comparator.comparingDouble(n -> n.accumulatedCost + n.estimatedCost);

    private static Search search;

    public static void main(String[] args) {

    }

    private static void solve(State initialState, SolveMethod solveMethod) {
        try {
            int time = 0;
            Node result = solveMethod.solve(initialState);
            System.out.println("Solution found");
            System.out.println(result.state.toString());
            System.out.println("Time: " + time);
            System.out.println("Depth: " + result.depth);
            System.out.println("Expanded nodes: " + search.expandedSize());
            System.out.println("Frontier nodes: " + search.frontierSize());
            System.out.println("Generated nodes: " + search.generatedSize());
        } catch (OutOfMemoryError e) {
            System.out.println("Search terminated due to insufficient memory");
        } catch (Exception e) {
            System.out.println("Unexpected exception arose during search:");
            System.out.println(e.toString());
        }
    }

    private static Node bfsSolve(State initialState) {
        search =  new Search(bfsComparator);
        return search.solve(initialState);
    }

    private static Node dfsSolve(State initialState) {
        search =  new Search(dfsComparator);
        return search.solve(initialState);
    }

    private static Node idsSolve(State initialState) {
        Node result = null;
        search = new Search(idsComparator);
        for (int i = 0; result == null; i++) {
            search.reset();
            result = search.solve(initialState, i);
        }
        return result;
    }

    private static Node greedySolve(State initialState) {
        search =  new Search(greedyComparator);
        return search.solve(initialState);
    }

    private static Node aStarSolve(State initialState) {
        search =  new Search(aStarComparator);
        return search.solve(initialState);
    }

    @FunctionalInterface
    private interface SolveMethod {
        Node solve(State initialState);
    }
}