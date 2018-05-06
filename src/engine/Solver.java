package engine;

import ar.com.itba.sia.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

public class Solver {

    private static Search<?> search;
    static boolean debug;

    public static void main(String[] args) {
        try {
            MyProperties properties;
            properties = new MyProperties();
            debug = properties.getDebug();
            solve(new sokoban.SokobanProblem("input4.txt"), new sokoban.SokobanDistanceHeuristic(), properties.getMethod());
        } catch(Exception e) {
            System.out.println("Unknown exception arose trying to solve game");
            System.out.println(e.getMessage());
        }
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
        Search<E> search =  new Search<>();
        Node<E> result = search.solve(problem, heuristic, false, Method.BFS);
        Solver.search = search;
        return result;
    }

    private static <E> Node<E> dfsSolve(Problem<E> problem, Heuristic<E> heuristic) {
        Search<E> search =  new Search<>();
        Node<E> result = search.solve(problem, heuristic, false, Method.DFS);
        Solver.search = search;
        return result;
    }

    private static <E> Node<E> iddfsSolve(Problem<E> problem, Heuristic<E> heuristic) {
        Search<E> search =  new Search<>();
        Node<E> result = search.solve(problem, heuristic, true, Method.DFS);
        Solver.search = search;
        return result;
    }

    private static <E> Node<E> greedySolve(Problem<E> problem, Heuristic<E> heuristic) {
        Search<E> search =  new Search<>();
        Node<E> result = search.solve(problem, heuristic, false, Method.GREEDY);
        Solver.search = search;
        return result;
    }

    private static <E> Node<E> aStarSolve(Problem<E> problem, Heuristic<E> heuristic) {
        Search<E> search =  new Search<>();
        Node<E> result = search.solve(problem, heuristic, false, Method.A_STAR);
        Solver.search = search;
        return result;
    }

    @FunctionalInterface
    private interface SolveMethod<E> {
        Node<E> solve(Problem<E> problem, Heuristic<E> heuristic);
    }

    private static class MyProperties {

        private Properties properties;

        MyProperties() throws IOException {
            properties = new Properties();
            properties.load(new FileInputStream("config.properties"));
        }

        SolveMethod getMethod() throws Exception {
            String s = properties.getProperty("METHOD");
            if (s == null) {
                throw new Exception("Method not found in the config.properties file.");
            }
            switch (s) {
                case "A_STAR":
                    return Solver::aStarSolve;
                case "BFS":
                    return Solver::bfsSolve;
                case "DFS":
                    return Solver::dfsSolve;
                case "GREEDY":
                    return Solver::greedySolve;
                case "IDDFS":
                    return Solver::iddfsSolve;
                default:
                    throw new Exception("The method written in the properties file is invalid.");
            }
        }

        boolean getDebug() throws Exception {
            String s = properties.getProperty("DEBUG");
            if (s == null) {
                throw new Exception("Debug not found in the config.properties file.");
            }
            return Boolean.parseBoolean(s);
        }
    }
}