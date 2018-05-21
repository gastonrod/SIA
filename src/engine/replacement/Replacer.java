package engine.replacement;

import engine.model.Individual;

import java.util.List;

@FunctionalInterface
public interface Replacer {

    List<Individual> replace(List<Individual> population, List<Individual> children, int generationalGap);
}
