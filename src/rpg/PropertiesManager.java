package rpg;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PropertiesManager {
    Properties prop;

    public PropertiesManager(String propertiesFile) {
        prop = new Properties();
        try {
            prop.load(new FileReader(propertiesFile));
        } catch (IOException e) {
            throw new RuntimeException("Error loading " + propertiesFile + " properties file");
        }
    }

    public String getBootsFileLocation() {
        return getFileLocation(Keys.BOOTS.name());
    }

    public String getChestPiecesFileLocation() {
        return getFileLocation(Keys.CHEST_PIECES.name());
    }

    public String getGlovesFileLocation() {
        return getFileLocation(Keys.GLOVES.name());
    }

    public String getHelmetsFileLocation() {
        return getFileLocation(Keys.HELMETS.name());
    }

    public String getWeaponsFileLocation() {
        return getFileLocation(Keys.WEAPONS.name());
    }

    private String getFileLocation(String file) {
        return prop.getProperty(Keys.ITEMS_FOLDER.name()) + "/" + prop.getProperty(file);
    }

    private enum Keys {
        BOOTS,
        CHEST_PIECES,
        GLOVES,
        HELMETS,
        WEAPONS,
        ITEMS_FOLDER;
    }
}
