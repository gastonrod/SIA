package rpg.items;

import rpg.ItemsInputManager;
import rpg.RpgPropertiesManager;

import java.util.ArrayList;

public class EquipmentStash {
    private ArrayList<ArrayList<Equipment>> equipmentsStashes;

    public EquipmentStash(RpgPropertiesManager prop) {
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
}
