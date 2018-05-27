package engine.selection;

import engine.FitnessFunction;
import engine.model.Individual;

import java.util.ArrayList;
import java.util.List;

abstract class AccumulatedSumSelector<T extends Individual> implements Selector<T> {


    protected double[] accumulatedSum;
    protected ArrayList<T> selectedIndividuals;
    protected ArrayList<T> pop;

    protected void init(List<T> population, int k, FitnessFunction<T> fitnessFunction) {
        accumulatedSum = new double[k];
        pop = new ArrayList<>(population.size());
        selectedIndividuals = new ArrayList<>(k);
        pop.addAll(population);
        double totalFitness = 0;
        for (int i = 0; i < k; i++) {
            double fitness = fitnessFunction.eval(pop.get(i));
            totalFitness += fitness;
            accumulatedSum[i] = totalFitness;
        }
        for (int i = 0; i < k; i++) {
            accumulatedSum[i] /= totalFitness;
        }
    }
}
