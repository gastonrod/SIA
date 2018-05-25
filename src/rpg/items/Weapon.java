package rpg.items;

public class Weapon extends Equipment {

    public Weapon(double[] stats) {
        super(stats);
    }

    @Override
    public EquipmentType getType() {
        return EquipmentType.WEAPON;
    }
}
