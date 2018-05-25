package engine.selection;

import engine.FitnessFunction;
import engine.engineException.InvalidLocusException;
import engine.model.Individual;

import java.util.*;

public class RankingSelector<T extends Individual> implements Selector<T> {

    private final RouletteSelector<MockPositionalIndividual> rouletteSelector = new RouletteSelector<>();
    private final MockFitnessFunction mockFitnessFunction = new MockFitnessFunction();

    @Override
    public List<T> select(List<T> population, int k, FitnessFunction<T> fitnessFunction) {
        ArrayList<MockPositionalIndividual> mockPopulation = new ArrayList<>();
        Iterator<T> populationIterator = population.iterator();
        for (int i = 1; populationIterator.hasNext(); i++) {
            mockPopulation.add(new MockPositionalIndividual(i));
        }
        mockPopulation.sort(Comparator.comparingInt(MockPositionalIndividual::getPosition));
        List<MockPositionalIndividual> mockChildren = rouletteSelector.select(mockPopulation, k, mockFitnessFunction);
        List<T> selectedChildren = new ArrayList<>(mockChildren.size());
        for (MockPositionalIndividual mpi : mockChildren) {
            selectedChildren.add(population.get(mpi.getPosition()-1));
        }
        return selectedChildren;
    }

    private static class MockPositionalIndividual implements Individual {

        private final int position;

        MockPositionalIndividual(int position) {
            this.position = position;
        }

        int getPosition() {
            return position;
        }

        @Override
        public Individual replicate() {
            throw new UnsupportedOperationException("Attempt to replicate " + getClass().toString());
        }

        @Override
        public int getLocusAmount() {
            throw new UnsupportedOperationException("Attempt to get locus amount " + getClass().toString());
        }

        @Override
        public void exchangeAt(Individual otherIndividual, int locus) throws InvalidLocusException {
            throw new UnsupportedOperationException("Attempt to exchange " + getClass().toString());
        }

        @Override
        public void mutateAt(int locus) throws InvalidLocusException {
            throw new UnsupportedOperationException("Attempt to mutate " + getClass().toString());
        }
    }

    private static class MockFitnessFunction implements FitnessFunction<MockPositionalIndividual> {

        @Override
        public double eval(MockPositionalIndividual i) {
            return i.position;
        }
    }

}
