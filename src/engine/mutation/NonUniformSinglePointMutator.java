package engine.mutation;

import engine.model.Individual;

public class NonUniformSinglePointMutator<T extends Individual> extends AbstractSinglePointMutator<T> {

    private VariationFunction variationFunction;
    private int currentGeneration;
    private double probability;

    public NonUniformSinglePointMutator() {
        this.variationFunction = g -> Math.random();
        this.currentGeneration = -1;
    }

    @Override
    public void mutate(T individual, int generation) {
        if (generation != currentGeneration) {
            currentGeneration = generation;
            probability = variationFunction.eval(generation);
        }
        mutateSinglePoint(individual, probability);
    }

    @FunctionalInterface
    public interface VariationFunction {

        double eval(int generation);
    }

}
