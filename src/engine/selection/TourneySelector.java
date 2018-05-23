package engine.selection;

import engine.FitnessFunction;
import engine.model.Individual;

import java.util.List;
import java.util.Random;

abstract class TourneySelector implements Selector{

    protected List<Individual> currentPopulation;
    protected Random rand;
    protected int m;

    TourneySelector(int m){
        rand = new Random();
        this.m = m;
    }

    int[] getParticipantsIndexes(){
        return rand.ints(0, currentPopulation.size()-1).distinct().limit(m).toArray();
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
