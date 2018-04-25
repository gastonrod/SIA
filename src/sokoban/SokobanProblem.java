package sokoban;

import ar.com.itba.sia.*;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class SokobanProblem implements Problem<SokobanState> {

    private SokobanState initialState;

    public SokobanProblem(String initialStateFile) {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(initialStateFile));
        } catch(FileNotFoundException e) {
            throw new IllegalArgumentException("Instantiating SokobanProblem from non-existent file " + initialStateFile, e);
        }
        SokobanState.SokobanBuilder builder = SokobanState.getNewBuilder();
        String line;
        try {
            line = reader.readLine();
            if (line == null) {
                throw new RuntimeException("Missing dimention in input file");
            }
            String[] sDims = line.split(" ");
            if (sDims.length != 2) {
                throw new RuntimeException("Invalid dimention line: " + line);
            }
            int x, y;
            try {
                x = Integer.parseInt(sDims[0]);
            } catch (NumberFormatException e) {
                throw new RuntimeException("Invalid integer for x dimention: " + sDims[0], e);
            }
            if (x <= 0) {
                throw new RuntimeException("Dimentions must be positive (x = " + x + ")");
            }
            try {
                y = Integer.parseInt(sDims[1]);
            } catch (NumberFormatException e) {
                throw new RuntimeException("Invalid integer for y dimention: " + sDims[1], e);
            }
            if (y <= 0) {
                throw new RuntimeException("Dimentions must be positive (y = " + y + ")");
            }
            builder.setDimentions(x, y);
            // TODO: read the rest of the file (the board itself)
        } catch(IOException e) {
            throw new RuntimeException("Something went wrong while reading the input file", e);
        }
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
