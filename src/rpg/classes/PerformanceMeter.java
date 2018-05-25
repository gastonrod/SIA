package rpg.classes;

import engine.FitnessFunction;
import static rpg.stats.Stats.*;
import rpg.Fighter;
import rpg.stats.AttackModifier;
import rpg.stats.DefenseModifier;
import rpg.stats.StatCalculator;
import rpg.stats.Stats;
import rpg.items.Equipment;

class PerformanceMeter implements FitnessFunction<Fighter> {
    private final double[] statModifiers;
    private final Profession profession;
    private final StatCalculator[] calculators;
    // Estos dos capaz son un poco overkill
    private final DefenseModifier defenseModifier;
    private final AttackModifier attackModifier;

    private final double[] stats;

    PerformanceMeter(Profession profession, double[] statModifiers, StatCalculator[] calculators,
                     DefenseModifier defenseModifier, AttackModifier attackModifier){
        stats = new double[Stats.values().length];
        this.statModifiers = statModifiers;
        this.profession = profession;
        this.calculators = calculators;
        this.defenseModifier = defenseModifier;
        this.attackModifier = attackModifier;
    }

    protected void calculateStats(Equipment[] equipment){
        double[] rawStats= new double[statModifiers.length];
        for(Equipment e : equipment){
            for(Stats s : Stats.values()){
                rawStats[s.i] += e.getStat(s);
            }
        }
        for(Stats s : Stats.values()){
            stats[s.i] = calculators[s.i].calculate(rawStats[s.i] * statModifiers[s.i]);
        }
    }

    private double attack(double height){
        return (stats[AGILITY.i] + stats[EXPERTISE.i]) * stats[STRENGTH.i] * attackModifier.calculate(height);
    }

    private double defense(double height){
        return (stats[RESISTANCE.i] + stats[EXPERTISE.i]) * stats[VITALITY.i] * defenseModifier.calculate(height);
    }

    @Override
    public double eval(Fighter fighter) {
        calculateStats(fighter.getEquipment());
        return attack(fighter.getHeight()) * profession.attackPerf + profession.defensePefrormance * defense(fighter.getHeight());
    }
}
