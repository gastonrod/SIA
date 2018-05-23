package engine.selection;

import engine.FitnessFunction;
import engine.model.Individual;

import java.util.List;
import java.util.Random;

abstract class TourneySelector implements Selector{

    protected List<Individual> currentPopulation;
    protected Random rand;
    protected int participantsPerDuel;

    TourneySelector(int participantsPerDuel){
        rand = new Random();
        this.participantsPerDuel = participantsPerDuel;
    }

    int[] getParticipantsIndexes(){
        return rand.ints(0, currentPopulation.size()-1).distinct().limit(participantsPerDuel).toArray();
    }

    Individual getWinner(int[] participants, FitnessFunction fitnessFunction){
        double biggestFitness = -1;
        int biggestFitnessIndex = 0;
        for(int i = 0; i< participants.length; i++){
            double f = fitnessFunction.eval(currentPopulation.get(i));
            if(f > biggestFitness){
                biggestFitness = f;
                biggestFitnessIndex = i;
            }
        }
        return currentPopulation.get(biggestFitnessIndex);
    }

}
