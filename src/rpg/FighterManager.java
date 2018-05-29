package rpg;

import engine.FitnessFunction;
import engine.model.IndividualManager;
import rpg.classes.PerformanceFunction;
import rpg.items.Equipment;
import rpg.items.EquipmentStash;
import rpg.stats.*;

import java.util.ArrayList;

public class FighterManager implements IndividualManager<Fighter> {

    public final static double MAX_HEIGHT = 2.0;
    public final static double MIN_HEIGHT = 1.3;
    private final static String RPG_PROPERTIES_FILE = "src/rpg/config.properties";
    private EquipmentStash equipmentStash;
    private PerformanceFunction fitnessFunction;
    private RpgPropertiesManager propertiesManager;

    public FighterManager() {
        initialize();
        equipmentStash = new EquipmentStash(propertiesManager);
    }

    @Override
    public void initialize(){
        propertiesManager = new RpgPropertiesManager(RPG_PROPERTIES_FILE);
        double[] statModifiers = new double[Stats.values().length];
        StatCalculator[] calculators = new StatCalculator[Stats.values().length];

        statModifiers[Stats.AGILITY.ordinal()] = propertiesManager.getAgilityModifier();
        statModifiers[Stats.STRENGTH.ordinal()] = propertiesManager.getStrengthModifier();
        statModifiers[Stats.EXPERTISE.ordinal()] = propertiesManager.getExpertiseModifier();
        statModifiers[Stats.RESISTANCE.ordinal()] = propertiesManager.getResistanceModifier();
        statModifiers[Stats.VITALITY.ordinal()] = propertiesManager.getVitalityModifier();

        calculators[Stats.AGILITY.ordinal()] = new AgilityCalculator();
        calculators[Stats.STRENGTH.ordinal()] = new StrengthCalculator();
        calculators[Stats.RESISTANCE.ordinal()] = new ResistanceCalculator();
        calculators[Stats.VITALITY.ordinal()] = new VitalityCalculator();
        calculators[Stats.EXPERTISE.ordinal()] = new ExpertiseCalculator();

        this.fitnessFunction = new PerformanceFunction(statModifiers, calculators, propertiesManager.getAttackPerformanceModifier(), propertiesManager.getDefensePerformanceModifier());
    }

    @Override
    public ArrayList<Fighter> createRandomPopulation(int size) {
        ArrayList<Fighter> population = new ArrayList<>();
        double height;
        Equipment[] set;
        for (int i = 0; i < size; i++) {
            height = (Math.random() * (MAX_HEIGHT - MIN_HEIGHT)) + MIN_HEIGHT;
            set = equipmentStash.generateRandomSet();
            population.add(new Fighter(height, set, equipmentStash));
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
