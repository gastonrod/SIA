package engine.model;

import engine.FitnessFunction;

import java.util.List;

public interface IndividualManager<T extends Individual> {

    List<T> createRandomPopulation(int size);

    FitnessFunction<T> getFitnessFunction();

    double getOptimalFitness();
}
