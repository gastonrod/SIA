package engine;

import engine.breaking.Breaker;
import engine.breaking.FitnessBreaker;
import engine.breaking.GenerationBreaker;
import engine.crossover.*;
import engine.model.Individual;
import engine.mutation.Mutator;
import engine.mutation.NonUniformSinglePointMutator;
import engine.mutation.UniformSinglepointMutator;
import engine.selection.*;
import engine.utils.PropertiesManagerUtils;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import static engine.utils.PropertiesManagerUtils.*;

public class EnginePropertiesManager {

    private Properties prop;

    public EnginePropertiesManager(String enginePropertiesFile) {
        prop = new Properties();
        try {
            FileReader fr = new FileReader(enginePropertiesFile);
            prop.load(fr);
            fr.close();
        } catch (IOException e) {
            throw new RuntimeException("Error loading " + enginePropertiesFile + " properties file.");
        }
    }

    public <T extends Individual> Selector<T> getFirstSelector() {
        return getSelector(Keys.FIRST_SELECTOR);
    }

    public <T extends Individual> Selector<T> getSecondSelector() {
        return getSelector(Keys.SECOND_SELECTOR);
    }

    private <T extends Individual> Selector<T> getSelector(Keys key) {
        switch (SelectorMethod.valueOf(PropertiesManagerUtils.retrieveValue(key.name(), prop))) {
            case BOLTZMANN:
                return new BoltzmannSelector<>();
            case DETERMINISTIC_TOURNEY:
                int popSize = retrieveInt(Keys.POP_SIZE.name(), prop);
                int participantsPerDuel = retrieveInt(Keys.PARTICIPANTS.name(), prop);
                if (popSize < participantsPerDuel) {
                    throw new IllegalArgumentException("Participants per duel (" + participantsPerDuel + ") is larger than population size (" + popSize + ")");
                }
                return new DeterministicTourneySelector<>(participantsPerDuel);
            case PROBABILISTIC_TOURNEY:
                popSize = retrieveInt(Keys.POP_SIZE.name(), prop);
                if (!ProbabilisticTourneySelector.isValidPopulationSize(popSize)) {
                    throw new IllegalArgumentException("Invalid population size (" + popSize + ") for probabilistic tourney selector");
                }
                return new ProbabilisticTourneySelector<>();
            case ELITE:
                return new EliteSelector<>();
            case RANKING:
                return new RankingSelector<>();
            case ROULETTE:
                return new RouletteSelector<>();
            case UNIVERSAL:
                return new UniversalSelector<>();
        }
        throw new RuntimeException("Selection method is not valid. Check the .properties file" +
            " to see the available options.");
    }

    public <T extends Individual> Mutator<T> getMutator() {
        switch (MutatorMethod.valueOf(retrieveValue(Keys.MUTATOR.name(), prop))) {
            case UNIFORM:
                return new UniformSinglepointMutator<>(retrieveDouble(Keys.MUTATOR_PROBABILITY.name(), prop));
            case NON_UNIFORM:
                return new NonUniformSinglePointMutator<>();
        }
        throw new RuntimeException("Mutation method is not valid. Check the .properties file" +
            " to see the available options.");
    }

    public <T extends Individual> Crosser<T> getCrosser() {
        switch (CrosserMethod.valueOf(retrieveValue(Keys.CROSSER.name(), prop))) {
            case ANNULAR:
                return new AnnularCrosser<>();
            case SINGLE_POINT:
                return new SinglePointCrosser<>();
            case TWO_POINTS:
                return new TwoPointCrosser<>();
            case UNIFORM:
                return new UniformCrosser<>(retrieveDouble(Keys.UNIFORM_CROSSER_PROBABILITY.name(), prop));
        }
        throw new RuntimeException("Crossover method is not valid. Check the .properties file" +
            " to see the available options.");
    }

    public <T extends Individual> Breaker<T> getBreaker() {
        switch (BreakerMethod.valueOf(retrieveValue(Keys.BREAKER.name(), prop))) {
            case GENERATION:
                return new GenerationBreaker<>(retrieveInt(Keys.MAX_GENERATIONS.name(), prop));
            case OPTIMAL:
                return new FitnessBreaker<>(retrieveDouble(Keys.THRESHOLD.name(), prop));
            case STRUCTURE:
                break;
            case CONTENT:
                break;
        }
        throw new RuntimeException("Breaker method is not valid. Check the .properties file" +
            " to see the available options.");
    }

    public int getPopulationSize() {
        return retrieveInt(Keys.POP_SIZE.name(), prop);
    }

    public double getGenerationalGap() {
        return retrieveDouble(Keys.GENERATIONAL_GAP.name(), prop);
    }

    public double getFirstSelectorPercentage() {
        return retrievePercentage(Keys.FIRST_SELECTOR_PCT.name(), prop);
    }

    public double getFirstReplacerPercentage() {
        return retrievePercentage(Keys.FIRST_REPLACER_PCT.name(), prop);
    }


    private enum Keys {
        FIRST_SELECTOR,
        SECOND_SELECTOR,
        MUTATOR,
        CROSSER,
        PARTICIPANTS,
        BREAKER,
        MUTATOR_PROBABILITY,
        UNIFORM_CROSSER_PROBABILITY,
        GENERATIONAL_GAP,
        POP_SIZE,
        FIRST_SELECTOR_PCT,
        FIRST_REPLACER_PCT,
        MAX_GENERATIONS,
        THRESHOLD
    }

    private enum SelectorMethod {
        BOLTZMANN,
        DETERMINISTIC_TOURNEY,
        PROBABILISTIC_TOURNEY,
        ELITE,
        RANKING,
        ROULETTE,
        UNIVERSAL
    }

    private enum MutatorMethod {
        UNIFORM,
        NON_UNIFORM
    }

    private enum CrosserMethod {
        ANNULAR,
        SINGLE_POINT,
        TWO_POINTS,
        UNIFORM
    }

    private enum BreakerMethod {
        GENERATION,
        OPTIMAL,
        STRUCTURE,
        CONTENT
    }

}
