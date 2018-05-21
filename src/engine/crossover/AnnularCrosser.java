package engine.crossover;

import engine.model.Individual;
import engine.model.Pair;

import java.util.Random;

public class AnnularCrosser extends AbstractCrosser implements Crosser{


    @Override
    public Pair<Individual> cross(Pair<Individual> pair) {
        super.init(pair);
        int r = rand.nextInt(locusAmount);
        int l = rand.nextInt((int) (locusAmount  / 2.0 - 1)) + 1;

        for(int i = 0; i < l; i++){
            children.first.exchangeAt(children.second, (r + i) % locusAmount);
        }
        return children;
    }
}
