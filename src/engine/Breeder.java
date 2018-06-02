package engine;

import engine.breaking.Breaker;
import engine.crossover.Crosser;
import engine.model.Individual;
import engine.model.IndividualManager;
import engine.model.Pair;
import engine.mutation.Mutator;
import engine.replacement.Replacer;
import engine.selection.MixSelector;
import engine.selection.Selector;
import rpg.Fighter;
import rpg.FighterManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

public class Breeder {

    private static final String ENGINE_PROPERTIES_FILE = "src/engine/config.properties";
    private final static Plotter plotter = new Plotter();

    public static void main(String[] args) {

        try {
            System.out.println("Loading data...");
            IndividualManager<Fighter> individualManager = new FighterManager();
            System.out.println("Data loaded");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            while(shouldContinue(br)) {
                plotter.resetDataset();
                individualManager.initialize();
                EnginePropertiesManager enginePropertiesManager = new EnginePropertiesManager(ENGINE_PROPERTIES_FILE);
                boolean debugging = enginePropertiesManager.isDebuggingEnabled();
                Selector<Fighter> mixSelector = new MixSelector<>(enginePropertiesManager.getFirstSelector(), enginePropertiesManager.getSecondSelector(), enginePropertiesManager.getFirstSelectorPercentage());
                Crosser<Fighter> crosser = enginePropertiesManager.getCrosser();
                Mutator<Fighter> mutator = enginePropertiesManager.getMutator();
                Replacer<Fighter> replacer = enginePropertiesManager.getReplacer();
                int populationSize = enginePropertiesManager.getPopulationSize();
                double generationalGap = enginePropertiesManager.getGenerationalGap();
                int k = (int) Math.round(populationSize * generationalGap);
                Breaker<Fighter> breaker = enginePropertiesManager.getBreaker();

                ArrayList<Fighter> population = individualManager.createRandomPopulation(populationSize);
                int generation;
                Pair<Fighter> toCross = new Pair<>();
                System.out.println("Initial population");
                System.out.println(population);
                for (generation = 0; !breaker.shouldBreak(population, generation, individualManager.getFitnessFunction(), individualManager.getOptimalFitness()); generation++) {
                    // Selection
                    ArrayList<Fighter> selected = mixSelector.select(population, k, individualManager.getFitnessFunction(), generation);
                    // Crossover
                    ArrayList<Fighter> crossed = new ArrayList<>(k);
                    Iterator<Fighter> selectedIterator = selected.iterator();
                    while (selectedIterator.hasNext()) {
                        toCross.first = selectedIterator.next();
                        if (selectedIterator.hasNext()) {
                            toCross.second = selectedIterator.next();
                            Pair<Fighter> offspring = crosser.cross(toCross);
                            crossed.add(offspring.first);
                            crossed.add(offspring.second);
                        } else {
                            crossed.add(toCross.first);
                        }
                    }
                    // Mutation
                    for (Fighter f : crossed) {
                        mutator.mutate(f, generation);
                    }
                    // Replacement
                    replacer.replace(population, crossed, individualManager.getFitnessFunction(), generation);
                    if (debugging) {
                        Fighter fittest = getFittest(population, individualManager.getFitnessFunction());
                        double bestFitness = individualManager.getFitnessFunction().eval(fittest);
                        System.out.println("Fitest individual: " + fittest + "\r\nFitness: " + bestFitness);
                        plotter.addBest(generation, bestFitness);
                        Fighter worst = getWorst(population, individualManager.getFitnessFunction());
                        double worstFitness = individualManager.getFitnessFunction().eval(worst);
                        plotter.addWorst(generation, worstFitness);
                        double averageFitness = getAverageFitness(population, individualManager.getFitnessFunction());
                        plotter.addAverage(generation, averageFitness);
                        plotter.plot();
                    }
                }
                // Get winner
                Iterator<Fighter> iterator = population.iterator();
                Fighter winner = iterator.next();
                double bestFitness = individualManager.getFitnessFunction().eval(winner);
                while (iterator.hasNext()) {
                    Fighter candidate = iterator.next();
                    double candidateFitness = individualManager.getFitnessFunction().eval(candidate);
                    if (candidateFitness > bestFitness) {
                        bestFitness = candidateFitness;
                        winner = candidate;
                    }
                }
                System.out.println("And the winner is: ");
                System.out.println(winner);
                System.out.println("With a fitness of: " + bestFitness);
                System.out.println("Optimal fitness: " + individualManager.getOptimalFitness());
                System.out.println("Generations: " + generation);
            }
        } catch (Exception e) {
            System.out.println("Falló");
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }

    }

    private static double getAverageFitness(ArrayList<Fighter> population, FitnessFunction<Fighter> fitnessFunction) {
        double average = 0;
        for (Fighter fighter : population) {
            average += fitnessFunction.eval(fighter);
        }
        average /= population.size();
        return average;
    }

    private static boolean shouldContinue(BufferedReader br) throws IOException {
        final String continueString = "next";
        System.out.println("Type \"" + continueString + "\" if you want to execute the program, anything else to quit.");
        return br.readLine().equalsIgnoreCase(continueString);
    }

    public static <T extends Individual> double getBestFitness(ArrayList<T> population, FitnessFunction<T> fitnessFunction) {
        return fitnessFunction.eval(Collections.max(population, Comparator.comparingDouble(fitnessFunction::eval)));
    }

    public static <T extends Individual> T getFittest(ArrayList<T> population, FitnessFunction<T> fitnessFunction) {
        return Collections.max(population, Comparator.comparingDouble(fitnessFunction::eval));
    }

    private static <T extends Individual> T getWorst(ArrayList<T> population, FitnessFunction<T> fitnessFunction) {
        return Collections.min(population, Comparator.comparingDouble(fitnessFunction::eval));
    }

}