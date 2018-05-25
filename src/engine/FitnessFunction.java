package engine;

import engine.model.Individual;

@FunctionalInterface
public interface FitnessFunction<T extends Individual> {

    double eval(T i);
}
