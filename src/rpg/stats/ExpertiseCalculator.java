package rpg.stats;

public class ExpertiseCalculator implements StatCalculator {
    @Override
    public double calculate(double stat) {
        return Math.tanh(stat * 0.01) * 0.6;
    }
}
