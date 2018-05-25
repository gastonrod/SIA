package engine.replacement;

import engine.engineException.InvalidGenerationGapException;
import engine.model.Individual;

import java.util.List;

public class SimpleReplacer implements Replacer {

    @Override
    public List<Individual> replace(List<Individual> population, List<Individual> children, double generationalGap) {
        int oldGenerationRemnants = (int) (population.size() * (1 - generationalGap));
        int newComers = population.size() - oldGenerationRemnants;
        boolean canFillGap = newComers <= children.size();
        if(!canFillGap) {
            throw new InvalidGenerationGapException();
        }
        List<Individual> nextGeneration = population.subList(0,oldGenerationRemnants);
        nextGeneration.addAll(children.subList(0,newComers));
        return nextGeneration;
    }
}
