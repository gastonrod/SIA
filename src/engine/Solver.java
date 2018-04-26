package engine;

import ar.com.itba.sia.*;
import sokoban.SokobanProblem;

public class Solver {

    private static Search<?> search;

    public static void main(String[] args) {
        Problem<?> problem;
        try {
            problem = new SokobanProblem("input.txt");
            System.out.println("Board parsing successful");
        } catch(Exception e) {
            System.out.println("Board parsing went wrong");
            System.out.println(e.getMessage());
        }
    }

    private static <E> void solve(Problem<E> problem, Heuristic<E> heuristic, SolveMethod<E> solveMethod) {
        try {
            int time = 0;
            Node result = solveMethod.solve(problem, heuristic);
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

    private static <E> Node<E> bfsSolve(Problem<E> problem, Heuristic<E> heuristic) {
        Search<E> search =  new Search<E>();
        Node<E> result = search.solve(problem, heuristic, false, Method.BFS);
        Solver.search = search;
        return result;
    }

    private static <E> Node<E> dfsSolve(Problem<E> problem, Heuristic<E> heuristic) {
        Search<E> search =  new Search<E>();
        Node<E> result = search.solve(problem, heuristic, false, Method.DFS);
        Solver.search = search;
        return result;
    }

    private static <E> Node<E> idaSolve(Problem<E> problem, Heuristic<E> heuristic) {
        Search<E> search =  new Search<E>();
        Node<E> result = search.solve(problem, heuristic, true, Method.DFS);
        Solver.search = search;
        return result;
    }

    private static <E> Node<E> greedySolve(Problem<E> problem, Heuristic<E> heuristic) {
        Search<E> search =  new Search<E>();
        Node<E> result = search.solve(problem, heuristic, false, Method.GREEDY);
        Solver.search = search;
        return result;
    }

    private static <E> Node<E> aStartSolve(Problem<E> problem, Heuristic<E> heuristic) {
        Search<E> search =  new Search<E>();
        Node<E> result = search.solve(problem, heuristic, false, Method.A_STAR);
        Solver.search = search;
        return result;
    }

    @FunctionalInterface
    private interface SolveMethod<E> {
        Node<E> solve(Problem<E> problem, Heuristic<E> heuristic);
    }
}