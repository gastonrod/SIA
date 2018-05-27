package engine.crossover;

import engine.model.Individual;
import engine.model.Pair;

@FunctionalInterface
public interface Crosser<T extends Individual> {

    Pair<T> cross(Pair<T> pair);
}
