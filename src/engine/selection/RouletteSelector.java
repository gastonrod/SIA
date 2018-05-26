package engine.selection;

import engine.FitnessFunction;
import engine.model.Individual;
import engine.utils.SearchUtils;

import java.util.List;


public class RouletteSelector<T extends Individual> extends AccumulatedSumSelector<T> {

    @Override
    public List<T> select(List<T> population, int k, FitnessFunction<T> fitnessFunction) {
        super.init(population, k, fitnessFunction);

        for(int i = 0; i < k; i++) {
            selectedIndividuals.add(pop.get(SearchUtils.upperBoundSearch(accumulatedSum, Math.random())));
        }
        return selectedIndividuals;
    }
}
