package engine.selection;

import engine.FitnessFunction;
import engine.model.Individual;

import java.util.ArrayList;

public class ProbabilisticTourneySelector<T extends Individual> extends TourneySelector<T> {
    private static final double p = 0.75;
    private static final int PARTICIPANTS = 2;

    public ProbabilisticTourneySelector() {
        super(PARTICIPANTS);
    }

    public static boolean isValidPopulationSize(int popSize) {
        return popSize >= PARTICIPANTS;
    }

    @Override
    public ArrayList<T> select(ArrayList<T> population, int k, FitnessFunction<T> fitnessFunction, int generation) {
        currentPopulation = population;
        ArrayList<T> winners = new ArrayList<>();

        for (int i = 0; i < k; i++) {
            int[] participants = getParticipantsIndexes();
            T ind1 = population.get(participants[0]);
            double fitness1 = fitnessFunction.eval(ind1);
            T ind2 = population.get(participants[1]);
            double fitness2 = fitnessFunction.eval(ind2);

            if (Math.random() <= p) {
                winners.add((fitness1 > fitness2) ? (ind1) : (ind2));
            } else {
                winners.add((fitness1 <= fitness2) ? (ind1) : (ind2));
            }
        }
        return winners;
    }
}