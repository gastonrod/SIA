package engine.mutation;

import engine.model.Individual;

abstract class AbstractSinglePointMutator<T extends Individual> implements Mutator<T> {

    protected void mutateSinglePoint(T individual, double probability) {
        int locus = (int) (Math.random() * individual.getLocusAmount());
        boolean shouldMutate = Math.random() < probability;
        if (shouldMutate) {
            individual.mutateAt(locus);
        }
    }
}
