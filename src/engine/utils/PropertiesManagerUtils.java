package engine.utils;

import java.util.Properties;

public class PropertiesManagerUtils {


    public static double retrievePercentage(String k, Properties prop) {
        double pct = retrieveDouble(k, prop);
        if (pct < 0 || pct > 1) {
            throw new RuntimeException(k + " percentage double must be between 0 and 1.");
        }
        return pct;
    }

    public static String retrieveValue(String key, Properties prop) {
        String value = prop.getProperty(key);
        if (value == null)
            throw new RuntimeException(key + " key was not found.");
        return value;
    }

    public static int retrieveInt(String key, Properties prop) {
        try {
            return Integer.parseInt(retrieveValue(key, prop));
        } catch (NumberFormatException e) {
            throw new RuntimeException("Invalid integer for " + key + " key.");
        }
    }

    public static double retrieveDouble(String key, Properties prop) {
        try {
            return Double.parseDouble(retrieveValue(key, prop));
        } catch (NumberFormatException e) {
            throw new RuntimeException("Invalid double for " + key + " key.");
        }
    }
}
