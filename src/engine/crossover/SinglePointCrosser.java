package engine.crossover;

import engine.model.Individual;
import engine.model.Pair;

public class SinglePointCrosser<T extends Individual> extends AbstractCrosser<T> {

    @Override
    public Pair<T> cross(Pair<T> pair) {
        super.init(pair);
        int r = rand.nextInt(pair.first.getLocusAmount());

        for (int i = r; i < pair.first.getLocusAmount(); i++) {
            children.first.exchangeAt(children.second, i);
        }

        return children;
    }
}
