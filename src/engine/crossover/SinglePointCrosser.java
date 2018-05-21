package engine.crossover;

import engine.model.Individual;
import engine.model.Pair;

import java.util.Random;

public class SinglePointCrosser implements Crosser {

    @Override
    public Pair<Individual> cross(Pair<Individual> pair) {
        Random rand = new Random();
        int r = rand.nextInt(pair.first.getLocusAmount());
        Pair<Individual> children = new Pair<>();

        children.first = pair.first.replicate() ;
        children.second = pair.second.replicate();

        for(int i = r; i < pair.first.getLocusAmount(); i++){
            children.first.exchangeAt(children.second, i);
        }

        return children;
    }
}
