package engine.mutation;

import engine.model.Individual;

public final class MultigenMutator<T extends Individual> implements Mutator<T> {

    private double individualProbability;
    private double genProbability;

    public MultigenMutator(double individualProbability, double genProbability) {

        if (individualProbability < 0 || 1 < individualProbability) {
            throw new IllegalArgumentException("Attempting to instantiate " + getClass() + " with invalid individual probability " + individualProbability);
        }
        if (genProbability < 0 || 1 < genProbability) {
            throw new IllegalArgumentException("Attempting to instantiate " + getClass() + " with invalid gen probability " + genProbability);
        }
        this.individualProbability = individualProbability;
        this.genProbability = genProbability;
    }

    @Override
    public void mutate(T individual, int generation) {

        boolean individualShouldMutate = Math.random() < individualProbability;
        if (individualShouldMutate) {
            int locus = individual.getLocusAmount();
            for (int i = 0; i < locus; i++) {
                boolean genShouldMutate = Math.random() < genProbability;
                if (genShouldMutate) {
                    individual.mutateAt(i);
                }
            }
        }
    }
}
