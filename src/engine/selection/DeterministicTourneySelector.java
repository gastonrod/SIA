package engine.selection;

import engine.FitnessFunction;
import engine.model.Individual;

import java.util.ArrayList;
import java.util.List;

public class DeterministicTourneySelector extends TourneySelector{

    public DeterministicTourneySelector(int participantsPerDuel){
        super(participantsPerDuel);
    }

    @Override
    public List<Individual> select(List<Individual> population, int k, FitnessFunction fitnessFunction) {
        currentPopulation = population;
        List<Individual> winners = new ArrayList<>();
        for(int i = 0; i < k; i++){
            winners.add(getWinner(getParticipantsIndexes(), fitnessFunction));
        }
        return winners;
    }

}
