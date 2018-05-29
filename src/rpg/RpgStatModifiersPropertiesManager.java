package rpg;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import static engine.utils.PropertiesManagerUtils.retrieveDouble;

public class RpgStatModifiersPropertiesManager {

    private Properties prop;

    public RpgStatModifiersPropertiesManager(String rpgPropertiesFile) {
        prop = new Properties();
        try {
            FileReader fr = new FileReader(rpgPropertiesFile);
            prop.load(fr);
            fr.close();
        } catch (IOException e) {
            throw new RuntimeException("Error loading " + rpgPropertiesFile + " properties file.");
        }
    }

    public double getStrengthModifier() {
        return retrieveDouble(Keys.STRENGTH.name(), prop);
    }

    public double getAgilityModifier() {
        return retrieveDouble(Keys.AGILITY.name(), prop);
    }

    public double getExpertiseModifier() {
        return retrieveDouble(Keys.EXPERTISE.name(), prop);
    }

    public double getResistanceModifier() {
        return retrieveDouble(Keys.RESISTANCE.name(), prop);
    }

    public double getVitalityModifier() {
        return retrieveDouble(Keys.VITALITY.name(), prop);
    }

    public double getAttackPerformanceModifier() {
        return retrieveDouble(Keys.ATTACK.name(), prop);
    }

    public double getDefensePerformanceModifier() {
        return retrieveDouble(Keys.DEFENSE.name(), prop);
    }


    private enum Keys {
        STRENGTH,
        AGILITY,
        EXPERTISE,
        RESISTANCE,
        VITALITY,
        ATTACK,
        DEFENSE
    }
}
