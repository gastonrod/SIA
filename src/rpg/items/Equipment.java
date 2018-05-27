package rpg.items;

import rpg.stats.Stats;

public class Equipment {
    private final int id;
    private final double[] stats;
    private final EquipmentType type;

    public Equipment(int id, double[] stats, EquipmentType type) {
        this.id = id;
        this.type = type;
        this.stats = stats;
    }

    public double getStat(Stats s) {
        return stats[s.ordinal()];
    }

    public EquipmentType getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "[" + type.name() + ", " + id + /*" , " + Arrays.toString(stats) + */"]";
    }

}
