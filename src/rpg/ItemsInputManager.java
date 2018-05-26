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

    public List<Equipment> readBoots(String file) throws IOException {
        return parseFile(EquipmentType.BOOTS, file);
    }

    public List<Equipment> readHelmets(String file) throws IOException {
        return parseFile(EquipmentType.HELMET, file);
    }

    public List<Equipment> readChestPieces(String file) throws IOException {
        return parseFile(EquipmentType.CHEST_PIECE, file);
    }

    public List<Equipment> readGloves(String file) throws IOException {
        return parseFile(EquipmentType.GLOVES, file);
    }

    public List<Equipment> readWeapons(String file) throws IOException {
        return parseFile(EquipmentType.WEAPON, file);
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
            st.nextToken(); // El item id
            for (int k = 0; k < stats.length; k++) {
                // El enum esta ordenado para que coincida con el orden
                //   en el que vienen los stats en los archivos.
                stats[k] = Double.parseDouble(st.nextToken());
            }
            list.add(new Equipment(stats, type));
        }
        fr.close();
        br.close();
        return list;
    }

}
