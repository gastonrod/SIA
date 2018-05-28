package engine.replacement;

import engine.FitnessFunction;
import engine.model.Individual;

import java.util.ArrayList;

@FunctionalInterface
public interface Replacer<T extends Individual> {

    void replace(ArrayList<T> population, ArrayList<T> children, FitnessFunction<T> fitnessFunction, int generation);
}
