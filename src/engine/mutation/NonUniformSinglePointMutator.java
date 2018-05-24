package engine.mutation;

import engine.VariationFunction;
import engine.model.Individual;

public class NonUniformSinglePointMutator extends AbstractSinglePointMutator implements Mutator{

    private VariationFunction variationFunction;

    public NonUniformSinglePointMutator(VariationFunction variationFunction) {
        super(0);
        this.variationFunction = variationFunction;
    }

    @Override
    public void mutate(Individual individual) {
        probability = variationFunction.eval(individual.getGeneration());
        mutateSinglePoint(individual);
    }
}
