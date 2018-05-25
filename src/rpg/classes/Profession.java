package rpg.classes;

import engine.FitnessFunction;
import static rpg.Stats.*;
import rpg.Fighter;
import rpg.Stats;
import rpg.items.Equipment;

abstract class Profession implements FitnessFunction<Fighter> {
    private double[] statModifiers;
    protected double height;
    protected double[] stats;
    protected Equipment[] equipment;


    protected Profession(double agi, double ex, double res, double str, double vit){
        statModifiers = new double[values().length];
        statModifiers[AGILITY.i] = agi;
        statModifiers[EXPERTISE.i] = ex;
        statModifiers[RESISTANCE.i] = res;
        statModifiers[STRENGTH.i] = str;
        statModifiers[VITALITY.i] = vit;
    }


    protected void calculateStats(){
        double[] rawStats= new double[statModifiers.length];
        for(Equipment e : equipment){
            for(Stats s : Stats.values()){
                rawStats[s.i] += e.getStat(s.i);
            }
        }
        stats[STRENGTH.i] = calculateStrength(rawStats[STRENGTH.i]);
        stats[AGILITY.i] = calculateAgility(rawStats[AGILITY.i]);
        stats[EXPERTISE.i] = calculateExpertise(rawStats[EXPERTISE.i]);
        stats[RESISTANCE.i] = calculateResistance(rawStats[RESISTANCE.i]);
        stats[VITALITY.i] = calculateVitality(rawStats[VITALITY.i]);
    }


    protected double attack(){
        return (stats[AGILITY.i] + stats[EXPERTISE.i]) * stats[STRENGTH.i] * attackModifier();
    }

    protected double defense(){
        return (stats[RESISTANCE.i] + stats[EXPERTISE.i]) * stats[VITALITY.i] * defenseModifier();
    }

    private double attackModifier(){
        return 0.5 - Math.pow((3* height - 5), 4) + Math.pow((3 * height - 5), 2) + height / 2;
    }

    private double defenseModifier(){
        return 2 + Math.pow((3 * height - 5), 4) - Math.pow((3 * height - 5), 2) - height / 2;
    }
    private double calculateStrength(double str){
        return 100 * Math.tanh(str * 0.01 * statModifiers[STRENGTH.i]);
    }
    private double calculateAgility(double agi){
        return Math.tanh(agi * 0.01 * statModifiers[AGILITY.i]);
    }

    private double calculateExpertise(double exp){
        return 0.6 * Math.tanh(exp * 0.01 * statModifiers[EXPERTISE.i]);
    }

    private double calculateResistance(double res){
        return Math.tanh(res * 0.01 * statModifiers[RESISTANCE.i]);
    }

    private double calculateVitality(double vit){
        return 100 * Math.tanh(vit * 0.01 * statModifiers[VITALITY.i]);
    }
}
