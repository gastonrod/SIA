package engine.mutation;

import engine.model.Individual;

public class UniformSinglepointMutator implements Mutator {

    private final double probability;

    public UniformSinglepointMutator(double probability) {
        this.probability = probability;
    }

    @Override
    public void mutate(Individual individual) {
        int locus = (int) (Math.random() * individual.getLocusAmount());
        boolean shouldMutate = Math.random() < probability;
        if (shouldMutate) {
            individual.mutateAt(locus);
        }
    }
}
