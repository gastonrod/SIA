package engine.selection;

import engine.FitnessFunction;
import engine.model.Individual;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class RouletteSelector implements Selector {

    @Override
    public List<Individual> select(List<Individual> population, int k, FitnessFunction fitnessFunction) {
        double accumulatedSum[] = new double[k];
        Individual pop[] = new Individual[population.size()];

        population.toArray(pop);
        accumulatedSum[0] = fitnessFunction.eval(pop[0]);

        for(int i = 1; i < k; i++){
            accumulatedSum[i] = accumulatedSum[i-1] + fitnessFunction.eval(pop[i]);
        }

        List<Individual> selectedIndividuals = new ArrayList<>();
        for(int selectedAmount = k; selectedAmount > 0; selectedAmount--) {
            selectedIndividuals.add(pop[search(accumulatedSum, Math.random())]);
        }
        return selectedIndividuals;
    }

    private static int search(double[] arr, double key) {
        int idx = Arrays.binarySearch(arr, key);
        /* From the documentation:
            Returns:
            index of the search key, if it is contained in the array;
            otherwise, (-(insertion point) - 1). The insertion point
            is defined as the point at which the key would be inserted
            into the array: the index of the first element greater than
            the key, or a.length if all elements in the array are less
            than the specified key. Note that this guarantees that the
            return value will be >= 0 if and only if the key is found.
         */
        return (idx >= 0) ? (idx) : ((-1) * idx - 1);
    }
}
