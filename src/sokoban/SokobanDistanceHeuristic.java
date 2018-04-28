package sokoban;

import ar.com.itba.sia.Heuristic;

public class SokobanDistanceHeuristic implements Heuristic<SokobanState> {

    @Override
    public double getValue(SokobanState sokobanState) {
        int boxes = sokobanState.getBoxes();
        int[] boxX = new int[boxes];
        int[] boxY = new int[boxes];
        int[] goalX = new int[boxes];
        int[] goalY = new int[boxes];
        int boxesFound = 0;
        int goalsFound = 0;
        int dimX = sokobanState.getDimX();
        int dimY = sokobanState.getDimY();
        for (int i = 0; boxesFound < boxes && goalsFound < boxes && i < dimX; i++) {
            for (int j = 0; boxesFound < boxes && goalsFound < boxes && j < dimY; j++) {
                Element element = sokobanState.getElementAt(i, j);
                if (element.hasBox()) {
                    boxX[boxesFound] = i;
                    boxY[boxesFound] = j;
                    boxesFound++;
                }
                if (element.hasGoal()) {
                    boxX[goalsFound] = i;
                    boxY[goalsFound] = j;
                    goalsFound++;
                }
            }
        }
        double result = 0;
        for (int i = 0; i < boxes; i++) {
            int minDistance = 0;
            for (int j = 0; j < boxes; j++) {
                int distance = Math.abs(boxX[i]-goalX[j]) + Math.abs(boxY[i]-goalY[j]);
                minDistance = (minDistance == 0 ? distance : Math.min(distance, minDistance));
            }
            result += minDistance;
        }
        return result;
    }
}
