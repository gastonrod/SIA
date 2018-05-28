package engine.replacement;

import engine.FitnessFunction;
import engine.model.Individual;

import java.util.ArrayList;
import java.util.Comparator;

public class WorstIndividualsReplacer<T extends Individual> implements Replacer<T> {
    @Override
    public void replace(ArrayList<T> population, ArrayList<T> children, FitnessFunction<T> fitnessFunction) {
        population.sort(Comparator.comparingDouble(fitnessFunction::eval));
        for (T individual : children) {
            population.remove(0);
            population.add(individual);
        }
    }
}
