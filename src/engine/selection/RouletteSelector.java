package engine.selection;

import engine.FitnessFunction;
import engine.model.Individual;
import java.util.List;


public class RouletteSelector extends AccumulatedSumSelector{

    @Override
    public List<Individual> select(List<Individual> population, int k, FitnessFunction fitnessFunction) {
        super.init(population, k, fitnessFunction);

        for(int i = 0; i < k; i++) {
            selectedIndividuals.add(pop[super.lowerBoundSearch(accumulatedSum, Math.random())]);
        }
        return selectedIndividuals;
    }

}
