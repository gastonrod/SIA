package engine.crossover;

import engine.model.Individual;
import engine.model.Pair;

public class SinglePointCrosser extends AbstractCrosser implements Crosser {

    @Override
    public Pair<Individual> cross(Pair<Individual> pair) {
        super.init(pair);
        int r = rand.nextInt(pair.first.getLocusAmount());

        for (int i = r; i < pair.first.getLocusAmount(); i++) {
            children.first.exchangeAt(children.second, i);
        }

        return children;
    }
}
