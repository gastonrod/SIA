package engine;

import engine.model.Individual;

@FunctionalInterface
public interface FitnessFunction {

    double eval(Individual i);
}
