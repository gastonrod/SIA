package engine.mutation;

import engine.variation.VariationFunction;
import engine.model.Individual;

public class NonUniformSinglePointMutator extends AbstractSinglePointMutator {

    private VariationFunction variationFunction;
    private int currentGeneration;

    public NonUniformSinglePointMutator(VariationFunction variationFunction, int firstGeneration) {
        super(variationFunction.eval(firstGeneration));
        this.variationFunction = variationFunction;
        this.currentGeneration = firstGeneration;
    }

    @Override
    public void mutate(Individual individual, int generation) {
        if(generation != currentGeneration) {
            currentGeneration = generation;
            probability = variationFunction.eval(generation);
        }
        mutateSinglePoint(individual);
    }
}
