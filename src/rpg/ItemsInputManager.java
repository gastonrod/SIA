package rpg;

import rpg.items.Equipment;
import rpg.items.EquipmentType;
import rpg.stats.Stats;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

public class ItemsInputManager {
    private final PropertiesManager prop;

    public ItemsInputManager(PropertiesManager prop){
        this.prop = prop;
    }

    public List<Equipment> readBoots() throws IOException {
        return parseFile(EquipmentType.BOOTS, prop.getBootsFileLocation());
    }

    public List<Equipment> readHelmets() throws IOException {
        return parseFile(EquipmentType.HELMET, prop.getHelmetsFileLocation());
    }

    public List<Equipment> readChestPieces() throws IOException {
        return parseFile(EquipmentType.CHEST_PIECE, prop.getChestPiecesFileLocation());
    }

    public List<Equipment> readGloves() throws IOException {
        return parseFile(EquipmentType.GLOVES, prop.getGlovesFileLocation());
    }

    public List<Equipment> readWeapons() throws IOException {
        return parseFile(EquipmentType.WEAPON, prop.getWeaponsFileLocation());
    }

    private List<Equipment> parseFile(EquipmentType type, String file) throws IOException {
        List<Equipment> list = new ArrayList<>();
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        StringTokenizer st;
        // Borro la primer linea de como vienen los archivos y pongo la
        //   cantidad de items que tiene el archivo.
        int lines = Integer.parseInt(br.readLine());
        for (int j = 0; j < lines; j++) {
            st = new StringTokenizer(br.readLine());
            double[] stats = new double[Stats.values().length];
            int id = Integer.parseInt(st.nextToken());
            for (int k = 0; k < stats.length; k++) {
                // El enum esta ordenado para que coincida con el orden
                //   en el que vienen los stats en los archivos.
                stats[k] = Double.parseDouble(st.nextToken());
            }
            list.add(new Equipment(id, stats, type));
        }
        fr.close();
        br.close();
        return list;
    }

}
