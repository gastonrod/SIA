package rpg;

import engine.engineException.InvalidLocusException;
import engine.model.Individual;
import rpg.items.Equipment;

public class Fighter implements Individual{

    private double height;
    private double[] stats;
    private Equipment[] equipment;

    @Override
    public Individual replicate() {
        return null;
    }

    @Override
    public int getLocusAmount() {
        return 0;
    }

    @Override
    public void exchangeAt(Individual otherIndividual, int locus) throws InvalidLocusException {

    }

    @Override
    public void mutateAt(int locus) throws InvalidLocusException {

    }

    public double getHeight(){
        return height;
    }

    public double[] getStats(){
        return stats;
    }

    public Equipment[] getEquipment(){
        return equipment;
    }

}
