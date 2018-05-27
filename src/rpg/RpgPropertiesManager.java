package rpg;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class RpgPropertiesManager {

    private Properties prop;

    public RpgPropertiesManager(String rpgPropertiesFile) {
        prop = new Properties();
        try {
            prop.load(new FileReader(rpgPropertiesFile));
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
        return retrieveValue(Keys.ITEMS_FOLDER) + "/" + retrieveValue(file);
    }

    private String retrieveValue(Keys key){
        String value = prop.getProperty(key.name());
        if(value == null)
            throw new RuntimeException(key.name() + " key was not found.");
        return value;
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
