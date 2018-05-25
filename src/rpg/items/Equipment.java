package rpg.items;

import rpg.stats.Stats;

public abstract class Equipment {
    double[] stats;

    protected Equipment(double[] stats){
        this.stats = stats;
    }

    public double getStat(Stats s){
        return stats[s.i];
    }
}
