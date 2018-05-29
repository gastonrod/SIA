package rpg.items;

import rpg.ItemsInputManager;
import rpg.RpgFilesLocationPropertiesManager;
import rpg.stats.Stats;

import java.util.ArrayList;
import java.util.Arrays;

public class EquipmentStash {

    private ArrayList<ArrayList<Equipment>> equipmentsStashes;

    private double[] bestValues;

    public EquipmentStash(RpgFilesLocationPropertiesManager prop) {
        ItemsInputManager itemsInputManager = new ItemsInputManager(prop);
        equipmentsStashes = new ArrayList<>(EquipmentType.values().length);

        for (int i = 0; i < EquipmentType.values().length; i++) {
            equipmentsStashes.add(i, new ArrayList<>());
        }
        equipmentsStashes.get(EquipmentType.BOOTS.ordinal()).addAll(itemsInputManager.readBoots());
        equipmentsStashes.get(EquipmentType.CHEST_PIECE.ordinal()).addAll(itemsInputManager.readChestPieces());
        equipmentsStashes.get(EquipmentType.GLOVES.ordinal()).addAll(itemsInputManager.readGloves());
        equipmentsStashes.get(EquipmentType.HELMET.ordinal()).addAll(itemsInputManager.readHelmets());
        equipmentsStashes.get(EquipmentType.WEAPON.ordinal()).addAll(itemsInputManager.readWeapons());

        calculateBestValues();
    }

    public Equipment[] generateRandomSet() {
        int setSize = EquipmentType.values().length;
        Equipment[] set = new Equipment[setSize];
        for (int i = 0; i < setSize; i++) {
            set[i] = getRandomEquipment(EquipmentType.values()[i]);
        }
        return set;
    }

    public Equipment getRandomEquipment(EquipmentType type) {
        return equipmentsStashes.get(type.ordinal()).get((int) (Math.random() * equipmentsStashes.get(type.ordinal()).size()));
    }

    public double[] getBestSumForStats() {
        return bestValues;
    }

    private void calculateBestValues() {
        bestValues = new double[Stats.values().length];
        double[] bestValuesAux = new double[bestValues.length];
        for (EquipmentType type : EquipmentType.values()) {
            Arrays.fill(bestValuesAux, 0);
            ArrayList<Equipment> equipments = equipmentsStashes.get(type.ordinal());
            for (Equipment equipment : equipments) {
                for (Stats stat : Stats.values()) {
                    bestValuesAux[stat.ordinal()] = Math.max(bestValuesAux[stat.ordinal()], equipment.getStat(stat));
                }
            }
            for (Stats stat : Stats.values()) {
                bestValues[stat.ordinal()] += bestValuesAux[stat.ordinal()];
            }
        }
    }
}