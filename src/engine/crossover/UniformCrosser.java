package engine.crossover;

import engine.model.Individual;
import engine.model.Pair;

public class UniformCrosser extends AbstractCrosser implements Crosser{
    @Override
    public Pair<Individual> cross(Pair<Individual> pair) {
        super.init(pair);

        for(int i = 0; i < locusAmount; i++){
            if(rand.nextBoolean()){
                children.first.exchangeAt(children.second, i);
            }
        }
        return children;
    }
}
