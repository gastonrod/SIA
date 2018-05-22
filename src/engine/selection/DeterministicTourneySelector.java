package engine.selection;

import engine.FitnessFunction;
import engine.model.Individual;

import java.util.ArrayList;
import java.util.List;

public class DeterministicTourneySelector extends TourneySelector implements Selector{

    public DeterministicTourneySelector(int m){
        super(m);
    }

    @Override
    public List<Individual> select(List<Individual> population, int k, FitnessFunction fitnessFunction) {
        pop = population;
        List<Individual> winners = new ArrayList<>();
        for(int i = 0; i < k; i++){
            winners.add(getWinner(getParticipantsIndexes(), fitnessFunction));
        }
        return winners;
    }

}
