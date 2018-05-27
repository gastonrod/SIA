package engine.selection;

import engine.FitnessFunction;
import engine.model.Individual;

import java.util.List;

@FunctionalInterface
public interface Selector<T extends Individual> {

    List<T> select(List<T> population, int k, FitnessFunction<T> fitnessFunction, int generation);
}
