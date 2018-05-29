package rpg.classes;

import engine.FitnessFunction;
import rpg.Fighter;
import rpg.items.Equipment;
import rpg.stats.AttackModifier;
import rpg.stats.DefenseModifier;
import rpg.stats.StatCalculator;
import rpg.stats.Stats;

import static rpg.stats.Stats.*;

public class PerformanceFunction implements FitnessFunction<Fighter> {

    private final double[] statModifiers;
    private final double attackPerformanceModifier;
    private final double defensePerformanceModifier;
    private final StatCalculator[] calculators;
    private final DefenseModifier defenseModifier;
    private final AttackModifier attackModifier;

    private final double[] stats;

    public PerformanceFunction(double[] statModifiers, StatCalculator[] calculators, double attackPerformanceModifier, double defensePerformanceModifier) {
        stats = new double[Stats.values().length];
        this.statModifiers = statModifiers;
        this.calculators = calculators;
        this.defenseModifier = new DefenseModifier();
        this.attackModifier = new AttackModifier();
        this.attackPerformanceModifier = attackPerformanceModifier;
        this.defensePerformanceModifier = defensePerformanceModifier;
    }

    private void calculateStats(Equipment[] equipment) {
        double[] rawStats = new double[statModifiers.length];
        for (Equipment e : equipment) {
            for (Stats s : Stats.values()) {
                rawStats[s.ordinal()] += e.getStat(s);
            }
        }
        for (Stats s : Stats.values()) {
            stats[s.ordinal()] = calculators[s.ordinal()].calculate(rawStats[s.ordinal()] * statModifiers[s.ordinal()]);
        }
    }

    private double attack(double height) {
        return (stats[AGILITY.ordinal()] + stats[EXPERTISE.ordinal()]) * stats[STRENGTH.ordinal()] * attackModifier.calculate(height);
    }

    private double defense(double height) {
        return (stats[RESISTANCE.ordinal()] + stats[EXPERTISE.ordinal()]) * stats[VITALITY.ordinal()] * defenseModifier.calculate(height);
    }

    @Override
    public double eval(Fighter fighter) {
        calculateStats(fighter.getEquipment());
        return attack(fighter.getHeight()) * attackPerformanceModifier + defensePerformanceModifier * defense(fighter.getHeight());
    }

    public double eval(double height, double[] stats) {
        for (int i = 0; i < stats.length; i++) {
            this.stats[i] = calculators[i].calculate(stats[i] * statModifiers[i]);
        }
        return attack(height) * attackPerformanceModifier + defensePerformanceModifier * defense(height);
    }
}
