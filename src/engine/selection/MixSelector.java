package engine.selection;

import engine.FitnessFunction;
import engine.engineException.InvalidProportionException;
import engine.model.Individual;

import java.util.ArrayList;
import java.util.List;

public class MixSelector<T extends Individual> implements Selector<T> {

    private Selector<T> selector1;
    private Selector<T> selector2;
    private double proportionOfK;

    public MixSelector(Selector<T> selector1, Selector<T> selector2, double proportionOfK) {
        this.selector1 = selector1;
        this.selector2 = selector2;
        boolean isValidProportion = 0 <= proportionOfK && proportionOfK <= 1;
        if (!isValidProportion) {
            throw new InvalidProportionException(proportionOfK);
        }
        this.proportionOfK = proportionOfK;
    }

    @Override
    public List<T> select(List<T> population, int k, FitnessFunction<T> fitnessFunction, int generation) {
        List<T> children = new ArrayList<>();
        int proportion1 = (int) Math.round(k * proportionOfK);
        int proportion2 = k - proportion1;
        children.addAll(selector1.select(population, proportion1, fitnessFunction, generation));
        children.addAll(selector2.select(population, proportion2, fitnessFunction, generation));
        return children;
    }
}
