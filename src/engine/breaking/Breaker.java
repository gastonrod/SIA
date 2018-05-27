package engine.breaking;

import engine.model.Individual;

import java.util.ArrayList;

@FunctionalInterface
public interface Breaker<T extends Individual> {

    boolean shouldBreak(ArrayList<T> population, int generation);
}
