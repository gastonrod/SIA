package engine.selection;

import engine.FitnessFunction;
import engine.model.Individual;

import java.util.ArrayList;
import java.util.List;

public class BoltzmannSelector<T extends Individual> implements Selector<T> {

    private RouletteSelector<T> rouletteSelector = new RouletteSelector<>();

    @Override
    public ArrayList<T> select(ArrayList<T> population, int k, FitnessFunction<T> fitnessFunction, int generation) {
        BoltzmannFitness boltzmannFitness = new BoltzmannFitness(population, fitnessFunction, generation);
        return rouletteSelector.select(population, k, boltzmannFitness, generation);
    }

    private class BoltzmannFitness implements FitnessFunction<T> {

        private static final double START_TEMP = 1000;  // Arbitrario
        private static final double END_TEMP = 1;       // Arbitrario

        private final double average;
        private final FitnessFunction<T> actualFitnessFunction;
        private final int generation;

        BoltzmannFitness(List<T> population, FitnessFunction<T> actualFitnessFunction, int generation) {
            this.actualFitnessFunction = actualFitnessFunction;
            this.generation = generation;
            double auxAverage = 0;
            for (T individual : population) {
                auxAverage += Math.exp(actualFitnessFunction.eval(individual) / calculateTemperature(generation));
            }
            auxAverage /= population.size();
            average = auxAverage;
        }

        @Override
        public double eval(T i) {
            return Math.exp(actualFitnessFunction.eval(i) / calculateTemperature(generation)) / average;
        }

        /* Es una función positiva y decreciente de la generación */
        private double calculateTemperature(int generation) {
            return (START_TEMP - END_TEMP) * Math.exp(-generation) + END_TEMP;
        }
    }
}
