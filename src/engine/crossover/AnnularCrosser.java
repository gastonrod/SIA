package engine.crossover;

import engine.model.Individual;
import engine.model.Pair;

public class AnnularCrosser<T extends Individual> extends AbstractCrosser<T> {


    @Override
    public Pair<T> cross(Pair<T> pair) {
        super.init(pair);
        int r = rand.nextInt(locusAmount);
        int l = rand.nextInt((int) (locusAmount / 2.0 - 1)) + 1;

        for (int i = 0; i < l; i++) {
            children.first.exchangeAt(children.second, (r + i) % locusAmount);
        }
        return children;
    }
}
