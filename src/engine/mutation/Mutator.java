package engine.mutation;

import engine.model.Individual;

public interface Mutator {

    Individual mutate(Individual individual);
}
