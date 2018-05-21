package engine.crossover;

import engine.engineException.InvalidLocusAmountException;
import engine.model.Individual;
import engine.model.Pair;

public class TwoPointCrosser extends AbstractCrosser implements Crosser{
    @Override
    public Pair<Individual> cross(Pair<Individual> pair) {
        if(pair.first.getLocusAmount() < 2) {
            throw new InvalidLocusAmountException();
        }

        super.init(pair);

        int r1 = rand.nextInt(pair.first.getLocusAmount());
        int r2;
        while ((r2 = rand.nextInt(pair.first.getLocusAmount())) == r1);

        for(int i = Math.min(r1,r2); i < Math.max(r1,r2); i++){
            children.first.exchangeAt(children.second, i);
        }

        return children;
    }
}