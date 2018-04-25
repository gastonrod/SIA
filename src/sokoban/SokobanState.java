package sokoban;

import ar.com.itba.sia.*;

public class SokobanState {

    public static SokobanBuilder getNewBuilder() {
        return new SokobanBuilder();
    }

    private Element[][] board;

    private SokobanState(Element[][] board) {
        this.board = board;
    }

    public static class SokobanBuilder {

        private boolean dimentionsSet;
        private int dimX, dimY;
        private Element[][] board;
        private int addedElements;
        private boolean guyPlaced;
        private int guyX, guyY;
        private int boxes;
        private int goals;

        private SokobanBuilder() {
            this.dimentionsSet = false;
        }

        public void setDimentions(int dimX, int dimY) {
            if (dimX <= 0 || dimY <= 0) {
                throw new IllegalArgumentException("Invalid dimensions for builder: x = " + dimX + " and y = " + dimY);
            }
            this.dimentionsSet = true;
            this.dimX = dimX;
            this.dimY = dimY;
            this.board = new Element[dimX][dimY];
            this.addedElements = 0;
            this.boxes = 0;
            this.goals = 0;
            this.guyPlaced = false;
        }

        public void setElement(int x, int y, Element element) {
            if (!dimentionsSet) {
                throw new RuntimeException("Trying to set element before setting dimension");
            }
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
            if (!dimentionsSet) {
                throw new RuntimeException("Trying to build state before setting dimension");
            }
            if (addedElements < dimX*dimY) {
                throw new RuntimeException("Attempt to build incomplete board");
            }
            return new SokobanState(board);
        }
    }

}
