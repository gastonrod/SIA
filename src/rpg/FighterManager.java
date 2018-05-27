package rpg;

import engine.FitnessFunction;
import engine.model.IndividualManager;
import rpg.classes.PerformanceFunction;
import rpg.items.Equipment;
import rpg.items.EquipmentStash;
import rpg.stats.*;

import java.util.ArrayList;
import java.util.List;

public class FighterManager implements IndividualManager<Fighter> {
    final static double maxHeight = 2.0;
    final static double minHeight = 1.3;
    private final static String RPG_PROPERTIES_FILE = "rpg/config.properties";
    final private PerformanceFunction fitnessFunction;

    public FighterManager() {
        RpgPropertiesManager pm = new RpgPropertiesManager(RPG_PROPERTIES_FILE);

        double[] statModifiers = new double[Stats.values().length];
        StatCalculator[] calculators = new StatCalculator[Stats.values().length];

        statModifiers[Stats.AGILITY.ordinal()] = pm.getAgilityModifier();
        statModifiers[Stats.STRENGTH.ordinal()] = pm.getStrengthModifier();
        statModifiers[Stats.EXPERTISE.ordinal()] = pm.getExpertiseModifier();
        statModifiers[Stats.RESISTANCE.ordinal()] = pm.getResistanceModifier();
        statModifiers[Stats.VITALITY.ordinal()] = pm.getVitalityModifier();

        calculators[Stats.AGILITY.ordinal()] = new AgilityCalculator();
        calculators[Stats.STRENGTH.ordinal()] = new StrengthCalculator();
        calculators[Stats.RESISTANCE.ordinal()] = new ResistanceCalculator();
        calculators[Stats.VITALITY.ordinal()] = new VitalityCalculator();
        calculators[Stats.EXPERTISE.ordinal()] = new ExpertiseCalculator();

        this.fitnessFunction = new PerformanceFunction(statModifiers, calculators, pm.getAttackPerformanceModifier(), pm.getDefensePerformanceModifier());
    }

    @Override
    public List<Fighter> createRandomPopulation(int size) {
        List<Fighter> population = new ArrayList<>();
        double height;
        Equipment[] set;
        for (int i = 0; i < size; i++) {
            height = (Math.random() * (maxHeight - minHeight)) + minHeight;
            set = EquipmentStash.generateRandomSet();
            population.add(new Fighter(height, set));
        }
        return population;
    }

    @Override
    public FitnessFunction<Fighter> getFitnessFunction() {
        return fitnessFunction;
    }

    @Override
    public double getOptimalFitness() {
        return fitnessFunction.getOptimalPerformance();
    }
}
