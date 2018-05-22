package engine.selection;

import engine.FitnessFunction;
import engine.model.Individual;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ProbabilisticTourneySelector extends TourneySelector implements Selector{
    private int m = 2;
    private int p;
    Random rand;

    public ProbabilisticTourneySelector(double p){
        this.p = (int) (p * 100);
        rand = new Random();
    }

    @Override
    public List<Individual> select(List<Individual> population, int k, FitnessFunction fitnessFunction) {
        super.init(population, fitnessFunction);
        List<Individual> winners = new ArrayList<>();

        for(int i = 0; i < k; i++){
            List<Integer> participants = getWinnersIndexes(m);
            if(rand.nextInt(99)+1 <= p){
                winners.add(getWinner(participants));
            }else{
                participants.remove(getWinner(participants));
                winners.add(getWinner(participants));
            }
        }
        return winners;
    }


}
