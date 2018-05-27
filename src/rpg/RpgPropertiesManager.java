package rpg;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import static engine.utils.PropertiesManagerUtils.*;
public class RpgPropertiesManager {

    private Properties prop;

    public RpgPropertiesManager(String rpgPropertiesFile) {
        prop = new Properties();
        try {
            FileReader fr = new FileReader(rpgPropertiesFile);
            prop.load(fr);
            fr.close();
        } catch (IOException e) {
            throw new RuntimeException("Error loading " + rpgPropertiesFile + " properties file.");
        }
    }

    public double getStrengthModifier() { return retrieveDouble(Keys.STRENGTH.name(), prop); }

    public double getAgilityModifier() { return retrieveDouble(Keys.AGILITY.name(), prop); }

    public double getExpertiseModifier() { return retrieveDouble(Keys.EXPERTISE.name(), prop); }

    public double getResistanceModifier() { return retrieveDouble(Keys.RESISTANCE.name(), prop); }

    public double getVitalityModifier() { return retrieveDouble(Keys.VITALITY.name(), prop); }

    public String getBootsFileLocation() {
        return getFileLocation(Keys.BOOTS);
    }

    public String getChestPiecesFileLocation() {
        return getFileLocation(Keys.CHEST_PIECES);
    }

    public String getGlovesFileLocation() {
        return getFileLocation(Keys.GLOVES);
    }

    public String getHelmetsFileLocation() {
        return getFileLocation(Keys.HELMETS);
    }

    public String getWeaponsFileLocation() {
        return getFileLocation(Keys.WEAPONS);
    }

    private String getFileLocation(Keys file) {
        return retrieveValue(Keys.ITEMS_FOLDER.name(), prop) + "/" + retrieveValue(file.name(), prop);
    }

    private enum Keys {
        BOOTS,
        CHEST_PIECES,
        GLOVES,
        HELMETS,
        WEAPONS,
        ITEMS_FOLDER,
        STRENGTH,
        AGILITY,
        EXPERTISE,
        RESISTANCE,
        VITALITY
    }
}
