package engine;

@FunctionalInterface
public interface VariationFunction {

    double eval(int initialValue);
}
