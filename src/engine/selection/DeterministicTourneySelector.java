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
        currentPopulation = population;
        ArrayList<T> winners = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            winners.add(getWinner(getParticipantsIndexes(), fitnessFunction));
        }
        return winners;
    }

}
