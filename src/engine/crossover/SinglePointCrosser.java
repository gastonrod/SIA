package engine.crossover;

import engine.model.Individual;
import engine.model.Pair;

public class SinglePointCrosser implements Crosser {

    @Override
    public Pair<Individual> cross(Pair<Individual> pair) {
        int locus = (int) (Math.random() * pair.first.getLocusAmount());
        Pair<Individual> children = new Pair<>();

        children.first = pair.first.replicate() ;
        children.second = pair.second.replicate();
        children.first.exchangeAt(children.second, locus);

        return children;
    }
}
