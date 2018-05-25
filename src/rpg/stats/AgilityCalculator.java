package rpg.stats;

import static rpg.stats.Stats.AGILITY;

public class AgilityCalculator implements StatCalculator {
    @Override
    public double calculate(double stat) {
        return Math.tanh(stat * 0.01);
    }
}
