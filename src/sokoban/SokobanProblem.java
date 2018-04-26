package sokoban;

import ar.com.itba.sia.*;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SokobanProblem implements Problem<SokobanState> {

    private SokobanState initialState;
    private Map<Character, Element> characterElementMap;

    public SokobanProblem(String initialStateFile) {
        buildCharacterElementMap();
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(initialStateFile));
        } catch(FileNotFoundException e) {
            throw new IllegalArgumentException("Instantiating SokobanProblem from non-existent file '" + initialStateFile + "'", e);
        }
        SokobanState.SokobanBuilder builder = SokobanState.getNewBuilder();
        String line;
        try {
            line = reader.readLine();
            if (line == null) {
                throw new RuntimeException("Missing dimension in input file");
            }
            String[] sDims = line.split(" ");
            if (sDims.length != 2) {
                throw new RuntimeException("Invalid dimension line: " + line);
            }
            int x, y;
            try {
                x = Integer.parseInt(sDims[0]);
            } catch (NumberFormatException e) {
                throw new RuntimeException("Invalid integer for x dimension: " + sDims[0], e);
            }
            if (x <= 0) {
                throw new RuntimeException("Dimensions must be positive (x = " + x + ")");
            }
            try {
                y = Integer.parseInt(sDims[1]);
            } catch (NumberFormatException e) {
                throw new RuntimeException("Invalid integer for y dimension: " + sDims[1], e);
            }
            if (y <= 0) {
                throw new RuntimeException("Dimensions must be positive (y = " + y + ")");
            }
            builder.setDimentions(x, y);
            for (int i = 0; i < x; i++) {
                line = reader.readLine();
                if (line == null) {
                    throw new RuntimeException("Board has fewer lines (" + i + ") than expected (" + x + ")");
                }
                if (line.length() != y) {
                    throw new RuntimeException("Line " + (i+1) + " has " + line.length() + " characters instead of " + y);
                }
                for (int j = 0; j < y; j++) {
                    char c = line.charAt(j);
                    Element element = characterElementMap.get(c);
                    if (element == null) {
                        throw new RuntimeException("Invalid element " + c);
                    }
                    builder.setElement(i, j, element);
                }
            }
            line = reader.readLine();
            if (line != null) {
                throw new RuntimeException("Extra line found");
            }
        } catch(IOException e) {
            throw new RuntimeException("Something went wrong while reading the input file", e);
        }
        initialState = builder.build();
    }

    private void buildCharacterElementMap() {
        characterElementMap = new HashMap<>();
        characterElementMap.put(' ', Element.EMPTY);
        characterElementMap.put('p', Element.PLAYER);
        characterElementMap.put('b', Element.BOX);
        characterElementMap.put('g', Element.GOAL);
        characterElementMap.put('x', Element.BOX_AND_GOAL);
        characterElementMap.put('w', Element.WALL);
    }

    @Override
    public SokobanState getInitialState() {
        return initialState;
    }

    @NotNull
    @Override
    public List<Rule<SokobanState>> getRules(SokobanState sokobanState) {
        return null;
    }

    @Override
    public boolean isResolved(SokobanState sokobanState) {
        return false;
    }
}
