package rpg.classes;

import engine.FitnessFunction;
import rpg.Fighter;
import rpg.items.Equipment;
import rpg.stats.AttackModifier;
import rpg.stats.DefenseModifier;
import rpg.stats.StatCalculator;
import rpg.stats.Stats;

import static rpg.stats.Stats.*;

class PerformanceFunction implements FitnessFunction<Fighter> {
    private final double[] statModifiers;
    private final Profession profession;
    private final StatCalculator[] calculators;
    private final DefenseModifier defenseModifier;
    private final AttackModifier attackModifier;

    private final double[] stats;

    PerformanceFunction(Profession profession, double[] statModifiers, StatCalculator[] calculators) {
        stats = new double[Stats.values().length];
        this.statModifiers = statModifiers;
        this.profession = profession;
        this.calculators = calculators;
        this.defenseModifier = new DefenseModifier();
        this.attackModifier = new AttackModifier();
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
        return attack(fighter.getHeight()) * profession.attackPerformance + profession.defensePerformance * defense(fighter.getHeight());
    }
}
