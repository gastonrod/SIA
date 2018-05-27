package engine;

import engine.breaking.Breaker;
import engine.crossover.Crosser;
import engine.model.IndividualManager;
import engine.model.Pair;
import engine.mutation.Mutator;
import engine.replacement.SimpleReplacer;
import engine.selection.Selector;
import rpg.Fighter;
import rpg.FighterManager;

import java.util.ArrayList;
import java.util.Iterator;

public class Breeder {

    private static final String ENGINE_PROPERTIES_FILE = "src/engine/config.properties";

    public static void main(String[] args) {

        try {
            EnginePropertiesManager enginePropertiesManager = new EnginePropertiesManager(ENGINE_PROPERTIES_FILE);
            //Selector<Fighter> mixSelector = new MixSelector<>(enginePropertiesManager.getFirstSelector(), enginePropertiesManager.getSecondSelector(), enginePropertiesManager.getFirstSelectorPercentage());
            Selector<Fighter> mixSelector = enginePropertiesManager.getFirstSelector();
            Crosser<Fighter> crosser = enginePropertiesManager.getCrosser();
            Mutator<Fighter> mutator = enginePropertiesManager.getMutator();
            //Replacer<Fighter> firstReplacer = enginePropertiesManager.getFirstReplacer();
            //Replacer<Fighter> secondReplacer = enginePropertiesManager.getSecondReplacer();
            double firstReplacerPercentage = enginePropertiesManager.getFirstReplacerPercentage();
            int populationSize = enginePropertiesManager.getPopulationSize();
            double generationalGap = enginePropertiesManager.getGenerationalGap();
            int k = (int) Math.round(populationSize * generationalGap);
            Breaker<Fighter> breaker = enginePropertiesManager.getBreaker();

            IndividualManager<Fighter> individualManager = new FighterManager();

            ArrayList<Fighter> population = individualManager.createRandomPopulation(populationSize);
            int generation;
            Pair<Fighter> toCross = new Pair<>();
            System.out.println("Initial population");
            System.out.println(population.toString());
            for (generation = 0; !breaker.shouldBreak(population, generation); generation++) {
                // Selection
                ArrayList<Fighter> selected = mixSelector.select(population, k, individualManager.getFitnessFunction(), generation);
                // Crossover
                ArrayList<Fighter> crossed = new ArrayList<>(k);
                Iterator<Fighter> selectedIterator = selected.iterator();
                while (selectedIterator.hasNext()) {
                    toCross.first = selectedIterator.next();
                    if (selectedIterator.hasNext()) {
                        toCross.second = selectedIterator.next();
                        Pair<Fighter> offspring = crosser.cross(toCross);
                        crossed.add(offspring.first);
                        crossed.add(offspring.second);
                    } else {
                        crossed.add(toCross.first);
                    }
                }
                // Mutation
                for (Fighter f : crossed) {
                    mutator.mutate(f, generation);
                }
                // Replacement
                new SimpleReplacer<Fighter>().replace(population, crossed);
                System.out.println(population);
            }
            System.out.println("Llegamos sanos y salvos");
        } catch (Exception e) {
            System.out.println("Falló");
            System.out.println(e.getMessage());
            throw e;
        }

    }


}
