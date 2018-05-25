package rpg.items;

import rpg.Stats;

public class Equipment {
    double[] stats;

    public double getStat(int i){
        return stats[Stats.values()[i].ordinal()];
    }
}
