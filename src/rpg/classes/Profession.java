package rpg.classes;

public enum Profession {
    WARRIOR(0.6, 0.4), ARCHER(0.9, 0.1), DEFENDER(0.1, 0.9), ASSASSIN(0.7, 0.3);

    public final double attackPerformance, defensePeformance;

    Profession(double attackPerformance, double defensePeformance) {
        this.attackPerformance = attackPerformance;
        this.defensePeformance = defensePeformance;
    }
}
