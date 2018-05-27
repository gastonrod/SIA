package engine.mutation;

import engine.model.Individual;

@FunctionalInterface
public interface Mutator<T extends Individual> {

    void mutate(T individual, int generation);
}
