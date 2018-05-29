package engine.breaking;

import engine.FitnessFunction;
import engine.model.Individual;

import java.util.ArrayList;
import java.util.HashSet;

public class StructureBreaker<T extends Individual> implements Breaker<T> {

    private double expectedUnchangedProportion;
    private HashSet<Integer> lastPopulationHashcodes;

    public StructureBreaker(double expectedUnchangedProportion) {
        this.expectedUnchangedProportion = expectedUnchangedProportion;
    }

    @Override
    public boolean shouldBreak(ArrayList<T> population, int generation, FitnessFunction<T> fitnessFunction, double optimalFitness) {
        if (lastPopulationHashcodes == null || expectedUnchangedProportion > unchangedProportion(population)) {
            lastPopulationHashcodes = new HashSet<>();
            for (T individual : population) {
                lastPopulationHashcodes.add(individual.hashCode());
            }
            return false;
        }
        return true;
    }

    private double unchangedProportion(ArrayList<T> population) {
        int unchanged = 0;
        for (T individual : population) {
            if (lastPopulationHashcodes.contains(individual.hashCode())) {
                unchanged++;
            }
        }
        return 1.0 * unchanged / population.size();
    }
}
