package sokoban;

import ar.com.itba.sia.*;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
        // TODO: read board
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
