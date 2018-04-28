package sokoban;

import ar.com.itba.sia.*;

public class SokobanPlacedBoxesHeuristic implements Heuristic<SokobanState> {

    @Override
    public double getValue(SokobanState sokobanState) {
        return sokobanState.getBoxes() - sokobanState.getPlacedBoxes();
    }
}
