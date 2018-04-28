package engine;

import ar.com.itba.sia.*;
import sokoban.SokobanPlacedBoxesHeuristic;
import sokoban.SokobanProblem;

import java.util.LinkedList;
import java.util.List;

public class Solver {

    private static Search<?> search;

    public static void main(String[] args) {
        Problem<?> problem = null;
        try {
            problem = new SokobanProblem("input1.txt");
            System.out.println("Board parsing successful");
        } catch(Exception e) {
            System.out.println("Board parsing went wrong");
            System.out.println(e.toString());
        }
        solve((Problem<sokoban.SokobanState>) problem, new SokobanPlacedBoxesHeuristic(), Solver::aStarSolve);
    }

    private static <E> void solve(Problem<E> problem, Heuristic<E> heuristic, SolveMethod<E> solveMethod) {
        try {
            long millis = System.currentTimeMillis();
            Node<?> result = solveMethod.solve(problem, heuristic);
            millis = System.currentTimeMillis() - millis;
            double time = 1.0*millis/1000;
            if (result != null) {
                System.out.println("Solution found");
                System.out.println(result.state.toString());
                System.out.println("Depth: " + result.depth);
                System.out.println("Applied rules:");
                printAppliedRules(result);
            } else {
                System.out.println("Solution not found");
            }
            System.out.println("Time: " + time + " seconds");
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

    private static void printAppliedRules(Node<?> result) {
        List<String> rules = new LinkedList<>();
        while (result.rule != null) {
            rules.add(0, result.rule.toString());
            result = result.parent;
        }
        for (String rule: rules) {
            System.out.println(rule);
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

    private static <E> Node<E> aStarSolve(Problem<E> problem, Heuristic<E> heuristic) {
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