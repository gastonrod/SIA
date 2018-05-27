package engine.selection;

import engine.FitnessFunction;
import engine.model.Individual;

import java.util.ArrayList;
import java.util.Iterator;

public class EliteSelector<T extends Individual> implements Selector<T> {

    @Override
    public ArrayList<T> select(ArrayList<T> population, int k, FitnessFunction<T> fitnessFunction, int generation) {
        ArrayList<T> elite = new ArrayList<>(population.size());
        Iterator<T> iterator = population.iterator();
        for (int i = 0; iterator.hasNext(); i++) {
            elite.add(i, iterator.next());
        }
        elite.sort((i1, i2) -> Double.compare(fitnessFunction.eval(i2), fitnessFunction.eval(i1)));
        return new ArrayList<>(elite.subList(0, k));
    }
}
