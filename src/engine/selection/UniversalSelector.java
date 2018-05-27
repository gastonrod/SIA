package engine.selection;

import engine.FitnessFunction;
import engine.model.Individual;
import engine.utils.SearchUtils;

import java.util.ArrayList;


public class UniversalSelector<T extends Individual> extends AccumulatedSumSelector<T> {

    @Override
    public ArrayList<T> select(ArrayList<T> population, int k, FitnessFunction<T> fitnessFunction, int generation) {
        super.init(population, k, fitnessFunction);
        double r = Math.random();

        for (int i = 1; i <= k; i++) {
            double rand = (r + i - 1.0) / k;
            selectedIndividuals.add(pop.get(SearchUtils.upperBoundSearch(accumulatedSum, rand)));
        }
        return selectedIndividuals;
    }

}