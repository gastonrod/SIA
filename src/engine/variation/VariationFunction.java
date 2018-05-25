package engine.variation;

@FunctionalInterface
public interface VariationFunction {

    double eval(int generation);
}
