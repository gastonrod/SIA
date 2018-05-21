package engine.crossover;

import engine.model.Individual;
import engine.model.Pair;

@FunctionalInterface
public interface Crosser {

    Pair<Individual> cross(Pair<Individual> pair);
}
