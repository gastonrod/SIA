package rpg.items;

import rpg.stats.Stats;

public class Equipment {
    private final double[] stats;
    private final EquipmentType type;

    protected Equipment(double[] stats, EquipmentType type) {
        this.type = type;
        this.stats = stats;
    }

    public double getStat(Stats s) {
        return stats[s.i];
    }

    public EquipmentType getType(){ return type; }

}
