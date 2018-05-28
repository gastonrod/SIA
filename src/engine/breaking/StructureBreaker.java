package engine.breaking;

import com.sun.xml.internal.bind.v2.TODO;
import engine.FitnessFunction;
import engine.model.Individual;

import java.util.ArrayList;

public class StructureBreaker<T extends Individual> implements Breaker<T> {

    private double expectedUnchangedProportion;
    private ArrayList<T> lastPopulation;

    public StructureBreaker(double expectedUnchangedProportion) {
        this.expectedUnchangedProportion = expectedUnchangedProportion;
        this.lastPopulation = null;
    }

    @Override
    public boolean shouldBreak(ArrayList<T> population, int generation, FitnessFunction<T> fitnessFunction, double optimalFitness) {
        if (lastPopulation == null || expectedUnchangedProportion < unchangedProportion(population)) {
            lastPopulation = (ArrayList<T>) population.clone();
            return false;
        }
        return true;
    }

    private double unchangedProportion(ArrayList<T> population) {
        // TODO: Algun metodo piola para ver esto.
        return 0;
    }
}
