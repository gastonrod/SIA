package engine.mutation;

import engine.model.Individual;

abstract class AbstractSinglePointMutator implements Mutator {

    protected void mutateSinglePoint(Individual individual, double probability) {
        int locus = (int) (Math.random() * individual.getLocusAmount());
        boolean shouldMutate = Math.random() < probability;
        if (shouldMutate) {
            individual.mutateAt(locus);
        }
    }
}
