package rpg;

import engine.FitnessFunction;
import engine.model.IndividualManager;
import rpg.classes.PerformanceFunction;
import rpg.classes.Profession;
import rpg.items.Equipment;
import rpg.items.EquipmentStash;
import rpg.stats.StatCalculator;

import java.util.ArrayList;
import java.util.List;

public class FighterManager implements IndividualManager<Fighter> {
    final static double maxHeight = 2.0;
    final static double minHeight = 1.3;

    final private PerformanceFunction fitnessFunction;

    public FighterManager(PerformanceFunction performanceFunction) {
        this.fitnessFunction = performanceFunction;
    }

    @Override
    public List<Fighter> createRandomPopulation(int size) {
        List<Fighter> population = new ArrayList<>();
        double height;
        Equipment[] set;
        for(int i = 0; i < size; i++) {
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
