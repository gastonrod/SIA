package engine.crossover;

import engine.model.Individual;
import engine.model.Pair;

import java.util.Random;

abstract class AbstractCrosser<T extends Individual> implements Crosser<T>{

    Random rand;
    Pair<T> children;
    int locusAmount;


    void init(Pair<T> pair) {
        rand = new Random();
        locusAmount = pair.first.getLocusAmount();
        children = new Pair<>();

        children.first = (T) pair.first.replicate();
        children.second = (T) pair.second.replicate();
    }
}
