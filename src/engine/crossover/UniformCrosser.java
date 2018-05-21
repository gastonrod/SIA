package engine.crossover;

import engine.model.Individual;
import engine.model.Pair;

public class UniformCrosser extends AbstractCrosser implements Crosser{

    private int p;

    public UniformCrosser(double p){
        this.p = (int) p * 100;
    }

    @Override
    public Pair<Individual> cross(Pair<Individual> pair) {
        super.init(pair);

        for(int i = 0; i < locusAmount; i++){
            if((rand.nextInt(99)+1) < p ){
                children.first.exchangeAt(children.second, i);
            }
        }
        return children;
    }
}
