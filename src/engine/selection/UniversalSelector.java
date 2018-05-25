package engine.selection;

import engine.FitnessFunction;
import engine.model.Individual;
import java.util.List;


public class UniversalSelector extends AccumulatedSumSelector{

    @Override
    public List<Individual> select(List<Individual> population, int k, FitnessFunction fitnessFunction) {
        super.init(population, k, fitnessFunction);
        double r = Math.random();

        for(int i = 1; i <= k; i++) {
            double rand = (r + i - 1.0) / k;
            selectedIndividuals.add(pop[super.upperBoundSearch(accumulatedSum, rand)]);
        }
        return selectedIndividuals;
    }

}

