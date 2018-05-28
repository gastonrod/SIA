package engine.model;

import engine.FitnessFunction;

import java.util.ArrayList;

public interface IndividualManager<T extends Individual> {

    ArrayList<T> createRandomPopulation(int size);

    FitnessFunction<T> getFitnessFunction();

    double getOptimalFitness();

    void initialize();
}
