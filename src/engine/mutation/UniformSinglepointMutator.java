package engine.mutation;

import engine.model.Individual;

public class UniformSinglepointMutator extends AbstractSinglePointMutator {

    public UniformSinglepointMutator(double probability) {
        super(probability);
    }

    @Override
    public void mutate(Individual individual) {
        mutateSinglePoint(individual);
    }
}
