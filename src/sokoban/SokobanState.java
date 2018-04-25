package sokoban;

import ar.com.itba.sia.*;

public class SokobanState {

    public static final SokobanBuilder BUILDER = new SokobanBuilder();

    private Element[][] board;

    private SokobanState(Element[][] board) {
        this.board = board;
    }

    public static class SokobanBuilder {

        private int dimX, dimY;
        private Element[][] board;
        private int addedElements;
        private boolean guyPlaced;
        private int boxes;
        private int goals;
        private int guyX;
        private int guyY;

        private SokobanBuilder() {}

        public void setDimentions(int dimX, int dimY) {
            this.dimX = dimX;
            this.dimY = dimY;
            this.board = new Element[dimX][dimY];
            this.addedElements = 0;
            this.boxes = 0;
            this.goals = 0;
            this.guyPlaced = false;
        }

        public void setElement(int x, int y, Element element) {
            if (x < 0 || x >= dimX || y < 0 || y >= dimY) {
                throw new RuntimeException("Adding element to invalid position (" + x + "," + y + ")");
            }
            if (board[x][y] != null) {
                throw new RuntimeException("Adding element " + element + " to occupied square (" + x + "," + y + ")");
            }
            if (element == Element.GUY) {
                if (guyPlaced) {
                    throw new RuntimeException("Adding guy to position (" + x + "," + y + ") when there's one at (" + guyX + "," + guyY + ")");
                } else {
                    guyPlaced = true;
                    guyX = x;
                    guyY = y;
                }
            }
            board[x][y] = element;
            addedElements++;
            if (element == Element.BOX) {
                boxes++;
            } else if (element == Element.GOAL) {
                goals++;
            } else if (element == Element.BOX_AND_GOAL) {
                boxes++;
                goals++;
            }
        }

        public SokobanState build() {
            if (addedElements < dimX*dimY) {
                throw new RuntimeException("Attempt to build incomplete board");
            }
            return new SokobanState(board);
        }
    }

}
