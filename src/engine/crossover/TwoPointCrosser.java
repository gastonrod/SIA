package engine.crossover;

import engine.engineException.InvalidLocusAmountException;
import engine.model.Individual;
import engine.model.Pair;

import java.util.Random;

public class TwoPointCrosser implements Crosser{
    @Override
    public Pair<Individual> cross(Pair<Individual> pair) {
        if(pair.first.getLocusAmount() < 2) {
            throw new InvalidLocusAmountException();
        }
        Random rand = new Random();
        int r1 = rand.nextInt(pair.first.getLocusAmount());
        int r2;
        while ((r2 = rand.nextInt(pair.first.getLocusAmount())) == r1);
        Pair<Individual> children = new Pair<>();

        children.first = pair.first.replicate() ;
        children.second = pair.second.replicate();

        for(int i = Math.min(r1,r2); i < Math.max(r1,r2); i++){
            children.first.exchangeAt(children.second, i);
        }

        return children;
    }
}
