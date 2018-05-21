package engine.crossover;

import engine.model.Individual;
import engine.model.Pair;

import java.util.Random;

public class AnnularCrosser implements Crosser{


    @Override
    public Pair<Individual> cross(Pair<Individual> pair) {
        Random rand = new Random();
        int locusAmount = pair.first.getLocusAmount();
        int r = rand.nextInt(locusAmount);
        int l = rand.nextInt((int) (locusAmount  / 2.0 - 1)) + 1;
        Pair<Individual> children = new Pair<>();

        children.first = pair.first.replicate() ;
        children.second = pair.second.replicate();

        for(int i = 0; i < l; i++){
            children.first.exchangeAt(children.second, (r + i) % locusAmount);
        }
        return children;
    }
}
