package engine.mutation;

import engine.model.Individual;

abstract class AbstractSinglePointMutator implements Mutator {

    double probability;

    protected AbstractSinglePointMutator(double probability) {
        this.probability = probability;
    }

    protected void mutateSinglePoint(Individual individual) {
        int locus = (int) (Math.random() * individual.getLocusAmount());
        boolean shouldMutate = Math.random() < probability;
        if (shouldMutate) {
            individual.mutateAt(locus);
        }
    }
}
