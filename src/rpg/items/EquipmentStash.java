package rpg.items;

import java.util.List;

public class EquipmentStash {
    private static final List<Equipment>[] equipmentsStashes = null;
    // TODO El inicializador, hay que ver como lo vamos a hacer.

    public static Equipment[] generateRandomSet() {
        int setSize = EquipmentType.values().length;
        Equipment[] set = new Equipment[setSize];
        for(int i = 0; i < setSize; i++) {
            set[i] = getRandomEquipment(EquipmentType.values()[i]);
        }
        return set;
    }

    public static Equipment getRandomEquipment(EquipmentType type) {
        return equipmentsStashes[type.ordinal()].get((int) (Math.random() * equipmentsStashes[type.ordinal()].size()));
    }
}
