package rpg.stats;

public class DefenseModifier implements StatCalculator {
    @Override
    public double calculate(double height) {
        return 2 + Math.pow((3 * height - 5), 4) - Math.pow((3 * height - 5), 2) - height / 2;
    }
}
