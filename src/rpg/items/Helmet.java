package rpg.items;

public class Helmet extends Equipment {

    public Helmet(double[] stats) {
        super(stats);
    }

    @Override
    public EquipmentType getType() {
        return EquipmentType.HELMET;
    }
}
