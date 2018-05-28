package engine.breaking;

import engine.FitnessFunction;
import engine.model.Individual;

import java.util.ArrayList;

public class FitnessBreaker<T extends Individual> implements Breaker<T>{

    private final double threshold;

    public FitnessBreaker(double threshold) {
        this.threshold = threshold;
    }

    @Override
    public boolean shouldBreak(ArrayList<T> population, int generation, FitnessFunction<T> fitnessFunction, double optimalFitness) {
        for (T individual : population) {
            if (fitnessFunction.eval(individual) >= optimalFitness*threshold) {
                return true;
            }
        }
        return false;
    }
}
