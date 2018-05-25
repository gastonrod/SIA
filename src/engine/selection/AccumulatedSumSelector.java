package engine.selection;

import engine.FitnessFunction;
import engine.model.Individual;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

abstract class AccumulatedSumSelector implements Selector{


    protected double accumulatedSum[];
    protected List<Individual> selectedIndividuals;
    protected Individual pop[];

    protected void init(List<Individual> population, int k, FitnessFunction fitnessFunction){
        accumulatedSum = new double[k];
        pop = new Individual[population.size()];
        selectedIndividuals = new ArrayList<>();

        population.toArray(pop);
        double totalFitness = 0;
        for(int i = 0; i < k; i++){
            double fitness = fitnessFunction.eval(pop[i]);
            totalFitness += fitness;
            accumulatedSum[i] = totalFitness;
        }
        for(int i = 0; i < k; i++){
            accumulatedSum[i] /= totalFitness;
        }
    }
}
