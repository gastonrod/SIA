package rpg;

import rpg.items.Equipment;
import rpg.items.EquipmentType;
import rpg.stats.Stats;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class ItemsInputManager {
    private final RpgPropertiesManager prop;
    private final int columnsPerRow = Stats.values().length + 1;

    public ItemsInputManager(RpgPropertiesManager prop) {
        this.prop = prop;
    }

    public List<Equipment> readBoots() {
        return parseFile(EquipmentType.BOOTS, prop.getBootsFileLocation());
    }

    public List<Equipment> readHelmets() {
        return parseFile(EquipmentType.HELMET, prop.getHelmetsFileLocation());
    }

    public List<Equipment> readChestPieces() {
        return parseFile(EquipmentType.CHEST_PIECE, prop.getChestPiecesFileLocation());
    }

    public List<Equipment> readGloves() {
        return parseFile(EquipmentType.GLOVES, prop.getGlovesFileLocation());
    }

    public List<Equipment> readWeapons() {
        return parseFile(EquipmentType.WEAPON, prop.getWeaponsFileLocation());
    }

    private List<Equipment> parseFile(EquipmentType type, String file) {
        List<Equipment> list = new ArrayList<>();
        try (FileReader fr = new FileReader(file);
             BufferedReader br = new BufferedReader(fr)) {
            StringTokenizer st;
            String line;
            int linesCounter = 2;
            while (true) {
                try {
                    line = br.readLine();
                    if (line == null)
                        break;
                } catch (IOException e) {
                    throw new RuntimeException("Error while loading line " + linesCounter + " in file " + file);
                }
                st = new StringTokenizer(line);
                if (st.countTokens() != columnsPerRow)
                    throw new RuntimeException("Line " + linesCounter + " has an invalid amount of columns." +
                        " Found " + st.countTokens() + " and there should be " + columnsPerRow + ".");
                double[] stats = new double[Stats.values().length];
                int id;
                try {
                    id = Integer.parseInt(st.nextToken());
                } catch (NumberFormatException e) {
                    throw new RuntimeException("Invalid integer for item id in line " + linesCounter + ".");
                }
                for (int k = 0; k < stats.length; k++) {
                    try {
                        stats[k] = Double.parseDouble(st.nextToken());
                    } catch (NumberFormatException e) {
                        throw new RuntimeException("Invalid double for item stat in line " + linesCounter + ", column " + (k + 1) + ".");
                    }
                }
                list.add(new Equipment(id, stats, type));
                linesCounter++;
            }
        } catch (IOException e) {
            throw new RuntimeException("Error while loading/closing the file " + file + ".");
        } catch (RuntimeException e) {
            throw e;
        }
        return list;
    }

}
