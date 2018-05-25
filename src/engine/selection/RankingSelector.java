package engine.selection;

import engine.FitnessFunction;
import engine.model.Individual;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class RankingSelector implements Selector {
    @Override
    public List<Individual> select(List<Individual> population, int k, FitnessFunction fitnessFunction) {
        Individual[] pop = new Individual[population.size()];
        population.toArray(pop);
        Arrays.sort(pop, Comparator.<Individual>comparingDouble(i -> fitnessFunction.eval(i)));

        List<Individual> winners = new ArrayList<>();
        for (int i = 1; i <= pop.length; i++){
            if (Math.random() <= 1.0 / i)
                winners.add(pop[i-1]);
        }
        return winners;
    }
}
