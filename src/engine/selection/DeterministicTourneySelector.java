package engine.selection;

import engine.FitnessFunction;
import engine.model.Individual;

import java.util.ArrayList;

public class DeterministicTourneySelector<T extends Individual> extends TourneySelector<T> {

    public DeterministicTourneySelector(int participantsPerDuel) {
        super(participantsPerDuel);
    }

    @Override
    public ArrayList<T> select(ArrayList<T> population, int k, FitnessFunction<T> fitnessFunction, int generation) {
        if (population.size() < participantsPerDuel) {
            throw new IllegalArgumentException("Participants per duel (" + participantsPerDuel + ") is larger than population size (" + population.size() + ")");
        }
        currentPopulation = population;
        ArrayList<T> winners = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            winners.add(getWinner(getParticipantsIndexes(), fitnessFunction));
        }
        return winners;
    }
}
