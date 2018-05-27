package engine;

import engine.crossover.*;
import engine.model.Individual;
import engine.mutation.*;
import engine.selection.*;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class EnginePropertiesManager {

    private Properties prop;

    public EnginePropertiesManager(String enginePropertiesFile) {
        prop = new Properties();
        try {
            prop.load(new FileReader(enginePropertiesFile));
        } catch (IOException e) {
            throw new RuntimeException("Error loading " + enginePropertiesFile + " properties file.");
        }
    }

    public <T extends Individual> Selector<T> getSelector() {
        switch (SelectorMethod.valueOf(retrieveValue(Keys.SELECTOR))) {
            case DETERMINISTIC_TOURNEY:
                return new DeterministicTourneySelector<>(retrieveInt(Keys.PARTICIPANTS));
            case PROBABILISTIC_TOURNEY:
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
            "to see the available options.");
    }

    public Mutator getMutator() {
        switch (MutatorMethod.valueOf(retrieveValue(Keys.MUTATOR))) {
            case UNIFORM:
                return new UniformSinglepointMutator(retrieveDouble(Keys.SP_MUTATOR_PROBABILITY));
            case NON_UNIFORM:
                // TODO variation functions
                return new NonUniformSinglePointMutator(null);
        }
        throw new RuntimeException("Mutation method is not valid. Check the .properties file" +
            "to see the available options.");
    }

    public Crosser getCrosser() {
        switch (CrosserMethod.valueOf(retrieveValue(Keys.CROSSER))) {
            case ANNULAR:
                return new AnnularCrosser();
            case SINGLE_POINT:
                return new SinglePointCrosser();
            case TWO_POINTS:
                return new TwoPointCrosser();
            case UNIFORM:
                return new UniformCrosser(retrieveDouble(Keys.UNIFORM_CROSSER_PROBABILITY));
        }
        throw new RuntimeException("Crossover method is not valid. Check the .properties file" +
            "to see the available options.");
    }

    private String retrieveValue(Keys key) {
        String value = prop.getProperty(key.name());
        if (value == null)
            throw new RuntimeException(key.name() + " key was not found.");
        return value;
    }

    private int retrieveInt(Keys key) {
        try {
            return Integer.parseInt(retrieveValue(key));
        } catch (NumberFormatException e) {
            throw new RuntimeException("Invalid integer for " + key.name() + " key.");
        }
    }

    private double retrieveDouble(Keys key) {
        try {
            return Double.parseDouble(retrieveValue(key));
        } catch (NumberFormatException e) {
            throw new RuntimeException("Invalid double for " + key.name() + " key.");
        }
    }

    private enum Keys {
        SELECTOR,
        MUTATOR,
        CROSSER,
        PARTICIPANTS,
        SP_MUTATOR_PROBABILITY,
        UNIFORM_CROSSER_PROBABILITY
    }

    private enum SelectorMethod {
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

}
