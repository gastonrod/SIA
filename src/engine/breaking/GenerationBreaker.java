package engine.breaking;

import engine.model.Individual;

import java.util.ArrayList;

public class GenerationBreaker<T extends Individual> implements Breaker<T> {

    private int maxGeneration;

    public GenerationBreaker(int maxGeneration) {
        this.maxGeneration = maxGeneration;
    }

    @Override
    public boolean shouldBreak(ArrayList<T> population, int generation) {
        return generation >= maxGeneration;
    }
}
