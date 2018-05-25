package rpg.items;

public class Gloves extends Equipment {

    public Gloves(double[] stats) {
        super(stats);
    }

    @Override
    public EquipmentType getType() {
        return EquipmentType.GLOVES;
    }
}
