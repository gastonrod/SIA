package rpg.classes;

import engine.FitnessFunction;
import rpg.Fighter;
import rpg.items.Equipment;
import rpg.stats.AttackModifier;
import rpg.stats.DefenseModifier;
import rpg.stats.StatCalculator;
import rpg.stats.Stats;

import static rpg.FighterManager.MAX_HEIGHT;
import static rpg.FighterManager.MIN_HEIGHT;
import static rpg.stats.Stats.*;

public class PerformanceFunction implements FitnessFunction<Fighter> {
    private static final int MAX_ATTACK_BOOST = 160;
    private static final int MAX_DEFENCE_BOOST = 160;
    private static final double HEIGHT_STEP = 0.01;

    private final double[] statModifiers;
    private final double attackPerformanceModifier;
    private final double defensePerformanceModifier;
    private final StatCalculator[] calculators;
    private final DefenseModifier defenseModifier;
    private final AttackModifier attackModifier;
    private final double optimalPerformance;

    private final double[] stats;

    public PerformanceFunction(double[] statModifiers, StatCalculator[] calculators, double attackPerformanceModifier, double defensePerformanceModifier) {
        stats = new double[Stats.values().length];
        this.statModifiers = statModifiers;
        this.calculators = calculators;
        this.defenseModifier = new DefenseModifier();
        this.attackModifier = new AttackModifier();
        this.attackPerformanceModifier = attackPerformanceModifier;
        this.defensePerformanceModifier = defensePerformanceModifier;

        double auxOptimalPerformance = 0;
        for (double h = MIN_HEIGHT; h < MAX_HEIGHT; h += HEIGHT_STEP) {
            double auxPerformance = attackPerformanceModifier * MAX_ATTACK_BOOST * attackModifier.calculate(h)
                    + defensePerformanceModifier * MAX_DEFENCE_BOOST * defenseModifier.calculate(h);
            if (auxPerformance > auxOptimalPerformance) {
                auxOptimalPerformance = auxPerformance;
            }
        }
        optimalPerformance = auxOptimalPerformance;
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

    public double getOptimalPerformance() {
        return optimalPerformance;
    }

    @Override
    public double eval(Fighter fighter) {
        calculateStats(fighter.getEquipment());
        return attack(fighter.getHeight()) * attackPerformanceModifier + defensePerformanceModifier * defense(fighter.getHeight());
    }
}
