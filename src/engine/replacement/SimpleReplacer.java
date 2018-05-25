package engine.replacement;

import engine.model.Individual;

import java.util.ArrayList;
import java.util.Collections;

public class SimpleReplacer implements Replacer {

    @Override
    public void replace(ArrayList<Individual> population, ArrayList<Individual> children) {
        Collections.shuffle(population);
        for(int i = 0; i < children.size(); i++) {
            population.remove(population.size() - 1);
        }
        population.addAll(children);
    }
}
