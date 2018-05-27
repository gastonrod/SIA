package engine.replacement;

import engine.model.Individual;

import java.util.ArrayList;
import java.util.Collections;

public class SimpleReplacer<T extends Individual> implements Replacer<T> {

    @Override
    public void replace(ArrayList<T> population, ArrayList<T> children) {
        Collections.shuffle(population);
        for (int i = 0; i < children.size(); i++) {
            population.remove(population.size() - 1);
        }
        population.addAll(children);
    }
}
