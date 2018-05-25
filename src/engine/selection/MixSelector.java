package engine.selection;

import engine.FitnessFunction;
import engine.engineException.InvalidFractionException;
import engine.model.Individual;

import java.util.ArrayList;
import java.util.List;

public class MixSelector implements Selector {

    private Selector selector1;
    private Selector selector2;
    private double fractionOfK;

    public MixSelector(Selector selector1, Selector selector2, double fractionOfK) {
        this.selector1 = selector1;
        this.selector2 = selector2;
        boolean isValidFraction = 0 <= fractionOfK && fractionOfK <= 1;
        if(!isValidFraction) {
            throw new InvalidFractionException(fractionOfK);
        }
        this.fractionOfK = fractionOfK;
    }
    @Override
    public List<Individual> select(List<Individual> population, int k, FitnessFunction fitnessFunction) {
        List<Individual> children = new ArrayList<>();
        int fraction1 = (int) (k / fractionOfK);
        int fraction2 = k - fraction1;
        children.addAll(selector1.select(population, fraction1, fitnessFunction));
        children.addAll(selector2.select(population, fraction2, fitnessFunction));
        return children;
    }
}
