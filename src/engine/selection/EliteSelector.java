package engine.selection;

import engine.FitnessFunction;
import engine.model.Individual;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EliteSelector<T extends Individual> implements Selector<T> {

    @Override
    public List<T> select(List<T> population, int k, FitnessFunction<T> fitnessFunction, int generation) {
        ArrayList<T> elite = new ArrayList<>(population.size());
        Iterator<T> iterator = population.iterator();
        for (int i = 0; iterator.hasNext(); i++) {
            elite.add(i, iterator.next());
        }
        elite.sort((i1, i2) -> Double.compare(fitnessFunction.eval(i2), fitnessFunction.eval(i1)));
        return elite.subList(0, k);
    }
}
