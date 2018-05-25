package engine.replacement;

import engine.model.Individual;

import java.util.ArrayList;
import java.util.List;

@FunctionalInterface
public interface Replacer {

    void replace(ArrayList<Individual> population, ArrayList<Individual> children);
}
