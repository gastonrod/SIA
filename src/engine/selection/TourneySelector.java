package engine.selection;

import engine.FitnessFunction;
import engine.model.Individual;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class TourneySelector {
    List<Individual> pop;
    ArrayList<Integer> numberList;
    FitnessFunction fitnessFunction;

    void init(List<Individual> population, FitnessFunction fitnessFunction){
        numberList= new ArrayList<Integer>();
        for (int i=0; i< pop.size(); i++) {
            numberList.add(new Integer(i));
        }
    }

    List<Integer> getWinnersIndexes(int m){
        Collections.shuffle(numberList);
        List<Integer> winners= new ArrayList<>();
        for (int i=0; i<m; i++) {
            winners.add(numberList.get(i));
        }
        return winners;
    }

    Individual getWinner(List<Integer> winners){
        double biggestFitness = -1;
        int biggestFitnessIndex = 0;
        for(Integer i: winners){
            double f = fitnessFunction.eval(pop.get(i));
            if(f > biggestFitness){
                biggestFitness = f;
                biggestFitnessIndex = i;
            }
        }
        return pop.get(biggestFitnessIndex);
    }


}
