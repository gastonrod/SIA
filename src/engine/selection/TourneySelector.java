package engine.selection;

import engine.FitnessFunction;
import engine.model.Individual;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public abstract class TourneySelector {

    List<Individual> pop;
    Random rand;
    int m;

    TourneySelector(int m){
        rand = new Random();
        this.m = m;
    }

    int[] getParticipantsIndexes(){
        return rand.ints(0, pop.size()-1).distinct().limit(m).toArray();
    }

    Individual getWinner(int[] winners, FitnessFunction fitnessFunction){
        double biggestFitness = -1;
        int biggestFitnessIndex = 0;
        for(int i = 0; i< winners.length; i++){
            double f = fitnessFunction.eval(pop.get(i));
            if(f > biggestFitness){
                biggestFitness = f;
                biggestFitnessIndex = i;
            }
        }
        return pop.get(biggestFitnessIndex);
    }

}
