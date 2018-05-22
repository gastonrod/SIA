package engine.selection;

import engine.FitnessFunction;
import engine.model.Individual;

import java.util.ArrayList;
import java.util.List;

public class DeterministicTourneySelector extends TourneySelector implements Selector{

    private int m;
    public DeterministicTourneySelector(int m){
        this.m = m;
    }

    @Override
    public List<Individual> select(List<Individual> population, int k, FitnessFunction fitnessFunction) {
        super.init(population, fitnessFunction);
        List<Individual> winners = new ArrayList<>();
        for(int i = 0; i < k; i++){
            winners.add(getWinner(getWinnersIndexes(m)));
        }
        return winners;
    }

}
