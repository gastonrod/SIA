package engine;

import ar.com.itba.sia.*;
import sokoban.SokobanDistanceHeuristic;
import sokoban.SokobanPlacedBoxesHeuristic;
import sokoban.SokobanProblem;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

public class Solver {

    private static Search<?> search;
    private static MyProperties properties;
    public static boolean debug;

    public static void main(String[] args) {
        Problem<?> problem = null;
        try {
            properties = new MyProperties();
            problem = new SokobanProblem(properties.getBoardFile());
            System.out.println("Board parsing successful");
        } catch (IOException e){
            System.out.println("Properties loading went wrong");
            System.out.println(e.getMessage());
        }catch(Exception e) {
            System.out.println("Board parsing went wrong");
            System.out.println(e.toString());
        }
        try {
            debug = properties.getDebug();
            solve((Problem<sokoban.SokobanState>) problem, properties.getHeuristic(), properties.getMethod());
        } catch(Exception e){
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

    private static class MyProperties{

        Properties properties;

        public MyProperties() throws IOException{
            properties = new Properties();
            properties.load(new FileInputStream("config.properties"));
        }

        public Heuristic getHeuristic() throws Exception{
            String s = properties.getProperty("HEURISTIC");
            if (s == null)
                throw new Exception("Heuristic not found in the config.properties file.");
            if (s.equals("DISTANCE")){
                return new SokobanDistanceHeuristic();
            }else if(s.equals("PLACED_BOXES")){
                return new SokobanPlacedBoxesHeuristic();
            }else{
                throw new Exception("The heuristic written in the properties file is wrong.");
            }
        }

        public SolveMethod getMethod() throws Exception{
            String s = properties.getProperty("METHOD");
            if (s == null)
                throw new Exception("Method not found in the config.properties file.");
            if ( s.equals("A_STAR")){
                return Solver::aStarSolve;
            }else if (s.equals("BFS")){
                return Solver::bfsSolve;
            }else if (s.equals("DFS")){
                return Solver::dfsSolve;
            }else if (s.equals("GREEDY")){
                return Solver::greedySolve;
            }else if ( s.equals("IDA")){
                return Solver::idaSolve;
            } else {
                throw new Exception("The method written in the properties file is wrong.");
            }
        }

        public boolean getDebug() throws Exception{
            String s = properties.getProperty("DEBUG");
            if (s == null)
                throw new Exception("Debug not found in the config.properties file.");
            return Boolean.parseBoolean(s);
        }

        public String getBoardFile() throws Exception{
            String s = properties.getProperty("BOARD");
            if (s == null)
                throw new Exception("Board filename not found in the config.properties file.");
            return s;

        }
    }

}