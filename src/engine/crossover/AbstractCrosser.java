package engine.crossover;

import engine.model.Individual;
import engine.model.Pair;

import java.util.Random;

public abstract class AbstractCrosser {

    Random rand;
    Pair<Individual> children;
    int locusAmount;


    void init(Pair<Individual> pair) {
        rand = new Random();
        locusAmount = pair.first.getLocusAmount();
        children = new Pair<>();

        children.first = pair.first.replicate();
        children.second = pair.second.replicate();
    }
}
