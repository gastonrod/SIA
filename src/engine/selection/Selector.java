package engine.selection;

import engine.FitnessFunction;
import engine.model.Individual;

import java.util.ArrayList;

@FunctionalInterface
public interface Selector<T extends Individual> {

    ArrayList<T> select(ArrayList<T> population, int k, FitnessFunction<T> fitnessFunction, int generation);
}
