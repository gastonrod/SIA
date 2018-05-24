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
        Individual pop[] = new Individual[population.size()];
        population.toArray(pop);
        Arrays.sort(pop, new Comparator<Individual>() {
            @Override
            public int compare(Individual i1, Individual i2) {
                double f1 = fitnessFunction.eval(i1);
                double f2 = fitnessFunction.eval(i2);
                if(f1==f2)
                    return 0;
                if(f1>f2)
                    return 1;
                return -1;
            }
        });
        List<Individual> winners = new ArrayList<>();
        for (int i = 0; i < pop.length; i++){
            if (Math.random() <= 1.0 / i)
                winners.add(pop[i]);
        }
        return winners;
    }
}
