package engine.breaking;

import engine.Breeder;
import engine.FitnessFunction;
import engine.engineException.InvalidProportionException;
import engine.model.Individual;

import java.util.ArrayList;

public class ContentBreaker<T extends Individual> implements Breaker<T> {

    private int window;
    private int sinceLastChange;
    private double tolerance;
    private double lastBest;

    public ContentBreaker(int window, double tolerance) {
        boolean isValidProportion = 0 <= tolerance && tolerance <= 1;
        if(!isValidProportion) {
            throw new InvalidProportionException(tolerance);
        }
        this.window = window;
        this.tolerance = tolerance;
        this.lastBest = 0;
        this.sinceLastChange = 0;
    }

    @Override
    public boolean shouldBreak(ArrayList<T> population, int generation, FitnessFunction<T> fitnessFunction, double optimalFitness) {
        double actualBest = Breeder.getBestFitness(population, fitnessFunction);
        boolean gotBetter = actualBest >= (lastBest + optimalFitness * tolerance);
        if(!gotBetter && sinceLastChange > window) {
            return true;
        } else if (gotBetter){
            sinceLastChange = 0;
            lastBest = actualBest;
        } else {
            sinceLastChange++;
        }
        return false;
    }
}
