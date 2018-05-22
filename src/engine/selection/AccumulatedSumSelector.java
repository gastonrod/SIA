package engine.selection;

import engine.FitnessFunction;
import engine.model.Individual;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AccumulatedSumSelector {


    double accumulatedSum[];
    List<Individual> selectedIndividuals;
    Individual pop[];

    void init(List<Individual> population, int k, FitnessFunction fitnessFunction){
        accumulatedSum = new double[k];
        pop = new Individual[population.size()];
        selectedIndividuals = new ArrayList<>();

        population.toArray(pop);
        double totalFitness = 0;
        for(int i = 0; i < k; i++){
            double fitness = fitnessFunction.eval(pop[i]);
            totalFitness += fitness;
            accumulatedSum[i] = fitness;
        }
        for(int i = 0; i < k; i++){
            accumulatedSum[i] /= totalFitness;
        }
    }

    int search(double[] arr, double key) {
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
