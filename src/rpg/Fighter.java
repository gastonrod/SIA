package rpg;

import engine.engineException.InvalidLocusException;
import engine.model.Individual;
import rpg.items.Equipment;
import rpg.items.EquipmentStash;

import java.util.Arrays;

import static rpg.FighterManager.MAX_HEIGHT;
import static rpg.FighterManager.MIN_HEIGHT;

public class Fighter implements Individual {

    private static int nextId = 0;

    private double height;
    private Equipment[] equipment;
    private EquipmentStash equipmentStash;
    private int id;

    Fighter(double height, Equipment[] equipment, EquipmentStash equipmentStash) {
        this.height = height;
        this.equipment = equipment;
        this.equipmentStash = equipmentStash;
        this.id = nextId++;
    }

    @Override
    public Fighter replicate() {
        return new Fighter(this.height, this.equipment.clone(), equipmentStash);
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
            height = (Math.random() * (MAX_HEIGHT - MIN_HEIGHT)) + MIN_HEIGHT;
        } else {
            equipment[locus - 1] = equipmentStash.getRandomEquipment(equipment[locus - 1].getType());
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

    @Override
    public String toString() {
        return "Fighter " + getId() + ": Height=" + height + "; equipment = " + Arrays.toString(equipment);
    }

    public int getId() {
        return id;
    }
}
