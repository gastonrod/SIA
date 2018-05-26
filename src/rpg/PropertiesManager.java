package rpg;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PropertiesManager {
    Properties prop;

    public PropertiesManager(String propertiesFile) throws IOException {
        prop = new Properties();
        prop.load(new FileReader(propertiesFile));
    }

    public String getBootsFileLocation() {
        return getFileLocation(Keys.BOOTS.s);
    }

    public String getChestPiecesFileLocation() {
        return getFileLocation(Keys.CHEST_PIECES.s);
    }

    public String getGlovesFileLocation() {
        return getFileLocation(Keys.GLOVES.s);
    }

    public String getHelmetsFileLocation() {
        return getFileLocation(Keys.HELMETS.s);
    }

    public String getWeaponsFileLocation() {
        return getFileLocation(Keys.WEAPONS.s);
    }

    private String getFileLocation(String file) {
        return prop.getProperty(Keys.ITEMS_FOLDER.s) + "/" + prop.getProperty(file);
    }

    private enum Keys {
        BOOTS("boots"),
        CHEST_PIECES("chestPieces"),
        GLOVES("gloves"),
        HELMETS("helmets"),
        WEAPONS("weapons"),
        ITEMS_FOLDER("itemsFolder");

        protected final String s;

        Keys(String s) {
            this.s = s;
        }
    }
}
