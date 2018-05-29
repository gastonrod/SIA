package rpg;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import static engine.utils.PropertiesManagerUtils.retrieveDouble;
import static engine.utils.PropertiesManagerUtils.retrieveValue;

public class RpgFilesLocationPropertiesManager {

    private Properties prop;

    public RpgFilesLocationPropertiesManager(String rpgPropertiesFile) {
        prop = new Properties();
        try {
            FileReader fr = new FileReader(rpgPropertiesFile);
            prop.load(fr);
            fr.close();
        } catch (IOException e) {
            throw new RuntimeException("Error loading " + rpgPropertiesFile + " properties file.");
        }
    }

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
        ITEMS_FOLDER
    }
}
