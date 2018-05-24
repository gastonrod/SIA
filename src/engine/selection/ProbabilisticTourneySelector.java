package engine.selection;

import engine.FitnessFunction;
import engine.model.Individual;

import java.util.ArrayList;
import java.util.List;

public class ProbabilisticTourneySelector extends TourneySelector{
    private static final double p = 0.75;
    private static final int participants = 2;

    public ProbabilisticTourneySelector(){
        super(participants);
    }

    @Override
    public List<Individual> select(List<Individual> population, int k, FitnessFunction fitnessFunction) {
        currentPopulation = population;
        List<Individual> winners = new ArrayList<>();

        for(int i = 0; i < k; i++){
            int[] participants = getParticipantsIndexes();
            Individual ind1 = population.get(participants[0]);
            double fitness1 = fitnessFunction.eval(ind1);
            Individual ind2 = population.get(participants[1]);
            double fitness2 = fitnessFunction.eval(ind2);

            if( Math.random() <= p){
                winners.add((fitness1 > fitness2)?(ind1):(ind2));
            }else{
                winners.add((fitness1 <= fitness2)?(ind1):(ind2));
            }
        }

        return winners;
    }


}
