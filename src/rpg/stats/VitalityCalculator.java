package rpg.stats;

public class VitalityCalculator implements StatCalculator {
    @Override
    public double calculate(double stat) {
        return Math.tanh(stat * 0.01) * 100;
    }
}
