package engine.selection;

import engine.FitnessFunction;
import engine.model.Individual;

import java.util.ArrayList;
import java.util.List;

public class DeterministicTourneySelector<T extends Individual> extends TourneySelector<T> {

    public DeterministicTourneySelector(int participantsPerDuel){
        super(participantsPerDuel);
    }

    @Override
    public List<T> select(List<T> population, int k, FitnessFunction<T> fitnessFunction) {
        currentPopulation = population;
        List<T> winners = new ArrayList<>();
        for(int i = 0; i < k; i++){
            winners.add(getWinner(getParticipantsIndexes(), fitnessFunction));
        }
        return winners;
    }

}
