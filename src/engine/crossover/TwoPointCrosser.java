package engine.crossover;

import engine.engineException.InvalidLocusAmountException;
import engine.model.Individual;
import engine.model.Pair;

public class TwoPointCrosser implements Crosser{
    @Override
    public Pair<Individual> cross(Pair<Individual> pair) {
        if(pair.first.getLocusAmount() < 2) {
            throw new InvalidLocusAmountException();
        }

        int locus1 = (int) (Math.random() * pair.first.getLocusAmount());
        int locus2;
        while ((locus2 = (int) (Math.random() * pair.first.getLocusAmount())) == locus1);
        Pair<Individual> children = new Pair<>();

        children.first = pair.first.replicate() ;
        children.second = pair.second.replicate();
        children.first.exchangeAt(children.second,Math.min(locus1,locus2));
        children.first.exchangeAt(children.second,Math.max(locus1,locus2));

        return children;
    }
}
