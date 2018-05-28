package engine.replacement;

import engine.FitnessFunction;
import engine.model.Individual;
import engine.selection.MixSelector;

import java.util.ArrayList;

public class MixReplacer<T extends Individual> implements Replacer<T> {

    private MixSelector selector;

    public MixReplacer(MixSelector<T> selector) {
        this.selector = selector;
    }

    @Override
    public void replace(ArrayList<T> population, ArrayList<T> children, FitnessFunction<T> fitnessFunction, int generation) {
        ArrayList<T> nextGeneration = new ArrayList<>();
        int oldGenerationRemnants = population.size() - children.size();
        nextGeneration.addAll(selector.select(population,oldGenerationRemnants,fitnessFunction,generation));
        population.clear();
        population.addAll(nextGeneration);
        population.addAll(children);
    }
}
