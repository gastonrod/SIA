package rpg.items;

public class Boots extends Equipment {

    public Boots(double[] stats) {
        super(stats);
    }

    @Override
    public EquipmentType getType() {
        return EquipmentType.BOOTS;
    }
}
