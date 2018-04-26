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
            // Read board dimensions
            line = reader.readLine();
            if (line == null) {
                throw new RuntimeException("Missing dimension in input file");
            }
            String[] sDims = line.split(" ");
            if (sDims.length != 2) {
                throw new RuntimeException("Invalid dimension line: " + line);
            }
            int dimX, dimY;
            try {
                dimX = Integer.parseInt(sDims[0]);
            } catch (NumberFormatException e) {
                throw new RuntimeException("Invalid integer for x dimension: " + sDims[0], e);
            }
            if (dimX <= 0) {
                throw new RuntimeException("Dimensions must be positive (x = " + dimX + ")");
            }
            try {
                dimY = Integer.parseInt(sDims[1]);
            } catch (NumberFormatException e) {
                throw new RuntimeException("Invalid integer for y dimension: " + sDims[1], e);
            }
            if (dimY <= 0) {
                throw new RuntimeException("Dimensions must be positive (y = " + dimY + ")");
            }
            builder.setDimentions(dimX, dimY);

            // Read player position
            line = reader.readLine();
            if (line == null) {
                throw new RuntimeException("Missing player position in input file");
            }
            String[] sPos = line.split(" ");
            if (sPos.length != 2) {
                throw new RuntimeException("Invalid dimension line: " + line);
            }
            int playerX, playerY;
            try {
                playerX = Integer.parseInt(sDims[0]);
            } catch (NumberFormatException e) {
                throw new RuntimeException("Invalid integer for player's x position: " + sPos[0], e);
            }
            if (playerX < 0 || playerX >= dimX) {
                throw new RuntimeException("Player's x position is outside the board boundaries");
            }
            try {
                playerY = Integer.parseInt(sPos[1]);
            } catch (NumberFormatException e) {
                throw new RuntimeException("Invalid integer for y dimension: " + sPos[1], e);
            }
            if (playerY < 0 || playerY >= dimY) {
                throw new RuntimeException("Player's y position is outside the board boundaries");
            }
            builder.placePlayer(playerX, playerY);

            for (int i = 0; i < dimX; i++) {
                line = reader.readLine();
                if (line == null) {
                    throw new RuntimeException("Board has fewer lines (" + i + ") than expected (" + dimX + ")");
                }
                if (line.length() != dimY) {
                    throw new RuntimeException("Line " + (i+1) + " has " + line.length() + " characters instead of " + dimY);
                }
                for (int j = 0; j < dimY; j++) {
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
