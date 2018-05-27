package engine.mutation;

import engine.engineException.InvalidProbabilityException;
import engine.model.Individual;

public class UniformSinglepointMutator extends AbstractSinglePointMutator {

    private final double probability;

    public UniformSinglepointMutator(double probability) {
        boolean isValidProbability = 0 <= probability && probability <= 1;
        if (!isValidProbability) {
            throw new InvalidProbabilityException(probability);
        }
        this.probability = probability;
    }

    @Override
    public void mutate(Individual individual, int generation) {
        mutateSinglePoint(individual, probability);
    }
}
