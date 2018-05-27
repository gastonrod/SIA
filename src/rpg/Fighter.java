package rpg;

import engine.engineException.InvalidLocusException;
import engine.model.Individual;
import rpg.items.Equipment;
import rpg.items.EquipmentStash;

import static rpg.FighterManager.maxHeight;
import static rpg.FighterManager.minHeight;

public class Fighter implements Individual {

    private double height;
    private Equipment[] equipment;

    Fighter(double height, Equipment[] equipment) {
        this.height = height;
        this.equipment = equipment;
    }

    @Override
    public Individual replicate() {
        return new Fighter(this.height, this.equipment.clone());
    }

    @Override
    public int getLocusAmount() {
        return equipment.length + 1;
    }


    @Override
    public void exchangeAt(Individual otherIndividual, int locus) throws InvalidLocusException {
        checkLocus(locus);
        Fighter otherFighter = (Fighter) otherIndividual;
        if (locus == 0) {
            double auxHeight = otherFighter.height;
            otherFighter.height = this.height;
            this.height = auxHeight;
        } else {
            Equipment auxEquipment = otherFighter.equipment[locus - 1];
            otherFighter.equipment[locus - 1] = this.equipment[locus - 1];
            this.equipment[locus - 1] = auxEquipment;
        }
    }


    @Override
    public void mutateAt(int locus) throws InvalidLocusException {
        checkLocus(locus);
        if (locus == 0) {
            height = (Math.random() * (maxHeight - minHeight)) + minHeight;
        } else {
            equipment[locus - 1] = EquipmentStash.getRandomEquipment(equipment[locus - 1].getType());
        }
    }

    private void checkLocus(int locus) {
        if (locus >= getLocusAmount() || locus < 0)
            throw new InvalidLocusException(locus);
    }

    public double getHeight() {
        return height;
    }

    public Equipment[] getEquipment() {
        return equipment;
    }
}
