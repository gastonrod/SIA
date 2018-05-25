package engine.selection;

import engine.FitnessFunction;
import engine.model.Individual;

import java.util.List;

@FunctionalInterface
public interface Selector {

    List<Individual> select(List<Individual> population, int k, FitnessFunction fitnessFunction);
}
