package engine.selection;

import engine.FitnessFunction;
import engine.model.Individual;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EliteSelector implements Selector {

    @Override
    public List<Individual> select(List<Individual> population, int k, FitnessFunction fitnessFunction) {
        ArrayList<Individual> elite = new ArrayList<>(population.size());
        Iterator<Individual> iterator = population.iterator();
        for (int i = 0; iterator.hasNext(); i++) {
            elite.add(i, iterator.next());
        }
        elite.sort((i1, i2) -> Double.compare(fitnessFunction.eval(i2), fitnessFunction.eval(i1)));
        return elite.subList(0, k);
    }
}
