package engine.mutation;

import engine.model.Individual;

@FunctionalInterface
public interface Mutator {

    void mutate(Individual individual, int generation);
}
